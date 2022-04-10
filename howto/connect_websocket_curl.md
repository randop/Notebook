# Connect and test websocket server using curl
```
curl --include --no-buffer \
    --header "Connection: keep-alive, Upgrade" \
    --header "Upgrade: websocket" \
    --header "Sec-Fetch-Dest: websocket" \
    --header "Sec-Fetch-Mode: websocket" \
    --header "User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:91.0) Gecko/20100101 Firefox/91.0" \
    --header "Sec-WebSocket-Key: 8Nsbc+r6RFnfuPxAHDb3AQ==" \
    --header "Sec-WebSocket-Version: 13" \
    "telnet://socketsbay.com/wss/v2/2/demo/"
```