# CanoScan LiDE 400

## Installing driver
```bash
sudo apt install ~/Downloads/scangearmp2-3.70-1-deb
```

## Installing SANE utilities
```bash
sudo apt-get install sane-utils
```

## Create menu shortcut
```bash
cat <<EOF | tee ~/.local/share/applications/canon-scangear.desktop
[Desktop Entry]
Version=1.0
Name=ScanGear
Exec="/usr/bin/scangearmp2" %u
Icon=/home/user/Applications/scangear/icon.png
Type=Application
Categories=Office;
Terminal=false
StartupWMClass=scangear
StartupNotify=true

EOF
```

## Register menu shortcut
```bash
xdg-desktop-menu install ~/.local/share/applications/canon-scangear.desktop
```
