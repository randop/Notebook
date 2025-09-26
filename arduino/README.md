# Arduino

### Setting Up an Arduino C++ Project on Arch Linux

Creating a simple Arduino project using C++. This is a basic LED blink example for an Arduino Uno. This project use **PlatformIO** as the build system—it's a lightweight, IDE-agnostic tool that treats your Arduino code as a standard C++ project with a `main.cpp` file, handles dependencies, and compiles/uploads via CLI. It's available in Arch's official repositories and is one of the most straightforward options for non-IDE workflows.

This assumes you have an Arduino Uno (or similar AVR-based board). Adjust the board ID if needed (e.g., `--board megaatmega2560` for Mega).

#### Step 1: Install Required Packages
Run these commands to install the core tools:

```bash
sudo pacman -Syu  # Update your system first
sudo pacman -S platformio-core avrdude  # PlatformIO for build/upload, avrdude for flashing
```

- `platformio-core`: The CLI for project management, compilation, and upload.
- `avrdude`: Handles firmware upload (often used under the hood by PlatformIO).

If you prefer the AUR version for the latest features:
```bash
yay -S platformio-git  # Or use your preferred AUR helper
```

#### Step 2: Configure Serial Port Permissions
Arduino boards connect via USB serial (e.g., `/dev/ttyACM0` or `/dev/ttyUSB0`). To access them without `sudo`:

1. Create a udev rule file:
   ```bash
   sudo nano /etc/udev/rules.d/01-ttyusb.rules
   ```

2. Add this content:
   ```
   ACTION!="remove", SUBSYSTEMS=="usb-serial", TAG+="uaccess"
   ```

3. Reload rules and unplug/replug your Arduino:
   ```bash
   sudo udevadm control --reload-rules
   sudo udevadm trigger
   ```

Find your port with `ls /dev/tty*` after plugging in.

#### Step 3: Create the Project
1. Create a new directory for your project:
   ```bash
   mkdir my_arduino_blink
   cd my_arduino_blink
   ```

2. Initialize the PlatformIO project for Arduino Uno:
   ```bash
   pio project init --board uno
   ```

   This creates:
   - `platformio.ini`: Config file (board, framework, etc.).
   - `src/main.cpp`: Your main C++ source file.
   - `lib/`: For custom libraries (optional).

3. Edit `platformio.ini` (it's auto-generated, but you can tweak it):
   ```
   [env:uno]
   platform = atmelavr
   board = uno
   framework = arduino
   monitor_speed = 9600  ; Optional: For serial monitor
   ```

4. Replace the contents of `src/main.cpp` with this simple blink example (standard Arduino C++):
   ```cpp
   #include <Arduino.h>

   // Pin definitions
   #define LED_PIN 13  // Built-in LED on Uno

   void setup() {
       pinMode(LED_PIN, OUTPUT);  // Set pin as output
   }

   void loop() {
       digitalWrite(LED_PIN, HIGH);  // Turn LED on
       delay(1000);                  // Wait 1 second
       digitalWrite(LED_PIN, LOW);   // Turn LED off
       delay(1000);                  // Wait 1 second
   }
   ```

   This is pure C++ with Arduino headers—`setup()` and `loop()` are the entry points.

#### Step 4: Compile and Upload
1. Compile the project (builds to `.pio/build/uno/`):
   ```bash
   pio run
   ```
   - Output: Something like "SUCCESS: Library Manager: Installing..." followed by build success.
   - If errors: Check for missing libraries (PlatformIO auto-installs Arduino core).

2. Upload to your board (connect via USB first):
   ```bash
   pio run --target upload
   ```
   - It detects the port automatically. If not, add `upload_port = /dev/ttyACM0` to `platformio.ini`.
   - Success: "Hard resetting via RTS pin..."

3. (Optional) Monitor serial output:
   ```bash
   pio device monitor
   ```

#### Troubleshooting
- **Board not detected?** Run `pio device list` to see connected ports.
- **Permission issues?** Double-check udev rules and group membership (`sudo usermod -a -G uucp $USER` then log out/in).
- **Custom boards/libraries?** Add to `platformio.ini` under `[env:uno]`, e.g., `lib_deps = SomeLibrary`.
- For a more manual Makefile-based approach (no PlatformIO), install `arduino-builder` from AUR and use commands like `arduino-builder -fqbn archlinux-arduino:avr:uno blink.ino`, but PlatformIO is simpler for C++ projects.

Your project is now a portable C++ setup—version it with Git, add more `.cpp`/`.h` files to `src/`, and build anywhere with PlatformIO installed. If you need expansions (e.g., for DUE or ESP32), let me know!
