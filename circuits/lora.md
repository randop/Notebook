# LoRa modules

Here’s a detailed **comparison** between the **Ebyte E32-433T30D** and **E32-900T20D**, two popular UART LoRa modules based on Semtech chips (SX1278 for 433 MHz and SX1276 for 900 MHz). Both are excellent for long-range, low-power wireless communication, but they differ significantly in frequency, power, range, size, and regional suitability.

### Key Specifications Comparison

| Feature                  | E32-433T30D                          | E32-900T20D                          | Winner / Notes |
|--------------------------|--------------------------------------|--------------------------------------|----------------|
| **Frequency Band**      | 410–441 MHz (default 433 MHz)       | 862–931 MHz (covers 868/915 MHz)    | Depends on region |
| **Max Transmit Power**  | 30 dBm (~1 W)                       | 20 dBm (~100 mW)                    | 433T30D (much stronger) |
| **Typical Range** (open line-of-sight, 5 dBi antenna, 2.4 kbps) | Up to **8 km**                     | Up to **5.5 km**                    | 433T30D |
| **Obstacle Penetration** (buildings, trees, urban) | **Better** (lower frequency → longer wavelength) | Good, but noticeably worse than 433 MHz | 433T30D |
| **Antenna Size**        | Larger antennas for optimal performance | Smaller antennas (easier integration) | 900T20D (more compact) |
| **Power Consumption** (TX @ max power) | ~460–520 mA                        | ~110–130 mA                         | 900T20D (lower draw) |
| **Size**                | 24 × 43 mm (DIP with SMA)           | 16 × 26 mm (more compact)           | 900T20D |
| **Regulatory / Region** | Good in many parts of Asia, Europe, etc. (check local limits — often 10–25 mW max in some countries) | Preferred in North America (915 MHz), Europe (868 MHz); higher power allowed in ISM bands | 900T20D (more globally ISM-friendly) |
| **Chip**                | SX1278                              | SX1276                              | — |
| **Interface**           | UART TTL (same)                     | UART TTL (same)                     | Tie |
| **Air Data Rate**       | 0.3–19.2 kbps (default 2.4 kbps)   | Same                                | Tie |
| **Price** (approx.)     | Slightly higher                     | Usually cheaper                     | 900T20D |

### When to Choose Each Module

- **Choose E32-433T30D** if you need:
  - **Maximum possible range** (especially in open/rural areas).
  - **Best penetration** through obstacles (urban, forested, or indoor-outdoor scenarios).
  - You can legally use high power (30 dBm) in your country/region.
  - You don’t mind a larger module and bigger antennas.

- **Choose E32-900T20D** if you need:
  - **Smaller size** and easier integration (great for compact devices).
  - **Better compatibility** with global ISM regulations (especially North America 915 MHz or Europe 868 MHz).
  - **Lower power consumption** during transmission.
  - You’re okay with ~30–40% less range compared to the 433T30D in ideal conditions.

### Quick Summary Recommendation

- For **maximum range and penetration** (rural, long-distance, or difficult environments) → **E32-433T30D** (if power regulations allow it).
- For **compact projects, global ISM compliance, or North America/Europe** → **E32-900T20D**.

Both modules use the same UART interface, configuration commands, and LoRa library support (e.g., the popular Arduino LoRa_E32 library), so switching between them is mostly plug-and-play once you adjust frequency/channel settings.

**Here’s a comparison of the two main sub-GHz unlicensed frequency bands** commonly used for low-power wide-area applications like LoRa/LoRaWAN, remote controls, sensors, IoT, and short-range devices (SRDs): **410–441 MHz** (centered around **433 MHz**) vs **862–931 MHz** (covering **868 MHz** in Europe and **915 MHz** in the Americas/Australia, within the broader 902–928 MHz ISM band).

These bands are **not interchangeable globally** due to major regulatory differences. The choice depends heavily on your target country/region, required range, interference tolerance, data rate, and antenna size.

### Key Physical & Performance Differences
Lower frequencies generally offer better propagation (longer range, better building/terrain penetration), but they come with trade-offs in bandwidth, data rates, and global availability.

- **433 MHz** (410–441 MHz range, typically 433.05–434.79 MHz)  
  → **Longer range** and **excellent penetration** through obstacles (walls, foliage, etc.) due to lower frequency.  
  → Narrower bandwidth (~1.74 MHz) → Lower data rates, fewer channels, more susceptible to congestion in crowded areas.  
  → Larger antennas needed (roughly 17–18 cm for quarter-wave).  

- **868/915 MHz** (862–931 MHz range, typically 863–870 MHz in Europe or 902–928 MHz in Americas)  
  → Shorter range than 433 MHz but still excellent for sub-GHz (often 2–5× better than 2.4 GHz Wi-Fi).  
  → Wider bandwidth (up to 26 MHz in 902–928 MHz) → Higher data rates, more channels, better for dense networks.  
  → Smaller antennas (around 8 cm for quarter-wave).  

Here are some real-world examples of typical performance in open rural conditions (actual results vary with power, antennas, modulation, and environment):

| Aspect                  | 433 MHz (e.g., EU433)                  | 868 MHz (e.g., EU868)                  | 915 MHz (e.g., US915)                  |
|-------------------------|----------------------------------------|----------------------------------------|----------------------------------------|
| **Typical Range** (LoRa SF12, rural) | 10–20+ km (excellent penetration)     | 5–15 km                               | 5–15 km (good, but less penetration)  |
| **Penetration**         | Best (low freq)                        | Good                                  | Good                                  |
| **Data Rate Potential** | Low (narrow band)                      | Medium                                | High (wider band + more channels)     |
| **Antenna Size**        | Larger (~17 cm)                        | Smaller (~8 cm)                       | Smaller (~8 cm)                       |

### Regulatory & Country Rules Overview (as of 2026)
These are **unlicensed ISM/SRD bands**, but rules vary significantly — always verify with your national regulator (e.g., FCC, ETSI/CE, ACMA) as duty cycles, power limits, and allowed uses change.

| Region / Major Countries          | 433 MHz (410–441 MHz) Allowed?                  | 868 MHz (863–870 MHz) Allowed?         | 915 MHz (902–928 MHz) Allowed?         | Typical Max Power & Key Restrictions                  |
|-----------------------------------|-------------------------------------------------|----------------------------------------|----------------------------------------|-------------------------------------------------------|
| **Europe (EU/ETSI countries)**    | Yes — License-free ISM/SRD (433.05–434.79 MHz) | Yes — Main band (EU868)                | Limited/No (not harmonized)            | 433: ≤10 mW, 10% duty cycle<br>868: ≤25 mW, strict 0.1–1% duty cycle or LBT |
| **United States (FCC)**           | Limited (Part 15.231 only for specific uses like alarms; very low power otherwise) | No                                     | Yes — Main band (US915)                | Up to 1W (with FHSS/digital modulation), no strict duty cycle but interference rules |
| **Canada**                        | Similar to US (limited)                         | No                                     | Yes                                    | Similar to FCC                                        |
| **Australia / New Zealand**       | Yes (limited)                                   | Limited                                | Yes (915–928 MHz)                      | Similar to FCC, some LBT options                      |
| **Asia (varies by country)**      | Often Yes (e.g., many countries allow 433 MHz) | Sometimes (country-specific)           | Yes in some (e.g., AS923 variants)     | Varies widely; e.g., China CN470 or AS923             |
| **Other (e.g., Russia, some CIS)**| Yes (often higher power tolerated)              | Varies                                 | Varies                                 | Check local rules                                     |

**Quick rule of thumb**:
- **Want to sell/deploy in Europe?** → Use **868 MHz** (EU868) primarily; 433 MHz is allowed but narrower & more restricted.
- **Want to sell/deploy in North America?** → Use **915 MHz** (US915); 433 MHz is heavily restricted.
- **Want maximum global compatibility with one device?** → 433 MHz is more widely tolerated in Asia/Europe, but **not** in the US. Many modern LoRa modules support multi-band (433/868/915) with software switching.

### Summary Recommendation
- Choose **433 MHz** if you prioritize **maximum range/penetration** and are targeting Europe, Asia, or regions without strict US-style restrictions.
- Choose **868/915 MHz** for better bandwidth, higher data rates, smaller antennas, and compliance in the largest markets (Europe for 868, Americas/Australia for 915).

For the most up-to-date details, consult the **LoRa Alliance Regional Parameters** document or your local spectrum authority, as rules evolve (e.g., some countries add LBT options or change duty cycles). If you're designing a product, multi-band hardware is often the safest path!

For modules used **in the Philippines**, the recommended and most practical choice among the bands you asked about (**410–441 MHz / 433 MHz** vs **862–931 MHz / 868/915 MHz**) is the **higher frequency range around 915 MHz** (specifically the **AS923-3** plan, which operates in **915–918 MHz**).

This is the primary band actively used for modern LoRa/LoRaWAN deployments in the country, as confirmed by community usage, LoRa Alliance regional parameters, and device vendors.

### Why 915 MHz (AS923-3) is the Best Choice in the Philippines
- The **Philippines** follows an Asia-Pacific style allocation in the **902–928 MHz ISM band**, but with a narrower subset for LoRaWAN: **915–918 MHz** using the **AS923-3** frequency plan (shifted down by ~6.6 MHz from the base AS923 to fit local rules).
- This allows decent bandwidth, multiple channels, higher data rates (compared to 433 MHz), and reasonable range/penetration for IoT applications.
- **Power limits** are typically up to **25 mW** (similar to many Asian plans), with no extremely strict duty cycle like Europe's 868 MHz.
- Active LoRaWAN networks (including Helium hotspots, community gateways, and commercial IoT) predominantly use this band.
- Smaller antennas (~8 cm quarter-wave) are easier to implement.

**Real-world performance** (LoRa SF12, rural/open conditions): Expect **5–12+ km** range depending on antenna height, terrain, and power — good balance for urban/rural Philippine environments.

### Status of 433 MHz (410–441 MHz) in the Philippines
- Allowed in principle as a low-power SRD/ISM band (similar to many Asian countries).
- Listed in some older or general summaries as an option (EU433 style).
- **However**, it is **not widely used** for LoRa/LoRaWAN in practice:
  - Narrow bandwidth (~1.7 MHz) → fewer channels, lower data rates, higher congestion risk.
  - Larger antennas required (~17 cm).
  - Less commercial/ecosystem support compared to 915 MHz.
  - Some sources indicate it's **not fully license-free** for public/gateway networks without checks (similar notes for 868 MHz).

You can technically use 433 MHz for private, very low-power, short-range projects, but it's **not recommended** for new deployments or anything needing good ecosystem compatibility/range trade-offs.

### Status of 868 MHz (EU863-870)
- Sometimes listed as technically possible (EU868 style).
- **Rarely used** in the Philippines.
- Faces the same issues as in Europe (strict 0.1–1% duty cycle, lower power ~25 mW max).
- Not aligned with local Asian-Pacific allocations → poor ecosystem support.

### Quick Comparison for Philippines Use

| Band                  | Allowed?              | Practical Usage in PH | Typical Range (LoRa) | Channels/Bandwidth | Antenna Size | Recommendation |
|-----------------------|-----------------------|-----------------------|----------------------|--------------------|--------------|----------------|
| **433 MHz** (410–441) | Yes (limited/low-power) | Low/rare             | 8–18+ km (best penetration) | Narrow (~1.7 MHz) | Large (~17 cm) | Avoid for new projects |
| **868 MHz** (EU868)   | Possible (but not common) | Very low             | 5–12 km             | Medium            | Small (~8 cm) | Avoid |
| **915 MHz** (AS923-3) | Yes — Primary band   | High (recommended)   | 5–12+ km            | Good (subset of 902–928) | Small (~8 cm) | **Strongly preferred** |

**Bottom line**:  
For compliance, performance, and future-proofing in the **Philippines** (as of January 2026), choose a **multi-band LoRa module** that supports **915 MHz / AS923-3** (most modern SX1262/SX1268-based or Helium-compatible modules do). Configure it to AS923-3 specifically.

Always double-check the latest **NTC (National Telecommunications Commission)** guidelines or consult a local LoRaWAN community (e.g., LoRaWAN PH Facebook group) for any updates, as spectrum rules can evolve. If you're building a commercial product, get it type-approved by NTC for the chosen band!
