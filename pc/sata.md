# SATA

Common SATA power cables from ATX PSUs use 5 wires that fan out to the 15 pins internally (each wire bridges to 2-3 adjacent pins in the connector housing):

| Wire color | Voltage | Pins it feeds |
|---|---|---|
| Orange | +3.3V | 1, 2, 3 |
| Black | GND | 4, 5, 6 |
| Red | +5V | 7, 8, 9 |
| Black | GND | 10, 11*, 12 |
| Yellow | +12V | 13, 14, 15 |

*Pin 11 is the reserved/staggered-spin-up pin — on cheap cables it's just tied to the same ground wire as 10/12.

