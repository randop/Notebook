# Optimized Arch Linux Installation Guide for Xeon E5 v4 (Broadwell-EP)

## System Specifications Targeted

- **CPU**: Intel Xeon E5 v4 (Broadwell-EP, up to 22 cores/socket)
- **Platform**: Dual-socket server/workstation
- **Memory**: 64-128GB DDR4 ECC
- **Storage**: SSD (NVMe/SATA)
- **Graphics**: Discrete GPU
- **Use Case**: Server/Homelab

---

## Table of Contents

1. [Pre-Installation](#pre-installation)
2. [BIOS/UEFI Configuration](#biosuefi-configuration)
3. [Live Environment Setup](#live-environment-setup)
4. [Disk Partitioning](#disk-partitioning)
5. [Base Installation](#base-installation)
6. [Kernel & Bootloader Configuration](#kernel--bootloader-configuration)
7. [Xeon-Specific Optimizations](#xeon-specific-optimizations)
8. [SSD Optimizations](#ssd-optimizations)
9. [Network Configuration](#network-configuration)
10. [Post-Installation](#post-installation)
11. [Performance Tuning](#performance-tuning)

---

## Pre-Installation

### Download Arch ISO

```bash
# Download latest Arch ISO
curl -O https://mirrors.kernel.org/archlinux/iso/latest/archlinux-x86_64.iso

# Verify checksum
curl -O https://mirrors.kernel.org/archlinux/iso/latest/archlinux-x86_64.iso.sig
gpg --verify archlinux-x86_64.iso.sig

# Create bootable USB (replace /dev/sdX with your USB device)
dd if=archlinux-x86_64.iso of=/dev/sdX bs=4M status=progress && sync
```

### Required Hardware

- USB drive (2GB+)
- Internet connection
- Xeon E5 v4 system with UEFI support

---

## BIOS/UEFI Configuration

### Critical Settings

1. **Boot Mode**: Set to **UEFI** (not Legacy/CSM)
2. **Secure Boot**: **Disable** (required for Arch)
3. **Virtualization**: Enable **VT-x** and **VT-d** (for VMs/GPU passthrough)
4. **Above 4G Decoding**: Enable (for large memory/GPU)
5. **SR-IOV**: Enable (if using for virtualization)

### Performance Settings

```
Intel SpeedStep: Enabled
Turbo Boost: Enabled
C-States: C1E or C6 (your choice - C6 saves more power)
Hyper-Threading: Enabled (for 44 threads total on dual 22-core)
Memory Interleaving: Auto or Enabled
```

### Server-Specific

```
NUMA: Enabled
Node Interleaving: Disabled (for best NUMA performance)
SRAT: Enabled
ACPI S3/S4: Disable S3 sleep (not needed for server)
```

---

## Live Environment Setup

### Boot Arch ISO

1. Insert USB and boot from it
2. Select **Arch Linux install medium (x86_64, UEFI)**

### Verify UEFI Mode

```bash
# Should show EFI variables
ls /sys/firmware/efi/efivars

# Check if booted in UEFI mode
[ -d /sys/firmware/efi ] && echo "UEFI" || echo "Legacy"
```

### Network Configuration

```bash
# For Ethernet (most server setups)
dhcpcd

# For Wi-Fi
iwctl
# Then: station wlan0 connect "SSID"

# Verify connection
ping -c 3 archlinux.org
```

### Update System Clock

```bash
timedatectl set-ntp true
timedatectl status
```

---

## Disk Partitioning

### Partition Scheme (UEFI + Single SSD)

| Partition | Size | Type | Filesystem | Mount |
|-----------|------|------|------------|-------|
| EFI | 1GB | EFI System | FAT32 | /boot/efi |
| Root | 50GB+ | Linux | ext4/btrfs | / |
| Home | Remainder | Linux | ext4/btrfs | /home |

### Using `parted` (Recommended for large drives)

```bash
# Identify your SSD
lsblk

# Enter parted (replace /dev/nvme0n1 with your device)
parted /dev/nvme0n1

# In parted:
mklabel gpt
mkpart ESP fat32 1MiB 1GiB
set 1 boot on
mkpart primary btrfs 1GiB 51GiB
mkpart primary btrfs 51GiB 100%
quit

# Verify partitions
lsblk
```

### Format Partitions

```bash
# Format EFI partition
mkfs.fat -F32 /dev/nvme0n1p1

# Format root with btrfs (recommended for servers)
mkfs.btrfs -f -L ROOT /dev/nvme0n1p2

# Format home with btrfs
mkfs.btrfs -f -L HOME /dev/nvme0n1p3
```

### Create Btrfs Subvolumes

```bash
# Mount root
mount /dev/nvme0n1p2 /mnt

# Create subvolumes
btrfs subvolume create /mnt/@
btrfs subvolume create /mnt/@snapshots
btrfs subvolume create /mnt/@var
btrfs subvolume create /mnt/@tmp

# Unmount
umount /mnt

# Mount with optimal SSD options
mount -o noatime,compress=zstd,subvol=@ /dev/nvme0n1p2 /mnt

# Create directories
mkdir -p /mnt/{boot/efi,home,var,tmp}

# Mount subvolumes
mount -o noatime,compress=zstd,subvol=@var /dev/nvme0n1p2 /mnt/var
mount -o noatime,compress=zstd,subvol=@tmp /dev/nvme0n1p2 /mnt/tmp

# Mount home
mount -o noatime,compress=zstd /dev/nvme0n1p3 /mnt/home

# Mount EFI
mount /dev/nvme0n1p1 /mnt/boot/efi

# Verify mounts
lsblk
```

---

## Base Installation

### Install Essential Packages

```bash
# Use reflector for fastest mirrors first
pacman -Sy reflector
reflector --country "United States" --protocol https --latest 10 --sort rate --save /etc/pacman.d/mirrorlist

# Base installation with LTS kernel
pacstrap -K /mnt base base-devel linux-lts linux-lts-headers linux-firmware \
    intel-ucode btrfs-progs vim nano sudo \
    networkmanager dhcpcd \
    grub efibootmgr os-prober \
    man-db man-pages texinfo \
    git curl wget htop iotop \
    tmux screen
```

### Generate fstab

```bash
# Generate fstab with btrfs subvolume detection
genfstab -U -p /mnt >> /mnt/etc/fstab

# Review fstab
cat /mnt/etc/fstab
```

---

## Chroot and Basic Configuration

### Enter New System

```bash
arch-chroot /mnt
```

### Timezone and Locale

```bash
# Set timezone (adjust to your location)
ln -sf /usr/share/zoneinfo/America/New_York /etc/localtime
hwclock --systohc

# Edit locale.gen
vim /etc/locale.gen
# Uncomment: en_US.UTF-8 UTF-8

# Generate locales
locale-gen

# Set default locale
echo "LANG=en_US.UTF-8" > /etc/locale.conf

# Set hostname
echo "xeon-server" > /etc/hostname

# Edit /etc/hosts
vim /etc/hosts
# Add:
# 127.0.0.1   localhost
# ::1         localhost
# 127.0.1.1   xeon-server.localdomain xeon-server
```

### Set Root Password

```bash
passwd
```

### Create User Account

```bash
# Create user (replace 'admin' with your username)
useradd -m -G wheel -s /bin/bash admin
passwd admin

# Enable wheel sudo access
EDITOR=vim visudo
# Uncomment: %wheel ALL=(ALL:ALL) ALL
```

---

## Kernel & Bootloader Configuration

### Install LTS Kernel (Already done in pacstrap)

```bash
# Verify LTS kernel is installed
pacman -Q linux-lts

# Optional: Install fallback/initramfs tools
pacman -S mkinitcpio
```

### Configure mkinitcpio for Btrfs

```bash
vim /etc/mkinitcpio.conf

# Find HOOKS line and ensure btrfs is included:
HOOKS=(base udev autodetect modconf kms keyboard keymap consolefont block filesystems fsck)

# Regenerate initramfs
mkinitcpio -P
```

### Install and Configure GRUB

```bash
# Install GRUB for UEFI
grub-install --target=x86_64-efi --efi-directory=/boot/efi --bootloader-id=GRUB

# Edit GRUB config for Xeon optimizations
vim /etc/default/grub
```

### Optimized GRUB Configuration

```bash
# /etc/default/grub - Xeon E5 v4 Optimized Settings

GRUB_DEFAULT=0
GRUB_TIMEOUT=5
GRUB_DISTRIBUTOR="Arch"
GRUB_CMDLINE_LINUX_DEFAULT="loglevel=3 quiet"

# Optimized kernel parameters for Xeon E5 v4
# - intel_pstate=active: Use Intel P-State driver
# - processor.max_cstate=1: Limit C-states for latency
# - intel_idle.max_cstate=1: Limit idle states
# - idle=halt: Better performance on servers
# - nopti: Disable Meltdown mitigations (if isolated environment)
# - nowatchdog: Disable watchdog for performance
# - mitigations=off: Disable Spectre/Meltdown (isolated only!)
# - threadirqs: Threaded IRQs for multi-core systems
# - pci=realloc: Reallocate PCI resources if needed

GRUB_CMDLINE_LINUX="intel_pstate=active processor.max_cstate=1 intel_idle.max_cstate=1 idle=halt nowatchdog threadirqs pci=realloc"

# For isolated server environment (not public-facing), add:
# nopti mitigations=off

GRUB_PRELOAD_MODULES="part_gpt part_btrfs"
GRUB_ENABLE_CRYPTODISK=y
GRUB_DISABLE_OS_PROBER=false
```

**Security Note**: Only disable mitigations (`nopti`, `mitigations=off`) if your server is in an isolated/trusted environment. For internet-facing servers, remove these options.

### Generate GRUB Config

```bash
grub-mkconfig -o /boot/grub/grub.cfg
```

---

## Xeon-Specific Optimizations

### Install Microcode and CPU Tools

```bash
# Intel microcode (already installed, but ensure latest)
pacman -S intel-ucode iucode-tool

# CPU frequency tools
pacman -S cpupower

# NUMA tools
pacman -S numactl

# Performance monitoring
pacman -S perf linux-tools
```

### Configure Intel P-State

```bash
# Check current P-State driver
cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_driver

# Create optimized P-State configuration
cat > /etc/default/cpupower << 'EOF'
# Intel P-State governor for server workloads
# Options: performance, powersave, schedutil

# For servers: use performance or schedutil
# performance: Max frequency always
# schedutil: Adaptive based on load (recommended for modern kernels)

GOVERNOR="performance"

# Min/Max frequency (optional)
# MIN_FREQ="1.2GHz"
# MAX_FREQ="2.8GHz"
EOF

# Enable cpupower service
systemctl enable cpupower.service
```

### NUMA Optimization

```bash
# Check NUMA topology
numactl --hardware

# For dual-socket Xeon, configure memory allocation
cat >> /etc/sysctl.d/99-numa.conf << 'EOF'
# NUMA optimizations for dual-socket Xeon
# Local memory allocation preference
vm.zone_reclaim_mode = 1

# Reduce swappiness for large memory systems
vm.swappiness = 10

# Increase dirty ratio for better throughput
vm.dirty_ratio = 40
vm.dirty_background_ratio = 10

# Huge pages for large memory systems
vm.nr_hugepages = 2048
EOF

# Apply sysctl settings
sysctl --system
```

### Huge Pages Configuration

```bash
# Enable transparent huge pages
cat >> /etc/sysctl.d/99-hugepages.conf << 'EOF'
# Enable transparent huge pages (madvise or always)
kernel.numa_balancing = 1
EOF

# Configure in GRUB (already done)
# Can also use: transparent_hugepage=madvise
```

### IRQ Affinity (Multi-Core Optimization)

```bash
# Install irqbalance for automatic IRQ distribution
pacman -S irqbalance

# Enable and start
systemctl enable irqbalance.service
```

---

## SSD Optimizations

### Btrfs Mount Options

Already configured in fstab with `noatime` and `compress=zstd`.

### TRIM/Discard Configuration

```bash
# Check if TRIM is supported
lsblk -D

# Enable fstrim timer for periodic TRIM
systemctl enable fstrim.timer
systemctl start fstrim.timer

# Alternative: Enable continuous TRIM in fstab (add 'discard')
# Note: continuous TRIM has performance impact on some SSDs
```

### I/O Scheduler

```bash
# Check current scheduler
cat /sys/block/nvme0n1/queue/scheduler

# For NVMe SSDs, use 'none' or 'mq-deadline'
# Create udev rule
cat > /etc/udev/rules.d/60-ioschedulers.rules << 'EOF'
# NVMe SSD - use none scheduler (best for NVMe)
ACTION=="add|change", KERNEL=="nvme[0-9]*", ATTR{queue/scheduler}="none"

# SATA SSD - use mq-deadline or bfq
ACTION=="add|change", KERNEL=="sd[a-z]*", ATTR{queue/rotational}=="0", ATTR{queue/scheduler}="mq-deadline"
EOF
```

### Btrfs Maintenance

```bash
# Create btrfs maintenance script
cat > /usr/local/bin/btrfs-maintenance.sh << 'EOF'
#!/bin/bash
# Btrfs maintenance for SSD

# Balance (run weekly)
btrfs balance start -dusage=50 /mnt

# Scrub (run monthly)
btrfs scrub start /mnt

# Defragment (if needed)
# btrfs filesystem defragment -r /
EOF

chmod +x /usr/local/bin/btrfs-maintenance.sh

# Add to cron (weekly)
echo "0 3 * * 0 root /usr/local/bin/btrfs-maintenance.sh" >> /etc/crontab
```

---

## Network Configuration

### Enable NetworkManager

```bash
# Already installed in base
systemctl enable NetworkManager.service
```

### Network Optimization for Server

```bash
# Create network optimization sysctl
cat > /etc/sysctl.d/99-network-tuning.conf << 'EOF'
# Network optimizations for server workloads

# Increase socket buffer sizes
net.core.rmem_max = 134217728
net.core.wmem_max = 134217728
net.core.rmem_default = 16777216
net.core.wmem_default = 16777216
net.core.optmem_max = 40960
net.ipv4.tcp_rmem = 4096 87380 134217728
net.ipv4.tcp_wmem = 4096 65536 134217728

# Increase connection tracking
net.netfilter.nf_conntrack_max = 2000000

# TCP optimizations
net.ipv4.tcp_congestion_control = bbr
net.core.default_qdisc = fq
net.ipv4.tcp_notsent_lowat = 16384
net.ipv4.tcp_fastopen = 3

# Disable IPv6 if not needed (optional)
# net.ipv6.conf.all.disable_ipv6 = 1
EOF

# Load BBR congestion control module
echo "tcp_bbr" > /etc/modules-load.d/tcp-bbr.conf

sysctl --system
```

### Firewall Configuration (Optional)

```bash
# Install firewalld
pacman -S firewalld
systemctl enable firewalld

# Or use iptables/nftables directly
pacman -S iptables
```

---

## Post-Installation

### Exit Chroot and Reboot

```bash
# Exit chroot
exit

# Unmount all
umount -R /mnt

# Reboot
reboot
```

### First Boot Configuration

```bash
# Login as your user

# Update system
sudo pacman -Syu

# Install essential server tools
sudo pacman -S \
    docker docker-compose \
    podman podman-compose \
    qemu-full virt-manager \
    samba nfs-utils \
    smartmontools nvme-cli \
    lm_sensors

# Enable Docker
sudo systemctl enable docker
sudo usermod -aG docker $USER

# Enable libvirt (for VMs)
sudo systemctl enable libvirtd
sudo usermod -aG libvirt $USER

# Configure sensors
sudo sensors-detect --auto
```

---

## Performance Tuning

### Verify Optimizations

```bash
# Check kernel version (should be LTS)
uname -r

# Check CPU frequency scaling
cpupower frequency-info

# Check NUMA status
numactl --show

# Check transparent huge pages
cat /sys/kernel/mm/transparent_hugepage/enabled

# Check I/O scheduler
cat /sys/block/nvme0n1/queue/scheduler

# Check BBR
cat /proc/sys/net/ipv4/tcp_congestion_control
sysctl net.core.default_qdisc

# Verify microcode
head -n 20 /proc/cpuinfo | grep microcode
```

### Benchmarking Tools

```bash
# Install benchmarks
sudo pacman -S \
    sysbench \
    fio \
    stress-ng \
    s-tui

# CPU benchmark
sysbench cpu --cpu-max-prime=20000 run

# Memory benchmark
sysbench memory --memory-block-size=1M --memory-total-size=10G run

# Disk benchmark (be careful with existing data!)
fio --name=test --filename=/home/testfile --bs=4k --iodepth=64 --size=4G --readwrite=randrw --rwmixread=75
```

### Monitoring Setup

```bash
# Install monitoring tools
sudo pacman -S \
    htop iotop \
    btop \
    glances \
    ncdu \
    nvtop  # For NVIDIA GPU monitoring

# Install Prometheus/Grafana (optional)
# See Arch Wiki for setup
```

---

## Troubleshooting

### Boot Issues

```bash
# If system won't boot:
# 1. Boot from Arch ISO
# 2. Mount system:
mount /dev/nvme0n1p2 /mnt
mount /dev/nvme0n1p1 /mnt/boot/efi
arch-chroot /mnt

# 3. Reinstall GRUB
grub-install --target=x86_64-efi --efi-directory=/boot/efi --bootloader-id=GRUB
grub-mkconfig -o /boot/grub/grub.cfg

# 4. Regenerate initramfs
mkinitcpio -P
```

### Kernel Panic or Boot Failure

```bash
# Boot with fallback options:
# In GRUB, edit kernel line (press 'e') and add:
# systemd.unit=rescue.target
# or
# nomodeset
# or
# init=/bin/bash
```

### Network Issues

```bash
# Check network interface
ip link

# Enable interface manually
sudo ip link set enp3s0 up
sudo dhcpcd

# Check NetworkManager
sudo systemctl status NetworkManager
sudo nmcli device status
```

---

## Maintenance

### System Updates

```bash
# Weekly update routine
sudo pacman -Syu

# Check for orphan packages
sudo pacman -Qdt
sudo pacman -Rns $(pacman -Qdtq)

# Clean package cache
sudo pacman -Sc
sudo pacman -Scc
```

### Backup Important Files

```bash
# Backup list
/etc/fstab
/etc/default/grub
/etc/mkinitcpio.conf
/etc/sysctl.d/*.conf
/etc/udev/rules.d/*.rules
/etc/NetworkManager/
/etc/hostname
/etc/locale.conf
```

---

## Additional Resources

- [Arch Wiki - Installation Guide](https://wiki.archlinux.org/title/Installation_guide)
- [Arch Wiki - Xeon E5](https://wiki.archlinux.org/title/Intel_Xeon)
- [Arch Wiki - Btrfs](https://wiki.archlinux.org/title/Btrfs)
- [Arch Wiki - KVM](https://wiki.archlinux.org/title/KVM)
- [Arch Wiki - Network Configuration](https://wiki.archlinux.org/title/Network_configuration)

---

## Summary of Optimizations

| Category | Optimization | Benefit |
|----------|--------------|---------|
| **Kernel** | Linux LTS | Stability, long-term support |
| **CPU** | Intel P-State performance | Maximum clock speed |
| **CPU** | Microcode updates | Security and stability fixes |
| **Memory** | NUMA tuning | Local memory allocation |
| **Memory** | Huge pages | Reduced TLB misses |
| **Storage** | Btrfs with zstd compression | Space savings, checksums |
| **Storage** | noatime, TRIM | Reduced SSD wear |
| **Storage** | None I/O scheduler | Optimal for NVMe |
| **Network** | BBR + FQ | Improved throughput |
| **System** | irqbalance | Even IRQ distribution |

---

**Created**: $(date +%Y-%m-%d)  
**Target**: Xeon E5 v4 (Broadwell-EP)  
**Kernel**: Linux LTS  
**Purpose**: Server/Homelab  
**Storage**: SSD with Btrfs
