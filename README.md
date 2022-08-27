<h1 align="center">Welcome to my Notebook 👋</h1>
<p>
  <img src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
  <a href="https://gitlab.com/randop/notebook">
    <img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" target="_blank" />
  </a>
  <a href="https://gitlab.com/randop/notebook/-/commits/main">
    <img alt="Maintenance" src="https://img.shields.io/badge/Maintained%3F-yes-green.svg" target="_blank" />
  </a>
</p>

### 🏠 [Homepage](https://gitlab.com/randop/notebook)
#### The repository of my notes on various topics and interest. This is for learning and sharing purposes.

## Author

👤 **Randolph Ledesma**

* 📱 +1 (415) 754-3092
* 🌐 [https://gitlab.com/randop](https://gitlab.com/randop)
* 👷 [https://www.linkedin.com/in/randop/](https://www.linkedin.com/in/randop/)
* 📍 Philippines

### Types of screws, screw heads, washers and nuts
![Picture of Types of screws, screw heads, washers and nuts](https://gitlab.com/randop/notebook/-/raw/main/assets/types-of-screws-screw-heads-washers-and-nuts.jpg?inline=true)

### How to read a metric screw
![Picture of How to read a metric screw](https://gitlab.com/randop/notebook/-/raw/main/assets/How-to-Read-a-Metric-Screw-Thread-Callout.png?inline=true)

### Hex screws sizes
![Picture of hex screws sizes](https://gitlab.com/randop/notebook/-/raw/main/assets/hex-screws-sizes.jpg?inline=true)

### Personal Computer (PC) M3 screw set specifications
![Picture of Personal Computer (PC) M3 screw set specifications](https://gitlab.com/randop/notebook/-/raw/main/assets/pc-m3-screws.png?inline=true)

## System Setup
#### Ubuntu Studio 22.04.1 Linux Operating System
```bash
wget --continue \
	https://cdimage.ubuntu.com/ubuntustudio/releases/22.04.1/release/ubuntustudio-22.04.1-dvd-amd64.iso
```

```
https://cdimage.ubuntu.com/ubuntustudio/releases/22.04.1/release/SHA256SUMS

3fe6fa9a17fa7a4b34b2ec7ff3b9500d354ca644aedcc37096b923f14247f504 *ubuntustudio-22.04.1-dvd-amd64.iso
```

```
https://cdimage.ubuntu.com/ubuntustudio/releases/22.04.1/release/SHA256SUMS.gpg

-----BEGIN PGP SIGNATURE-----

iQIzBAABCgAdFiEEhDk43yKNIvezdCvA2Uqj8O/iEJIFAmL02gcACgkQ2Uqj8O/i
EJLOHxAAnlY1tyghKTNQB3izXQJCXj5NIXjO50RCZQbJbxfUMzzKnzdJeaImUyO/
KwEFowGXdQ12gpBVjdmD4ocGTagDbFy8WGJ7tkq+YP++cKTnDtsIO9fk+QhhYalL
cQ/uP2BXSqDoepIrRGM/QVixnAM171VLqDBTcCR8jqKucA2fn+u0i0C+t7WWQaLk
PTrUJiY9n/Ms8UskP1oN4zBRB0TucBv9ZiuHTW4KS+diXoLSFasaMSbkLPHaKD0s
MmPbXXGACUc48rdJtn7FEUwEnTAm71IwAL19U0hbfrKqq4tTFhWRCJ16nO50ivHa
5fkq+Ah42iTNWMSdATMUQoWKrXhbgdNG6xMlXeawGtZkU/7wwplBzp+kIdtrV1aJ
HRHNlNNFc/9O3Hv/YUTv+ELLThrgZSeygq70/NLXHd4aTaV66m6ECz4rfZ/M+aiO
j2kVmCWa48it7YFmhTI5lasWNKqRqepNmJkkhP+ZbyMcAKEVc+smMnefa13aPVso
kPW3aGxxyTc5ZpBJXM22MyL+YiqNgIPSFAm2L6gsFxqPvztoEC3ti93TCEV9CX8d
SG8OcYBDaqNgsGd+qEyyFUi7t6g+X3l0VXTe+2xz9b5Qde1PEu52PDBYXeT7Rd77
PHgrqSxI3FFNIj1wWiG5iWJxD2MuG/upOdfg6k210/Np2wwd3i0=
=QlUg
-----END PGP SIGNATURE-----

```

#### OS Tweaks
```bash
sudo systemctl stop \
	cups.path \
	cups-browsed.service \
	cups.service \
	cups.socket \
	apt-daily-upgrade.timer \
	apt-daily.timer \
	fwupd-refresh.timer \
	motd-news.timer \
	plocate-updatedb.timer \
	ua-timer.timer \
	unattended-upgrades \
	packagekit

sudo systemctl mask \
	cups.path \
	cups-browsed.service \
	cups.service \
	cups.socket \
	apt-daily-upgrade.timer \
	apt-daily.timer \
	fwupd-refresh.timer \
	motd-news.timer \
	plocate-updatedb.timer \
	ua-timer.timer \
	unattended-upgrades \
	packagekit

sudo apt purge --auto-remove unattended-upgrades

sudo snap remove --purge firefox
sudo snap remove --purge gnome-3-38-2004
sudo snap remove --purge gtk-common-themes
sudo snap remove --purge bare
sudo snap remove --purge core20
sudo snap remove --purge snapd
sudo apt purge --yes --auto-remove snapd

sudo nano /etc/apt/preferences.d/nosnap.pref

# /etc/apt/preferences.d/nosnap.pref
Package: snapd
Pin: release a=*
Pin-Priority: -10

sudo nano /etc/sysctl.d/80-ipv6-disable.conf

# /etc/sysctl.d/80-ipv6-disable.conf
net.ipv6.conf.all.disable_ipv6=1
net.ipv6.conf.default.disable_ipv6=1
net.ipv6.conf.lo.disable_ipv6=1

sudo nano /etc/modprobe.d/edac-blacklist.conf

# /etc/modprobe.d/edac-blacklist.conf
blacklist i82975x_edac
blacklist amd64_edac_mod
blacklist skx_edac
blacklist e752x_edac
blacklist i5400_edac
blacklist i3200_edac
blacklist i5000_edac
blacklist ie31200_edac
blacklist edac_mce_amd
blacklist i3000_edac
blacklist sb_edac
blacklist pnd2_edac
blacklist i7core_edac
blacklist i7300_edac
blacklist x38_edac
blacklist i5100_edac

sudo nano /etc/modprobe.d/nouveau-blacklist.conf

# /etc/modprobe.d/nouveau-blacklist.conf
blacklist nouveau
options nouveau modeset=0

sudo ufw enable
sudo ufw status verbose

sudo mkdir /etc/xdg/autostart.disabled
sudo mv -v \
	/etc/xdg/autostart/baloo_file.desktop
	/etc/xdg/autostart.disabled
sudo mv -v \
	/etc/xdg/autostart/geoclue-demo-agent.desktop
	/etc/xdg/autostart.disabled
sudo mv -v \
	/etc/xdg/autostart/org.kde.discover.notifier.desktop
	/etc/xdg/autostart.disabled
sudo mv -v \
	/etc/xdg/autostart/spice-vdagent.desktop
	/etc/xdg/autostart.disabled

sudo apt update && sudo apt upgrade -y
sudo ubuntu-drivers autoinstall
sudo update-initramfs -u
```

#### Flatpak
```bash
flatpak --user \
	remote-add flathub \
	https://flathub.org/repo/flathub.flatpakrepo

flatpak --user install org.mozilla.firefox

nano ~/.bashrc
# ~/.bashrc
export XDG_DATA_DIRS=$XDG_DATA_DIRS:/var/lib/flatpak/exports/share/:~/.local/share/flatpak/exports/share/

```

#### Emacs
```bash
sudo apt install git emacs ripgrep fd-find
git clone --depth 1 https://github.com/doomemacs/doomemacs ~/.emacs.d
~/.emacs.d/bin/doom install
```

#### NodeJS
```bash
# Install Node Version Manager using curl
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash

# Install Node Version Manager using wget
wget -qO- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash

# Install latest NodeJS LTS version
nvm install --lts
```

## 📝 License

Copyright © 2016 — 2022 [Randolph Ledesma](https://gitlab.com/randop).

Last updated on 2022-08-27T15:44:05.000Z
