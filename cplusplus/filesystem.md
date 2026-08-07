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

