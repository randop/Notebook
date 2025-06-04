# Write large file on a USB flash drive using Linux
> This is slower but will directly put data on the disk and will allow instant eject.

```bash
dd if=/home/randop/Downloads/OpenMandriva-Lx-x86-64-rolling-3702.img \
	of=/dev/sdc \
	bs=16k \
	oflag=direct \
	conv=sync \
	status=progress
```

```bash
dd if=rhel-baseos-9.0-x86_64-dvd.iso \
	of=/run/media/randop/BOOTKIT/rhel-baseos-9.0-x86_64-dvd.iso \
	oflag=sync \
	bs=120k \
	status=progress
```

```bash
dd if=OracleLinux-R9-U0-x86_64-dvd.iso \
	of=/dev/sdb \
	conv=sync \
	oflag=sync \
	bs=16k \
	status=progress
```

```bash
dd if=Rocky-9.0-x86_64-dvd.iso \
	of=/run/media/randop/Ventoy/Rocky-9.0-x86_64-dvd.iso \
	bs=120k \
	oflag=direct \
	status=progress
```

```bash
dd if=Rocky-9.0-x86_64-dvd.iso \
	of=/dev/sda \
	bs=120k \
	oflag=direct \
	conv=sync \
	status=progress
```

```bash
dd if=debian-live-11.7.0-amd64-kde-nonfree.iso \
	of=/dev/sda \
	bs=120k \
	oflag=direct \
	conv=sync \
	status=progress
```
