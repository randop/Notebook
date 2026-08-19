# Networking: Fiber Optics

## Singlemode Fiber

**OS1** — indoor-rated, tight-buffered, PVC or LSZH jacket. Core/cladding: 9/125 µm. Attenuation spec: ≤1.0 dB/km at both 1310 nm and 1550 nm. Older ITU-T G.652 spec, mostly superseded but still sold for short indoor patch runs and cross-connects. Corresponds to ITU-T G.652 (original, subtypes A/B), sometimes G.652.C. Standardized under IEC 60793-2-50 as fiber type B1.1/B1.3. TIA equivalent designation: TIA-492CAAB.

**OS2** — the current standard for almost all singlemode deployment, indoor or outdoor. Loose-tube construction, often gel-filled or gel-free (dry) for outdoor/direct-burial runs. Same 9/125 µm core, but tighter attenuation: ≤0.4 dB/km at 1310 nm, ≤0.4 dB/km at 1550 nm (some specs list 0.35/0.22 dB/km depending on fiber grade). Low water peak fiber (G.652.D), meaning it can also be used across the E-band (1383 nm) without the absorption spike older fiber had — relevant if you're running CWDM. Max unamplified reach in the tens of km, well past 100 km with amplification/DWDM. This is what you want for SFP+/SFP28 long-reach optics (LR, ER, ZR) and any outdoor rack-to-rack fiber run. Corresponds to ITU-T G.652.D (low water peak, zero water peak fiber). IEC designation B1.3. Also the base fiber used when referencing ITU-T G.657 (bend-insensitive variant, A1/A2/B2/B3 subtypes, common in FTTH drop cables where tight bend radii are needed). The OS2 designation itself comes from ISO/IEC 11801 and TIA-568.3-D, which define the OS1/OS2 category system for premises cabling.

Both OS1/OS2 are yellow jacketed by TIA/EIA convention. Other ITU-T singlemode specs you'll see referenced in long-haul/DWDM contexts: G.653 (dispersion-shifted), G.654 (cutoff-shifted, submarine), G.655 (non-zero dispersion-shifted, DWDM-optimized). These aren't OS1/OS2 category names, they're the underlying fiber physics specs.

## Multimode Fiber

**OM1** — orange jacket, 62.5/125 µm core, LED-driven, legacy. 10GBASE-SR reach: ~33 m. Basically obsolete for new builds.

**OM2** — orange jacket, 50/125 µm core, still LED era. 10GBASE-SR reach: ~82 m.

**OM3** — aqua jacket, laser-optimized 50/125 µm (VCSEL, 850 nm). 10GBASE-SR: 300 m. 40/100GBASE-SR4: ~100 m. This is the practical minimum for 10G multimode in a datacenter/homelab context.

**OM4** — aqua or sometimes violet/erika-violet jacket, same 50/125 µm core but tighter bandwidth spec (4700 MHz·km EMB vs OM3's 2000). 10GBASE-SR: 550 m. 40/100GBASE-SR4: 150 m.

**OM5** — lime green jacket ("wideband multimode fiber," WBMMF). Same 50/125 µm geometry but characterized across 850–953 nm to support SWDM (shortwave WDM) — multiple wavelengths down one fiber pair instead of parallel fiber, used for 40G/100G/200G over duplex LC instead of MPO. Reach is similar to OM4 for single-wavelength 850nm use, longer for SWDM applications.

The OM-number system itself comes from **ISO/IEC 11801** and **IEC 60793-2-10**, which classify multimode fiber under type **A1** with sub-numbering:

- **OM1** — IEC A1b
- **OM2** — IEC A1a.2
- **OM3** — IEC A1a.2 (laser-optimized, defined via **IEC 60793-2-10** amendment adding effective modal bandwidth/EMB requirements), TIA designation **492AAAC**
- **OM4** — IEC A1a.3, TIA **492AAAD**
- **OM5** — IEC A1a.4 (WBMMF), TIA **492AAAE**, formalized in **TIA-492AAAE** and **IEC 60793-2-10:2019**

## Quick reference

| Type | Core (µm) | Jacket | Typical use |
|---|---|---|---|
| OS1 | 9/125 | Yellow | Indoor SM patching |
| OS2 | 9/125 | Yellow | Indoor/outdoor SM, long haul, DWDM/CWDM |
| OM1 | 62.5/125 | Orange | Legacy LED, short runs |
| OM2 | 50/125 | Orange | Legacy LED, short runs |
| OM3 | 50/125 | Aqua | 10G, up to 300m |
| OM4 | 50/125 | Aqua/violet | 10G/40G, up to 550m/150m |
| OM5 | 50/125 | Lime green | SWDM 40/100/200G |

## IEEE 802.3 link-layer references

- **10GBASE-SR/LR/ER** — **IEEE 802.3ae** (2002, folded into 802.3-2008 and later consolidated editions)
- **40GBASE-SR4/LR4**, **100GBASE-SR4/LR4/ER4** — **IEEE 802.3ba** (2010)
- **25GBASE-SR/LR** — **IEEE 802.3by** (2016)
- **200GBASE-SR4/DR4/FR4/LR4**, **400GBASE series** — **IEEE 802.3bs** (2017) and **802.3cd** (2018)
- SWDM4 (the OM5 multi-wavelength scheme) isn't an IEEE spec itself — it's a MSA (multi-source agreement) from the SWDM Alliance, layered on top of 802.3ba/by physical parameters.

## Connector polish standards

- UPC/APC geometry is defined under **IEC 61754** (series, by connector type — e.g. IEC 61754-20 for LC).
- Return loss and insertion loss test methods: **IEC 61300-3-6** (return loss), **IEC 61300-3-4** (insertion loss).

## Practical notes for SFP/SFP+ optics

- Singlemode optics (LX, LR, ER, ZR) use 1310 nm or 1550 nm and require OS1/OS2 fiber. Pairing a singlemode optic with multimode fiber, or vice versa, results in massive signal loss or no link at all — the core diameters are wildly mismatched (9 µm vs 50/62.5 µm).
- Multimode optics (SX, SR) use 850 nm VCSELs and are cheaper per port but distance-limited.
- BiDi optics push both TX/RX over a single strand using two different wavelengths (commonly 1310/1550 nm pairs), halving fiber count for a given link — works on both OS1 and OS2, though OS2's tighter attenuation spec gives more margin.
- Connector polish matters independently of fiber type: UPC (blue, flat polish, ~-55dB return loss) vs APC (green, 8° angled polish, ~-65dB return loss). APC is standard for singlemode telecom/FTTH runs; UPC is common for multimode and shorter SM patches. Mixing UPC and APC connectors on the same mated pair causes high insertion loss from the angle mismatch.
