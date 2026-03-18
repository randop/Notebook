# Fonts

## 0xProto
```sh
mkdir -pv ~/.local/share/fonts
wget -O /tmp/0xproto.zip https://github.com/0xType/0xProto/releases/download/2.502/0xProto_2_502.zip
mkdir -pv /tmp/0xproto
unzip /tmp/0xproto.zip -d /tmp/0xproto
cp -v /tmp/0xproto/*.{otf,ttf,woff2} ~/.local/share/fonts/
rm -v /tmp/0xproto.zip
rm -rfv /tmp/0xproto
fc-cache -fv
```
