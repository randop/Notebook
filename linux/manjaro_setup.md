# SETUP: Manjaro
```
sudo pacman -S nvme-cli vim
sudo systemctl enable --now fstrim.timer
sudo systemctl start fstrim.timer
sudo timedatectl set-local-rtc 0
sudo timedatectl set-timezone UTC
sudo hwclock --systohc --utc
sudo systemctl enable --now systemd-timesyncd
sudo systemctl start systemd-timesyncd
```