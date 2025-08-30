## Requirements
* Ubuntu 24.04 or latest
* libmagickwand-dev 6.9 or latest
* libdmtx-dev 0.7 or latest
* git 2.43 or latest

### Install development environment
```sh
apt update && apt install -y build-essential libmagickwand-dev libdmtx-dev git
```

### Compile dmtx-utils
```sh
git clone https://github.com/dmtx/dmtx-utils.git
cd dmtx-utils
./autogen.sh
./configure
make
sudo make install
```

### Issues
> Using the dmtx-utils package from apt have error:
> 
> echo "hello" | dmtxwrite -o image.png
>
> *** buffer overflow detected ***: terminated
