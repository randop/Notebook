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

## Table of contents
* [How to save the pricess using 8 programming languages](#how-to-save-the-pricess-using-8-programming-languages)
* [Types of screws, screw heads, washers and nuts](#types-of-screws-screw-heads-washers-and-nuts)
* [Hex screws sizes](#hex-screws-sizes)
* [PC M3 screw set specifications](#personal-computer-pc-m3-screw-set-specifications)
* [System Setup](#system-setup)
* [How to code using NASM assembler](#how-to-code-using-nasm-assembler-assembly-programming-language)
* [License](#-license)

## 😆
![How to save the pricess using 8 programming languages](https://gitlab.com/randop/notebook/-/raw/main/assets/git-the-princess.jpg?inline=true)

### Types of screws, screw heads, washers and nuts
![Picture of Types of screws, screw heads, washers and nuts](https://gitlab.com/randop/notebook/-/raw/main/assets/types-of-screws-screw-heads-washers-and-nuts.jpg?inline=true)

### How to read a metric screw
![Picture of How to read a metric screw](https://gitlab.com/randop/notebook/-/raw/main/assets/How-to-Read-a-Metric-Screw-Thread-Callout.png?inline=true)

### Hex screws sizes
![Picture of hex screws sizes](https://gitlab.com/randop/notebook/-/raw/main/assets/hex-screws-sizes.jpg?inline=true)

### Personal Computer (PC) M3 screw set specifications
![Picture of Personal Computer (PC) M3 screw set specifications](https://gitlab.com/randop/notebook/-/raw/main/assets/pc-m3-screws.png?inline=true)

## System Setup

### Ubuntu Studio 24.04.1 (Noble Numbat) Linux 6.8.0-51-lowlatency (64-bit) KDE 5.27.11
```bash
wget --continue \
    https://cdimage.ubuntu.com/ubuntustudio/releases/noble/release/ubuntustudio-24.04.1-dvd-amd64.iso
```
```
https://cdimage.ubuntu.com/ubuntustudio/releases/noble/release/SHA256SUMS

f2d1e8999b9d11fe8249d6eb3c1d53eff549c6c7b388747e3d0442e6cb138f8f *ubuntustudio-24.04.1-dvd-amd64.iso
```
```
https://cdimage.ubuntu.com/ubuntustudio/releases/noble/release/ubuntustudio-24.04.1-dvd-amd64.iso

-----BEGIN PGP SIGNATURE-----

iQIzBAABCgAdFiEEhDk43yKNIvezdCvA2Uqj8O/iEJIFAmbQpvsACgkQ2Uqj8O/i
EJJOwRAAgSOJkrjC6wu6zKZ0bx261CYjOBV0jJ5mloaDk8nDE9wnpxc0jvhxJ/5Q
MgIvymK2ayF6t+iULK3wj9TN2sxXawGELwq01/AD/AlQU2xvvFnUk7FDYeRIwigR
8g2lR4Qhp4JGODn0Q8N75mPmN8gnwbILEr0RfgTDp0PySenpObG1s29c4vjxDMx2
LziDqjESik17FEU4YhEpXfECYh3+7w7jlRn7urOzW40PA3W7uWUCr5OaUzDwzZjJ
ntOt8JyQMuFHqhhJsm+xK83eGnAOu5zqDsNMYHtTleiihDE3Njaggklw8OgDLKRO
awyOeifiEQmCV1OXdS8WmY465CUzPG6YwRPhxuUhGvdGrdPMfTECPWmXmnQ7qMnp
Ia21kMSik4IkTiTYUfrosBJUxX3MW8L8C6wmw5XpSYDCvN8zLvCy0S8x05HxKQcZ
sbd2oTcHYnKzFncodEQPKmPhGTeHLw7xpt1fpDlWRdw+80sI8I1LJVWvGSQ6rsTo
eLqJRbFOc+ubnfabzJi+hQp/0NWOQ8DHNlIvWOE97bIXFCtsy4OSL/YJ9yAtrm58
68NuVQJN9gc02KP0CnN8VOg58W/1lC2ah9aWVK+L22qxZAV9jZQGoERWigtWaPaz
0G4865GU9cymNwdNNY8IMNX8kczFFMANSO9KU23zJ6+aW9Ss3tU=
=FW8z
-----END PGP SIGNATURE-----
```

#### OS Tweaks
```bash
sudo systemctl stop \
	apt-daily-upgrade.timer \
	apt-daily.timer \
	fwupd-refresh.timer \
	motd-news.timer \
	plocate-updatedb.timer \
	ua-timer.timer \
	unattended-upgrades \
	packagekit

sudo systemctl mask \
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
sudo snap remove --purge gnome-42-2204
sudo snap remove --purge firmware-updater
sudo snap remove --purge gtk-common-themes
sudo snap remove --purge freeshow
sudo snap remove --purge thunderbird
sudo snap remove --purge snapd-desktop-integration
sudo snap remove --purge bare
sudo snap remove --purge core22
sudo snap remove --purge snapd

sudo apt purge --yes --auto-remove snapd

sudo ufw enable
sudo ufw status verbose

sudo mkdir /etc/xdg/autostart.disabled
sudo mv -v \
	/etc/xdg/autostart/baloo_file.desktop \
	/etc/xdg/autostart.disabled
sudo mv -v \
	/etc/xdg/autostart/geoclue-demo-agent.desktop \
	/etc/xdg/autostart.disabled
sudo mv -v \
	/etc/xdg/autostart/org.kde.discover.notifier.desktop \
	/etc/xdg/autostart.disabled
sudo mv -v \
	/etc/xdg/autostart/spice-vdagent.desktop \
	/etc/xdg/autostart.disabled

sudo apt update && sudo apt upgrade -y
sudo ubuntu-drivers autoinstall
sudo update-initramfs -u
```

### Disable IPv6
```bash
cat << EOF | sudo tee /etc/sysctl.d/80-ipv6-disable.conf
net.ipv6.conf.all.disable_ipv6=1
net.ipv6.conf.default.disable_ipv6=1
net.ipv6.conf.lo.disable_ipv6=1
EOF
```

### Nosnap
```bash
cat << EOF | sudo tee /etc/apt/preferences.d/nosnap.pref
Package: snapd
Pin: release a=*
Pin-Priority: -10
EOF
```

### Use Unbuffered DIMM
```bash
cat << EOF | sudo tee /etc/modprobe.d/edac-blacklist.conf
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
EOF
```

### nvidia
```bash
sudo apt install -y libglvnd-dev
cat << EOF | sudo tee /etc/modprobe.d/nouveau-blacklist.conf
blacklist nouveau
options nouveau modeset=0
EOF
```

### iommu
> Enable IOMMU support by setting the correct kernel parameter depending on the type of CPU in use:
>
> For Intel CPUs (VT-d) set `intel_iommu=on`
>
> For AMD CPUs (AMD-Vi) set `amd_iommu=on`
>
> You should also append the `iommu=pt` parameter. This will prevent Linux from touching devices which cannot be passed through.

#### Flatpak
```bash
flatpak --user \
	remote-add flathub \
	https://flathub.org/repo/flathub.flatpakrepo

flatpak --user install org.mozilla.firefox
```

#### Emacs
```bash
sudo apt install -y git emacs ripgrep fd-find
git clone --depth 1 https://github.com/doomemacs/doomemacs ~/.emacs.d
~/.emacs.d/bin/doom install
```

#### NodeJS
```bash
# Install Node Version Manager using curl
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.1/install.sh | bash

# Install Node Version Manager using wget
wget -qO- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.1/install.sh | bash

# Install latest NodeJS LTS version
nvm install --lts
```

#### Development Tools
```bash
sudo apt install -y \
	kate \
	kwrite \
	kdevelop \
	meson \
	clang \
	build-essential \
	pkg-config
```

#### Docker
```bash
sudo apt install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt update
sudo apt install -y \
	docker-ce \
	docker-ce-cli \
	containerd.io \
	docker-compose-plugin

sudo usermod -aG docker $USER
newgrp docker
docker run hello-world

# Disable docker service to optimize boot speed
sudo systemctl disable docker.service
sudo systemctl disable containerd.service

# Enable docker
sudo systemctl enable docker.service
sudo systemctl enable containerd.service
```

#### PostgreSQL
```bash
sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'
wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
sudo apt update
sudo apt install -y postgresql-11 postgresql-11-postgis-3

# Disable postgresql to optimize boot speed
sudo systemctl disable --now postgresql
sudo systemctl status postgresql
```

#### Redpanda
```bash
curl -1sLf 'https://dl.redpanda.com/nzc4ZYQK3WRGd9sy/redpanda/cfg/setup/bash.deb.sh' | \
sudo -E bash && sudo apt install redpanda -y

curl -LO https://github.com/redpanda-data/redpanda/releases/latest/download/rpk-linux-amd64.zip
mkdir -p ~/.local/bin
export PATH="~/.local/bin:$PATH"
unzip rpk-linux-amd64.zip -d ~/.local/bin/
rpk version
```

---
## How to code using NASM assembler (Assembly programming language)
```asm
; Copyright© 1998—2025 Randolph Ledesma (randop at me.com)
; $ nasm -g -f elf64 hello.asm
; $ ld -o hello hello.o
global _start

section .data
        str: db "Hello World, Randolph Ledesma", 0xA
        STRSIZE: equ $ - str
        STDOUT: equ 1

section .text
_start:
        mov rax, 1
        mov rdi, STDOUT
        mov rsi, str
        mov rdx, STRSIZE
        syscall

        mov rax, 60
        syscall

```
#### Linux NASM
```bash
nasm -g -f elf64 hello.asm
ld -o hello hello.o

nasm -g -f elf64 demo.asm
ld -o demo demo.o
gcc -o [-no-pie] demo demo.o -v
```

#### NVIDIA System Management Interface -- v525.60.11
```bash
user@linux:~/Downloads$ nvidia-smi
Mon Apr 10 12:15:48 2023
+-----------------------------------------------------------------------------+
| NVIDIA-SMI 525.60.11    Driver Version: 525.60.11    CUDA Version: 12.0     |
|-------------------------------+----------------------+----------------------+
| GPU  Name        Persistence-M| Bus-Id        Disp.A | Volatile Uncorr. ECC |
| Fan  Temp  Perf  Pwr:Usage/Cap|         Memory-Usage | GPU-Util  Compute M. |
|                               |                      |               MIG M. |
|===============================+======================+======================|
|   0  Quadro P400         Off  | 00000000:02:00.0  On |                  N/A |
| 34%   42C    P8    N/A /  N/A |    753MiB /  2048MiB |     31%      Default |
|                               |                      |                  N/A |
+-------------------------------+----------------------+----------------------+

+-----------------------------------------------------------------------------+
| Processes:                                                                  |
|  GPU   GI   CI        PID   Type   Process name                  GPU Memory |
|        ID   ID                                                   Usage      |
|=============================================================================|
|    0   N/A  N/A      1266      G   /usr/lib/xorg/Xorg                308MiB |
|    0   N/A  N/A      1530      G   /usr/bin/kwin_x11                  73MiB |
|    0   N/A  N/A      1599      G   /usr/bin/plasmashell               36MiB |
|    0   N/A  N/A      8851      G   /app/lib/firefox/firefox          164MiB |
|    0   N/A  N/A     48822      G   ...in,WebAssemblyTrapHandler       94MiB |
|    0   N/A  N/A     81537      G   ...in,WebAssemblyTrapHandler       68MiB |
+-----------------------------------------------------------------------------+
```

#### Curl
##### View contents of remote file
```bash
curl -1sLf 'https://gitlab.com/randop/notebook/-/raw/main/README.md?inline=false'
```

#### Downloading using aria2
```bash
aria2c --continue=true \
    --max-concurrent-downloads=1 \
    --max-connection-per-server=12 \
    --file-allocation=none \
    --input-file=debian-iso-dvd.txt
```

---
## 📝 License

Copyright © 2010 — 2025 [Randolph Ledesma](https://gitlab.com/randop).

Last updated on 2025-06-02T20:14:51.000Z
