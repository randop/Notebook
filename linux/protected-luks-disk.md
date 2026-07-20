# Protected LUKS disk file

**Here's how to create a 50MB LUKS (LUKS2) encrypted disk file/container on Arch Linux.**

### 1. Create the 50MB file (sparse for speed)

```bash
fallocate -l 50M encrypted-container.img
```

Or with `dd` (slower but ensures random data):

```bash
dd if=/dev/urandom of=encrypted-container.img bs=1M count=50 status=progress
```

### 2. Format it as LUKS2

```bash
sudo cryptsetup luksFormat --type luks2 encrypted-container.img
```

- You'll be asked to set a **strong passphrase** (twice).
- Use `--type luks2` for the modern format (default on recent cryptsetup).

### 3. Open the container

```bash
sudo cryptsetup luksOpen encrypted-container.img myencrypted
```

This creates `/dev/mapper/myencrypted`.

### 4. Create a filesystem inside (e.g. ext4)

```bash
sudo mkfs.ext4 -L encrypted /dev/mapper/myencrypted
```

### 5. Mount it

```bash
mkdir ~/encrypted-mount
sudo mount /dev/mapper/myencrypted ~/encrypted-mount
```

Now you can use `~/encrypted-mount` like a normal folder.

---

### To unmount and close later

```bash
sudo umount ~/encrypted-mount
sudo cryptsetup luksClose myencrypted
```

### Quick one-liner reminder for future use

To reopen the container:

```bash
sudo cryptsetup luksOpen encrypted-container.img myencrypted
sudo mount /dev/mapper/myencrypted ~/encrypted-mount
```

### Useful tips

- **Check status**: `sudo cryptsetup status myencrypted`
- **List LUKS info**: `sudo cryptsetup luksDump encrypted-container.img`
- **Change passphrase**: `sudo cryptsetup luksChangeKey encrypted-container.img`
- For better performance on large files, you can use `fallocate` + `cryptsetup --use-random` if needed.

