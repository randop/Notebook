# Use an ISO File as a CD-ROM Repository
## https://www.baeldung.com/linux/repository-type-iso-file
```bash
mount --types iso9660 /opt/iso/debian-12.4.0-amd64-DVD-1.iso /media/apt --options ro,loop
apt-cdrom --cdrom=/media/apt add --no-mount

deb [trusted=yes] file:///media/apt bookworm main
```

### Update apt configuration
> /etc/apt/sources.list
```
deb [trusted=yes] file:///media/apt bookworm main non-free-firmware
```
