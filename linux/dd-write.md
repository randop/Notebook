# Write large file on a USB flash drive using Linux
> This is slower but will directly put data on the disk and will allow instant eject.
```
dd if=rhel-baseos-9.0-x86_64-dvd.iso \
	of=/run/media/randop/BOOTKIT/rhel-baseos-9.0-x86_64-dvd.iso \
	oflag=sync \
	bs=120k \
	status=progress
```

```
dd if=OracleLinux-R9-U0-x86_64-dvd.iso \
	of=/dev/sdb
	conv=sync
	oflag=sync \
	bs=16k \
	status=progress
```

```
dd if=Rocky-9.0-x86_64-dvd.iso \
	of=/run/media/randop/Ventoy/Rocky-9.0-x86_64-dvd.iso \
	bs=120k \
	oflag=direct \
	status=progress
```
