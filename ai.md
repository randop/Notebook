# Setup Python virtual environment
```bash
export PIP_REQUIRE_VIRTUALENV=true
export PIP_DOWNLOAD_CACHE=/media/randop/FILES/python-venv/cache
cd /media/randop/FILES/
python -m venv python-venv
mkdir /media/randop/FILES/python-venv/cache
source /media/randop/FILES/python-venv/bin/activate
ln -sv /media/randop/FILES/python-venv/cache ~/.cache/pip
```

# Install Hugging Face
```bash
pip install 'huggingface_hub[cli,torch]'
huggingface-cli login
```

# Download Mixtral AI
```bash
huggingface-cli download --cache-dir /media/randop/FILES/huggingface/cache --resume-download mistralai/Mixtral-8x7B-Instruct-v0.1
```

# GPT4ALL
```bash
cd /media/randop/FILES/gpt4all
docker run -it --rm -v $(pwd)/cache:/root/.cache/gpt4all -v $(pwd):/projects python:3.10-bookworm /bin/bash

pip install langchain gpt4all langchain-experimental tabulate
```
