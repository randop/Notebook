# Artix

### List services that start at boot
```shell
s6-rc-db contents default
```

### List all actively running services
```shell
s6-rc -a list
```

### Setup startup services
```shell
s6 repository sync && s6 set commit && s6 live install
```

### Enable a service (e.g. `sshd`)
```shell
s6-rc -u change sshd
```

### Disable a service
```shell
s6-rc -d change sshd
```

### Check catch-all service logs
```shell
tail -f -n 150 /run/uncaught-logs/current
```

### Follow a service log
```shell
tail -f -n 150 /var/log/<name>/current

# example:
tail -f -n 150 /var/log/dhcpcd/current
```

### Check service status
```shell
# s6-svstat /run/service/<name>
s6-svstat /run/service/dhcpcd-srv
```
