### Setting Up the Environment

To create and compile a C++ project for Arduino without using the Arduino IDE, we'll use the AVR-GCC toolchain (the same compiler the IDE uses under the hood). This allows building via command line with a simple Makefile.

#### Prerequisites
1. **Install AVR-GCC**:
   - On **Windows**: Download and install from [WinAVR](http://winavr.sourceforge.net/) or use MSYS2 (`pacman -S mingw-w64-x86_64-avr-gcc`).
   - On **macOS**: Use Homebrew (`brew install avr-gcc`).
   - On **Linux (Ubuntu/Debian)**: `sudo apt update && sudo apt install gcc-avr avr-libc avrdude make`.
   - On Arch Linux: `sudo pacman -Syu avr-gcc avr-libc avrdude make`
   
   Verify installation: Run `avr-gcc --version`. It should show something like "avr-gcc (GCC) 13.x".

2. **Target Board**: I'll assume an Arduino Uno (ATmega328P microcontroller). Adjust if using a different board (e.g., change `MCU` in the Makefile).

3. **Project Directory**: Create a folder, e.g., `my_arduino_project/`, and navigate into it: `cd my_arduino_project`.

### Creating the C++ Project

We'll create a simple "Blink" example that toggles the built-in LED on pin 13. Arduino code is C++ but wrapped in a `.ino` file (which gets preprocessed to `.cpp`).

#### Main Sketch File: `Blink.ino`
This is your C++ source file. Place it in the project directory.

```cpp
// Blink.ino - Simple LED blink example for Arduino Uno

// Define the pin for the built-in LED
#define LED_PIN 13

// Setup function: Runs once at startup
void setup() {
  // Initialize the LED pin as an output
  pinMode(LED_PIN, OUTPUT);
}

// Loop function: Runs repeatedly
void loop() {
  digitalWrite(LED_PIN, HIGH);  // Turn LED on
  delay(1000);                  // Wait 1 second
  digitalWrite(LED_PIN, LOW);   // Turn LED off
  delay(1000);                  // Wait 1 second
}
```

This uses Arduino's core library functions (`pinMode`, `digitalWrite`, `delay`), which are provided by `avr-libc` and the Arduino core.

### Build System: Makefile

Create a file named `Makefile` in the project directory. This automates compilation using AVR-GCC.

```makefile
# Makefile for Arduino project without IDE
# Targets: Arduino Uno (ATmega328P)

# Compiler and flags
CC = avr-gcc
OBJCOPY = avr-objcopy
SIZE = avr-size
MCU = atmega328p          # Microcontroller (change for other boards, e.g., atmega2560)
F_CPU = 16000000UL        # Clock frequency in Hz
ARDUINO_CORE = /usr/share/arduino/hardware/arduino/avr/cores/arduino  # Path to Arduino core (adjust if needed)

# Adjust ARDUINO_CORE path:
# - Linux: Often /usr/share/arduino/hardware/arduino/avr/cores/arduino
# - macOS: /usr/local/share/arduino/hardware/arduino/avr/cores/arduino
# - Windows (MSYS2): /mingw64/share/arduino/hardware/arduino/avr/cores/arduino
# If unsure, find it via `find /usr -name "wiring.h" 2>/dev/null` (Linux/macOS)

# Source files
SOURCES = Blink.ino
OBJECTS = $(SOURCES:.ino=.o)

# Compiler flags
CFLAGS = -g -Os -w -std=gnu11 -ffunction-sections -fdata-sections -MMD -flto -fno-fat-lto-objects -mmcu=$(MCU) -DF_CPU=$(F_CPU)
LDFLAGS = -Wl,--gc-sections,--relax -mmcu=$(MCU)

# Arduino libraries (include paths)
INCLUDES = -I$(ARDUINO_CORE) -I.

# Targets
all: blink.hex

# Preprocess .ino to .cpp (Arduino-specific: adds includes)
%.cpp: %.ino
	@echo "Preprocessing $<"
	@cpp -dM -P - < $< > temp.preprocessed
	@awk '/^# [0-9]+/ {next} {print}' temp.preprocessed > $@  # Simple de-mangling (or use full Arduino preprocessor if needed)
	@rm -f temp.preprocessed
	@echo "#include <Arduino.h>" > temp.header
	@cat $@ >> temp.header
	@mv temp.header $@

# Compile
%.o: %.cpp
	$(CC) $(CFLAGS) $(INCLUDES) -c $< -o $@

# Link
blink.elf: $(OBJECTS)
	$(CC) $(OBJECTS) $(LDFLAGS) -o $@

# Generate hex file for upload
blink.hex: blink.elf
	$(OBJCOPY) -O ihex -R .eeprom $< $@
	$(SIZE) $@

# Clean
clean:
	rm -f *.o *.d *.elf *.hex temp.*

# Upload (optional, requires avrdude and USB connection)
upload: blink.hex
	avrdude -F -V -c arduino -p $(MCU) -P /dev/ttyUSB0 -b 57600 -U flash:w:$<:i  # Adjust port (e.g., COM3 on Windows)

# Phony targets
.PHONY: all clean upload
```

**Notes on Makefile**:
- **Preprocessing**: Arduino `.ino` files need to be converted to `.cpp` by adding `#include <Arduino.h>`. The simple `awk` here is a basic substitute; for complex sketches, you might need the full Arduino preprocessor (install `arduino-builder` via `pip install arduino-builder` and adjust).
- **ARDUINO_CORE Path**: Update this to match your system. It includes headers like `Arduino.h` for wiring, pins, etc.
- If you get include errors, ensure `avr-libc` is installed and the path is correct.

### Compiling the Project

1. Open a terminal in the project directory.
2. Run:
   ```
   make
   ```
   This will:
   - Preprocess `Blink.ino` to `Blink.cpp`.
   - Compile to object files.
   - Link into `blink.elf`.
   - Convert to `blink.hex` (the uploadable firmware).

   Expected output:
   ```
   Preprocessing Blink.ino
   avr-gcc ... -c Blink.cpp -o Blink.o
   avr-gcc ... Blink.o -o blink.elf
   avr-objcopy ... blink.elf blink.hex
     text    data     bss     dec     hex filename
      924     146       0    1070     42e blink.elf
   ```

3. **Verify**: The `blink.hex` file is your compiled firmware. Use `avr-size blink.elf` to check size (should be small for this example).

### Uploading (Optional)
If connected via USB:
- Run `make upload` (adjust serial port in Makefile, e.g., `/dev/cu.usbmodem*` on macOS or `COM3` on Windows).
- Requires `avrdude` (installed with AVR tools).

### Troubleshooting
- **Error: Arduino.h not found**: Fix `ARDUINO_CORE` path. Download Arduino core manually if needed from [GitHub](https://github.com/arduino/ArduinoCore-avr) and point to `cores/arduino/`.
- **Larger Projects**: Add more `.ino`/`.cpp` files to `SOURCES`. For libraries, add `-I/path/to/lib` to `INCLUDES`.
- **Different Board**: Change `MCU` (e.g., `atmega2560` for Mega) and `F_CPU`.
- **Advanced**: For full IDE-like features without the GUI, consider Arduino CLI (`curl -fsSL https://raw.githubusercontent.com/arduino/arduino-cli/master/install.sh | sh`), then `arduino-cli compile --fqbn arduino:avr:uno .`.

This setup gives you a fully command-line C++ Arduino workflow. Expand by adding more files or libraries!
