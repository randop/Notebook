# Machine Learning

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

(venv) $ python -m pip install jupyterlab
(venv) $ python -m pip install notebook
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
