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

## Hack
```sh
mkdir -pv ~/.local/share/fonts
mkdir -v /tmp/hack
wget -O /tmp/hack/hack-ttf.zip https://github.com/source-foundry/Hack/releases/download/v3.003/Hack-v3.003-ttf.zip
wget -O /tmp/hack/hack-webfont.zip https://github.com/source-foundry/Hack/releases/download/v3.003/Hack-v3.003-webfonts.zip
unzip /tmp/hack/hack-ttf.zip -d /tmp/hack
unzip /tmp/hack/hack-webfont.zip -d /tmp/hack
cp -v /tmp/hack/ttf/*.{otf,ttf,woff2} ~/.local/share/fonts/
rm -rfv /tmp/hack
fc-cache -fv
fc-list | grep "Hack"
```
