# Genesis (node) server

## SmartOS (build: 20241128T004404Z)

### System
```
uname -a
SunOS genesis 5.11 joyent_20241128T004404Z i86pc i386 i86pc
```

### Command
```bash
fmadm faulty
```

### Logs
```
--------------- ------------------------------------  -------------- ---------
TIME            EVENT-ID                              MSG-ID         SEVERITY
--------------- ------------------------------------  -------------- ---------
Nov 29 15:00:31 a057d0ce-147b-4ef6-9293-2f05739c049b  ZFS-8000-HC    Major

Host        : genesis
Platform    : MS-7D22   Chassis_id  : Default-string
Product_sn  :

Fault class : fault.fs.zfs.io_failure_wait
Affects     : zfs://pool=filepool
                  faulted but still in service
Problem in  : zfs://pool=filepool
                  faulted but still in service

Description : The ZFS pool has experienced currently unrecoverable I/O
                    failures.  Refer to http://illumos.org/msg/ZFS-8000-HC for
              more information.

Response    : No automated response will be taken.

Impact      : Read and write I/Os cannot be serviced.

Action      : Make sure the affected devices are connected, then run
                    'zpool clear'.

--------------- ------------------------------------  -------------- ---------
TIME            EVENT-ID                              MSG-ID         SEVERITY
--------------- ------------------------------------  -------------- ---------
Nov 29 15:00:21 704825e5-6713-4ffe-8d20-5ef368feaebb  PCIEX-8000-KP  Major

Host        : genesis
Platform    : MS-7D22   Chassis_id  : Default-string
Product_sn  :

Fault class : fault.io.pciex.device-interr-corr max 50%
              fault.io.pciex.bus-linkerr-corr 25%
Affects     : dev:////pci@0,0/pci8086,43bc@1c/pci197b,0@0
              dev:////pci@0,0/pci8086,43bc@1c
                  faulted and taken out of service
FRU         : "MB" (hc://:product-id=MS-7D22:server-id=genesis:chassis-id=Default-string/motherboard=0)
                  faulty

Description : Too many recovered bus errors have been detected, which indicates
              a problem with the specified bus or with the specified
              transmitting device. This may degrade into an unrecoverable
              fault.
              Refer to http://illumos.org/msg/PCIEX-8000-KP for more
              information.

Response    : One or more device instances may be disabled

Impact      : Loss of services provided by the device instances associated with
              this fault

Action      : If a plug-in card is involved check for badly-seated cards or
              bent pins. Otherwise schedule a repair procedure to replace the
              affected device.  Use fmadm faulty to identify the device or
              contact your illumos distribution team for support.

```
