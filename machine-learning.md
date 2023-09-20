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
```

## Using Keras Tensorflow with AMD GPU
[https://saturncloud.io/blog/using-keras-tensorflow-with-amd-gpu/](https://saturncloud.io/blog/using-keras-tensorflow-with-amd-gpu/)
