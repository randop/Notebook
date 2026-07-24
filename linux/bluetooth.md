# Bluetooth

### Fix `br-connection-profile-unavailable` error when using bluetooth wireless headset
```shell
sudo apt install libspa-0.2-bluetooth bluez
systemctl --user daemon-reload
systemctl --user restart pipewire pipewire-pulse wireplumber
sudo systemctl restart bluetooth
```

