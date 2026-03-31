# STM32

## STM32

**STM32** is a family of 32-bit microcontrollers manufactured by **STMicroelectronics**, based on ARM Cortex-M processor cores.

---

### Name Breakdown

- **ST** — STMicroelectronics, the manufacturer
- **M** — Microcontroller
- **32** — 32-bit architecture

---

### Architecture

STM32 devices are built on ARM **Cortex-M** cores, spanning several variants:

- **Cortex-M0 / M0+** — minimal silicon area, lowest power
- **Cortex-M3** — moderate performance, hardware multiply/divide
- **Cortex-M4** — adds DSP instructions and optional FPU (Floating Point Unit)
- **Cortex-M7** — higher performance, dual-issue pipeline, larger caches
- **Cortex-M33** — ARMv8-M, adds TrustZone security extensions
- **Cortex-M55 / M85** — newer cores with Helium (M-Profile Vector Extension)

---

### Product Lines

ST organizes STM32 into series, each targeting a different segment:

| Series | Focus |
|---|---|
| **STM32F0, F1, F3** | General-purpose, entry-level |
| **STM32F4, F7** | High-performance |
| **STM32H7** | Very high performance (up to 480 MHz) |
| **STM32L0, L1, L4, L5** | Ultra-low power |
| **STM32G0, G4** | Cost-optimized / mixed-signal |
| **STM32U5** | Ultra-low power with Cortex-M33 |
| **STM32WB, WL** | Wireless (Bluetooth, Zigbee, LoRa) |
| **STM32MP1** | Hybrid MPU+MCU (runs Linux) |

---

### Internal Peripherals

A typical STM32 device integrates:

- **GPIO** — General Purpose Input/Output pins
- **Timers** — general-purpose, advanced-control, basic, and low-power variants
- **ADC / DAC** — analog-to-digital and digital-to-analog converters
- **USART / UART** — serial communication
- **SPI / I2C / I2S** — synchronous serial buses
- **USB** — Full-Speed or High-Speed depending on series
- **CAN / FDCAN** — controller area network for automotive/industrial
- **DMA** — Direct Memory Access controller
- **RTC** — Real-Time Clock
- **Watchdog timers** — IWDG (independent) and WWDG (window)
- **Flash & SRAM** — on-chip program and data memory

---

### Memory Architecture

STM32 uses a **Harvard-like modified bus matrix**, where:

- **Flash** stores the program (typically 16 KB to several MB)
- **SRAM** is used for runtime data (often split into multiple banks)
- **CCM RAM** (Core Coupled Memory) — on some series, tightly coupled to the CPU for zero-wait-state access
- Peripherals are **memory-mapped** into the 4 GB ARM address space

---

### Clock System

The clock tree includes multiple sources:

- **HSI** — High-Speed Internal RC oscillator
- **HSE** — High-Speed External crystal
- **LSI** — Low-Speed Internal (typically for watchdog/RTC)
- **LSE** — Low-Speed External (32.768 kHz crystal for RTC)
- **PLL** — Phase-Locked Loop to multiply clock frequencies

The **RCC** (Reset and Clock Control) peripheral manages gating, routing, and enabling clocks to each peripheral independently.

---

### Power Modes

STM32 devices support layered low-power states:

- **Sleep** — CPU halted, peripherals active
- **Stop** — most clocks off, RAM retained
- **Standby** — near-full shutdown, only RTC/wakeup logic active
- **Shutdown** — deepest state, minimal leakage

---

### Boot Modes

Three boot configurations are selected via **BOOT0/BOOT1** pins:

- Boot from **user Flash** (normal operation)
- Boot from **System Memory** (ST's built-in bootloader for programming via UART/USB/SPI/I2C)
- Boot from **embedded SRAM**

---

### Development Ecosystem

- **HAL / LL drivers** — ST's official C abstraction layers (Hardware Abstraction Layer and Low-Layer)
- **STM32CubeMX** — graphical pin/clock/peripheral configurator that generates initialization code
- **STM32CubeIDE** — Eclipse-based integrated development environment
- **CMSIS** — ARM's Cortex Microcontroller Software Interface Standard, underlying the drivers
- **OpenOCD / ST-Link** — debugging and flashing interfaces using SWD (Serial Wire Debug) or JTAG
