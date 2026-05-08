# Network Time Protocol (NTP)

## Overview

NTP (Network Time Protocol, RFC 5905) synchronizes clocks across a network to within a few milliseconds of UTC. It operates over UDP port 123 and uses a hierarchical system of time sources called **strata**.

---

## Stratum Hierarchy

| Stratum | Description |
|---------|-------------|
| 0 | Reference clocks (GPS, atomic, radio) — not on the network |
| 1 | Primary servers directly connected to stratum-0 devices |
| 2 | Servers synchronized to stratum-1 |
| 3–15 | Each level syncs from the level above |
| 16 | Unsynchronized / unreachable |

---

## Packet Exchange Flow

```mermaid
sequenceDiagram
    participant C as NTP Client
    participant S as NTP Server

    Note over C,S: UDP Port 123

    C->>S: Request (mode=3 Client)<br/>T1 = client transmit timestamp

    Note over S: Server receives at T2<br/>Server replies at T3

    S-->>C: Response (mode=4 Server)<br/>T2 = server receive timestamp<br/>T3 = server transmit timestamp

    Note over C: Client receives at T4

    Note over C: Offset θ = ((T2−T1) + (T3−T4)) / 2<br/>Delay δ = (T4−T1) − (T3−T2)
```

---

## Four NTP Timestamps Explained

```mermaid
timeline
    title NTP Timestamp Timeline
    T1 : Client sends request
       : (client clock)
    T2 : Server receives request
       : (server clock)
    T3 : Server sends reply
       : (server clock)
    T4 : Client receives reply
       : (client clock)
```

### Derived Metrics

| Metric | Formula | Meaning |
|--------|---------|---------|
| **Round-trip delay** δ | `(T4 − T1) − (T3 − T2)` | Total network latency |
| **Clock offset** θ | `((T2 − T1) + (T3 − T4)) / 2` | How far client clock is from server |

The client adjusts its clock by θ. If |θ| is large (> 128 ms by default), `ntpd` **steps** the clock immediately; otherwise it **slews** (gradually adjusts) the clock rate.

---

## Full Stratum Synchronization Flow

```mermaid
flowchart TD
    GPS["🛰️ GPS / Atomic Clock\n(Stratum 0)"]
    S1A["NTP Server A\n(Stratum 1)"]
    S1B["NTP Server B\n(Stratum 1)"]
    S2A["NTP Server C\n(Stratum 2)"]
    S2B["NTP Server D\n(Stratum 2)"]
    S2C["NTP Server E\n(Stratum 2)"]
    C1["Client 1\n(Stratum 3)"]
    C2["Client 2\n(Stratum 3)"]
    C3["Client 3\n(Stratum 3)"]

    GPS -->|PPS / serial| S1A
    GPS -->|PPS / serial| S1B

    S1A -->|NTP UDP/123| S2A
    S1A -->|NTP UDP/123| S2B
    S1B -->|NTP UDP/123| S2B
    S1B -->|NTP UDP/123| S2C

    S2A -->|NTP UDP/123| C1
    S2B -->|NTP UDP/123| C1
    S2B -->|NTP UDP/123| C2
    S2C -->|NTP UDP/123| C3
```

---

## NTP Operating Modes

```mermaid
stateDiagram-v2
    [*] --> INIT : daemon start

    INIT --> UNSYNCED : no peers reachable
    UNSYNCED --> POLLING : peer discovered

    POLLING --> MEASURING : send client request (mode 3)
    MEASURING --> FILTERING : receive server reply (mode 4)

    FILTERING --> SELECTING : apply clock filter\n(8-sample shift register,\ndiscard outliers)
    SELECTING --> CLUSTERING : intersection algorithm\n(RFC 5905 §11.2)
    CLUSTERING --> COMBINING : survivor set chosen
    COMBINING --> SYNCED : best offset computed

    SYNCED --> POLLING : next poll interval\n(64s–1024s adaptive)
    SYNCED --> STEPPING : |offset| > 128 ms\nstep clock
    STEPPING --> POLLING : clock stepped
    SYNCED --> SLEWING : |offset| < 128 ms\nadjust rate via adjtime(2)
    SLEWING --> SYNCED : slew complete

    SYNCED --> UNSYNCED : all peers lost
```

---

## Poll Interval Backoff

NTP uses an adaptive poll interval (MINPOLL=6 → 64 s, MAXPOLL=10 → 1024 s by default). The interval increases exponentially when the clock is stable and decreases when jitter is detected.

```mermaid
xychart-beta
    title "Poll Interval vs Clock Stability"
    x-axis ["Unstable", "Low", "Medium", "High", "Very High"]
    y-axis "Poll Interval (seconds)" 0 --> 1100
    bar [64, 128, 256, 512, 1024]
```

---

## Kiss-o'-Death (KoD) Packets

If a server wants to throttle or reject a client, it responds with **stratum 0** and a 4-character ASCII code in the reference ID field:

| Code | Meaning |
|------|---------|
| `DENY` | Client access denied |
| `RSTR` | Client access restricted |
| `RATE` | Client polling too fast |
| `STEP` | Accept the KoD, step clock |

```mermaid
sequenceDiagram
    participant C as NTP Client
    participant S as NTP Server

    C->>S: Request (poll interval too short)
    S-->>C: KoD Response<br/>stratum=0, refid="RATE"
    Note over C: Back off poll interval<br/>Do not count as valid sample
```

---

## NTPv4 Packet Structure

```
 0                   1                   2                   3
 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|LI | VN  |Mode |    Stratum    |     Poll      |   Precision   |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                          Root Delay                           |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                       Root Dispersion                         |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                     Reference Identifier                      |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                   Reference Timestamp (64)                    |
|                                                               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                   Originate Timestamp (64)                    |  ← T1
|                                                               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                    Receive Timestamp (64)                     |  ← T2
|                                                               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                    Transmit Timestamp (64)                    |  ← T3/T4
|                                                               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                 Key Identifier (optional) (32)                |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                 Message Digest (optional) (128)               |
|                                                               |
|                                                               |
|                                                               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
```

| Field | Bits | Description |
|-------|------|-------------|
| LI | 2 | Leap Indicator (0=ok, 1=+1s, 2=−1s, 3=unsync) |
| VN | 3 | Version Number (4) |
| Mode | 3 | 1=sym-active, 2=sym-passive, 3=client, 4=server, 5=broadcast |
| Stratum | 8 | Clock stratum (0=KoD, 1=primary, 2–15=secondary) |
| Poll | 8 | Log₂ of poll interval in seconds |
| Precision | 8 | Log₂ of clock precision in seconds (signed) |
| Root Delay | 32 | Round-trip delay to stratum-1 (NTP short format) |
| Root Dispersion | 32 | Max error from primary source |
| Reference ID | 32 | Stratum-1: 4-char ASCII; Stratum≥2: IPv4 of upstream |

---

## Security: NTP Authentication

```mermaid
flowchart LR
    subgraph Symmetric ["Symmetric Key (NTPv4)"]
        A1[Client] -->|"HMAC-MD5(key, packet)"| B1[Server]
        B1 -->|"HMAC-MD5(key, packet)"| A1
    end

    subgraph Autokey ["Autokey (deprecated, RFC 5906)"]
        A2[Client] -->|"RSA signed\ncertificate chain"| B2[Server]
    end

    subgraph NTS ["NTS (RFC 8915 — modern)"]
        A3[Client] -->|"TLS 1.3 handshake\n(NTS-KE port 4460)"| B3[NTS-KE Server]
        B3 -->|"AES-SIV cookies\n+ C2S/S2C keys"| A3
        A3 -->|"NTPv4 + NTS extension\nfields (UDP 123)"| C3[NTP Server]
    end
```

**NTS (Network Time Security)** is the current recommended approach — it provides authenticated, replay-protected NTP without requiring pre-shared keys by bootstrapping from TLS.

---

## References

- [RFC 5905](https://datatracker.ietf.org/doc/html/rfc5905) — NTPv4 Specification  
- [RFC 8915](https://datatracker.ietf.org/doc/html/rfc8915) — Network Time Security (NTS)  
- [RFC 5906](https://datatracker.ietf.org/doc/html/rfc5906) — NTP Autokey (deprecated)  
- [ntppool.org](https://www.ntppool.org/) — Public NTP Pool Project
