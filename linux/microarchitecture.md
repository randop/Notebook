# x86-64 Microarchitecture Levels & AMD Zen Targets

## x86-64 Feature Levels

Defined in 2020 by AMD, Intel, Red Hat, and SUSE. Each level is a strict superset of the previous.

### x86-64-v1 (baseline)

| Feature | Example instruction |
|---|---|
| CMOV | `cmov` |
| CX8 (CMPXCHG8B) | `cmpxchg8b` |
| FPU | `fld` |
| FXSR | `fxsave` |
| MMX | `emms` |
| OSFXSR | `fxsave` |
| SCE (syscall) | `syscall` |
| SSE | `cvtss2si` |
| SSE2 | `cvtpi2pd` |

- **Reference CPUs:** AMD K8 (2003), Intel Prescott (2004)
- 128-bit XMM registers (16 in 64-bit mode)
- SSE2 is mandatory because the System V AMD64 ABI passes `float`/`double` in XMM registers
- GCC flag: `-march=x86-64`

### x86-64-v2

Adds on top of v1:

| Feature | Example instruction |
|---|---|
| CMPXCHG16B (CX16) | `cmpxchg16b` |
| LAHF-SAHF | `lahf` |
| POPCNT | `popcnt` |
| SSE3 (PNI) | `movddup` |
| SSSE3 | `pshufb` |
| SSE4.1 | `dpps` |
| SSE4.2 | `pcmpestrm` |

- **Reference CPUs:** Intel Nehalem (2008), AMD Bulldozer (2011)
- 128-bit vector width
- GCC flag: `-march=x86-64-v2`
- Adopted as default by RHEL 9, .NET 11 (2026)

### x86-64-v3

Adds on top of v2:

| Feature | Example instruction |
|---|---|
| AVX | `vaddps` |
| AVX2 | `vpaddb` |
| BMI1 | `andn` |
| BMI2 | `bzhi` |
| F16C | `vcvtph2ps` |
| FMA | `vfmadd231ps` |
| LZCNT | `lzcnt` |
| MOVBE | `movbe` |
| XSAVE (OSXSAVE) | `xsave` |

- **Reference CPUs:** Intel Haswell (2013), AMD Excavator (2015)
- 256-bit YMM registers (16 in 64-bit mode)
- VEX encoding, 3-operand form for many instructions
- FMA computes `a*b+c` with a single rounding
- GCC flag: `-march=x86-64-v3`
- Adopted as default by RHEL 10 (2024)

### x86-64-v4

Adds on top of v3:

| Feature | Example instruction |
|---|---|
| AVX512F | `vaddps zmm0, zmm1, zmm2` |
| AVX512BW | `vpmaddubsw zmm0, zmm1, zmm2` |
| AVX512CD | `vconflict` |
| AVX512DQ | `vpmovm2b` |
| AVX512VL | `vaddps ymm0, ymm1, ymm2` |

- **Reference CPUs:** Intel Skylake-SP (2017), AMD Zen 4 (2022)
- 512-bit ZMM registers (32 in 64-bit mode) + 8 K mask registers
- EVEX encoding, 4-operand form
- GCC flag: `-march=x86-64-v4`

---

## AMD Zen Targets (`-march=`)

Each `znverN` enables a specific instruction set **and** a µarch-specific cost model (scheduling, port assignment, vector width preference).

### znver1 (Zen / Zen+)

| Aspect | Detail |
|---|---|
| Family | 17h |
| GCC | ≥ 6.3 (meaningful tuning from 8/9) |
| Vector width | **128-bit native**; 256-bit AVX2 ops split into 2×128-bit µops |
| Pipeline | 4-wide decode, 2×128-bit ALU, 2×128-bit FP |

**Full instruction set enabled:**

MMX, SSE, SSE2, SSE3, SSSE3, SSE4.1, SSE4.2, SSE4A, ABM, CX16, MOVBE, POPCNT, LZCNT, BMI1, BMI2, F16C, FMA, AVX, AVX2, AES, PCLMUL, FSGSBASE, ADX (ADCX), RDRND, RDSEED, MWAITX, SHA, CLZERO, XSAVEC, XSAVES, CLFLUSHOPT

**New over Excavator (bdver4):** ADX, RDRND, RDSEED, MWAITX, SHA, CLZERO, XSAVEC, XSAVES, CLFLUSHOPT, POPCNT

**Key tuning difference vs. x86-64-v3:**
- Prefers 128-bit vector operations (avoids 256-bit AVX2 where possible)
- 256-bit `vfmadd` = 2 µops; 128-bit `vfmadd` = 1 µop
- This is the single most impactful scheduling difference

### znver2 (Zen 2)

| Aspect | Detail |
|---|---|
| Family | 17h |
| GCC | ≥ 9.2 |
| Vector width | **256-bit native** (2×256-bit FP pipes) |

**New over znver1:**

| Feature | Note |
|---|---|
| CLWB | Cache Line Write Back |
| RDPID | Read Processor ID |
| WBNOINVD | Write Back, No Invalidate |

- 256-bit AVX2 ops now execute as single µops
- 256-bit FMA = 1 µop (vs. 2 on znver1)

### znver3 (Zen 3)

| Aspect | Detail |
|---|---|
| Family | 19h |
| GCC | ≥ 10.3 |
| Vector width | 256-bit native |

**New over znver2:**

| Feature | Note |
|---|---|
| VAES | Vector AES |
| VPCLMULQDQ | Vector carry-less multiply |
| PKU | Protection Keys for Userspace |

**Tuning changes:**
- `idiv` latency reduced (shorter pipeline for integer division)
- Gather instructions (AVX2 `vgather`) now beneficial (faster than znver1/2)

### znver4 (Zen 4)

| Aspect | Detail |
|---|---|
| Family | 19h |
| GCC | ≥ 12.3 |
| Vector width | **256-bit native for AVX-512** (512-bit ops split into 2×256-bit) |

**New over znver3 (AVX-512 modules):**

| Module | Function |
|---|---|
| AVX512F | 512-bit foundation, EVEX encoding, opmasks, broadcast |
| AVX512DQ | Double/quadword integer ops |
| AVX512IFMA | Integer FMA |
| AVX512CD | Conflict detection |
| AVX512BW | Byte/word integer ops |
| AVX512VL | Vector length agnostic (128/256-bit) |
| AVX512BF16 | Brain Floating Point 16 |
| AVX512VBMI | Vector byte manipulation |
| AVX512BITALG | Bit algebra |

**Tuning:**
- 512-bit instructions decode to 2×256-bit µops (same as znver1 did for 256-bit)
- `TARGET_AVX512_SPLIT_REGS` flag: 512-bit vectors split internally to 256-bit for scheduling
- AVX-512 move/store by pieces enabled

### znver5 / znver6

- **znver5:** Added in GCC 15 (2025). Family 1Ah (Zen 5). Adds AVX512VPOPCNTDQ, AVX512FP16, AVXVNNI, VPEXT, and other newer extensions. 512-bit ops still split to 256-bit.
- **znver6:** Added in GCC 16 (2025–2026). Same AVX-512 split-256 model, updated cost model.

---

## Superset Relationships

```
x86-64-v1 ⊂ x86-64-v2 ⊂ x86-64-v3 ⊂ x86-64-v4

znver1 ⊃ x86-64-v3  (adds ADX, FSGSBASE, RDRND, SHA, CLZERO, etc.)
znver2 ⊃ znver1     (adds CLWB, RDPID, WBNOINVD)
znver3 ⊃ znver2     (adds VAES, VPCLMULQDQ, PKU)
znver4 ⊃ znver3     (adds full AVX-512)
znver5 ⊃ znver4     (adds AVX512VPOPCNTDQ, FP16, etc.)
znver6 ⊃ znver5
```

---

## Practical `-march` / `-mtune` Combinations

| Goal | Flags |
|---|---|
| Maximum portability (any 64-bit x86) | `-march=x86-64` |
| Any post-2008 CPU | `-march=x86-64-v2` |
| Any post-2013 CPU (Haswell+/Zen+) | `-march=x86-64-v3` |
| Any AVX-512 CPU | `-march=x86-64-v4` |
| Best perf on known Zen 1 | `-march=znver1` |
| Best perf on known Zen 2 | `-march=znver2` |
| Best perf on known Zen 3 | `-march=znver3` |
| Best perf on known Zen 4 | `-march=znver4` |
| Portable binary, tuned for Zen 3 | `-march=x86-64-v3 -mtune=znver3` |

---

## Checking Your CPU Level

```bash
# /proc/cpuinfo flags
grep -oP '(?<=flags\s).*' /proc/cpuinfo | head -1 | tr ' ' '\n' | sort

# Or use the dedicated tool:
x86-64-level
```

```bash
# Quick check for v3:
grep -oP '\b(avx2|bmi2|fma|f16c)\b' /proc/cpuinfo | sort -u
# If all four present → v3 capable
```
