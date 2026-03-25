# Artix Linux Installation Guide
## Dual-Socket Intel Xeon E5 v4 Platform

### Prerequisites
- USB drive (4GB+) with Artix Linux ISO
- Dual-socket Xeon E5 v4 server/workstation
- Internet connection (Ethernet preferred for initial setup)
- Basic familiarity with Linux command line

---

## 1. Base Installation

### Boot from ISO
1. Download Artix Linux base ISO (s6 edition)
2. Create bootable USB: `dd if=artix.iso of=/dev/sdX bs=4M status=progress`
3. Boot target system from USB

### Partition Setup
```bash
# Identify disks
lsblk

# Partition with GPT (UEFI)
gdisk /dev/nvme0n1  # or /dev/sda
# Create: EFI partition (512MB, type EF00), root, swap (optional)

# Format partitions
mkfs.fat -F32 /dev/nvme0n1p1
mkfs.ext4 /dev/nvme0n1p2
mkswap /dev/nvme0n1p3

# Mount
mount /dev/nvme0n1p2 /mnt
mkdir -p /mnt/boot/efi
mount /dev/nvme0n1p1 /mnt/boot/efi
swapon /dev/nvme0n1p3
```

### Install Base System
```bash
# Update pacman mirrors
reflector --latest 20 --protocol https --sort rate --save /etc/pacman.d/mirrorlist

# Bootstrap base system
basestrap /mnt base base-devel s6 s6-rc s6-linux-init elogind-s6 \
    linux linux-headers linux-firmware nano vim networkmanager \
    networkmanager-s6 dhcpcd dhcpcd-s6

# Generate fstab
fstabgen -U /mnt >> /mnt/etc/fstab

# Chroot
artix-chroot /mnt
```

---

## 2. rEFInd Boot Manager

### Installation
```bash
# Install rEFInd
pacman -S refind

# Install to ESP
refind-install

# Configure
nano /boot/efi/EFI/refind/refind.conf
```

### rEFInd Configuration
```
timeout 10
use_nvram false

# Kernel parameters for Xeon optimization
"Boot Artix Linux" {
    loader /vmlinuz-linux
    initrd /initramfs-linux.img
    options "root=UUID=YOUR-ROOT-UUID rw quiet intel_pstate=passive intel_idle.max_cstate=1 processor.max_cstate=1"
}

# Disable unnecessary scans
scanfor manual,external
```

### Xeon E5 v4 Optimizations
Add to kernel parameters in rEFInd:
- `intel_pstate=passive` - Disable aggressive power management
- `intel_idle.max_cstate=1` - Limit CPU sleep states (reduces latency)
- `processor.max_cstate=1` - Additional stability for dual-socket
- `numa_balancing=disable` - Disable automatic NUMA balancing if causing issues

---

## 3. Network Configuration (WiFi)

### Install WiFi Tools
```bash
pacman -S iw wpa_supplicant wireless_tools

# Enable s6 service
ln -s /etc/s6/sv/wpa_supplicant /etc/s6/sv/wpa_supplicant-log /run/service/
```

### WiFi s6 Service Setup
Create `/etc/s6/sv/wpa_supplicant/run`:
```bash
#!/bin/bash
exec 2>&1
exec wpa_supplicant -c /etc/wpa_supplicant/wpa_supplicant.conf -i wlan0
```

Create `/etc/s6/sv/wpa_supplicant/finish`:
```bash
#!/bin/bash
wpa_cli terminate
```

Make executable:
```bash
chmod +x /etc/s6/sv/wpa_supplicant/run
chmod +x /etc/s6/sv/wpa_supplicant/finish
```

### WiFi Configuration
```bash
# Generate config
wpa_passphrase "Your_SSID" "Your_Password" > /etc/wpa_supplicant/wpa_supplicant.conf

# Enable service
s6-svscanctl -a /run/service
```

---

## 4. MariaDB with Memory Limits

### Installation
```bash
pacman -S mariadb mariadb-s6

# Initialize database
mysql_install_db --user=mysql --basedir=/usr --datadir=/var/lib/mysql

# Enable s6 service with supervision
ln -s /etc/s6/sv/mysqld /run/service/
```

### Memory Configuration
Create `/etc/mysql/my.cnf.d/memory-limit.cnf`:
```ini
[mysqld]
# Limit MariaDB to 1GB RAM total
performance_schema = off
key_buffer_size = 64M
max_allowed_packet = 1M
thread_stack = 192K
thread_cache_size = 8
max_connections = 50
table_open_cache = 400
sort_buffer_size = 512K
net_buffer_length = 8K
read_buffer_size = 256K
read_rnd_buffer_size = 512K
myisam_sort_buffer_size = 8M
innodb_buffer_pool_size = 512M
innodb_log_file_size = 64M
innodb_thread_concurrency = 8
```

### s6 Memory Monitoring & Auto-Restart
Create `/etc/s6/sv/mysqld/check`:
```bash
#!/bin/bash
# Check if mysqld exceeds 1GB RSS
PID=$(pgrep -x mysqld)
if [ -n "$PID" ]; then
    RSS=$(cat /proc/$PID/status | grep VmRSS | awk '{print $2}')
    # 1GB = 1048576 KB
    if [ "$RSS" -gt "1048576" ]; then
        echo "MariaDB exceeded 1GB RAM limit (RSS: ${RSS}KB)" >&2
        exit 1
    fi
fi
exit 0
```

Add to `/etc/s6/sv/mysqld/run` before exec:
```bash
#!/bin/bash
exec 2>&1

# Set OOM score to make mysql killable before system services
echo 500 > /proc/self/oom_score_adj

exec mysqld_safe --defaults-file=/etc/mysql/my.cnf
```

Make check executable:
```bash
chmod +x /etc/s6/sv/mysqld/check
```

### Systemd-style OOM Protection (s6 alternative)
Create `/etc/s6/sv/mysqld/finish`:
```bash
#!/bin/bash
# Log restart reason
logger -t mysqld "MariaDB process exited with code $1, restarting..."
exit 0  # Exit 0 to allow restart by s6-supervise
```

---

## 5. OpenSSH Server (Hardened)

### Installation
```bash
pacman -S openssh openssh-s6

# Generate host keys (if not auto-generated)
ssh-keygen -A
```

### Hardened SSH Configuration
Edit `/etc/ssh/sshd_config`:
```
# Network
Port 22
ListenAddress 0.0.0.0
AddressFamily inet

# Authentication (Secure)
PermitRootLogin no
PasswordAuthentication no
PubkeyAuthentication yes
AuthenticationMethods publickey
ChallengeResponseAuthentication no

# Security hardening
X11Forwarding no
AllowTcpForwarding no
PermitTunnel no
GatewayPorts no
AllowAgentForwarding no
MaxAuthTries 3
MaxSessions 2
ClientAliveInterval 300
ClientAliveCountMax 2
LoginGraceTime 30

# Cryptography
Ciphers chacha20-poly1305@openssh.com,aes256-gcm@openssh.com,aes128-gcm@openssh.com,aes256-ctr,aes192-ctr,aes128-ctr
MACs hmac-sha2-512-etm@openssh.com,hmac-sha2-256-etm@openssh.com,umac-128-etm@openssh.com
KexAlgorithms curve25519-sha256,curve25519-sha256@libssh.org,ecdh-sha2-nistp521,ecdh-sha2-nistp384,ecdh-sha2-nistp256,diffie-hellman-group-exchange-sha256

# Allowed users only
AllowUsers yourusername

# Logging
SyslogFacility AUTH
LogLevel VERBOSE

# UsePrivilegeSeparation sandbox
UsePrivilegeSeparation sandbox
```

### Generate SSH Keys (Client)
```bash
# Ed25519 (recommended)
ssh-keygen -t ed25519 -C "user@host" -f ~/.ssh/id_ed25519

# Or RSA 4096 for legacy compatibility
ssh-keygen -t rsa -b 4096 -C "user@host" -f ~/.ssh/id_rsa

# Copy to server
ssh-copy-id -i ~/.ssh/id_ed25519.pub user@server
```

### s6 Service Setup
```bash
ln -s /etc/s6/sv/sshd /run/service/

# Verify running
s6-svstat /run/service/sshd
```

---

## 6. nftables Firewall

### Installation
```bash
pacman -S nftables nftables-s6

# Enable service
ln -s /etc/s6/sv/nftables /run/service/
```

### nftables Configuration
Create `/etc/nftables.conf`:
```
#!/usr/sbin/nft -f

flush ruleset

table inet filter {
    # Define sets for efficiency
    set tcp_allowed_out {
        type inet_service
        elements = { 22 }
    }

    chain input {
        type filter hook input priority 0; policy drop;

        # Allow established and related
        ct state established,related accept
        ct state invalid drop

        # Allow loopback
        iif lo accept

        # Drop invalid packets
        ip protocol tcp tcp flags & (fin|syn) == (fin|syn) drop
        ip protocol tcp tcp flags & (syn|rst) == (syn|rst) drop
        ip protocol tcp tcp flags & fin fin != 0 drop
        ip protocol tcp tcp flags & (ack|urg) == urg drop
        ip protocol tcp tcp flags == 0 drop

        # Rate limiting for new connections
        tcp flags syn limit rate 25/second burst 50 packets accept

        # Drop all incoming
        log prefix "nftables-input-dropped: " limit rate 5/minute
        drop
    }

    chain forward {
        type filter hook forward priority 0; policy drop;
        drop
    }

    chain output {
        type filter hook output priority 0; policy drop;

        # Allow established and related
        ct state established,related accept

        # Allow loopback
        oif lo accept

        # Allow DNS (needed for resolution)
        udp dport { 53, 853 } accept
        tcp dport { 53, 853 } accept

        # Allow SSH outbound only
        tcp dport @tcp_allowed_out accept

        # Log and drop everything else
        log prefix "nftables-output-dropped: " limit rate 5/minute
        drop
    }
}
```

### Load Configuration
```bash
nft -f /etc/nftables.conf

# Verify
nft list ruleset

# Test SSH connectivity before committing
# Keep current session open, test new connection
```

### Make Persistent
```bash
chmod +x /etc/nftables.conf
systemctl enable nftables  # If using s6, ensure service loads config
```

---

## 7. Final System Configuration

### Hostname & Locale
```bash
# Set hostname
echo "xeon-server" > /etc/hostname

# Configure hosts
cat >> /etc/hosts << EOF
127.0.0.1       localhost
127.0.1.1       xeon-server
::1             localhost ip6-localhost ip6-loopback
ff02::1         ip6-allnodes
ff02::2         ip6-allrouters
EOF

# Generate locale
nano /etc/locale.gen  # Uncomment your locale
locale-gen
echo "LANG=en_US.UTF-8" > /etc/locale.conf
```

### Users & Groups
```bash
# Create user
useradd -m -G wheel,audio,video,optical,storage -s /bin/bash username
passwd username

# Configure sudo
EDITOR=nano visudo
# Uncomment: %wheel ALL=(ALL) ALL
```

### Enable Services at Boot
```bash
# Create s6 service links for boot
mkdir -p /etc/s6/rc/init

# Essential services
ln -s /etc/s6/sv/dhcpcd /etc/s6/rc/init/
ln -s /etc/s6/sv/networkmanager /etc/s6/rc/init/
ln -s /etc/s6/sv/sshd /etc/s6/rc/init/
ln -s /etc/s6/sv/nftables /etc/s6/rc/init/
```

### Exit & Reboot
```bash
exit
umount -R /mnt
reboot
```

---

## 8. Post-Installation Verification

### Verify Services
```bash
# Check s6 services
s6-svstat /run/service/*

# Verify MariaDB memory
cat /proc/$(pgrep mysqld)/status | grep -E "VmRSS|VmHWM"

# Test SSH (from client)
ssh -v -i ~/.ssh/id_ed25519 user@server

# Verify firewall
nft list ruleset
ss -tlnp  # Should only show SSH port 22
```

### NUMA Optimization (Dual Xeon)
```bash
# Install numactl
pacman -S numactl

# Check NUMA layout
numactl --hardware

# Pin MariaDB to specific NUMA node if needed
# Add to s6 service run script:
# exec numactl --cpunodebind=0 --membind=0 mysqld_safe ...
```

---

## Troubleshooting

### Boot Issues
- If rEFInd fails to boot, check ESP partition flags: `parted /dev/nvme0n1 set 1 esp on`
- Verify initramfs was generated: `mkinitcpio -P`

### Memory Issues
- Monitor with: `watch -n 1 'cat /proc/$(pgrep mysqld)/status | grep VmRSS'`
- Check OOM killer logs: `dmesg | grep -i kill`

### Network Issues
- Test WiFi manually: `wpa_supplicant -c /etc/wpa_supplicant/wpa_supplicant.conf -i wlan0 -d`
- Check s6 service logs: `s6-svdt /run/service/wpa_supplicant`

### SSH Access Lost
- Boot from USB, mount system, edit `/etc/ssh/sshd_config` to temporarily enable password auth
- Or use physical console to troubleshoot

---

## Security Checklist

- [ ] Root login disabled in SSH
- [ ] Password authentication disabled in SSH
- [ ] Only key-based auth enabled
- [ ] Firewall blocking all incoming
- [ ] Firewall allowing only SSH outgoing
- [ ] MariaDB memory limited to 1GB
- [ ] MariaDB restart on OOM configured
- [ ] SSH using strong ciphers only
- [ ] Unnecessary services disabled
- [ ] Regular updates enabled: `pacman -Syu` weekly

---

## References

- [Artix Linux Wiki](https://wiki.artixlinux.org/)
- [s6 Supervision](https://skarnet.org/software/s6/)
- [rEFInd Documentation](https://www.rodsbooks.com/refind/)
- [nftables HowTo](https://wiki.nftables.org/)
- [MariaDB Optimization](https://mariadb.com/kb/en/optimization-and-tuning/)
- [OpenSSH Security](https://www.ssh.com/academy/ssh/security)
