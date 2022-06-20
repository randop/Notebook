#### References:
* https://developer.android.com/studio#offline
* https://developer.android.com/studio/intro/studio-config#offline
* https://dl.google.com/android/studio/maven-google-com/stable/offline-gmaven-stable.zip

***

#### Problem
    04/19 03:55:13: Launching 'app' on StarMobile UP HD.
    Installation did not succeed.
    The application could not be installed: INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES
    Installation failed due to: 'null'

#### Solution
Remove the existing application on the phone.

***

#### Problem:
    05/03 22:41:37: Launching 'app' on StarMobile UP HD.
    Installation did not succeed.
    The application could not be installed: INSTALL_FAILED_DEXOPT
    The device might have stale dexed jars that don't match the current version (dexopt error).

#### Root Cause:
The dex file gets larger then buffer size.

#### Solution:
Fix for INSTALL_FAILED_DEXOPT issue when trying to run project on android device that is running Android 5.0
1. Add the following options at gradle.properties
```
android.enableD8=false
android.enableD8.desugaring= false
```
2. Configure the app for multidex support. Refer this link to use it: https://developer.android.com/tools/building/multidex.html
3. Change proguard settings to minifyEnabled = true
```
buildTypes {
    release {
        minifyEnabled true
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
```
***