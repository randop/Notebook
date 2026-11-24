# Ladybird

Ladybird for Linux is available for x86-64 and ARM64.

## Installation
```bash
flatpak install --user 'https://flatpak.ladybird.org/ladybird.flatpakref'
```

### Verification
```bash
wget -qO- 'https://flatpak.ladybird.org/ladybird.flatpakref' | sed -n 's/^GPGKey=//p' | base64 -d | gpg --show-keys

# Expected fingerprint:
# 9436EDD8A32302AD5B9BC80FD941A9B53C448496
```
