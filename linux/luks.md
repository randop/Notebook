# LUKS

**Barebones Arch Linux mkinitcpio hook: `encrypt-mac`**  
This custom hook replaces the standard `encrypt` hook. It automatically reads the MAC address of the **first non-loopback network interface** (from `/sys/class/net/`) and uses that exact string (e.g. `00:11:22:33:44:55`) as the LUKS passphrase. No password prompt appears.

**Security warning**  
A MAC address is **not secret** — it is visible on the local network and easily read from hardware. This is only suitable for convenience in fully controlled environments. Anyone who knows or can obtain your LAN MAC can unlock the drive. Use a strong passphrase for real security.

### 1. Create the two hook files

```bash
# Create the runtime hook (executed inside the initramfs)
sudo mkdir -p /etc/initcpio/hooks
sudo tee /etc/initcpio/hooks/encrypt-mac > /dev/null <<'EOF'
#!/bin/sh

run_hook() {
    # Parse cryptdevice= from kernel command line
    cryptdev=""
    cryptname=""
    for arg in $(cat /proc/cmdline); do
        case "$arg" in
            cryptdevice=*)
                # Split on first colon only
                cryptdev="${arg#cryptdevice=}"
                cryptname="${cryptdev#*:}"
                cryptdev="${cryptdev%%:*}"
                ;;
        esac
    done

    if [ -z "$cryptdev" ] || [ -z "$cryptname" ]; then
        echo "ERROR: cryptdevice= not found in kernel command line"
        echo "       Example: cryptdevice=UUID=abcd-1234:cryptroot"
        return 1
    fi

    # Resolve device (UUID, LABEL, or path)
    if command -v resolve_device > /dev/null 2>&1; then
        cryptdev="$(resolve_device "$cryptdev" 30)" || {
            echo "ERROR: Failed to resolve cryptdevice '$cryptdev'"
            return 1
        }
    else
        case "$cryptdev" in
            UUID=*)
                cryptdev="/dev/disk/by-uuid/${cryptdev#UUID=}"
                ;;
            LABEL=*)
                cryptdev="/dev/disk/by-label/${cryptdev#LABEL=}"
                ;;
        esac
    fi

    # Read first non-lo network interface MAC address
    mac=""
    for netdev in /sys/class/net/*; do
        if [ -e "$netdev/address" ] && [ "$(basename "$netdev")" != "lo" ]; then
            mac="$(cat "$netdev/address")"
            echo "Using MAC from $(basename "$netdev"): $mac"
            break
        fi
    done

    if [ -z "$mac" ]; then
        echo "ERROR: No LAN MAC address found!"
        echo "       Add your network driver to MODULES= in mkinitcpio.conf"
        return 1
    fi

    # Unlock using MAC as passphrase (no prompt)
    if printf '%s' "$mac" | cryptsetup luksOpen --key-file=- "$cryptdev" "$cryptname"; then
        echo "LUKS device '$cryptname' unlocked successfully using LAN MAC passphrase."
    else
        echo "ERROR: Failed to unlock LUKS device with MAC-derived passphrase!"
        return 1
    fi
}
EOF
```

```bash
# Create the build hook (tells mkinitcpio what to include)
sudo mkdir -p /etc/initcpio/install
sudo tee /etc/initcpio/install/encrypt-mac > /dev/null <<'EOF'
#!/bin/bash

build() {
    add_runscript          # adds the encrypt-mac hook script
    add_module dm-crypt    # kernel module for dm-crypt/LUKS
    add_binary cryptsetup  # the cryptsetup binary
}

help() {
    cat <<HELPEOF
This hook unlocks a LUKS root device using the LAN MAC address
as the passphrase (no interactive prompt).

Usage:
  1. Set cryptdevice=... in your kernel command line (same as before)
  2. Replace "encrypt" with "encrypt-mac" in HOOKS=
  3. Add your NIC driver to MODULES= if needed
  4. mkinitcpio -P && update-grub (or equivalent)
HELPEOF
}
EOF
```

Make them executable:
```bash
sudo chmod 755 /etc/initcpio/hooks/encrypt-mac /etc/initcpio/install/encrypt-mac
```

### 2. Update `/etc/mkinitcpio.conf`

```bash
# Replace the standard encrypt hook with our custom one
HOOKS=(base udev autodetect modconf keyboard keymap consolefont block encrypt-mac filesystems fsck)

# Optional but recommended: force your network driver early
# Example for Intel e1000e:
# MODULES=(e1000e)
```

### 3. Rebuild initramfs and update bootloader

```bash
sudo mkinitcpio -P
# If using GRUB:
sudo grub-mkconfig -o /boot/grub/grub.cfg
# Or for systemd-boot / efibootmgr users, just reboot
```

### 4. Kernel command line (unchanged)

Keep your existing `cryptdevice=` parameter exactly as before, for example:

```
cryptdevice=UUID=abcd1234-5678-efgh-ijkl-9012mnopqrst:cryptroot root=/dev/mapper/cryptroot
```

### 5. Set your LUKS passphrase to match the MAC

From a live environment or after booting normally, set the passphrase to the **exact** output of:

```bash
cat /sys/class/net/<your-interface>/address
```

Example:
```bash
# Check current MAC
cat /sys/class/net/enp0s31f6/address
# → 00:11:22:33:44:55

# Add/change key on the LUKS device
sudo cryptsetup luksChangeKey /dev/sdX   # (or use luksAddKey first)
# When prompted for current key, enter your old one
# When prompted for new key, paste the MAC string exactly (including colons)
```

### 6. Test & troubleshoot

- Reboot and watch the boot log (or add `debug` to kernel cmdline).
- If “No LAN MAC address found” → add your NIC driver to `MODULES=` and rebuild.
- Common drivers: `e1000e`, `igb`, `r8169`, `virtio_net`, etc.
- The hook picks the **first** non-`lo` interface. If you have multiple NICs and want a specific one, modify the loop in the hook.

