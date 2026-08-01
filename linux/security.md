# Security

```bash
shred -n 3 -z -u erase.file
```

**SELinux** and **AppArmor** are **Linux Security Modules (LSMs)** that implement **Mandatory Access Control (MAC)**. They restrict what processes can do beyond traditional Unix permissions (DAC). The key difference is how they decide what is allowed.

