# C++23 Android application using **NativeActivity only**
No Java, no Kotlin, no wrappers.

---

## 1) Directory Layout

```
native_cpp_app/
├── CMakeLists.txt
├── app/
│   ├── src/
│   │   └── main.cpp
│   └── AndroidManifest.xml
```

---

## 2) AndroidManifest.xml

`app/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest package="com.example.nativecpp"
          xmlns:android="http://schemas.android.com/apk/res/android">

    <application android:label="NativeCPP"
                 android:hasCode="false">

        <activity android:name="android.app.NativeActivity"
                  android:exported="true"
                  android:configChanges="orientation|screenSize|keyboardHidden">

            <meta-data android:name="android.app.lib_name"
                       android:value="nativecpp" />

            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>

        </activity>
    </application>
</manifest>
```

**Key points**

* `android:hasCode="false"` ensures no Java bytecode is expected.
* `android.app.lib_name` must match the `.so` name **without `lib` prefix**.

---

## 3) C++23 Entry Point

`app/src/main.cpp`

```cpp
#include <android/native_activity.h>
#include <android/log.h>
#include <android/native_window.h>
#include <unistd.h>

#define LOG_TAG "NativeCPP"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

static void onStart(ANativeActivity* activity) {
    LOGI("onStart");
}

static void onResume(ANativeActivity* activity) {
    LOGI("onResume");
}

static void onPause(ANativeActivity* activity) {
    LOGI("onPause");
}

static void onStop(ANativeActivity* activity) {
    LOGI("onStop");
}

static void onDestroy(ANativeActivity* activity) {
    LOGI("onDestroy");
}

extern "C"
void ANativeActivity_onCreate(ANativeActivity* activity,
                             void* savedState,
                             size_t savedStateSize) {

    LOGI("NativeActivity created");

    activity->callbacks->onStart   = onStart;
    activity->callbacks->onResume  = onResume;
    activity->callbacks->onPause   = onPause;
    activity->callbacks->onStop    = onStop;
    activity->callbacks->onDestroy = onDestroy;

    // Minimal event loop (no looper, no input yet)
    while (true) {
        sleep(1);
    }
}
```

**Notes**

* Entry symbol must be exactly: `ANativeActivity_onCreate`
* Must be `extern "C"` (no name mangling)
* Infinite loop keeps process alive (replace later with proper looper)

---

## 4) CMake Configuration

`CMakeLists.txt`

```cmake
cmake_minimum_required(VERSION 3.22)

project(nativecpp LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 23)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

add_library(nativecpp SHARED
    app/src/main.cpp
)

find_library(log-lib log)
find_library(android-lib android)

target_link_libraries(nativecpp
    ${log-lib}
    ${android-lib}
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

## 5) Build (NDK CLI Only)

**Required**

* Android SDK
* Android NDK (r26+ recommended)

#### Generate build

```bash
cmake -S . -B build \
  -DCMAKE_TOOLCHAIN_FILE=$NDK/build/cmake/android.toolchain.cmake \
  -DANDROID_ABI=arm64-v8a \
  -DANDROID_PLATFORM=android-26
```

#### Compile

```bash
cmake --build build
```

Output:

```
build/libnativecpp.so
```

---

## 6) Package APK (no Gradle)

Create minimal APK manually.

#### Step 1: Prepare structure

```
apk/
├── lib/arm64-v8a/libnativecpp.so
├── AndroidManifest.xml
```

#### Step 2: Compile manifest

```bash
aapt2 compile app/AndroidManifest.xml -o compiled/
aapt2 link -o app.apk -I $ANDROID_HOME/platforms/android-34/android.jar \
    --manifest app/AndroidManifest.xml \
    compiled/*.flat
```

#### Step 3: Add native library

```bash
zip -r app.apk lib/
```

#### Step 4: Sign

```bash
apksigner sign --ks my.keystore app.apk
```

---

## 7) Install & Run

```bash
adb install -r app.apk
adb logcat | grep NativeCPP
```

---

## 8) Critical Rules (Non-Negotiable)

* No `main()` — Android controls lifecycle
* No JVM — enforced via `android:hasCode="false"`
* No STL exceptions unless you explicitly enable them
* No blocking main thread in production (replace loop with `ALooper`)
* All callbacks must be set manually
* Shared library name must match manifest metadata

---

## 9) Next Step (If Expanding)

* Replace infinite loop with `ALooper_pollAll`
* Add `ANativeWindow` rendering (EGL/Vulkan)
* Handle input via `AInputQueue`
* Add asset loading via `AAssetManager`

---

Last updated: 2026-04-30 15:00:23 UTC
