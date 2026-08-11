# Ubuntu

### network auto DHCP network configuration
```shell
sudo netplan set ethernets.enp2s0.dhcp4=true && sudo netplan apply
```
