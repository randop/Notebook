# Low-Voltage Disconnect (LVD)

The **XH-M609** is already a low-voltage disconnect (LVD) module with a built-in relay that disconnects the **load** when battery voltage drops below your set threshold (and reconnects after voltage recovers + hysteresis).

Most people use it in the standard way:

**Battery** ───► **IN+ / IN-** (of XH-M609)  
**OUT+ / OUT-** (of XH-M609) ───► **Load**

When low voltage is triggered → **relay opens** → load power is cut.

If this is **exactly** what you already have and it works well → **you're done** — no extra circuit needed.

### If you really want to completely disconnect the battery (including from the XH-M609 itself)

The problem: the XH-M609 itself continues to draw ~5–15 mA (relay coil + display) even after it has disconnected the load, which can slowly flatten a battery left for weeks/months.

Here are the most practical solutions, from simplest → more complete disconnection:

### Option 1 – Good enough for most people (recommended)
Use the XH-M609 in the normal way + add a good **manual disconnect** (Anderson/powerpole connector / battery switch / big knife switch) near the battery.

When you know you'll leave the system for long time → switch off the battery completely.  
Simple, zero extra parts, zero extra failure points.

### Option 2 – Use bigger external relay (most popular upgrade)

Use the XH-M609 relay output to control a **much bigger/better relay** (or contactor) that can handle your full load current with very low coil consumption.

Typical wiring:

```
Battery+ ────────────────┬──────────────────────────┐
                         │                          │
                         ▼                          │
                    XH-M609 IN+                    │
                         │                          │
                         ▼                          │
               XH-M609 relay contact (common)      │
                         │                          │
                         ▼                          │
               Big relay coil (+) ─────► Big relay coil (-) ───► Battery-
                         │
                         ▼
               Big relay main contacts (NO)
                         │
                         ▼
                      LOAD+
Battery- ─────────────────────────────────────────────► LOAD-
```

- When XH-M609 relay **closes** → big relay coil gets power → big relay closes → load gets power
- When low voltage → XH-M609 relay **opens** → big relay coil loses power → big relay opens → load completely disconnected

**Advantages**:  
- XH-M609 only drives very small coil current (~100–400 mA)  
- Main load current goes through the big relay contacts (can be 40–200 A easily)  
- Still ~5–15 mA standby draw from XH-M609 (acceptable for most applications)

**Popular choices for big relay**:
- Automotive 12V/24V 40–80 A relays (cheap, common)
- 12V/24V contactor 100–200 A (much lower coil power than big relay)
- Solid-state relay (only if your load is DC and you accept ~1–3 mA leakage when off)

### Option 3 – Almost complete shutdown (very low standby current)

Add a **latching relay** (bistable) or **p-channel MOSFET** + some extra logic:

**Simplest realistic version with p-MOSFET** (very low consumption):

```
Battery+ ───► Source of P-MOSFET (IRF4905, IRF9540N, or similar big one)
              Drain of P-MOSFET ───► XH-M609 IN+   +   all your loads in parallel
Battery-  ───────────────────────────────────────► XH-M609 IN-  +  loads -

Gate control circuit (simple version):
XH-M609 OUT+ ───► 1kΩ resistor ───► Gate of P-MOSFET
                            │
                           10kΩ to Battery+
```

**How it works**:
- When XH-M609 relay is **closed** (voltage OK) → OUT+ = Battery+ → Gate pulled low → P-MOSFET on
- When low voltage → XH-M609 relay opens → OUT+ floats → 10k pulls Gate high → MOSFET turns off → **everything** including XH-M609 loses power

**Restart**: you need a **momentary push-button** from Battery+ → Gate (to turn on again).

This method can get standby current down to << 100 µA when off.

Pick the method according to how critical the last few percent of battery capacity is to you.

Most people are happy with **Option 1** or **Option 2** — they give very good protection without much complexity.

Here are the **most practical ways** to achieve **complete shutdown** (including the XH-M609 module itself → **near-zero** or **true zero** standby current after low-voltage disconnect):

### 1. Recommended realistic solution – P-channel MOSFET latching-style shutdown  
This is the most popular & reliable method used with XH-M609 / similar cheap LVD modules.

**How it works**  
- Big **P-channel MOSFET** sits between battery positive and **everything** (XH-M609 IN+ + all loads).  
- When voltage is good → XH-M609 relay pulls MOSFET gate **low** → MOSFET turns ON hard.  
- When low voltage triggers → relay opens → pull-up resistor turns MOSFET **OFF** → **whole system** including XH-M609 loses power (current drops to leakage only, usually << 100 µA).  
- To restart you need a **momentary push-button** from battery+ to gate (pulls gate low briefly → turns MOSFET on → system wakes up).

**Basic circuit** (high-side switch):

```
Battery + ───────┬──────────────────────────────┐
                 │                              │
                 ├───── Source (S)             │
               P-MOSFET                        │
                 ├───── Drain (D) ───► XH-M609 IN+   +   all loads +
                 │                              │
                 Gate ───┬─── 10k–100kΩ ────────┘   (pull-up to Battery+)
                         │
                         ├───── XH-M609 OUT+ ───┬─── 1k–4.7kΩ resistor ───┘
                         │                         (helps pull gate low stronger)
                         │
                       [ momentary NO button ]    ← push to start
                         │
                       Battery +
```

**Component suggestions** (depends on your current & voltage):

| Current     | Voltage     | Good MOSFET choices                  | Rds(on) approx | Package     | Notes                             |
|-------------|-------------|--------------------------------------|----------------|-------------|-----------------------------------|
| <10–15 A    | 12–36 V     | IRF4905, IRF9540N, AO3407, SiA431DJ  | 5–20 mΩ        | TO-220 / DPAK | Very common, cheap                |
| 15–40 A     | 12–48 V     | IRF4905 or better (parallel two)     | <10 mΩ         | TO-220      | Needs small heatsink if >20 A     |
| <5 A small  | 12–24 V     | AO3401, Si2301CDS, DMP3098L          | 20–50 mΩ       | SOT-23      | Tiny, SMD, very low power         |

**Tips**  
- Use logic-level MOSFET if your battery is often < ~12 V (Vgs needs to be enough with your lowest good voltage).  
- Add 100 nF–1 µF capacitor gate-to-source if you get flickering/oscillation during turn-on.  
- The 1k–4.7k resistor from OUT+ to gate is very important – without it the MOSFET may not turn on reliably (weak pull-down through relay alone).

### 2. Even cleaner version – Use latching (bistable) relay

Very popular in off-grid/solar setups.

**Concept**  
- XH-M609 relay controls a **latching relay coil** (pulse to change state).  
- Once low-voltage triggers → short pulse opens main contacts → everything dies including XH-M609.  
- Big advantage: **zero holding current** when off (unlike normal relay).  
- To turn back on → momentary button gives pulse to set coil.

**Common parts**  
- 12 V or 24 V latching relay (1 coil or 2 coil type), 40–100 A contacts  
- Examples: TE Connectivity KUEP, Panasonic HE, Hongfa HFD4, or cheap AliExpress "12V latching relay 80A"  
- Needs simple pulse circuit (capacitor + resistor + diode usually)

Downside: more expensive (~$8–25) and slightly more complex wiring.

### 3. Simplest (but not automatic restart)

Just put a good **200 A battery disconnect switch** (Blue Sea, Anderson SB50/175, or cheap knife switch) right after the battery.  
When you see low voltage happened (or after long storage) → manually disconnect.  
→ Zero extra parts, zero extra failure points, zero quiescent current.

### Quick comparison

| Method                     | Quiescent current when off | Auto reconnect?        | Complexity | Cost extra | Reliability |
|----------------------------|----------------------------|------------------------|------------|------------|-------------|
| Normal XH-M609 only        | ~5–20 mA                   | Yes                    | Very low   | $0         | ★★★         |
| P-MOSFET + button          | <<100 µA (leakage)         | Needs button press     | Low        | $2–8       | ★★★★        |
| Latching relay             | ~0 µA                      | Needs button pulse     | Medium     | $10–30     | ★★★★★       |
| Manual big disconnect      | 0 µA                       | Manual                 | Zero       | $5–20      | ★★★★★       |

For most people who want **automatic cutoff + near zero drain** and are okay pressing a button once to restart → **P-MOSFET version** is the sweet spot.

The practical guide to implement using a **latching (bistable) relay** for **complete shutdown** of the entire system (including the XH-M609 itself) when low voltage is triggered. This gives **true zero quiescent current** when off (no coil holding power needed), which is ideal for long-term battery storage or ultra-low power solar/off-grid setups.

### Why latching relay is cleaner
- Normal relays or the XH-M609 draw continuous current when "on".
- **Latching relays** only need a short **pulse** (~50–500 ms) to change state (set/on or reset/off), then consume **0 mA** in either position.
- Result → When low voltage triggers → system completely dies (zero drain), and you restart manually with a button.

### Two main types of latching relays suitable here
1. **Single-coil magnetic latching** (most common & simplest for this)  
   → Polarity reversal on the same coil flips the state (one pulse one way = ON, opposite polarity = OFF).
2. **Dual-coil latching**  
   → Separate set coil & reset coil (easier control logic, but more wiring).

Popular ready-to-use high-current options (12V, 80–200 A continuous, widely available on Amazon/AliExpress/eBay/solar suppliers):
- Intellitec 01-00055-000 (100A, magnetic latching, fused versions available)
- Sterling Power ProLatch-R (80A programmable version, very nice for battery protect)
- Generic Chinese 12V 80A/100A/200A single-coil or dual-coil latching relays (search "12V 100A latching relay battery disconnect")
- Automotive/marine types like those from Littelfuse or Shallco (100–300A series)

### Basic wiring concept (single-coil type – most popular)
You use the XH-M609 relay contacts to **pulse** the latching relay coil briefly in the correct direction.

```
Battery + ─────► [Big Main Contacts of Latching Relay (NO)] ─────► XH-M609 IN+   +   All Loads +
                   │
                   └────► Latching Relay coil terminal 1
                                 │
                                 ├────► Diode + small cap circuit (for polarity control)
                                 │
                   XH-M609 OUT+ ─┘   (when closed = voltage good → pulse to SET/ON)
                                 │
                                 └────► Diode + cap + resistor for reverse polarity pulse when relay opens
Battery - ───────────────────────────────────────────────────────► XH-M609 IN-  +  Loads -  +  coil terminal 2 (common)

Manual restart button (momentary): Battery+ ──► momentary NO push-button ──► coil SET direction
```

**Simplified logic flow**:
- System starts off (disconnected).
- Press momentary **ON button** → short +12V pulse to coil → latching relay closes → power flows to XH-M609 + loads → system runs.
- Voltage good → XH-M609 relay **closed**.
- Low voltage → XH-M609 relay **opens** → clever polarity circuit (or simple timer cap/diode) sends reverse polarity pulse → latching relay **opens** → **everything dies** (zero current draw).
- To restart → press the **ON button** again.

For dual-coil types it's even simpler:
- XH-M609 closed → pulse **SET** coil (ON).
- XH-M609 opens → pulse **RESET** coil (OFF) via inverted logic or capacitor delay.

### Key tips for success
- Most latching relays need **only 5–20 W pulse** (very short) → XH-M609 relay can handle it directly (rated ~10A).
- Add **diodes** (1N4007 or Schottky) across coil to protect from spikes.
- Use a **small electrolytic capacitor** (100–1000 µF) + resistor/diode network to create the reverse pulse when the XH-M609 relay opens — this is the "clever" part many DIY videos show.
- If your relay is **dual-coil** → even easier: route XH-M609 OUT+ to SET coil, and use a simple RC delay + transistor to pulse RESET when OUT+ drops.
- Set XH-M609 **hysteresis** reasonably (e.g. 0.5–1V) so it doesn't chatter.
- Test pulses with multimeter/oscilloscope first — wrong polarity can do nothing or damage in rare cases.

