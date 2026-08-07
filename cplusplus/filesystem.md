# std::filesystem

No CVE exists for `std::filesystem::canonical` itself — it's a standard library function, not a product, so CVEs get filed against things that *misuse* it. But it's a real source of vulnerabilities in practice.

**Real attack vectors:**

1. **TOCTOU (time-of-check-to-time-of-use) race**: `canonical()` resolves the path at call time. Between that call and when you actually open/use the file, an attacker can swap a symlink to point elsewhere. Classic race condition — resolving isn't atomic with the actual file operation.

2. **Path traversal / incomplete validation**: A common broken pattern is calling `canonical()` on user input *before* checking it's within a base directory, or using string prefix checks instead of proper path comparison after canonicalization. CVE-2022-25299 (Mongoose), CVE-2021-23514 (Crow), and CVE-2022-25298 (Webcc) are real C++ path traversal vulnerabilities found by Snyk researchers in projects using this kind of pattern incorrectly.

3. **`canonical()` throws if the path doesn't exist** — if you canonicalize user input before validating existence, you get an exception-based oracle (can leak info about what exists on the filesystem via error/timing differences). Use `weakly_canonical()` when the target may not exist yet.

4. **Symlink resolution is exactly the attack surface**: canonical resolves ".", "..", and symlinks — that's necessary for correct traversal prevention, but it also means the resolved path depends on live filesystem state (mounts, symlinks) at call time, not just the string.

**The safe pattern** (from Snyk's writeup): canonicalize your base path once, canonicalize `base / user_input` with `weakly_canonical`, then check the result is still prefixed by the canonical base — using `std::filesystem::path` comparison, not naive string `starts_with` (which fails on things like `/base` vs `/base-evil`).

