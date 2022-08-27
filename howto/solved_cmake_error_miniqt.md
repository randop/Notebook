# (SOLVED) npm nodegui ERROR: CMake Error at miniqt 

## Error
```bash
[username@localhost nodegui-starter]$ npm install
npm WARN deprecated xmldom@0.1.31: Deprecated due to CVE-2021-21366 resolved in 0.5.0
npm ERR! code 1
npm ERR! path /home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui
npm ERR! command failed
npm ERR! command sh -c cross-env npm run setupqt && (node ./scripts/skip.js || npm run build:addon)
npm ERR! > @nodegui/nodegui@0.36.0 setupqt
npm ERR! > cross-env node ./scripts/setupMiniQt.js
npm ERR! 
npm ERR! Minimal Qt 5.14.1 setup:
npm ERR! Extracting /home/username/.cache/nodegui-mini-qt-nodejs/5.14.1-0-202001240953qtsvg-Linux-RHEL_7_6-GCC-Linux-RHEL_7_6-X86_64.7z to /home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/miniqt ...
npm ERR! Qt SVG for Minimal Qt: 5.14.1 installation was setup successfully.  outDir: /home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/miniqt
npm ERR! Extracting /home/username/.cache/nodegui-mini-qt-nodejs/5.14.1-0-202001240953icu-linux-Rhel7.2-x64.7z to /home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/miniqt ...
npm ERR! Qt ICU for Minimal Qt: 5.14.1 installation was setup successfully.  outDir: /home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/miniqt
npm ERR! Extracting /home/username/.cache/nodegui-mini-qt-nodejs/5.14.1-0-202001240953qtbase-Linux-RHEL_7_6-GCC-Linux-RHEL_7_6-X86_64.7z to /home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/miniqt ...
npm ERR! Qt Base for Minimal Qt: 5.14.1 installation was setup successfully.  outDir: /home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/miniqt
npm ERR! 
npm ERR! > @nodegui/nodegui@0.36.0 build:addon
npm ERR! > cross-env CMAKE_BUILD_PARALLEL_LEVEL=8 cmake-js compile
npm ERR! 
npm ERR! [
npm ERR!   '/home/username/.nvm/versions/node/v16.15.1/bin/node',
npm ERR!   '/home/username/Projects/nodegui-starter/node_modules/.bin/cmake-js',
npm ERR!   'compile'
npm ERR! ]
npm ERR! Not searching for unused variables given on the command line.
npm ERR! -- The C compiler identification is GNU 11.2.1
npm ERR! -- The CXX compiler identification is GNU 11.2.1
npm ERR! -- Detecting C compiler ABI info
npm ERR! -- Detecting C compiler ABI info - done
npm ERR! -- Check for working C compiler: /usr/bin/cc - skipped
npm ERR! -- Detecting C compile features
npm ERR! -- Detecting C compile features - done
npm ERR! -- Detecting CXX compiler ABI info
npm ERR! -- Detecting CXX compiler ABI info - done
npm ERR! -- Check for working CXX compiler: /usr/bin/c++ - skipped
npm ERR! -- Detecting CXX compile features
npm ERR! -- Detecting CXX compile features - done
npm ERR! -- Using QT installation for nodegui_core QT_CMAKE_HOME_DIR:/home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/miniqt/5.14.1/gcc_64/lib/cmake/Qt5
npm ERR! -- Configuring incomplete, errors occurred!
npm ERR! See also "/home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/build/CMakeFiles/CMakeOutput.log".
npm ERR! Not searching for unused variables given on the command line.
npm ERR! -- The C compiler identification is GNU 11.2.1
npm ERR! -- The CXX compiler identification is GNU 11.2.1
npm ERR! -- Detecting C compiler ABI info
npm ERR! -- Detecting C compiler ABI info - done
npm ERR! -- Check for working C compiler: /usr/bin/cc - skipped
npm ERR! -- Detecting C compile features
npm ERR! -- Detecting C compile features - done
npm ERR! -- Detecting CXX compiler ABI info
npm ERR! -- Detecting CXX compiler ABI info - done
npm ERR! -- Check for working CXX compiler: /usr/bin/c++ - skipped
npm ERR! -- Detecting CXX compile features
npm ERR! -- Detecting CXX compile features - done
npm ERR! -- Using QT installation for nodegui_core QT_CMAKE_HOME_DIR:/home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/miniqt/5.14.1/gcc_64/lib/cmake/Qt5
npm ERR! -- Configuring incomplete, errors occurred!
npm ERR! See also "/home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/build/CMakeFiles/CMakeOutput.log".
npm ERR! info TOOL Using Ninja generator, because ninja is available.
npm ERR! info DIST Downloading distribution files.
npm ERR! http DIST 	- https://nodejs.org/dist/v16.15.1/SHASUMS256.txt
npm ERR! http DIST 	- https://nodejs.org/dist/v16.15.1/node-v16.15.1-headers.tar.gz
npm ERR! info CMD CONFIGURE
npm ERR! info RUN [
npm ERR! info RUN   'cmake',
npm ERR! info RUN   '/home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui',
npm ERR! info RUN   '--no-warn-unused-cli',
npm ERR! info RUN   '-G',
npm ERR! info RUN   'Ninja',
npm ERR! info RUN   '-DCMAKE_JS_VERSION=6.2.1',
npm ERR! info RUN   '-DCMAKE_BUILD_TYPE=Release',
npm ERR! info RUN   '-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=/home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/build/Release',
npm ERR! info RUN   '-DCMAKE_JS_INC=/home/username/.cmake-js/node-x64/v16.15.1/include/node',
npm ERR! info RUN   '-DCMAKE_JS_SRC=',
npm ERR! info RUN   '-DNODE_RUNTIME=node',
npm ERR! info RUN   '-DNODE_RUNTIMEVERSION=16.15.1',
npm ERR! info RUN   '-DNODE_ARCH=x64'
npm ERR! info RUN ]
npm ERR! CMake Error at miniqt/5.14.1/gcc_64/lib/cmake/Qt5Gui/Qt5GuiConfigExtras.cmake:9 (message):
npm ERR!   Failed to find "GL/gl.h" in "/usr/include/libdrm".
npm ERR! Call Stack (most recent call first):
npm ERR!   miniqt/5.14.1/gcc_64/lib/cmake/Qt5Gui/Qt5GuiConfig.cmake:207 (include)
npm ERR!   miniqt/5.14.1/gcc_64/lib/cmake/Qt5Widgets/Qt5WidgetsConfig.cmake:100 (find_package)
npm ERR!   miniqt/5.14.1/gcc_64/lib/cmake/Qt5/Qt5Config.cmake:28 (find_package)
npm ERR!   config/qt.cmake:23 (find_package)
npm ERR!   CMakeLists.txt:212 (AddQtSupport)
npm ERR! 
npm ERR! 
npm ERR! info REP Build has been failed, trying to do a full rebuild.
npm ERR! info CMD CLEAN
npm ERR! info RUN [
npm ERR! info RUN   'cmake',
npm ERR! info RUN   '-E',
npm ERR! info RUN   'remove_directory',
npm ERR! info RUN   '/home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/build'
npm ERR! info RUN ]
npm ERR! info CMD CONFIGURE
npm ERR! info RUN [
npm ERR! info RUN   'cmake',
npm ERR! info RUN   '/home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui',
npm ERR! info RUN   '--no-warn-unused-cli',
npm ERR! info RUN   '-G',
npm ERR! info RUN   'Ninja',
npm ERR! info RUN   '-DCMAKE_JS_VERSION=6.2.1',
npm ERR! info RUN   '-DCMAKE_BUILD_TYPE=Release',
npm ERR! info RUN   '-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=/home/username/Projects/nodegui-starter/node_modules/@nodegui/nodegui/build/Release',
npm ERR! info RUN   '-DCMAKE_JS_INC=/home/username/.cmake-js/node-x64/v16.15.1/include/node',
npm ERR! info RUN   '-DCMAKE_JS_SRC=',
npm ERR! info RUN   '-DNODE_RUNTIME=node',
npm ERR! info RUN   '-DNODE_RUNTIMEVERSION=16.15.1',
npm ERR! info RUN   '-DNODE_ARCH=x64'
npm ERR! info RUN ]
npm ERR! CMake Error at miniqt/5.14.1/gcc_64/lib/cmake/Qt5Gui/Qt5GuiConfigExtras.cmake:9 (message):
npm ERR!   Failed to find "GL/gl.h" in "/usr/include/libdrm".
npm ERR! Call Stack (most recent call first):
npm ERR!   miniqt/5.14.1/gcc_64/lib/cmake/Qt5Gui/Qt5GuiConfig.cmake:207 (include)
npm ERR!   miniqt/5.14.1/gcc_64/lib/cmake/Qt5Widgets/Qt5WidgetsConfig.cmake:100 (find_package)
npm ERR!   miniqt/5.14.1/gcc_64/lib/cmake/Qt5/Qt5Config.cmake:28 (find_package)
npm ERR!   config/qt.cmake:23 (find_package)
npm ERR!   CMakeLists.txt:212 (AddQtSupport)
npm ERR! 
npm ERR! 
npm ERR! ERR! OMG Process terminated: 1

npm ERR! A complete log of this run can be found in:
npm ERR!     /home/username/.npm/_logs/2022-06-24T23_09_21_102Z-debug-0.log
```

## Solution
```
apt install mesa-common-dev libglu1-mesa-dev
dnf install mesa-libGL mesa-libGL-devel
zypper install Mesa-libGL-devel
```
