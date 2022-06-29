# Setup and Build Yocto for MYD-C8MMX
```
sudo apt install \
	gawk wget git diffstat unzip texinfo gcc \
	build-essential chrpath socat cpio python3 \
	python3-pip python3-pexpect xz-utils \
	debianutils iputils-ping python3-git \
	python3-jinja2 libegl1-mesa libsdl1.2-dev \
	pylint3 xterm python3-subunit mesa-common-dev \
	zstd liblz4-tool
```
```
sudo apt install \
	gawk wget git-core diffstat unzip texinfo \
	gcc-multilib build-essential chrpath socat \
	libsdl1.2-dev libsdl1.2-dev xterm sed cvs \
	subversion coreutils texi2html docbook-utils \
	python-pysqlite2 help2man make gcc g++ \
	desktop-file-utils libgl1-mesa-dev libglu1-mesa-dev \
	mercurial autoconf automake groff curl lzop \
	asciidoc u-boot-tools python3-pip
```
```
export GIT_SSL_NO_VERIFY=1
```
```
sudo apt build-dep qemu
```
```
DISTRO=fsl-imx-xwayland MACHINE=myd-imx8mm source myir-setup-release.sh -b build-xwayland
```
---
> [http://d.myirtech.com/MYD-C8MMX/](http://d.myirtech.com/MYD-C8MMX/)