### Fastest Compilation Options for C++ Meson Projects

To achieve the fastest compilation times in a Meson-based C++ project, focus on options that minimize per-file compilation overhead, enable parallelism, and reduce redundant work. These primarily involve Meson's built-in configuration options (set during `meson setup`), combined with runtime build commands and external tools like ccache. Note that "fastest" here prioritizes **build time** over runtime performance—use a debug-like build type and avoid heavy optimizations like LTO.

I'll break this down into **Meson configuration options**, **build command tweaks**, **code-level practices**, and **external tools**. All Meson options can be set via:
- Command line: `meson setup builddir -Doption=value`
- In `meson.build`: `project(default_options: ['option=value'])`
- Post-setup: `meson configure builddir -Doption=value`

#### 1. Meson Configuration Options
These directly control compiler flags and build structure for speed.

| Option | Recommended Value | Description & Impact | How to Set |
|--------|-------------------|----------------------|------------|
| `buildtype` | `debug` or `plain` | Sets a low-optimization mode (`optimization=0` implied), skipping expensive flags like `-O2` or `-O3` that slow compilation. `debug` adds debug info but keeps builds fast; `plain` strips even that for minimal overhead. | `-Dbuildtype=debug` |
| `optimization` | `0` or `plain` | Explicitly disables optimizations to reduce compile time per file (e.g., no loop unrolling). Overrides parts of `buildtype`. | `-Doptimization=0` |
| `unity` | `on` | Enables unity (header-only) builds, merging multiple source files into single compilation units to cut redundant header parsing—can speed up large projects by 2-5x. | `-Dunity=on` |
| `unity_size` | `8` to `16` | Increases files per unity block (default 4); larger sizes amplify speed gains but may slow incremental rebuilds or use more RAM. Tune based on project size. | `-Dunity_size=8` (with `unity=on`) |
| `b_pch` | `true` (default) | Enables precompiled headers (PCH) to cache common includes (e.g., STL), speeding up repeated compilations by 20-50% in header-heavy C++ code. Create a `pch` target in `meson.build` for best results. | `-Db_pch=true` |
| `backend` | `ninja` (default) | Uses Ninja for efficient dependency tracking and parallelism out-of-the-box. Avoid slower backends like `vs` unless needed. | `-Dbackend=ninja` |
| `b_sanitize` | `none` | Disables sanitizers (e.g., AddressSanitizer) that add instrumentation overhead, reducing compile time by 10-30%. | `-Db_sanitize=none` |
| `b_coverage` | `false` (default) | Avoids coverage instrumentation, which bloats compile times. | `-Db_coverage=false` |
| `cpp_debugstl` | `false` (default) | Disables STL debug checks, avoiding extra validation code during compilation. | `-Dcpp_debugstl=false` |
| `cpp_eh` | `none` (if your code doesn't use exceptions) | Disables exception handling (`-fno-exceptions`), simplifying codegen and speeding up compiles by ~5-10%. | `-Dcpp_eh=none` |
| `cpp_rtti` | `false` (if no dynamic_cast/typeid needed) | Disables RTTI, reducing metadata generation and compile overhead. | `-Dcpp_rtti=false` |
| `b_lto` | `false` (default) | Avoids link-time optimization, which serializes and re-optimizes across files—great for runtime but adds 20-100% to build time. | `-Db_lto=false` |

For shared sources across targets (e.g., common utils), define them as a `static_library` in `meson.build` and link it to executables—this compiles once and reuses, avoiding per-target rebuilds.

#### 2. Build Command Tweaks
After setup, run builds with parallelism:
- `meson compile -C builddir -j$(nproc)` (uses all CPU cores; replace `$(nproc)` with a number like `8` for control).
- Ninja (default backend) excels here, scaling better than Make/CMake for incremental builds.

#### 3. Code-Level Practices
- **Minimize includes**: Only `#include` what's needed; use forward declarations. Tools like clangd can audit this.
- **Reduce dependencies**: Break code into smaller, independent modules to limit recompiles.
- **Header organization**: Group common headers into PCH files (e.g., via `pch_dep = declare_dependency(include_directories: '...')`).

#### 4. External Tools
- **ccache**: Caches compilation results for 5-10x faster rebuilds. Install it, then prefix your compiler: `export CC="ccache gcc" CXX="ccache g++"` before `meson setup`. Meson detects it automatically.
- **mold linker**: Faster than GNU ld/Gold. Set `export LD=mold` or add to `cpp_link_args='-fuse-ld=mold'`.
- **distcc**: Distributes compiles across machines for massive scaling. Set `export CC="distcc gcc"` etc., but requires setup.

Start with `buildtype=debug`, `unity=on`, and `-j` for quick wins—expect 2-3x faster builds on typical projects. Test incrementally, as some (e.g., large `unity_size`) trade incremental speed for full rebuilds. For more, see Meson's [builtin options docs](https://mesonbuild.com/Builtin-options.html).
