# Development Tools

### Ghostty
* Installation
```bash
wget -nc -O ~/.local/bin/ghostty https://github.com/pkgforge-dev/ghostty-appimage/releases/download/v1.1.3%2B3/Ghostty-1.1.3-x86_64.AppImage

wget -nc -O ~/.local/share/icons/ghostty.png "https://github.com/pkgforge-dev/ghostty-appimage/blob/main/assets/ghostty.png?raw=true"

chmod +x ~/.local/bin/ghostty

cat << EOF | tee ~/.local/share/applications/ghostty.desktop
[Desktop Entry]
Name=Ghostty
Exec=%HOME%/.local/bin/ghostty
Type=Application
Icon=%HOME%/.local/share/icons/ghostty.png
Categories=System;TerminalEmulator;
X-DBUS-StartupType=Unique
StartupNotify=true
X-KDE-AuthorizeAction=shell_access
StartupWMClass=ghostty
Keywords=ghostty;terminal;console;script;run;execute;command;command-line;commandline;cli;bash;sh;shell;zsh;cmd;command prompt
EOF

sed -i "s#%HOME%#$HOME#g" ~/.local/share/applications/ghostty.desktop

update-desktop-database ~/.local/share/applications/
```