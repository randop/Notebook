VM create error caused by invalid mac address

```
first of 1 error: first of 1 error: Command failed: zone '96ebd97c-0241-4b2b-9cb4-6d0c7ba8b24a': error creating VNIC eth0 (global NIC admin)
zone '96ebd97c-0241-4b2b-9cb4-6d0c7ba8b24a': msg: dladm: vnic creation over e1000g0 failed: invalid MAC address value
zone '96ebd97c-0241-4b2b-9cb4-6d0c7ba8b24a': Failed cmd: dladm create-vnic -t -l e1000g0 -p  mtu=1500,zone=96ebd97c-0241-4b2b-9cb4-6d0c7ba8b24a -m dd:b2:ce:10:1f:05 tmp70300
zone '96ebd97c-0241-4b2b-9cb4-6d0c7ba8b24a': /zones/96ebd97c-0241-4b2b-9cb4-6d0c7ba8b24a/root/checkpoints: No such file or directory
zone '96ebd97c-0241-4b2b-9cb4-6d0c7ba8b24a': destroying snapshot: No such zone configured
```
