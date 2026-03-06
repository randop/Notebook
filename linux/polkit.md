# Polkit (PolicyKit)

#### Allow a non-wheel user on Arch Linux to perform shutdown and restart without the wheel privileges.

```sh
sudo mkdir -p /etc/polkit-1/rules.d
sudo tee /etc/polkit-1/rules.d/70-allow-user-power.rules > /dev/null << 'EOF'
polkit.addRule(function(action, subject) {
    if (subject.user == "username") {
        if (action.id == "org.freedesktop.login1.power-off" ||
            action.id == "org.freedesktop.login1.power-off-multiple-sessions" ||
            action.id == "org.freedesktop.login1.reboot" ||
            action.id == "org.freedesktop.login1.reboot-multiple-sessions") {
            return polkit.Result.YES;
        }
    }
});
EOF
sudo chown root:polkitd /etc/polkit-1/rules.d/70-allow-user-power.rules
sudo chmod 644 /etc/polkit-1/rules.d/70-allow-user-power.rules
sudo systemctl reload polkit.service
```

```sh
# To shutdown:
systemctl poweroff

# To restart:
systemctl reboot
```

#### How it differs from sudo
* ***sudo*** gives an entire process root privileges (or runs a command as root) after you authenticate.
* ***polkit*** is more granular: it authorizes one specific action at a time, often without running the whole program as root. This makes it safer and better suited for desktop use cases.
