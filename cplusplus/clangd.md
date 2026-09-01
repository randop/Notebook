# clangd

**`compile_commands.json`** is a JSON compilation database that records the exact compiler invocations used for each translation unit. Tools such as clangd, clang-tidy, and many IDEs use it for accurate code intelligence, diagnostics, and refactoring.

### Using CMake

CMake (3.5+) can generate the file when using the **Makefile** or **Ninja** generators.

**Option 1 – Command-line flag (most common)**

```bash
mkdir build && cd build
cmake -DCMAKE_EXPORT_COMPILE_COMMANDS=ON ..
# or with an explicit generator
cmake -G Ninja -DCMAKE_EXPORT_COMPILE_COMMANDS=ON ..
```

The file appears in the build directory as `compile_commands.json`.

**Option 2 – Inside `CMakeLists.txt`**

```cmake
cmake_minimum_required(VERSION 3.5)
project(myproject)

set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

add_executable(myapp main.cpp)
```

Then just run the normal configure step.

**Tips**
- Symlink it to the source root if tools expect it there:  
  `ln -s build/compile_commands.json .`
- It is ignored by Visual Studio / Xcode generators.
- Re-run CMake after adding/removing source files.

### Using Meson

Meson generates `compile_commands.json` **automatically** (via the Ninja backend) in the build directory. No extra flag is required.

```bash
meson setup build          # or meson build
# compile_commands.json is now in the build/ directory
```

If the file is missing or you need to regenerate it:

```bash
meson --reconfigure build
# or manually with Ninja
ninja -C build -t compdb c_COMPILER cpp_COMPILER > compile_commands.json
```

(The exact rule names can vary; `c_COMPILER` / `cpp_COMPILER` are the usual ones Meson creates.)

**Notes**
- Only the Ninja backend supports it (the default). Visual Studio / Xcode backends do not.
- On Windows, response files (`.rsp`) can appear; modern Meson + Ninja usually expand them correctly.

### Example `compile_commands.json`

A minimal, realistic example (CMake-style):

```json
[
  {
    "directory": "/home/user/project/build",
    "command": "/usr/bin/c++ -I/home/user/project/include -std=c++20 -O2 -c /home/user/project/src/main.cpp -o CMakeFiles/myapp.dir/src/main.cpp.o",
    "file": "/home/user/project/src/main.cpp",
    "output": "CMakeFiles/myapp.dir/src/main.cpp.o"
  },
  {
    "directory": "/home/user/project/build",
    "command": "/usr/bin/c++ -I/home/user/project/include -std=c++20 -O2 -c /home/user/project/src/utils.cpp -o CMakeFiles/myapp.dir/src/utils.cpp.o",
    "file": "/home/user/project/src/utils.cpp",
    "output": "CMakeFiles/myapp.dir/src/utils.cpp.o"
  }
]
```

Alternative form that uses an `arguments` array (also valid):

```json
[
  {
    "directory": "/home/user/project/build",
    "arguments": [
      "/usr/bin/clang++",
      "-I/home/user/project/include",
      "-std=c++20",
      "-O2",
      "-c",
      "/home/user/project/src/main.cpp",
      "-o",
      "CMakeFiles/myapp.dir/src/main.cpp.o"
    ],
    "file": "/home/user/project/src/main.cpp",
    "output": "CMakeFiles/myapp.dir/src/main.cpp.o"
  }
]
```

### Quick comparison

| Build system | How to generate                          | Location              | Notes                              |
|--------------|------------------------------------------|-----------------------|------------------------------------|
| **CMake**    | `-DCMAKE_EXPORT_COMPILE_COMMANDS=ON`     | Build directory       | Makefile / Ninja only              |
| **Meson**    | Automatic (Ninja backend)                | Build directory       | Just run `meson setup`             |

After generating the file, point your language server / IDE at the directory that contains it (or create a symlink in the project root).

---

## LazyVim Navigation keymaps

| Key | Action |
|---|---|
| `gd` | Go to definition |
| `gD` | Go to declaration (e.g. jump to the `.h` prototype) |
| `gr` | Find references |
| `gI` | Go to implementation |
| `gy` | Go to type definition |
| `gO` | Document symbols (outline of current file) |
| `K` | Hover (shows type/doc info) |
| `<leader>ch` | Switch between header and source file (clangd's `ClangdSwitchSourceHeader`) |
| `<leader>cl` | LSP info picker (confirms clangd is attached) |

For jumping between classes/files project-wide, LazyVim's fuzzy finder is handy too: `<leader>sw` (grep word under cursor), `<leader>ss` (document symbols), or `<leader>sS` (workspace symbols) if you're on a version with Telescope/fzf-lua/Snacks picker enabled.

