# Run an application and limit RAM usage and automatically restart it when the limit is exceeded

```sh
systemd-run --service \
  -p MemoryMax=1G \
  -p Restart=on-failure \
  -p RestartSec=5s \
  --user \
  --unit=firefox-limited.service \
  /usr/bin/firefox
```
