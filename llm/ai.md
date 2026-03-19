# AI

### make a local API call to evaluate NIM
```sh
curl -X 'POST' \
'http://127.0.0.1:8000/v1/chat/completions' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
    "model": "nvidia/nemotron-3-super-120b-a12b",
    "messages": [{"role":"user", "content":"Which number is larger, 9.11 or 9.8?"}],
    "max_tokens": 64
}'
```
