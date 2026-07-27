# GPG recovery

**Backup and restore GPG keys on Linux**

### Recommended: Full backup
GPG stores everything under `~/.gnupg`.
Backing up the whole directory is the most reliable method.

**Backup**
```bash
# Stop any running gpg-agent
gpgconf --kill gpg-agent

# Create a compressed archive
tar -czf gpg-backup-$(date +%Y%m%d).tar.gz -C ~ .gnupg
```

**Restore**
```bash
# Extract (NOTE: this overwrites the existing ~/.gnupg)
tar -xzf gpg-backup-YYYYMMDD.tar.gz -C ~

# Fix permissions
chmod 700 ~/.gnupg
find ~/.gnupg -type f -exec chmod 600 {} \;
find ~/.gnupg -type d -exec chmod 700 {} \;

# Restart agent
gpgconf --kill gpg-agent
gpg --list-secret-keys   # verify
```

---

### Export individual keys using portable method

**1. Backup public keys**
```bash
gpg --export --armor --output public-keys.asc
# or a specific key:
gpg --export --armor KEYID > public-KEYID.asc
```

**2. Backup secret (private) keys**
```bash
gpg --export-secret-keys --armor --output private-keys.asc
# or a specific key:
gpg --export-secret-keys --armor KEYID > private-KEYID.asc
```

**3. Backup ownertrust**
```bash
gpg --export-ownertrust > ownertrust.txt
```

**Restore from exported files**
```bash
gpg --import public-keys.asc
gpg --import private-keys.asc
gpg --import-ownertrust ownertrust.txt

# Optional: set ultimate trust on your own keys
gpg --edit-key KEYID
```

---

### Useful extra commands

| Task                        | Command                                      |
|-----------------------------|----------------------------------------------|
| List secret keys            | `gpg --list-secret-keys --keyid-format LONG` |
| List public keys            | `gpg --list-keys --keyid-format LONG`        |
| Delete a key (careful!)     | `gpg --delete-secret-and-public-keys KEYID`  |
| Change passphrase           | `gpg --change-passphrase KEYID`              |
| Check key integrity         | `gpg --check-trustdb`                        |

