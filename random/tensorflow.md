## Using implementation of Google's Tacotron speech synthesis with pre-trained model
```
docker pull tensorflow/tensorflow:latest-py3
docker run --name tensordev -it -v /home/randop/Projects:/projects -p 9000:9000 tensorflow/tensorflow:latest-py3 /bin/bash
cd /projects
git clone https://github.com/keithito/tacotron.git
cd tacotron
pip install -r requirements.txt
pip install tensorflow-cpu
curl https://data.keithito.com/data/speech/tacotron-20180906.tar.gz | tar xzC /tmp
python3 demo_server.py --checkpoint /tmp/tacotron-20180906/model.ckpt
```