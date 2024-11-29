# Setup Debian on OmniOS using Bhyve virtualization

## OmniOS r151052  omnios-r151052-dbe4644ba92      November 2024

#### Purge and start from scratch
```bash
zoneadm -z debian uninstall -F
zonecfg -z debian delete -F
```

### Installation
```bash
pkg install system/bhyve
pkg install brand/bhyve
pkg install zadm
zfs create -o mountpoint=/zones rpool/zones
dladm create-vnic -l e1000g0 bhyve0
zfs create -V 30G rpool/bhyve0
zonecfg -z debian
```

```
create -b
set brand=bhyve
set zonepath=/zones/bhyve
set ip-type=exclusive
add net
    set allowed-address=10.0.0.112/16
    set physical=bhyve0
end
add device
    set match=/dev/zvol/rdsk/rpool/bhyve0
end
add attr
    set name=bootdisk
    set type=string
    set value=rpool/bhyve0
end
add fs
    set dir=/filepool/randop/downloads/debian-12.8.0-amd64-netinst.iso
    set special=/filepool/randop/downloads/debian-12.8.0-amd64-netinst.iso
    set type=lofs
    add options ro
    add options nodevices
end
add attr
    set name=cdrom
    set type=string
    set value=/filepool/randop/downloads/debian-12.8.0-amd64-netinst.iso
end
add attr
    set name=acpi
    set type=string
    set value=off
end
add attr
    set name=bootrom
    set type=string
    set value=BHYVE_RELEASE
end
add attr
    set name=vnc
    set type=string
    set value=on
end
add device
  set match=/dev/ppt0
end
add attr
    set name=extra
    set type=string
    set value="-S -s 8:0,passthru,/dev/ppt0"
end
add dataset
    set name=filepool/shared
end
add attr
    set name=virtfs0
    set type=string
    set value=files,/files
end
verify
commit
exit

```

```bash
zoneadm -z debian install
zoneadm -z debian boot
zlogin -C debian
```

### Post Install
```bash
zonecfg -z debian
```
```
remove attr name=cdrom
verify
commit
exit
```

### VNC
```bash
zadm vnc -w 0.0.0.0:5900 debian
```

### Linux virtual installations
#### Realtek RTL8125 driver setup
[https://pineboards.io/blogs/tutorials/how-to-install-the-realtek-rtl8125-2-5g-ethernet-driver-on-ubuntu](https://pineboards.io/blogs/tutorials/how-to-install-the-realtek-rtl8125-2-5g-ethernet-driver-on-ubuntu)
```bash
tee -a /etc/modprobe.d/blacklist-r8169.conf > /dev/null <<EOT
# To use r8125 driver explicitly
blacklist r8169
EOT

update-initramfs -u

apt install build-essential dkms git
git clone https://github.com/awesometic/realtek-r8125-dkms.git
cd realtek-r8125-dkms
sudo ./dkms-install.sh
```

## Shared filesystem
```bash
zfs create -o quota=150G -o mountpoint=/files -o zoned=on filepool/shared
```

### Shared filesystem (setup inside debian)
```bash
mkdir /files
mount -t 9p -o trans=virtio,noatime,nodev,nodevmap,nosuid,uname=root,cache=mmap,access=client,trans=virtio,version=9p2000.L,msize=512000 files /files

echo 9pnet_virtio >> /etc/initramfs-tools/modules
export PATH=/sbin:$PATH
update-initramfs -u
```
