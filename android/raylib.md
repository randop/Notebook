# raylib

## 1) Build raylib for Android (NDK)

raylib already supports Android via NDK. Build it once, then link.

### Clone

```bash
git clone https://github.com/raysan5/raylib.git
cd raylib
```

### Build (NDK toolchain)

```bash
cmake -S . -B build-android \
  -DCMAKE_TOOLCHAIN_FILE=$NDK/build/cmake/android.toolchain.cmake \
  -DANDROID_ABI=arm64-v8a \
  -DANDROID_PLATFORM=android-26 \
  -DBUILD_SHARED_LIBS=OFF

cmake --build build-android
```

Output:

```
build-android/raylib/libraylib.a
```

---

## 2) Modify Project Structure

```id="wq3d9a"
native_cpp_app/
├── CMakeLists.txt
├── external/
│   └── raylib/
│       ├── include/
│       └── libraylib.a
├── app/
│   ├── src/main.cpp
│   └── AndroidManifest.xml
```

Copy:

* `raylib/src/*.h` → `external/raylib/include`
* `libraylib.a` → `external/raylib/`

---

## 3) Replace main.cpp with raylib loop

`app/src/main.cpp`

```cpp id="q8j2pz"
#include <android/native_activity.h>
#include <android/log.h>
#include <unistd.h>

#include "raylib.h"

#define LOG_TAG "NativeCPP"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C"
void ANativeActivity_onCreate(ANativeActivity* activity,
                             void* savedState,
                             size_t savedStateSize) {

    LOGI("Starting raylib NativeActivity");

    // Initialize raylib (Android platform handled internally)
    const int screenWidth = 800;
    const int screenHeight = 450;

    InitWindow(screenWidth, screenHeight, "raylib NativeActivity");
    SetTargetFPS(60);

    while (!WindowShouldClose()) {

        BeginDrawing();
        ClearBackground(RAYWHITE);

        DrawText("raylib running on NativeActivity", 40, 200, 20, BLACK);

        EndDrawing();
    }

    CloseWindow();

    // Ensure process stays alive until system kills it
    while (true) {
        sleep(1);
    }
}
```

---

## 4) Update CMakeLists.txt

```cmake id="n3t6vy"
cmake_minimum_required(VERSION 3.22)

project(nativecpp LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

add_library(nativecpp SHARED
    app/src/main.cpp
)

# raylib
add_library(raylib STATIC IMPORTED)
set_target_properties(raylib PROPERTIES
    IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/external/raylib/libraylib.a
)

target_include_directories(nativecpp PRIVATE
    external/raylib/include
)

find_library(log-lib log)
find_library(android-lib android)
find_library(GLESv2-lib GLESv2)
find_library(EGL-lib EGL)
find_library(m-lib m)
find_library(dl-lib dl)

target_link_libraries(nativecpp
    raylib
    ${android-lib}
    ${log-lib}
    ${GLESv2-lib}
    ${EGL-lib}
    ${m-lib}
    ${dl-lib}
)

target_compile_options(nativecpp PRIVATE
    -Wall
    -Wextra
    -Werror
    -fno-exceptions
    -fno-rtti
)
```

---

## 5) Critical Details (Do Not Skip)

* raylib on Android uses **OpenGL ES internally**
* `InitWindow()` is valid — raylib handles Android platform glue
* No need to manually touch `ANativeWindow`
* Do NOT mix your own rendering loop outside raylib

---

## 6) Manifest stays the same

Ensure:

```xml
<meta-data android:name="android.app.lib_name"
           android:value="nativecpp" />
```

---

## 7) Build

```bash
cmake -S . -B build \
  -DCMAKE_TOOLCHAIN_FILE=$NDK/build/cmake/android.toolchain.cmake \
  -DANDROID_ABI=arm64-v8a \
  -DANDROID_PLATFORM=android-26

cmake --build build
```

---

## 8) Result

* NativeActivity launches
* raylib initializes EGL + GLES
* Window renders text
* No JVM, no glue code, no frameworks

---

## 9) If Something Breaks

Typical causes:

* Missing `EGL` / `GLESv2` linkage
* Wrong ABI mismatch (`arm64-v8a` vs built raylib)
* raylib not built with same NDK toolchain
* `.a` compiled with different STL flags

---

Last updated: 2026-04-30 15:09 UTC
