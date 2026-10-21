# Bluetooth

### Fix `br-connection-profile-unavailable` error when using bluetooth wireless headset
```shell
sudo apt install libspa-0.2-bluetooth bluez
systemctl --user daemon-reload
systemctl --user restart pipewire pipewire-pulse wireplumber
sudo systemctl restart bluetooth
```


### Fix bluetooth no audio after idle playback
```bash
# Disable WirePlumber node suspension
mkdir -p ~/.config/wireplumber/wireplumber.conf.d
cat > ~/.config/wireplumber/wireplumber.conf.d/51-disable-suspension.conf << 'EOF'
monitor.alsa.rules = [
  {
    matches = [
      { node.name = "~alsa_input.*" }
      { node.name = "~alsa_output.*" }
    ]
    actions = {
      update-props = {
        session.suspend-timeout-seconds = 0
      }
    }
  }
]

monitor.bluez.rules = [
  {
    matches = [
      { node.name = "~bluez_input.*" }
      { node.name = "~bluez_output.*" }
    ]
    actions = {
      update-props = {
        session.suspend-timeout-seconds = 0
      }
    }
  }
]
EOF
systemctl --user restart wireplumber pipewire pipewire-pulse
```
