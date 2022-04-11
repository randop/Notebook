# Run Android Emulator with Guest GPU mode in Linux
```
# Setup PATH to include android emulator
# export PATH=$PATH:/home/$(whoami)/Android/Sdk/emulator

# List AVD
emulator -list-avds

# Run an AVD from one of the list
emulator -avd Pixel_2_API_27 -gpu guest
```