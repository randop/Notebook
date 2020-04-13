# How to create a bootable linux usb drive
> Reference: https://linuxize.com/post/how-to-create-a-bootable-linux-usb-drive/

***
1.) Determine the USB flash drive to use
```
lsblk
```

2.) Unmount the USB flash drive
```
sudo umount /dev/sdc1
```

3.) Flash the ISO image to the USB drive
```
sudo dd bs=4M if=/path/to/ubuntu-18.04.2-desktop-amd64.iso of=/dev/sdc1 status=progress oflag=sync
```