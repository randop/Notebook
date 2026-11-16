# GPG setup

Setup using GnuPG 2.2 (the version in Debian bookworm) with Ed25519/Curve25519 keys, a certify-only primary key, and a separate signing subkey, so the primary key can be kept offline.

## 1. Install and harden

```bash
sudo apt update

# scdaemon only if you'll use a YubiKey
sudo apt install gnupg pinentry-curses scdaemon

mkdir -p ~/.gnupg && chmod 700 ~/.gnupg
cat > ~/.gnupg/gpg.conf <<'EOF'
personal-digest-preferences SHA512 SHA384 SHA256
cert-digest-algo SHA512
default-preference-list SHA512 SHA384 SHA256 AES256 AES192 AES ZLIB BZIP2 ZIP Uncompressed
keyid-format 0xlong
with-fingerprint
require-cross-certification
no-emit-version
no-comments
EOF
```

## 2. Generate keys in a throwaway keyring

Ideally do this on an offline machine (e.g. a live Debian USB). At minimum, use a RAM-backed temp directory:

```bash
export GNUPGHOME=$(mktemp -d -p /dev/shm)
cp ~/.gnupg/gpg.conf "$GNUPGHOME/"

# Primary key: certify-only, 2-year expiry. You'll be prompted for a strong passphrase.
gpg --quick-generate-key "Your Name <you@example.com>" ed25519 cert 2y

# Grab the fingerprint
FPR=$(gpg --list-keys --with-colons you@example.com | awk -F: '/^fpr/{print $10; exit}')

# Signing subkey (1 year), which is what git will use day to day
gpg --quick-add-key "$FPR" ed25519 sign 1y

# Optional: encryption subkey
gpg --quick-add-key "$FPR" cv25519 encr 1y
```

Use the same email address that's verified on your GitHub/GitLab account, otherwise commits will show as "Unverified".

## 3. Back up and protect the primary key

GnuPG 2.2 auto-creates a revocation certificate in `$GNUPGHOME/openpgp-revocs.d/`. Copy it somewhere safe and offline. Then export everything:

```bash
mkdir -p ~/gpg-backup && chmod 700 ~/gpg-backup
gpg --armor --export "$FPR"                 > ~/gpg-backup/public.asc
gpg --armor --export-secret-keys "$FPR"     > ~/gpg-backup/primary-and-subkeys.asc
gpg --armor --export-secret-subkeys "$FPR"  > ~/gpg-backup/subkeys-only.asc
cp "$GNUPGHOME"/openpgp-revocs.d/*.rev ~/gpg-backup/
```

Move `~/gpg-backup` onto encrypted offline media (and ideally a second copy elsewhere), then import only the subkeys into your everyday keyring:

```bash
unset GNUPGHOME
gpg --import ~/gpg-backup/subkeys-only.asc   # run this on your daily machine, with the file copied over
gpg --list-secret-keys                       # primary should show "sec#" (the # means the secret is absent)
```

Securely wipe the temp directory and any copies on the working machine (`shred -u` on the files, then reboot if you used a live USB).

## 4. Configure git

```bash
git config --global user.name "Your Name"
git config --global user.email "you@example.com"
git config --global user.signingkey "$FPR"
git config --global commit.gpgsign true
git config --global tag.gpgSign true

echo 'export GPG_TTY=$(tty)' >> ~/.bashrc   # needed for pinentry in terminals
```

If you want git to use one exact subkey, run `gpg --list-secret-keys --keyid-format=long` and set `user.signingkey` to the signing subkey's ID with a trailing `!`.

## 5. Publish the public key

```bash
gpg --armor --export "$FPR"
```

Paste the output into GitHub (Settings → SSH and GPG keys) or GitLab (Preferences → GPG Keys). Test it with:

```bash
git commit --allow-empty -S -m "test signed commit"
git log --show-signature -1
```

## Maintenance

- **Extend expiry:** on the machine with the primary key, run `gpg --quick-set-expire "$FPR" 2y` for the primary key, or `gpg --quick-set-expire "$FPR" 1y '*'` for all subkeys. Re-export the public key afterward.
- **Rotate subkeys:** add a new one with `--quick-add-key` and revoke the old one.
- **Hardware token:** for stronger protection, move the subkeys to a YubiKey or similar with `gpg --edit-key $FPR` → `keytocard`. Back up before doing this, since `keytocard` removes the local copy.
