# Android Faults

### Common causes and fixes for Android 13 hotspot-triggered reboots:

- **Overheating/battery protection** — check if phone gets hot before restart; let it cool and retry
- **Software bug in that Android 13 build** — check Settings → System → System update for a patch
- **Corrupted cache** — reboot into recovery, clear cache partition (Volume Up + Power at boot, varies by OEM)
- **Rogue app conflict** — boot into Safe Mode, test hotspot there; if it doesn't crash, an app is the culprit
- **APN/network settings corruption** — Settings → Network → Mobile network → Access Point Names → reset to default
- **Bad battery** — if the phone is old or battery is swollen/degraded, hotspot's power draw spike can trigger a brownout reboot

Fastest diagnostic: try Safe Mode first, then check logs if you have USB debugging enabled — `adb logcat` right after triggering the crash will show the actual kernel panic or tombstone.

#### Typical smartphone battery lifespan:

- **300–500 full charge cycles** to drop to ~80% capacity (this is the industry benchmark most manufacturers use)
- In real-world terms, that's roughly **2–3 years** of daily charging before noticeable degradation
- After ~500 cycles, expect capacity closer to 80%, and continued use accelerates further decline

**What shortens it faster:**
- Frequent 0–100% deep cycles (better to keep between 20–80%)
- Heat exposure (charging while gaming, direct sun, using hotspot for long periods — ties into your earlier issue)
- Always charging to 100% and leaving it plugged in overnight
- Fast charging heavily/constantly

**Signs of a dying battery:** rapid drain, phone shutting off at 20-30% "remaining," swelling (visible bulge — stop using immediately), overheating during normal use, or unexpected reboots under power spikes (like your hotspot issue).

If your phone is 2+ years old and reboots specifically when hotspot draws extra power, a degraded battery is a real suspect alongside the software causes I mentioned. Most phones let you check battery health via Settings → Battery, or third-party apps like AccuBattery for more detail.
