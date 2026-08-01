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

- **SELinux Project (Official Hub)**  
  https://selinuxproject.github.io/  
  Central site for the upstream project, tools, libraries, and documentation.
- **SELinux Reference Policy (refpolicy)**  
  https://github.com/SELinuxProject/refpolicy  
  The main modular policy used by most distributions (including Arch/Artix efforts).
- **SELinux Userspace Tools & Libraries**  
  https://github.com/SELinuxProject/selinux
- **The SELinux Notebook** (comprehensive open-source book)  
  https://github.com/SELinuxProject/selinux-notebook  
  One of the best deep technical references covering kernel, userspace, and policy.
- ArchWiki SELinux page  
  https://wiki.archlinux.org/title/SELinux  
- archlinuxhardened SELinux packages & build scripts  
  https://github.com/archlinuxhardened/selinux  
- Arch-specific policy patches  
  https://github.com/archlinuxhardened/selinux-policy-arch
- Getting started with SELinux (RHEL 8/9 style)  
  https://docs.redhat.com/en/documentation/red_hat_enterprise_linux/8/html/using_selinux/getting-started-with-selinux_using-selinux  
- Full SELinux User’s and Administrator’s Guide (classic, still excellent)  
  https://docs.redhat.com/en/documentation/red_hat_enterprise_linux/7/html/selinux_users_and_administrators_guide/index  
- CentOS SELinux HowTo  
  https://wiki.centos.org/HowTos/SELinux
- Gentoo SELinux main page  
  https://wiki.gentoo.org/wiki/SELinux  
- Gentoo SELinux Tutorials (highly recommended for learning concepts)  
  https://wiki.gentoo.org/wiki/SELinux/Tutorials  
- Gentoo SELinux Installation guide  
  https://wiki.gentoo.org/wiki/SELinux/Installation  
- Gentoo SELinux Project  
  https://wiki.gentoo.org/wiki/Project:SELinux
- Official Android SELinux documentation  
  https://source.android.com/docs/security/features/selinux  
- LineageOS SELinux guide (practical for custom ROMs)  
  https://lineageos.org/engineering/HowTo-SELinux/
- **Introduction to SELinux** (GitHub Blog – modern practical overview)  
  https://github.blog/developer-skills/programming-languages-and-frameworks/introduction-to-selinux/
- **Stop Disabling SELinux**  
  https://stopdisablingselinux.com/
- **Dan Walsh** (“Mr. SELinux”) – long-time Red Hat engineer  
  - LiveJournal archive: https://danwalsh.livejournal.com/  
  - GitHub: https://github.com/rhatdan  
  - Red Hat author page: https://www.redhat.com/en/authors/dan-walsh

### Useful Tools & Related Projects

- `audit2allow`, `semanage`, `restorecon`, `setools` – come with the policycoreutils / setools packages.
- AppArmor comparison context (for when you decide between the two):  
  https://wiki.archlinux.org/title/AppArmor  
  https://apparmor.net/

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
