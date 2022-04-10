# Health check a websocket server using curl
```
curl --include --no-buffer \
    --header "Connection: close" \
    --header "Upgrade: websocket" \
    --header "Sec-WebSocket-Key: SGVsbG8sIHdvcmxkIQ==" \
    --header "Sec-WebSocket-Version: 13" \
    https://broker.mqtt.com:8084/mqtt
```
---
> [https://stackoverflow.com/questions/47353406/websocket-server-health-check-with-curl](https://stackoverflow.com/questions/47353406/websocket-server-health-check-with-curl)