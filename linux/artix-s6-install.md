# Artix Linux

## Part 1: Artix Linux Installation (s6 init)

### 1. Boot the ISO and verify network

```bash
ping -c3 artixlinux.org
```

### 2. Partition the disk

```bash
cfdisk /dev/sdX
```

Create:
- EFI System partition (512M, type `EFI System`)
- Root partition
- Optional swap/home partitions

### 3. Format partitions

```bash
mkfs.fat -F32 /dev/sdX1      # EFI
mkfs.ext4 /dev/sdX2          # root
```

### 4. Mount partitions

```bash
mount /dev/sdX2 /mnt
mkdir -p /mnt/boot/efi
mount /dev/sdX1 /mnt/boot/efi
```

### 5. Install the base system with s6

```bash
basestrap /mnt base base-devel s6-base elogind-s6 linux linux-firmware
```

### 6. Generate fstab

```bash
fstabgen -U /mnt >> /mnt/etc/fstab
```

### 7. Chroot into the new system

```bash
artix-chroot /mnt
```

### 8. Set timezone

```bash
ln -sf /usr/share/zoneinfo/Asia/Manila /etc/localtime
hwclock --systohc
```

### 9. Set locale

```bash
echo "en_US.UTF-8 UTF-8" >> /etc/locale.gen
locale-gen
echo "LANG=en_US.UTF-8" > /etc/locale.conf
```

### 10. Set hostname and hosts file

```bash
echo "myhostname" > /etc/hostname
cat >> /etc/hosts <<EOF
127.0.0.1   localhost
::1         localhost
127.0.1.1   myhostname
EOF
```

### 11. Set root password

```bash
passwd
```

### 12. Install and configure the bootloader

```bash
pacman -S grub efibootmgr
grub-install --target=x86_64-efi --efi-directory=/boot/efi --bootloader-id=artix
grub-mkconfig -o /boot/grub/grub.cfg
```

### 13. Enable networking service (s6-rc)

```bash
pacman -S networkmanager-s6
mkdir -p /etc/s6-rc/source/default/contents.d
touch /etc/s6-rc/source/default/contents.d/networkmanager
```

> Path/method varies slightly by package version — confirm the service name with:
> ```bash
> ls /etc/s6-rc/source/ | grep -i network
> ```

### 14. Create your user account

```bash
useradd -m -G wheel -s /bin/bash randolph
passwd randolph
EDITOR=nano visudo   # uncomment %wheel ALL=(ALL:ALL) ALL
```

### 15. Recompile and switch the s6-rc database

```bash
readlink -f /etc/s6-rc/compiled
s6-rc-compile /etc/s6-rc/compiled-new /etc/s6-rc/source
s6-rc-update /etc/s6-rc/compiled-new
```

### 16. Exit, unmount, and reboot

```bash
exit
umount -R /mnt
reboot
```

Post-install: install your DE/WM (dwm/st or similar), audio stack (e.g. `linux-rt-lts` if needed), and an AUR helper via `chaotic-aur` or manual builds.

---

## Part 2: Enabling Periodic `fstrim` (s6-rc)

### 1. Create the service source directory

```bash
mkdir -p /etc/s6-rc/source/fstrim-weekly
```

### 2. Declare it as a longrun service

```bash
echo longrun > /etc/s6-rc/source/fstrim-weekly/type
```

### 3. Write the run script

**Execline version** (native to s6):

```bash
cat > /etc/s6-rc/source/fstrim-weekly/run <<'EOF'
#!/bin/execlineb -P
loopwhilex
  fdmove -c 2 1
  foreground { fstrim -av }
  sleep 604800
EOF
chmod +x /etc/s6-rc/source/fstrim-weekly/run
```

**POSIX shell version** (simpler, if execline syntax isn't your thing):

```bash
cat > /etc/s6-rc/source/fstrim-weekly/run <<'EOF'
#!/bin/sh
while true; do
    fstrim -av
    sleep 604800
done
EOF
chmod +x /etc/s6-rc/source/fstrim-weekly/run
```

> `604800` seconds = 7 days. Adjust to taste (e.g. `2592000` for monthly).

### 4. Add it to the default bundle

```bash
echo "fstrim-weekly" >> /etc/s6-rc/source/default/contents
```

### 5. Check current compiled database path

```bash
readlink -f /etc/s6-rc/compiled
```

### 6. Recompile the s6-rc database

```bash
s6-rc-compile /etc/s6-rc/compiled-new /etc/s6-rc/source
```

### 7. Apply the new database live

```bash
s6-rc-update /etc/s6-rc/compiled-new
```

### 8. Start the service

```bash
s6-rc -u change fstrim-weekly
```

### 9. Verify it's running

```bash
s6-rc -a list
s6-svstat /run/service/fstrim-weekly
```
---
### Run fstrim manually

```bash
fstrim -av
```

---

## Notes

- Confirm TRIM support before setting any of this up:
  ```bash
  lsblk --discard
  ```
  Non-zero `DISC-GRAN` / `DISC-MAX` values confirm support.
- Avoid the continuous `discard` mount option in `/etc/fstab` for most SSDs — periodic `fstrim` is the recommended practice over continuous trim.
