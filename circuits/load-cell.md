# Load Cell

### Load Cell Amplifier HX711

#### Components Needed
- Load cell (e.g., 1 kg, 5 kg, 20 kg, 50 kg bar type — choose based on max weight)
- HX711 24-bit ADC amplifier module (cheap, ~$1–2, designed exactly for load cells)
- Microcontroller (Arduino Uno/Nano, ESP32, etc.)
- Optional: LCD/OLED display, buttons for tare/calibration

#### Wiring (Typical for Arduino)
- Load cell has 4 wires: Red (E+), Black (E-), White (A-), Green (A+)
- Connect to HX711:
  - E+ → Red
  - E- → Black
  - A- → White
  - A+ → Green
- HX711 to microcontroller:
  - VCC → 5V
  - GND → GND
  - DT (Data) → e.g., digital pin 3
  - SCK (Clock) → e.g., digital pin 2

#### Basic Steps to Read Weight
1. **Install HX711 library** in Arduino IDE (by bogde or similar — search "HX711" in Library Manager).
2. **Tare** (zero) the scale with no weight.
3. **Calibrate** using a known weight (e.g., 500 g bag of rice/sugar).
4. Read the value and convert to grams/kg.

#### Example Arduino Code (Calibration + Reading)
```cpp
#include "HX711.h"

#define DOUT  3   // Data pin
#define CLK   2   // Clock pin

HX711 scale;

float calibration_factor = -7050;  // ← Change this after calibration!

void setup() {
  Serial.begin(9600);
  Serial.println("HX711 scale demo - Remove all weight!");
  
  scale.begin(DOUT, CLK);
  scale.set_scale();
  scale.tare();  // Reset to 0
  
  Serial.println("Place known weight → adjust factor with + / - keys");
}

void loop() {
  scale.set_scale(calibration_factor);  // Apply calibration
  
  Serial.print("Weight: ");
  Serial.print(scale.get_units(10), 2);  // Average of 10 readings
  Serial.println(" kg");
  
  // Simple serial calibration adjustment
  if (Serial.available()) {
    char temp = Serial.read();
    if (temp == '+' || temp == 'a') calibration_factor += 10;
    else if (temp == '-' || temp == 'z') calibration_factor -= 10;
    Serial.print("New calibration factor: "); Serial.println(calibration_factor);
  }
  
  delay(500);
}
```

#### Calibration Process
- Run code with **no weight** → tare happens automatically.
- Put known weight (e.g., 1 kg) on scale.
- See raw/uncalibrated reading in serial monitor.
- Adjust `calibration_factor` (using + / - in serial monitor) until displayed value matches real weight.
- Hardcode the final factor in your main sketch.

Typical factor range: -400 to -5000 depending on load cell capacity & setup.

This gives good accuracy (often 0.1–1 g depending on cell quality) after proper mechanical mounting (rigid base + platform).

## References:
- [https://learn.sparkfun.com/tutorials/load-cell-amplifier-hx711-breakout-hookup-guide/all](https://learn.sparkfun.com/tutorials/load-cell-amplifier-hx711-breakout-hookup-guide/all)
