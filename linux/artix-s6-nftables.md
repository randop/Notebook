# Artix

## Installation
```shell
pacman -S nftables nftables-s6
```

## Test configuration
```shell
nft -c -f /etc/nftables.conf
```

## Apply configuration
```shell
nft -f /etc/nftables.conf
```

## Check rules list
```shell
nft list ruleset
```

## Flush rules
```shell
nft flush ruleset
```

## Enable service on startup
```shell
s6 set enable nftables && \
  s6 set commit && \
  s6 live install
```

## Restart and reload rules
```shell
s6 process restart nftables
```

## Check service status
```shell
s6 process status nftables
```
