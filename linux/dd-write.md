# Write large file on a USB flash drive using Linux
> This is slower but will directly put data on the disk and will allow instant eject.
```
dd if=rhel-baseos-9.0-x86_64-dvd.iso \
	of=/run/media/randop/BOOTKIT/rhel-baseos-9.0-x86_64-dvd.iso \
	oflag=sync \
	bs=120k \
	status=progress
```