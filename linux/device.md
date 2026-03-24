# Device

### Emulate USB device
```sh
pacman -Syy socat
socat -d -d PTY,link=/dev/ttyUSB1,mode=666,unlink-close=1 /dev/zero
```
