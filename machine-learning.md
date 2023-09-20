# Machine Learning

## Install AMD ROCm
[https://rocm.docs.amd.com/en/latest/tutorials/install/index.html](https://rocm.docs.amd.com/en/latest/tutorials/install/index.html)
```bash
su
sudo usermod -a -G render,video $LOGNAME
# Make the directory if it doesn't exist yet.
# This location is recommended by the distribution maintainers.
sudo mkdir --parents --mode=0755 /etc/apt/keyrings
# Download the key, convert the signing-key to a full
# keyring required by apt and store in the keyring directory
wget https://repo.radeon.com/rocm/rocm.gpg.key -O - | \
    gpg --dearmor | sudo tee /etc/apt/keyrings/rocm.gpg > /dev/null

apt install python3-venv python3-dev

wget -q -O - https://debian.rickslab.com/PUBLIC.KEY | sudo gpg --dearmour -o /usr/share/keyrings/rickslab-agent.gpg

echo 'deb [arch=amd64 signed-by=/usr/share/keyrings/rickslab-agent.gpg] https://debian.rickslab.com/gpu-utils/ eddore main' | sudo tee /etc/apt/sources.list.d/rickslab-gpu-utils.list

apt update
apt install rickslab-gpu-utils

gpu-chk

printf 'amdgpu.ppfeaturemask=0x%x\n' "$(($(cat /sys/module/amdgpu/parameters/ppfeaturemask) | 0x4000))"
amdgpu.ppfeaturemask=0xffffffff

vim /etc/default/grub

sudo update-grub

sudo reboot

wget https://repo.radeon.com/amdgpu-install/23.10.3/ubuntu/jammy/amdgpu-install_5.5.50503-1_all.deb
su
apt install ./amdgpu-install_5.5.50503-1_all.deb
apt update

sudo amdgpu-install --vulkan=amdvlk,pro --opencl=rocr,legacy -y --accept-eula

apt install wget build-essential libreadline-dev libncursesw5-dev libssl-dev libsqlite3-dev tk-dev libgdbm-dev libc6-dev libbz2-dev libffi-dev zlib1g-dev -y

exit

wget https://www.python.org/ftp/python/3.10.13/Python-3.10.13.tgz
tar xvzf Python-3.10.13.tgz
cd Python-3.10.13
./configure --enable-optimizations

su

make altinstall

python3.10 --version

update-alternatives --install /usr/bin/python python /usr/local/bin/python3.10 1
update-alternatives --install /usr/bin/pip pip /usr/local/bin/pip3.10 1

wget -qO- https://apt.llvm.org/llvm-snapshot.gpg.key | sudo tee /etc/apt/trusted.gpg.d/apt.llvm.org.asc

deb http://apt.llvm.org/bullseye/ llvm-toolchain-bullseye main
deb-src http://apt.llvm.org/bullseye/ llvm-toolchain-bullseye main

apt install clang-format clang-tidy clang-tools clang clangd libc++-dev libc++1 libc++abi-dev libc++abi1 libclang-dev libclang1 liblldb-dev libllvm-ocaml-dev libomp-dev libomp5 lld lldb llvm-dev llvm-runtime llvm python3-clang 

apt install amdgpu-core rocm-opencl

apt install rocm-dkms
The following packages have unmet dependencies:
 rocm-gdb : Depends: libpython3.10 but it is not installable or
                     libpython3.8 but it is not installable
 rocm-llvm : Depends: libstdc++-5-dev but it is not installable or
                      libstdc++-7-dev but it is not installable or
                      libstdc++-11-dev but it is not installable
             Depends: libgcc-5-dev but it is not installable or
                      libgcc-7-dev but it is not installable or
                      libgcc-11-dev but it is not installable
             Recommends: gcc-multilib but it is not going to be installed
             Recommends: g++-multilib but it is not going to be installed
E: Unable to correct problems, you have held broken packages.

exit

python --version
```

## Installing python virtual environment
```bash
$ python3 -m venv venv
The virtual environment was not created successfully because ensurepip is not
available.  On Debian/Ubuntu systems, you need to install the python3-venv
package using the following command.

    apt-get install python3-venv

You may need to use sudo with that command.  After installing the python3-venv
package, recreate your virtual environment.

Failing command: ['/home/projects/venv/bin/python3', '-Im', 'ensurepip', '--upgrade', '--default-pip']

$ su

# apt install python3-venv

$ source venv/bin/activate

(venv) $ which pip
/home/projects/venv/bin/pip

(venv) $ python -m pip install tensorflow-rocm
Collecting tensorflow-rocm
  Downloading tensorflow_rocm-2.12.0.560-cp39-cp39-manylinux2014_x86_64.whl (497.7 MB)
     |███████                         | 89.2 MB 602 kB/s eta 0:10:31
Collecting astunparse>=1.6.0
  Downloading astunparse-1.6.3-py2.py3-none-any.whl (12 kB)
Collecting six>=1.12.0
  Downloading six-1.16.0-py2.py3-none-any.whl (11 kB)
Collecting libclang>=13.0.0
  Downloading libclang-16.0.6-py2.py3-none-manylinux2010_x86_64.whl (22.9 MB)
     |████████████████████████████████| 22.9 MB 572 kB/s 
Collecting google-pasta>=0.1.1
  Downloading google_pasta-0.2.0-py3-none-any.whl (57 kB)
     |████████████████████████████████| 57 kB 1.0 MB/s 
Collecting tensorboard<2.13,>=2.12
  Downloading tensorboard-2.12.3-py3-none-any.whl (5.6 MB)
     |████████████████████████████████| 5.6 MB 250 kB/s 
Collecting opt-einsum>=2.3.2
  Downloading opt_einsum-3.3.0-py3-none-any.whl (65 kB)
     |████████████████████████████████| 65 kB 2.5 MB/s 
Collecting numpy<1.24,>=1.22
  Downloading numpy-1.23.5-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (17.1 MB)
     |████████████████████████████████| 17.1 MB 462 kB/s 
Collecting termcolor>=1.1.0
  Downloading termcolor-2.3.0-py3-none-any.whl (6.9 kB)
Requirement already satisfied: setuptools in ./venv/lib/python3.9/site-packages (from tensorflow-rocm) (44.1.1)
Collecting absl-py>=1.0.0
  Downloading absl_py-2.0.0-py3-none-any.whl (130 kB)
     |████████████████████████████████| 130 kB 1.2 MB/s 
Collecting grpcio<2.0,>=1.24.3
  Downloading grpcio-1.58.0-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (5.3 MB)
     |████████████████████████████████| 5.3 MB 521 kB/s 
Collecting typing-extensions>=3.6.6
  Downloading typing_extensions-4.8.0-py3-none-any.whl (31 kB)
Collecting h5py>=2.9.0
  Downloading h5py-3.9.0-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (4.8 MB)
     |████████████████████████████████| 4.8 MB 536 kB/s 
Collecting tensorflow-estimator<2.13,>=2.12.0
  Downloading tensorflow_estimator-2.12.0-py2.py3-none-any.whl (440 kB)
     |████████████████████████████████| 440 kB 999 kB/s 
Collecting tensorflow-io-gcs-filesystem>=0.23.1
  Downloading tensorflow_io_gcs_filesystem-0.34.0-cp39-cp39-manylinux_2_12_x86_64.manylinux2010_x86_64.whl (2.4 MB)
     |████████████████████████████████| 2.4 MB 294 kB/s 
Collecting gast<=0.4.0,>=0.2.1
  Downloading gast-0.4.0-py3-none-any.whl (9.8 kB)
Collecting flatbuffers>=2.0
  Downloading flatbuffers-23.5.26-py2.py3-none-any.whl (26 kB)
Collecting packaging
  Downloading packaging-23.1-py3-none-any.whl (48 kB)
     |████████████████████████████████| 48 kB 1.6 MB/s 
Collecting jax>=0.3.15
  Downloading jax-0.4.16-py3-none-any.whl (1.6 MB)
     |████████████████████████████████| 1.6 MB 517 kB/s 
Collecting keras<2.13,>=2.12.0
  Downloading keras-2.12.0-py2.py3-none-any.whl (1.7 MB)
     |████████████████████████████████| 1.7 MB 685 kB/s 
Collecting wrapt<1.15,>=1.11.0
  Downloading wrapt-1.14.1-cp39-cp39-manylinux_2_5_x86_64.manylinux1_x86_64.manylinux_2_17_x86_64.manylinux2014_x86_64.whl (77 kB)
     |████████████████████████████████| 77 kB 5.1 MB/s 
Collecting protobuf!=4.21.0,!=4.21.1,!=4.21.2,!=4.21.3,!=4.21.4,!=4.21.5,<5.0.0dev,>=3.20.3
  Downloading protobuf-4.24.3-cp37-abi3-manylinux2014_x86_64.whl (311 kB)
     |████████████████████████████████| 311 kB 3.0 MB/s 
Collecting wheel<1.0,>=0.23.0
  Downloading wheel-0.41.2-py3-none-any.whl (64 kB)
     |████████████████████████████████| 64 kB 1.6 MB/s 
Collecting importlib-metadata>=4.6
  Downloading importlib_metadata-6.8.0-py3-none-any.whl (22 kB)
Collecting ml-dtypes>=0.2.0
  Downloading ml_dtypes-0.3.0-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (206 kB)
     |████████████████████████████████| 206 kB 4.8 MB/s 
Collecting scipy>=1.7
  Downloading scipy-1.11.2-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (36.5 MB)
     |████████████████████████████████| 36.5 MB 596 kB/s 
Collecting zipp>=0.5
  Downloading zipp-3.17.0-py3-none-any.whl (7.4 kB)
Collecting google-auth-oauthlib<1.1,>=0.5
  Downloading google_auth_oauthlib-1.0.0-py2.py3-none-any.whl (18 kB)
Collecting requests<3,>=2.21.0
  Downloading requests-2.31.0-py3-none-any.whl (62 kB)
     |████████████████████████████████| 62 kB 781 kB/s 
Collecting werkzeug>=1.0.1
  Downloading werkzeug-2.3.7-py3-none-any.whl (242 kB)
     |████████████████████████████████| 242 kB 1.5 MB/s 
Collecting google-auth<3,>=1.6.3
  Downloading google_auth-2.23.0-py2.py3-none-any.whl (181 kB)
     |████████████████████████████████| 181 kB 945 kB/s 
Collecting markdown>=2.6.8
  Downloading Markdown-3.4.4-py3-none-any.whl (94 kB)
     |████████████████████████████████| 94 kB 1.7 MB/s 
Collecting tensorboard-data-server<0.8.0,>=0.7.0
  Downloading tensorboard_data_server-0.7.1-py3-none-manylinux2014_x86_64.whl (6.6 MB)
     |████████████████████████████████| 6.6 MB 295 kB/s 
Collecting cachetools<6.0,>=2.0.0
  Downloading cachetools-5.3.1-py3-none-any.whl (9.3 kB)
Collecting urllib3<2.0
  Downloading urllib3-1.26.16-py2.py3-none-any.whl (143 kB)
     |████████████████████████████████| 143 kB 2.8 MB/s 
Collecting rsa<5,>=3.1.4
  Downloading rsa-4.9-py3-none-any.whl (34 kB)
Collecting pyasn1-modules>=0.2.1
  Downloading pyasn1_modules-0.3.0-py2.py3-none-any.whl (181 kB)
     |████████████████████████████████| 181 kB 3.5 MB/s 
Collecting requests-oauthlib>=0.7.0
  Downloading requests_oauthlib-1.3.1-py2.py3-none-any.whl (23 kB)
Collecting pyasn1<0.6.0,>=0.4.6
  Downloading pyasn1-0.5.0-py2.py3-none-any.whl (83 kB)
     |████████████████████████████████| 83 kB 1.4 MB/s 
Collecting charset-normalizer<4,>=2
  Downloading charset_normalizer-3.2.0-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (202 kB)
     |████████████████████████████████| 202 kB 5.7 MB/s 
Collecting idna<4,>=2.5
  Downloading idna-3.4-py3-none-any.whl (61 kB)
     |████████████████████████████████| 61 kB 142 kB/s 
Collecting certifi>=2017.4.17
  Downloading certifi-2023.7.22-py3-none-any.whl (158 kB)
     |████████████████████████████████| 158 kB 4.8 MB/s 
Collecting oauthlib>=3.0.0
  Downloading oauthlib-3.2.2-py3-none-any.whl (151 kB)
     |████████████████████████████████| 151 kB 2.8 MB/s 
Collecting MarkupSafe>=2.1.1
  Downloading MarkupSafe-2.1.3-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (25 kB)
Installing collected packages: urllib3, pyasn1, idna, charset-normalizer, certifi, zipp, rsa, requests, pyasn1-modules, oauthlib, cachetools, requests-oauthlib, numpy, MarkupSafe, importlib-metadata, google-auth, wheel, werkzeug, tensorboard-data-server, six, scipy, protobuf, opt-einsum, ml-dtypes, markdown, grpcio, google-auth-oauthlib, absl-py, wrapt, typing-extensions, termcolor, tensorflow-io-gcs-filesystem, tensorflow-estimator, tensorboard, packaging, libclang, keras, jax, h5py, google-pasta, gast, flatbuffers, astunparse, tensorflow-rocm
Successfully installed MarkupSafe-2.1.3 absl-py-2.0.0 astunparse-1.6.3 cachetools-5.3.1 certifi-2023.7.22 charset-normalizer-3.2.0 flatbuffers-23.5.26 gast-0.4.0 google-auth-2.23.0 google-auth-oauthlib-1.0.0 google-pasta-0.2.0 grpcio-1.58.0 h5py-3.9.0 idna-3.4 importlib-metadata-6.8.0 jax-0.4.16 keras-2.12.0 libclang-16.0.6 markdown-3.4.4 ml-dtypes-0.3.0 numpy-1.23.5 oauthlib-3.2.2 opt-einsum-3.3.0 packaging-23.1 protobuf-4.24.3 pyasn1-0.5.0 pyasn1-modules-0.3.0 requests-2.31.0 requests-oauthlib-1.3.1 rsa-4.9 scipy-1.11.2 six-1.16.0 tensorboard-2.12.3 tensorboard-data-server-0.7.1 tensorflow-estimator-2.12.0 tensorflow-io-gcs-filesystem-0.34.0 tensorflow-rocm-2.12.0.560 termcolor-2.3.0 typing-extensions-4.8.0 urllib3-1.26.16 werkzeug-2.3.7 wheel-0.41.2 wrapt-1.14.1 zipp-3.17.0
     
(venv) $ python -m pip install keras
Requirement already satisfied: keras in ./venv/lib/python3.9/site-packages (2.12.0)

(venv) $ python -m pip install jupyterlab
Collecting jupyterlab
  Downloading jupyterlab-4.0.6-py3-none-any.whl (9.2 MB)
     |████████████████████████████████| 9.2 MB 554 kB/s 
Collecting traitlets
  Downloading traitlets-5.10.0-py3-none-any.whl (120 kB)
     |████████████████████████████████| 120 kB 710 kB/s 
Collecting tomli
  Downloading tomli-2.0.1-py3-none-any.whl (12 kB)
Collecting jupyter-lsp>=2.0.0
  Downloading jupyter_lsp-2.2.0-py3-none-any.whl (65 kB)
     |████████████████████████████████| 65 kB 1.6 MB/s 
Collecting ipykernel
  Downloading ipykernel-6.25.2-py3-none-any.whl (154 kB)
     |████████████████████████████████| 154 kB 2.1 MB/s 
Requirement already satisfied: importlib-metadata>=4.8.3 in ./venv/lib/python3.9/site-packages (from jupyterlab) (6.8.0)
Collecting tornado>=6.2.0
  Downloading tornado-6.3.3-cp38-abi3-manylinux_2_5_x86_64.manylinux1_x86_64.manylinux_2_17_x86_64.manylinux2014_x86_64.whl (427 kB)
     |████████████████████████████████| 427 kB 2.1 MB/s 
Collecting jupyter-server<3,>=2.4.0
  Downloading jupyter_server-2.7.3-py3-none-any.whl (375 kB)
     |████████████████████████████████| 375 kB 3.1 MB/s 
Collecting jupyterlab-server<3,>=2.19.0
  Downloading jupyterlab_server-2.25.0-py3-none-any.whl (57 kB)
     |████████████████████████████████| 57 kB 2.5 MB/s 
Collecting notebook-shim>=0.2
  Downloading notebook_shim-0.2.3-py3-none-any.whl (13 kB)
Requirement already satisfied: packaging in ./venv/lib/python3.9/site-packages (from jupyterlab) (23.1)
Collecting jupyter-core
  Downloading jupyter_core-5.3.1-py3-none-any.whl (93 kB)
     |████████████████████████████████| 93 kB 1.8 MB/s 
Collecting jinja2>=3.0.3
  Downloading Jinja2-3.1.2-py3-none-any.whl (133 kB)
     |████████████████████████████████| 133 kB 4.4 MB/s 
Collecting async-lru>=1.0.0
  Downloading async_lru-2.0.4-py3-none-any.whl (6.1 kB)
Requirement already satisfied: typing-extensions>=4.0.0 in ./venv/lib/python3.9/site-packages (from async-lru>=1.0.0->jupyterlab) (4.8.0)
Requirement already satisfied: zipp>=0.5 in ./venv/lib/python3.9/site-packages (from importlib-metadata>=4.8.3->jupyterlab) (3.17.0)
Requirement already satisfied: MarkupSafe>=2.0 in ./venv/lib/python3.9/site-packages (from jinja2>=3.0.3->jupyterlab) (2.1.3)
Collecting jupyter-events>=0.6.0
  Downloading jupyter_events-0.7.0-py3-none-any.whl (18 kB)
Collecting nbformat>=5.3.0
  Downloading nbformat-5.9.2-py3-none-any.whl (77 kB)
     |████████████████████████████████| 77 kB 1.8 MB/s 
Collecting jupyter-client>=7.4.4
  Downloading jupyter_client-8.3.1-py3-none-any.whl (104 kB)
     |████████████████████████████████| 104 kB 3.7 MB/s 
Collecting send2trash>=1.8.2
  Downloading Send2Trash-1.8.2-py3-none-any.whl (18 kB)
Collecting websocket-client
  Downloading websocket_client-1.6.3-py3-none-any.whl (57 kB)
     |████████████████████████████████| 57 kB 3.4 MB/s 
Collecting jupyter-server-terminals
  Downloading jupyter_server_terminals-0.4.4-py3-none-any.whl (13 kB)
Collecting terminado>=0.8.3
  Downloading terminado-0.17.1-py3-none-any.whl (17 kB)
Collecting argon2-cffi
  Downloading argon2_cffi-23.1.0-py3-none-any.whl (15 kB)
Collecting prometheus-client
  Downloading prometheus_client-0.17.1-py3-none-any.whl (60 kB)
     |████████████████████████████████| 60 kB 6.4 MB/s 
Collecting overrides
  Downloading overrides-7.4.0-py3-none-any.whl (17 kB)
Collecting pyzmq>=24
  Downloading pyzmq-25.1.1-cp39-cp39-manylinux_2_12_x86_64.manylinux2010_x86_64.whl (1.1 MB)
     |████████████████████████████████| 1.1 MB 322 kB/s 
Collecting anyio>=3.1.0
  Downloading anyio-4.0.0-py3-none-any.whl (83 kB)
     |████████████████████████████████| 83 kB 1.2 MB/s 
Collecting nbconvert>=6.4.4
  Downloading nbconvert-7.8.0-py3-none-any.whl (254 kB)
     |████████████████████████████████| 254 kB 1.8 MB/s 
Collecting exceptiongroup>=1.0.2
  Downloading exceptiongroup-1.1.3-py3-none-any.whl (14 kB)
Collecting sniffio>=1.1
  Downloading sniffio-1.3.0-py3-none-any.whl (10 kB)
Requirement already satisfied: idna>=2.8 in ./venv/lib/python3.9/site-packages (from anyio>=3.1.0->jupyter-server<3,>=2.4.0->jupyterlab) (3.4)
Collecting python-dateutil>=2.8.2
  Downloading python_dateutil-2.8.2-py2.py3-none-any.whl (247 kB)
     |████████████████████████████████| 247 kB 3.3 MB/s 
Collecting platformdirs>=2.5
  Downloading platformdirs-3.10.0-py3-none-any.whl (17 kB)
Collecting referencing
  Downloading referencing-0.30.2-py3-none-any.whl (25 kB)
Collecting rfc3339-validator
  Downloading rfc3339_validator-0.1.4-py2.py3-none-any.whl (3.5 kB)
Collecting python-json-logger>=2.0.4
  Downloading python_json_logger-2.0.7-py3-none-any.whl (8.1 kB)
Collecting pyyaml>=5.3
  Downloading PyYAML-6.0.1-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (738 kB)
     |████████████████████████████████| 738 kB 3.7 MB/s 
Collecting jsonschema[format-nongpl]>=4.18.0
  Downloading jsonschema-4.19.0-py3-none-any.whl (83 kB)
     |████████████████████████████████| 83 kB 1.5 MB/s 
Collecting rfc3986-validator>=0.1.1
  Downloading rfc3986_validator-0.1.1-py2.py3-none-any.whl (4.2 kB)
Collecting jsonschema-specifications>=2023.03.6
  Downloading jsonschema_specifications-2023.7.1-py3-none-any.whl (17 kB)
Collecting attrs>=22.2.0
  Downloading attrs-23.1.0-py3-none-any.whl (61 kB)
     |████████████████████████████████| 61 kB 4.2 MB/s 
Collecting rpds-py>=0.7.1
  Downloading rpds_py-0.10.3-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (1.2 MB)
     |████████████████████████████████| 1.2 MB 1.9 MB/s 
Collecting isoduration
  Downloading isoduration-20.11.0-py3-none-any.whl (11 kB)
Collecting fqdn
  Downloading fqdn-1.5.1-py3-none-any.whl (9.1 kB)
Collecting webcolors>=1.11
  Downloading webcolors-1.13-py3-none-any.whl (14 kB)
Collecting uri-template
  Downloading uri_template-1.3.0-py3-none-any.whl (11 kB)
Collecting jsonpointer>1.13
  Downloading jsonpointer-2.4-py2.py3-none-any.whl (7.8 kB)
Collecting babel>=2.10
  Downloading Babel-2.12.1-py3-none-any.whl (10.1 MB)
     |████████████████████████████████| 10.1 MB 661 kB/s 
Requirement already satisfied: requests>=2.31 in ./venv/lib/python3.9/site-packages (from jupyterlab-server<3,>=2.19.0->jupyterlab) (2.31.0)
Collecting json5>=0.9.0
  Downloading json5-0.9.14-py2.py3-none-any.whl (19 kB)
Collecting pygments>=2.4.1
  Downloading Pygments-2.16.1-py3-none-any.whl (1.2 MB)
     |████████████████████████████████| 1.2 MB 512 kB/s 
Collecting bleach!=5.0.0
  Downloading bleach-6.0.0-py3-none-any.whl (162 kB)
     |████████████████████████████████| 162 kB 3.7 MB/s 
Collecting jupyterlab-pygments
  Downloading jupyterlab_pygments-0.2.2-py2.py3-none-any.whl (21 kB)
Collecting mistune<4,>=2.0.3
  Downloading mistune-3.0.1-py3-none-any.whl (47 kB)
     |████████████████████████████████| 47 kB 5.0 MB/s 
Collecting nbclient>=0.5.0
  Downloading nbclient-0.8.0-py3-none-any.whl (73 kB)
     |████████████████████████████████| 73 kB 1.7 MB/s 
Collecting pandocfilters>=1.4.1
  Downloading pandocfilters-1.5.0-py2.py3-none-any.whl (8.7 kB)
Collecting defusedxml
  Downloading defusedxml-0.7.1-py2.py3-none-any.whl (25 kB)
Collecting tinycss2
  Downloading tinycss2-1.2.1-py3-none-any.whl (21 kB)
Collecting beautifulsoup4
  Downloading beautifulsoup4-4.12.2-py3-none-any.whl (142 kB)
     |████████████████████████████████| 142 kB 3.8 MB/s 
Collecting webencodings
  Downloading webencodings-0.5.1-py2.py3-none-any.whl (11 kB)
Requirement already satisfied: six>=1.9.0 in ./venv/lib/python3.9/site-packages (from bleach!=5.0.0->nbconvert>=6.4.4->jupyter-server<3,>=2.4.0->jupyterlab) (1.16.0)
Collecting fastjsonschema
  Downloading fastjsonschema-2.18.0-py3-none-any.whl (23 kB)
Requirement already satisfied: charset-normalizer<4,>=2 in ./venv/lib/python3.9/site-packages (from requests>=2.31->jupyterlab-server<3,>=2.19.0->jupyterlab) (3.2.0)
Requirement already satisfied: certifi>=2017.4.17 in ./venv/lib/python3.9/site-packages (from requests>=2.31->jupyterlab-server<3,>=2.19.0->jupyterlab) (2023.7.22)
Requirement already satisfied: urllib3<3,>=1.21.1 in ./venv/lib/python3.9/site-packages (from requests>=2.31->jupyterlab-server<3,>=2.19.0->jupyterlab) (1.26.16)
Collecting ptyprocess
  Downloading ptyprocess-0.7.0-py2.py3-none-any.whl (13 kB)
Collecting argon2-cffi-bindings
  Downloading argon2_cffi_bindings-21.2.0-cp36-abi3-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (86 kB)
     |████████████████████████████████| 86 kB 2.6 MB/s 
Collecting cffi>=1.0.1
  Downloading cffi-1.15.1-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (441 kB)
     |████████████████████████████████| 441 kB 4.3 MB/s 
Collecting pycparser
  Downloading pycparser-2.21-py2.py3-none-any.whl (118 kB)
     |████████████████████████████████| 118 kB 2.7 MB/s 
Collecting soupsieve>1.2
  Downloading soupsieve-2.5-py3-none-any.whl (36 kB)
Collecting matplotlib-inline>=0.1
  Downloading matplotlib_inline-0.1.6-py3-none-any.whl (9.4 kB)
Collecting nest-asyncio
  Downloading nest_asyncio-1.5.8-py3-none-any.whl (5.3 kB)
Collecting ipython>=7.23.1
  Downloading ipython-8.15.0-py3-none-any.whl (806 kB)
     |████████████████████████████████| 806 kB 292 kB/s 
Collecting psutil
  Downloading psutil-5.9.5-cp36-abi3-manylinux_2_12_x86_64.manylinux2010_x86_64.manylinux_2_17_x86_64.manylinux2014_x86_64.whl (282 kB)
     |████████████████████████████████| 282 kB 4.1 MB/s 
Collecting comm>=0.1.1
  Downloading comm-0.1.4-py3-none-any.whl (6.6 kB)
Collecting debugpy>=1.6.5
  Downloading debugpy-1.8.0-cp39-cp39-manylinux_2_17_x86_64.manylinux2014_x86_64.whl (3.4 MB)
     |████████████████████████████████| 3.4 MB 684 kB/s 
Collecting backcall
  Downloading backcall-0.2.0-py2.py3-none-any.whl (11 kB)
Collecting pickleshare
  Downloading pickleshare-0.7.5-py2.py3-none-any.whl (6.9 kB)
Collecting stack-data
  Downloading stack_data-0.6.2-py3-none-any.whl (24 kB)
Collecting prompt-toolkit!=3.0.37,<3.1.0,>=3.0.30
  Downloading prompt_toolkit-3.0.39-py3-none-any.whl (385 kB)
     |████████████████████████████████| 385 kB 4.2 MB/s 
Collecting decorator
  Downloading decorator-5.1.1-py3-none-any.whl (9.1 kB)
Collecting pexpect>4.3
  Downloading pexpect-4.8.0-py2.py3-none-any.whl (59 kB)
     |████████████████████████████████| 59 kB 3.1 MB/s 
Collecting jedi>=0.16
  Downloading jedi-0.19.0-py2.py3-none-any.whl (1.6 MB)
     |████████████████████████████████| 1.6 MB 673 kB/s 
Collecting parso<0.9.0,>=0.8.3
  Downloading parso-0.8.3-py2.py3-none-any.whl (100 kB)
     |████████████████████████████████| 100 kB 3.6 MB/s 
Collecting wcwidth
  Downloading wcwidth-0.2.6-py2.py3-none-any.whl (29 kB)
Collecting arrow>=0.15.0
  Downloading arrow-1.2.3-py3-none-any.whl (66 kB)
     |████████████████████████████████| 66 kB 5.1 MB/s 
Collecting asttokens>=2.1.0
  Downloading asttokens-2.4.0-py2.py3-none-any.whl (27 kB)
Collecting executing>=1.2.0
  Downloading executing-1.2.0-py2.py3-none-any.whl (24 kB)
Collecting pure-eval
  Downloading pure_eval-0.2.2-py3-none-any.whl (11 kB)
Installing collected packages: rpds-py, attrs, referencing, traitlets, python-dateutil, platformdirs, jsonschema-specifications, tornado, pyzmq, pycparser, jupyter-core, jsonschema, fastjsonschema, arrow, webencodings, webcolors, uri-template, soupsieve, rfc3986-validator, rfc3339-validator, ptyprocess, nbformat, jupyter-client, jsonpointer, isoduration, fqdn, cffi, wcwidth, tinycss2, terminado, sniffio, pyyaml, python-json-logger, pygments, pure-eval, parso, pandocfilters, nbclient, mistune, jupyterlab-pygments, jinja2, executing, exceptiongroup, defusedxml, bleach, beautifulsoup4, asttokens, argon2-cffi-bindings, websocket-client, stack-data, send2trash, prompt-toolkit, prometheus-client, pickleshare, pexpect, overrides, nbconvert, matplotlib-inline, jupyter-server-terminals, jupyter-events, jedi, decorator, backcall, argon2-cffi, anyio, psutil, nest-asyncio, jupyter-server, json5, ipython, debugpy, comm, babel, tomli, notebook-shim, jupyterlab-server, jupyter-lsp, ipykernel, async-lru, jupyterlab
Successfully installed anyio-4.0.0 argon2-cffi-23.1.0 argon2-cffi-bindings-21.2.0 arrow-1.2.3 asttokens-2.4.0 async-lru-2.0.4 attrs-23.1.0 babel-2.12.1 backcall-0.2.0 beautifulsoup4-4.12.2 bleach-6.0.0 cffi-1.15.1 comm-0.1.4 debugpy-1.8.0 decorator-5.1.1 defusedxml-0.7.1 exceptiongroup-1.1.3 executing-1.2.0 fastjsonschema-2.18.0 fqdn-1.5.1 ipykernel-6.25.2 ipython-8.15.0 isoduration-20.11.0 jedi-0.19.0 jinja2-3.1.2 json5-0.9.14 jsonpointer-2.4 jsonschema-4.19.0 jsonschema-specifications-2023.7.1 jupyter-client-8.3.1 jupyter-core-5.3.1 jupyter-events-0.7.0 jupyter-lsp-2.2.0 jupyter-server-2.7.3 jupyter-server-terminals-0.4.4 jupyterlab-4.0.6 jupyterlab-pygments-0.2.2 jupyterlab-server-2.25.0 matplotlib-inline-0.1.6 mistune-3.0.1 nbclient-0.8.0 nbconvert-7.8.0 nbformat-5.9.2 nest-asyncio-1.5.8 notebook-shim-0.2.3 overrides-7.4.0 pandocfilters-1.5.0 parso-0.8.3 pexpect-4.8.0 pickleshare-0.7.5 platformdirs-3.10.0 prometheus-client-0.17.1 prompt-toolkit-3.0.39 psutil-5.9.5 ptyprocess-0.7.0 pure-eval-0.2.2 pycparser-2.21 pygments-2.16.1 python-dateutil-2.8.2 python-json-logger-2.0.7 pyyaml-6.0.1 pyzmq-25.1.1 referencing-0.30.2 rfc3339-validator-0.1.4 rfc3986-validator-0.1.1 rpds-py-0.10.3 send2trash-1.8.2 sniffio-1.3.0 soupsieve-2.5 stack-data-0.6.2 terminado-0.17.1 tinycss2-1.2.1 tomli-2.0.1 tornado-6.3.3 traitlets-5.10.0 uri-template-1.3.0 wcwidth-0.2.6 webcolors-1.13 webencodings-0.5.1 websocket-client-1.6.3

(venv) $ python -m pip install notebook

(venv) $ python verify.py
Traceback (most recent call last):
  File "/home/randop/Projects/notebook/venv/verify.py", line 1, in <module>
    import tensorflow as tf
  File "/home/randop/Projects/venv/lib/python3.9/site-packages/tensorflow/__init__.py", line 37, in <module>
    from tensorflow.python.tools import module_util as _module_util
  File "/home/randop/Projects/venv/lib/python3.9/site-packages/tensorflow/python/__init__.py", line 36, in <module>
    from tensorflow.python import pywrap_tensorflow as _pywrap_tensorflow
  File "/home/randop/Projects/venv/lib/python3.9/site-packages/tensorflow/python/pywrap_tensorflow.py", line 26, in <module>
    self_check.preload_check()
  File "/home/randop/Projects/venv/lib/python3.9/site-packages/tensorflow/python/platform/self_check.py", line 63, in preload_check
    from tensorflow.python.platform import _pywrap_cpu_feature_guard
ImportError: libhsa-runtime64.so.1: cannot open shared object file: No such file or directory
```

## Using Keras Tensorflow with AMD GPU
[https://saturncloud.io/blog/using-keras-tensorflow-with-amd-gpu/](https://saturncloud.io/blog/using-keras-tensorflow-with-amd-gpu/)

## Jupyter Notebook
### launch JupyterLab 
```bash
jupyter lab
```
### classic Jupyter Notebook
```bash
jupyter notebook
```
