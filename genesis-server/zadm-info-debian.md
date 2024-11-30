# Debian on OmniOS using Bhyve virtualization

## OmniOS r151052  omnios-r151052-dbe4644ba92      November 2024

### Command
```bash
zadm show debian
```

```json
{
  "acpi": "off",
  "autoboot": "true",
  "bootargs": "",
  "bootdisk": {
    "blocksize": "8K",
    "path": "rpool/bhyve0",
    "size": "30G",
    "sparse": "false"
  },
  "bootrom": "BHYVE_RELEASE",
  "brand": "bhyve",
  "dataset": [
    {
      "name": "datapool/shared",
      "comment": "/*** Use zfs pool and set the name ***/"
    }
  ],
  "device": [
    {
      "match": "/dev/ppt0"
    }
  ],
  "extra": [
    "-S -s 8:0,passthru,/dev/ppt0"
  ],
  "fs-allowed": "",
  "hostid": "",
  "ip-type": "exclusive",
  "limitpriv": "",
  "net": [
    {
      "allowed-address": "192.168.1.1/16",
      "physical": "bhyve0",
      "comment": "/*** Use server ip and set the ip address ***/"
    }
  ],
  "pool": "",
  "scheduling-class": "",
  "virtfs": [
    {
      "name": "files",
      "path": "/files",
      "ro": "false"
    }
  ],
  "vnc": {
    "enabled": "off"
  },
  "zonename": "debian",
  "zonepath": "/zones/bhyve"
}
```
