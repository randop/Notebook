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

### Fix emoji rendering
```sh
# Check the issue
fc-match --format='%{file}\n' ":charset=1F600"
# Make sure that DejaVu Sans is not stealing the glyphs
# /usr/share/fonts/truetype/dejavu/DejaVuSans.ttf (ISSUE)
# /usr/share/fonts/truetype/noto/NotoColorEmoji.ttf (GOOD)

# Fix it
sudo apt apt install fonts-noto-color-emoji
mkdir -p ~/.config/fontconfig
cat > ~/.config/fontconfig/fonts.conf << 'EOF'
<?xml version="1.0"?>
<!DOCTYPE fontconfig SYSTEM "fonts.dtd">
<fontconfig>

  <!-- Force Noto Color Emoji to win for any codepoint it covers -->
  <match>
    <test name="family" compare="eq"><string>monospace</string></test>
    <edit name="family" mode="prepend" binding="strong">
      <string>Noto Color Emoji</string>
    </edit>
  </match>

  <!-- Global emoji family alias -->
  <match>
    <test name="family" compare="eq"><string>emoji</string></test>
    <edit name="family" mode="prepend" binding="strong">
      <string>Noto Color Emoji</string>
    </edit>
  </match>

  <!-- Block DejaVu from serving emoji codepoints -->
  <match target="scan">
    <test name="family" compare="eq"><string>DejaVu Sans</string></test>
    <edit name="charset" mode="assign">
      <minus>
        <name>charset</name>
        <charset>
          <!-- Emoticons block U+1F600–U+1F64F -->
          <range><int>0x1F600</int><int>0x1F64F</int></range>
          <!-- Misc symbols and pictographs U+1F300–U+1F5FF -->
          <range><int>0x1F300</int><int>0x1F5FF</int></range>
          <!-- Transport and map U+1F680–U+1F6FF -->
          <range><int>0x1F680</int><int>0x1F6FF</int></range>
          <!-- Supplemental symbols U+1F900–U+1F9FF -->
          <range><int>0x1F900</int><int>0x1F9FF</int></range>
          <!-- Symbols extended-A U+1FA00–U+1FA6F -->
          <range><int>0x1FA00</int><int>0x1FA6F</int></range>
          <!-- Misc symbols U+2600–U+26FF -->
          <range><int>0x2600</int><int>0x26FF</int></range>
          <!-- Dingbats U+2700–U+27BF -->
          <range><int>0x2700</int><int>0x27BF</int></range>
        </charset>
      </minus>
    </edit>
  </match>

</fontconfig>
EOF
fc-cache -fv

# Verify
if echo "$LANG" | grep -q "UTF-8"; then
    echo "LANG is UTF-8: $LANG"
else
    echo "LANG is not UTF-8: ${LANG:-<unset>}"
fi
fc-list | grep -i noto | grep -i color
fc-match --format='%{file}\n' ":charset=1F600"

# Final check
echo "😀 🚀 👨‍👩‍👦 ✅"
```
