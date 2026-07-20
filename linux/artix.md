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

