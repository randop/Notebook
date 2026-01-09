# Embedded Cryptography

### Libsodium
Libsodium is a portable, modern cryptography library that can be used on embedded platforms like ESP32 and STM32 (ARM Cortex-M series), though with some limitations and manual configuration required.

### Official Source
Download and build from the official repository:  
https://github.com/jedisct1/libsodium

Documentation:  
https://libsodium.gitbook.io/doc/

### For ESP32 (Recommended Approach)
Espressif provides an official ported component for ESP-IDF projects.

- Add it as a dependency in your ESP-IDF project:  
  ```
  idf.py add-dependency "espressif/libsodium^1.0.20~2"
  ```
- This integrates libsodium (version based on 1.0.20) seamlessly, supporting encryption, signatures, password hashing, etc.
- Details and registry page: https://components.espressif.com/components/espressif/libsodium

If using Arduino-ESP32 framework, older versions included it directly, but newer ones (v3+) may require manual addition or the IDF component approach.

### For STM32 and Other ARM Cortex-M Devices
Libsodium is not officially tested or optimized for Cortex-M0/M3/M4, and the docs explicitly warn:  
**"Using libsodium on ARM Cortex M0, M3, and M4 CPUs is untested and not recommended if side-channels are a concern."**  
(Timing side-channels may be an issue due to unoptimized implementations.)

It can still be cross-compiled and used successfully (community reports success on STM32F4, for example). Use the ARM embedded toolchain:

1. Install arm-none-eabi-gcc toolchain.
2. Configure and build:
   ```
   export PATH=/path/to/arm-none-eabi-gcc/bin:$PATH
   export CFLAGS='-Os -mcpu=cortex-m4'  # Adjust -mcpu for your core (e.g., cortex-m3, cortex-m33)
   export LDFLAGS='--specs=nosys.specs'
   ./configure --host=arm-none-eabi --prefix=/your/install/path
   make
   make install
   ```
3. Integrate the static library (.a) into your STM32 project (e.g., via STM32CubeIDE, Makefile, or CMake).

- Use `-Os` for size optimization.
- Avoid link-time optimization (LTO) and sanitizers, as they can introduce issues.

#### Minimal Build for Reduced Size
For resource-constrained devices, build a minimal version (includes only core high-level APIs like secretbox, auth, etc., significantly reducing code size):
```
./configure --enable-minimal [other options as above]
make
make install
```

This is useful for microcontrollers with limited flash/RAM.

If side-channel resistance or extreme size constraints are critical, consider alternatives like Monocypher (libsodium-inspired, smaller footprint) or hardware-accelerated crypto via STM32's Crypto peripheral (with STM32Cube Crypto Library).

Community examples are sparse, but the above compilation works for bare-metal/no-OS setups. Test thoroughly on your target hardware.

### Monocypher
Monocypher is a compact, audited cryptography library (~10-15 KB code size) designed for embedded systems. It is portable, has no dependencies, and provides libsodium-like APIs. Download `monocypher.h` and `monocypher.c` from https://monocypher.org/download and include them in your project.

#### Authenticated Symmetric Encryption (XChaCha20-Poly1305 AEAD)
```c
#include "monocypher.h"  // Single header

// Shared secret key (32 bytes) and nonce (8 bytes) – generate securely in practice
uint8_t key[32]   = { /* 32-byte key */ };
uint8_t nonce[8]  = { /* 8-byte nonce */ };

uint8_t plaintext[128] = { /* your message */ };
size_t  pt_len = sizeof(plaintext);

uint8_t ciphertext[128 + 16];  // +16 bytes for authentication tag
size_t  ct_len;

// Encrypt
crypto_aead_lock(ciphertext, &ct_len, plaintext, pt_len,
                 NULL, 0,          // No additional authenticated data
                 nonce, key);

// Decrypt and verify
uint8_t decrypted[128];
if (crypto_aead_unlock(decrypted, pt_len, ciphertext, ct_len,
                       nonce, key) != 0) {
    // Authentication failed (tampered or wrong key)
}
```
**Notes**: Constant-time on all platforms; very small footprint; ideal for constrained STM32/ESP32.

#### Public-Key Signature (EdDSA)
```c
#include "monocypher.h"

uint8_t secret_key[32] = { /* your secret key */ };
uint8_t public_key[32];  // Derive: crypto_eddsa_key_pair(public_key, secret_key, seed);

crypto_from_eddsa_public(public_key, eddsa_pk);  // If needed for interop

uint8_t signature[64];
crypto_eddsa_sign(signature, NULL, 0, "Hello", 5, public_key, secret_key);

// Verify
if (crypto_eddsa_check(signature, "Hello", 5, public_key) != 0) {
    // Invalid signature
}
```

### STM32 Hardware-Accelerated Crypto Example
Many STM32 series (L4/L5/H7/U5/WB etc.) have dedicated AES hardware with GCM support for authenticated encryption. Use the HAL driver (hardware-accelerated automatically). Enable the AES peripheral and clock in STM32CubeMX.

#### AES-256-GCM Authenticated Encryption (Newer STM32 with AES peripheral)
```c
#include "stm32xx_hal.h"  // e.g., stm32l5xx_hal.h

AES_HandleTypeDef haes;

// In your init code
haes.Instance = AES;
HAL_AES_Init(&haes);

// Buffers
uint8_t key[32]      = { /* 32-byte key */ };
uint8_t iv[12]       = { /* 12-byte IV/nonce – recommended size */ };
uint8_t aad[16]      = { /* optional additional authenticated data */ };
uint8_t plaintext[128];
size_t  pt_len = sizeof(plaintext);
uint8_t ciphertext[128];
uint8_t tag[16];  // 16-byte authentication tag

// Encrypt (with optional AAD)
if (HAL_AES_GCM_Encrypt(&haes, plaintext, pt_len, key, iv, sizeof(iv),
                        aad, sizeof(aad), ciphertext, tag) != HAL_OK) {
    // Error
}

// Decrypt and verify
uint8_t decrypted[128];
uint8_t computed_tag[16];
if (HAL_AES_GCM_Decrypt(&haes, ciphertext, pt_len, key, iv, sizeof(iv),
                        aad, sizeof(aad), decrypted, computed_tag) != HAL_OK) {
    // Error
}
if (HAL_CRYP_GetError(&haes) != HAL_CRYP_ERROR_AUTHENTICATION || 
    memcmp(tag, computed_tag, 16) != 0) {
    // Authentication failed
}

// Deinit when done
HAL_AES_DeInit(&haes);
```
**Notes**: Fully hardware-accelerated on supported MCUs; very fast. For older series with CRYP peripheral (e.g., F4/F7), use `HAL_CRYPEx_AESGCM_Encrypt()`/`Decrypt()` (multi-phase: init, header, payload, finish). For more algorithms/portability, use ST's X-CUBE-CRYPTOLIB (CMOX) which leverages hardware where available – download from st.com for full examples.

---
### References
- Official Repository: https://github.com/jedisct1/libsodium
- Official Documentation: https://libsodium.gitbook.io/doc/
- Espressif libsodium Component (ESP-IDF/ESP32): https://components.espressif.com/components/espressif/libsodium
- Official Website: https://monocypher.org/
- Direct Download Page: https://monocypher.org/download
- Monocypher GitHub Mirror (latest source): https://github.com/LoupVaillant/Monocypher
- X-CUBE-CRYPTOLIB (ST Crypto Library with hardware acceleration): https://www.st.com/en/embedded-software/x-cube-cryptolib.html
- STM32CubeMX (configuration tool): https://www.st.com/en/development-tools/stm32cubemx.html
- STM32L4 Series: https://www.st.com/resource/en/reference_manual/rm0432-stm32l4-series-advanced-armbased-32bit-mcus-stmicroelectronics.pdf
- STM32L5 Series: https://www.st.com/resource/en/reference_manual/rm0438-stm32l5-series-advanced-armbased-32bit-mcus-stmicroelectronics.pdf
- STM32H7 Series: https://www.st.com/resource/en/reference_manual/rm0433-stm32h742-743-753-and-750-value-line-advanced-armbased-32bit-mcus-stmicroelectronics.pdf
- STM32U5 Series: https://www.st.com/resource/en/reference_manual/rm0456-stm32u5-series-arm-cortex-m33-mcus-stmicroelectronics.pdf
- STM32WB Series: https://www.st.com/resource/en/reference_manual/rm0434-stm32wb-series-arm-cortex-m4m0-mcus-stmicroelectronics.pdf
