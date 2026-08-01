# Security

```bash
shred -n 3 -z -u erase.file
```

**SELinux** and **AppArmor** are **Linux Security Modules (LSMs)** that implement **Mandatory Access Control (MAC)**. They restrict what processes can do beyond traditional Unix permissions (DAC). The key difference is how they decide what is allowed.

## SELinux vs AppArmor – Full Security Comparison

Both implement Mandatory Access Control. The philosophical and technical differences are significant.

### Design Philosophy

| Aspect                  | SELinux                                      | AppArmor                                      |
|-------------------------|----------------------------------------------|-----------------------------------------------|
| Model                   | Label-based (Type Enforcement)               | Path-based                                    |
| Default stance          | Deny by default                              | Allow by default + restrict per profile       |
| Object identity         | Labels follow the inode                      | Bound to the path                             |
| Policy format           | Compiled binary modules                      | Human-readable text files                     |
| Learning curve          | Steep                                        | Gentle                                        |
| MLS / MCS support       | Full                                         | None                                          |
| LSM hooks mediated      | ~217 (Linux 6.19)                            | ~80                                           |

### Security Strength

**SELinux is the stronger security model** because:

- More complete kernel mediation
- True Multi-Level Security (MLS) and Multi-Category Security (MCS)
- Superior container isolation (containers can be prevented from seeing each other)
- Labels travel with the object → harder to bypass by renaming/moving files
- Designed for high-assurance environments from the beginning

**AppArmor advantages:**

- Dramatically easier to write, understand, and audit
- Lower risk of dangerous misconfiguration due to complexity
- Lower performance and memory overhead
- Excellent tooling on Ubuntu (`aa-genprof`, `aa-logprof`)

### When to Choose Which

**Choose SELinux when:**
- Maximum security is required (government, finance, multi-tenant hosting, critical infrastructure)
- You need MLS/MCS or strong container isolation
- You run RHEL family or are willing to invest in the learning curve

**Choose AppArmor when:**
- You want solid protection with much lower administrative cost
- You run Ubuntu/Debian or prefer simplicity
- You are on a desktop or general-purpose server
- You are learning MAC concepts for the first time

---

### Resources

- [archlinuxhardened/selinux](https://github.com/archlinuxhardened/selinux)

---

## Final Summary

| Goal                              | Recommended Choice      |
|-----------------------------------|-------------------------|
| Maximum theoretical security      | SELinux                 |
| Practical security + maintainability | AppArmor             |
| Enterprise / Government / Android | SELinux                 |
| Desktop / Artix / Ubuntu          | AppArmor      |
| Learning MAC concepts             | AppArmor     |
