# std::filesystem

No CVE exists for `std::filesystem::canonical` itself — it's a standard library function, not a product, so CVEs get filed against things that *misuse* it. But it's a real source of vulnerabilities in practice.

**Real attack vectors:**

1. **TOCTOU (time-of-check-to-time-of-use) race**: `canonical()` resolves the path at call time. Between that call and when you actually open/use the file, an attacker can swap a symlink to point elsewhere. Classic race condition — resolving isn't atomic with the actual file operation.

2. **Path traversal / incomplete validation**: A common broken pattern is calling `canonical()` on user input *before* checking it's within a base directory, or using string prefix checks instead of proper path comparison after canonicalization. CVE-2022-25299 (Mongoose), CVE-2021-23514 (Crow), and CVE-2022-25298 (Webcc) are real C++ path traversal vulnerabilities found by Snyk researchers in projects using this kind of pattern incorrectly.

3. **`canonical()` throws if the path doesn't exist** — if you canonicalize user input before validating existence, you get an exception-based oracle (can leak info about what exists on the filesystem via error/timing differences). Use `weakly_canonical()` when the target may not exist yet.

4. **Symlink resolution is exactly the attack surface**: canonical resolves ".", "..", and symlinks — that's necessary for correct traversal prevention, but it also means the resolved path depends on live filesystem state (mounts, symlinks) at call time, not just the string.

**The safe pattern** (from Snyk's writeup): canonicalize your base path once, canonicalize `base / user_input` with `weakly_canonical`, then check the result is still prefixed by the canonical base — using `std::filesystem::path` comparison, not naive string `starts_with` (which fails on things like `/base` vs `/base-evil`).

---

Component-wise containment check + post-open verification. String prefix checks are broken (`/base` matches `/base-evil`); this uses path iterators.

```cpp
#include <filesystem>
#include <system_error>
#include <optional>

namespace fs = std::filesystem;

// Returns resolved path if it's safely contained within base_dir, else nullopt.
// base_dir must already exist.
std::optional<fs::path> safe_resolve(const fs::path& base_dir,
                                      const fs::path& user_input) {
    std::error_code ec;

    // Reject absolute paths outright — user_input should always be relative.
    if (user_input.is_absolute()) {
        return std::nullopt;
    }

    fs::path base_canonical = fs::canonical(base_dir, ec);
    if (ec) return std::nullopt; // base itself invalid — fail closed

    fs::path candidate = fs::weakly_canonical(base_canonical / user_input, ec);
    if (ec) return std::nullopt;

    // Component-wise prefix check, NOT string prefix check.
    auto base_it  = base_canonical.begin();
    auto cand_it  = candidate.begin();
    for (; base_it != base_canonical.end(); ++base_it, ++cand_it) {
        if (cand_it == candidate.end() || *cand_it != *base_it) {
            return std::nullopt; // escaped base_dir
        }
    }

    return candidate;
}
```

**Why each piece matters:**

- `is_absolute()` reject — otherwise `user_input = "/etc/passwd"` bypasses `base_dir` entirely since `path / absolute_path` discards the left side. This is the #1 way people get bitten.
- `fs::canonical` on `base_dir` (must exist) vs `fs::weakly_canonical` on the joined candidate (may not exist yet, e.g. writing a new Maildir file).
- Component iteration, not `candidate.string().starts_with(base.string())` — the string version lets `/var/mail` match `/var/mailicious`.
- Fail closed on any `error_code` — don't let a canonicalization error fall through to "assume safe."

**What this does NOT fix — TOCTOU:**

`weakly_canonical` resolves against the filesystem *at call time*. Between this check and your actual `open()`, an attacker with write access to any intermediate directory in the path can swap a symlink and redirect you outside `base_dir`. For vibemail's Maildir writes, this matters if any path component is attacker-influenced and the process doesn't own every directory in the chain.

Real fix for that is race-free traversal with `openat(2)` + `O_NOFOLLOW` per component (reject if any component is a symlink), or open with `O_NOFOLLOW | O_DIRECTORY` walking one segment at a time, rather than trusting a single resolved path string. 

---

Race-free path resolution using `openat` + `O_NOFOLLOW` per component. No single resolved-string check can be race-free — the kernel has to refuse the symlink at each step, atomically, as part of the actual open.

```cpp
// safe_path_open.hpp
#pragma once

#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>
#include <string>
#include <string_view>
#include <vector>
#include <optional>

class safe_fd {
public:
    safe_fd() noexcept : fd_(-1) {}
    explicit safe_fd(int fd) noexcept : fd_(fd) {}
    safe_fd(const safe_fd&) = delete;
    safe_fd& operator=(const safe_fd&) = delete;
    safe_fd(safe_fd&& other) noexcept : fd_(other.fd_) { other.fd_ = -1; }
    safe_fd& operator=(safe_fd&& other) noexcept {
        if (this != &other) { reset(); fd_ = other.fd_; other.fd_ = -1; }
        return *this;
    }
    ~safe_fd() { reset(); }

    int get() const noexcept { return fd_; }
    int release() noexcept { int f = fd_; fd_ = -1; return f; }
    void reset(int fd = -1) noexcept {
        if (fd_ >= 0) ::close(fd_);
        fd_ = fd;
    }
    explicit operator bool() const noexcept { return fd_ >= 0; }

private:
    int fd_;
};

// Rejects: absolute paths, "..", ".", empty segments, embedded NUL.
// Anything not on this allowlist path shape returns nullopt.
inline std::optional<std::vector<std::string>>
split_untrusted_relpath(std::string_view input) {
    if (input.empty() || input.front() == '/') return std::nullopt;
    if (input.find('\0') != std::string_view::npos) return std::nullopt;

    std::vector<std::string> parts;
    size_t start = 0;
    while (start <= input.size()) {
        size_t slash = input.find('/', start);
        std::string_view part = (slash == std::string_view::npos)
            ? input.substr(start)
            : input.substr(start, slash - start);

        if (part.empty() || part == "." || part == "..") {
            return std::nullopt;
        }
        parts.emplace_back(part);

        if (slash == std::string_view::npos) break;
        start = slash + 1;
    }
    return parts;
}

// base_fd: already-open, trusted directory fd — opened once at startup
// from a path YOU control, never from attacker input.
// untrusted_relpath: the attacker-influenced part (mailbox name, etc).
// final_flags: e.g. O_RDONLY, or O_WRONLY|O_CREAT|O_EXCL for new mail files.
//
// Every intermediate component is opened with O_DIRECTORY|O_NOFOLLOW, so
// if any path segment is (or becomes) a symlink, the open fails instead
// of following it. This closes the TOCTOU window that a single
// canonical()/weakly_canonical() check leaves open, because there is no
// separate "check" step — the refusal happens inside the same syscall
// that does the traversal.
inline safe_fd open_within(int base_fd,
                            std::string_view untrusted_relpath,
                            int final_flags,
                            mode_t create_mode = 0600,
                            bool allow_leaf_symlink = false) {
    auto parts = split_untrusted_relpath(untrusted_relpath);
    if (!parts) return safe_fd{};

    int dup_fd = ::dup(base_fd);
    if (dup_fd < 0) return safe_fd{};
    safe_fd cur(dup_fd);

    for (size_t i = 0; i < parts->size(); ++i) {
        bool is_leaf = (i + 1 == parts->size());
        const std::string& comp = (*parts)[i];

        int flags = is_leaf
            ? (final_flags | O_CLOEXEC | (allow_leaf_symlink ? 0 : O_NOFOLLOW))
            : (O_DIRECTORY | O_NOFOLLOW | O_CLOEXEC | O_RDONLY);

        int next_fd = ::openat(cur.get(), comp.c_str(), flags, create_mode);
        if (next_fd < 0) return safe_fd{};

        struct stat st{};
        if (::fstat(next_fd, &st) != 0) {
            ::close(next_fd);
            return safe_fd{};
        }
        if (!is_leaf && !S_ISDIR(st.st_mode)) {
            ::close(next_fd);
            return safe_fd{};
        }
        if (is_leaf && !allow_leaf_symlink && S_ISLNK(st.st_mode)) {
            ::close(next_fd);
            return safe_fd{};
        }

        cur.reset(next_fd);
    }

    return cur;
}
```

**Usage for a Maildir-style store:**

```cpp
int maildir_base = ::open("/var/vibemail/maildir", O_DIRECTORY | O_CLOEXEC);
// maildir_base opened once, from a trusted config path, never from network input.

safe_fd f = open_within(maildir_base,
                         user_mailbox + "/new/" + generated_filename,
                         O_WRONLY | O_CREAT | O_EXCL);
if (!f) {
    // reject — either escape attempt, symlink, or legitimate error
}
```

**What this guarantees vs. the `canonical`/`weakly_canonical` approach:**

| | string/path check | this |
|---|---|---|
| `../../etc/passwd` | blocked (rejected before syscall) | blocked (rejected before syscall) |
| symlink swapped in *after* the check, *before* the open | **vulnerable** | blocked — no separate check exists to race |
| symlink swapped mid-traversal (one dir deep is later replaced) | vulnerable | blocked per-component |
| absolute path in input | must remember to check | rejected by `split_untrusted_relpath` |

**Caveats:**
- POSIX-only (fine for Artix/Linux; won't compile on Windows).
- Doesn't defend against hardlink attacks the way symlink defense does — if that matters for your threat model (multi-user box, shared writable dirs), also check `st_nlink == 1` on the leaf before trusting it's not a hardlink alias.
- If different components can live on different mounts and that matters to you, add a `st_dev` continuity check across the walk.

---

The strongest practical version: `openat2(RESOLVE_NO_SYMLINKS | RESOLVE_BENEATH)` as the primary path (kernel-atomic, closes the whole class of TOCTOU races in one syscall), falling back to the manual per-component walker for older kernels, plus the additional checks that matter: hardlink aliasing, mount-crossing, resource-exhaustion via pathological input, and setuid/setgid paranoia.

```cpp
// safe_path_open.hpp
#pragma once

#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/syscall.h>
#include <linux/openat2.h>
#include <string>
#include <string_view>
#include <vector>
#include <optional>
#include <cstring>

class safe_fd {
public:
    safe_fd() noexcept : fd_(-1) {}
    explicit safe_fd(int fd) noexcept : fd_(fd) {}
    safe_fd(const safe_fd&) = delete;
    safe_fd& operator=(const safe_fd&) = delete;
    safe_fd(safe_fd&& o) noexcept : fd_(o.fd_) { o.fd_ = -1; }
    safe_fd& operator=(safe_fd&& o) noexcept {
        if (this != &o) { reset(); fd_ = o.fd_; o.fd_ = -1; }
        return *this;
    }
    ~safe_fd() { reset(); }
    int get() const noexcept { return fd_; }
    int release() noexcept { int f = fd_; fd_ = -1; return f; }
    void reset(int fd = -1) noexcept { if (fd_ >= 0) ::close(fd_); fd_ = fd; }
    explicit operator bool() const noexcept { return fd_ >= 0; }
private:
    int fd_;
};

namespace safe_path_detail {

// Resource-exhaustion limits — tune per application. These stop pathological
// input (thousands of components, absurdly long names) from wasting syscalls
// or triggering pathname length edge cases in the kernel.
inline constexpr size_t kMaxComponents   = 32;
inline constexpr size_t kMaxComponentLen = 255; // matches typical NAME_MAX
inline constexpr size_t kMaxTotalLen     = 4096;

inline std::optional<std::vector<std::string>>
split_untrusted_relpath(std::string_view input) {
    if (input.empty() || input.size() > kMaxTotalLen) return std::nullopt;
    if (input.front() == '/') return std::nullopt;
    if (input.find('\0') != std::string_view::npos) return std::nullopt;

    std::vector<std::string> parts;
    size_t start = 0;
    while (start <= input.size()) {
        size_t slash = input.find('/', start);
        std::string_view part = (slash == std::string_view::npos)
            ? input.substr(start)
            : input.substr(start, slash - start);

        if (part.empty() || part == "." || part == "..") return std::nullopt;
        if (part.size() > kMaxComponentLen) return std::nullopt;
        // Reject raw control characters — defense against terminal/log
        // injection if names ever get echoed into logs or shells.
        for (unsigned char c : part) {
            if (c < 0x20 || c == 0x7f) return std::nullopt;
        }

        parts.emplace_back(part);
        if (parts.size() > kMaxComponents) return std::nullopt;

        if (slash == std::string_view::npos) break;
        start = slash + 1;
    }
    return parts;
}

// Rejects symlinks, requires single hardlink count (no aliasing), and
// optionally pins the filesystem (st_dev) so traversal can't cross a
// mount point onto attacker-controlled or lower-trust storage.
inline bool leaf_is_acceptable(int fd, bool is_dir_expected,
                                dev_t expected_dev, bool enforce_dev) {
    struct stat st{};
    if (::fstat(fd, &st) != 0) return false;
    if (S_ISLNK(st.st_mode)) return false;
    if (is_dir_expected && !S_ISDIR(st.st_mode)) return false;
    if (!is_dir_expected && st.st_nlink > 1) return false; // hardlink alias
    if ((st.st_mode & (S_ISUID | S_ISGID)) != 0) return false; // paranoia
    if (enforce_dev && st.st_dev != expected_dev) return false;
    return true;
}

#ifdef __NR_openat2
inline bool openat2_available() {
    static const bool avail = [] {
        struct open_how how{};
        how.flags = O_RDONLY;
        long r = ::syscall(__NR_openat2, AT_FDCWD, ".", &how, sizeof(how));
        if (r >= 0) { ::close((int)r); return true; }
        return errno != ENOSYS;
    }();
    return avail;
}

inline int do_openat2(int dirfd, const char* path, uint64_t flags,
                       uint64_t resolve, mode_t mode) {
    struct open_how how{};
    how.flags = flags;
    how.mode = mode;
    how.resolve = resolve;
    return (int)::syscall(__NR_openat2, dirfd, path, &how, sizeof(how));
}
#endif

} // namespace safe_path_detail

// base_fd: trusted, opened once at startup from a config path — never
// from attacker input.
// untrusted_relpath: attacker-influenced component (mailbox/user/filename).
// final_flags: e.g. O_RDONLY, or O_WRONLY|O_CREAT|O_EXCL for new mail.
// enforce_single_mount: reject traversal that crosses filesystem boundaries
// (recommended true for Maildir-style stores under one volume).
inline safe_fd open_within(int base_fd,
                            std::string_view untrusted_relpath,
                            int final_flags,
                            mode_t create_mode = 0600,
                            bool enforce_single_mount = true) {
    using namespace safe_path_detail;

    auto parts = split_untrusted_relpath(untrusted_relpath);
    if (!parts) return safe_fd{};

    struct stat base_st{};
    if (::fstat(base_fd, &base_st) != 0) return safe_fd{};
    dev_t base_dev = base_st.st_dev;

#ifdef __NR_openat2
    // Fast path: kernel-atomic symlink/traversal refusal. RESOLVE_BENEATH
    // guarantees the resolved path never leaves base_fd's subtree, and
    // RESOLVE_NO_SYMLINKS refuses any symlink anywhere in the path — both
    // enforced inside one syscall, so there is no window between check
    // and use for an attacker to exploit.
    if (openat2_available()) {
        std::string joined;
        for (size_t i = 0; i < parts->size(); ++i) {
            if (i) joined += '/';
            joined += (*parts)[i];
        }
        int fd = do_openat2(base_fd, joined.c_str(),
                             final_flags | O_CLOEXEC,
                             RESOLVE_BENEATH | RESOLVE_NO_SYMLINKS | RESOLVE_NO_MAGICLINKS,
                             create_mode);
        if (fd < 0) return safe_fd{};
        bool wants_dir = (final_flags & O_DIRECTORY) != 0;
        if (!leaf_is_acceptable(fd, wants_dir, base_dev, enforce_single_mount)) {
            ::close(fd);
            return safe_fd{};
        }
        return safe_fd(fd);
    }
#endif

    // Fallback: manual per-component walk with O_NOFOLLOW at each step.
    // Not quite as strong as openat2 (a symlink swapped in the split
    // second between our openat and fstat of the SAME component is not
    // racy — O_NOFOLLOW+fstat on the fd it returned is atomic per step —
    // but sibling components can't be re-verified after the fact).
    int dup_fd = ::dup(base_fd);
    if (dup_fd < 0) return safe_fd{};
    safe_fd cur(dup_fd);

    for (size_t i = 0; i < parts->size(); ++i) {
        bool is_leaf = (i + 1 == parts->size());
        const std::string& comp = (*parts)[i];

        int flags = is_leaf
            ? (final_flags | O_CLOEXEC | O_NOFOLLOW)
            : (O_DIRECTORY | O_NOFOLLOW | O_CLOEXEC | O_RDONLY);

        int next_fd = ::openat(cur.get(), comp.c_str(), flags, create_mode);
        if (next_fd < 0) return safe_fd{};

        if (!leaf_is_acceptable(next_fd, !is_leaf, base_dev, enforce_single_mount)) {
            ::close(next_fd);
            return safe_fd{};
        }
        cur.reset(next_fd);
    }
    return cur;
}
```

**What this closes vs. what it can't:**

| Attack | Covered |
|---|---|
| `../../etc/passwd`, absolute paths | yes — rejected before any syscall |
| Symlink swapped between check and open (TOCTOU) | yes — `openat2` refusal is inside the atomic resolve; fallback path refuses per-component with no gap |
| Hardlink aliasing a leaf file to a sensitive target | yes — `st_nlink > 1` rejected on non-dir leaves |
| Crossing a mount point mid-traversal onto other storage | yes — `st_dev` pinned when `enforce_single_mount=true` |
| Resource exhaustion via huge/deep paths | yes — component count, length, total length capped |
| Control-character / log-injection via filenames | yes — rejected in the split step |
| setuid/setgid leaf abuse | yes — bits rejected defensively |

**What it does not and cannot cover:**
- Bugs in the kernel's own path resolution (openat2 itself, filesystem drivers).
- Misuse *after* you get the fd back — e.g. leaking it across a fork to a lower-privilege or attacker-controlled process, or writing attacker-controlled data through it insecurely.
- Anything upstream of this function: if `untrusted_relpath` was already decoded, normalized, or concatenated unsafely before reaching here, this can't undo that.
- Non-Linux targets — `openat2` is Linux 5.6+ only (fine for Artix, but no Windows/BSD path here).
- Physical, side-channel, or supply-chain attacks — out of scope for any single function.

