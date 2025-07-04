# OpenMandriva

## Installing OpenMandriva Lx on aarch64 hardware with UEFI support

### Write USB image
```bash
dd if=OpenMandriva-Lx-x86-64-rolling-3702.img \
	of=/dev/sda \
	bs=16k \
	oflag=direct \
	conv=sync \
	status=progress
```

### To install OpenMandriva Lx on aarch64 hardware with UEFI support

* Insert a USB storage device on another machine
* Write the image to the USB storage device using the write2device script
* Put the USB storage device into your aarch64 device and boot it from the USB device
* Log in as user "omv" with password "omv"
* If you want to install OpenMandriva Lx to the harddisk or internal storage, run ./install-openmandriva from /home/omv


```bash
sudo nmcli con add type ethernet ifname enp2s0 con-name "system-eth" \
  ipv4.method auto \
  autoconnect yes
```
