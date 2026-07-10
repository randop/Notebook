# WWAN

For mainline Linux, **Quectel RM520N-GL** (Snapdragon X62, Cat 20, 5G NSA/SA) is the safest bet — best community support and native kernel drivers.

**Why it wins:**
- Uses in-kernel `mhi_pci_generic` (PCIe) + `qmi_wwan`/`mhi_net`, no out-of-tree driver needed since kernel ~6.0+ Ubuntu 24.04 ships kernel 6.8 with improved support for the module, though full support landed in kernel 6.0+
- Works cleanly with ModemManager + NetworkManager out of the box on Ubuntu/Arch/Manjaro
- Huge amount of forum/first-hand Linux setup documentation (Quectel forums, Arch wiki, Manjaro writeups)
- M.2 key B+M, standard SIM+eSIM, widely stocked (also sold on AliExpress/Shopee — easy to get in PH)

**Known gotchas (still worth it):**
- If it came pre-installed in a Lenovo/Dell laptop, it's often **FCC-locked** and needs an unlock service before radio comes up many RM520N modules, especially those in laptops, come with an FCC lock that needs to be disabled before use, though Ubuntu 24.04 includes support for unlocking FCC-locked modems
- PCIe mode occasionally has RX-stuck-at-0 bugs on certain firmware/kernel combos on some setups the PCIe mhi_hwip0 interface gets a correct IP/gateway but the RX packet counter stays at 0 while the USB fallback works fine, just slower — if you hit this, fall back to the module's USB mode or bump firmware
- Watch out for **fake/relabeled** RM520N-GL units sold by laptop OEMs that lock you out of firmware updates a Lenovo-branded RM520N-GL turned out to be a faked variant that wasn't usable as a genuine Quectel modem — buy the standalone module directly, not a pulled OEM part if you can avoid it

**Alternatives if RM520N-GL doesn't fit your needs:**
- **Quectel RM500Q-GL** (Cat 20, X55) — older, even more battle-tested on Linux, but 5G peak throughput lower than RM520N
- **Fibocom FM350-GL** — works but Linux support is spottier, more Windows-first firmware tooling
- **Sierra Wireless EM9191** — decent, but pricier and less commonly available in PH

