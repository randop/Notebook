# Genesis (node) server

## SmartOS (build: 20241128T004404Z)

### System
```
uname -a
SunOS genesis 5.11 joyent_20241128T004404Z i86pc i386 i86pc
```

### Command
```bash
dmesg
```

### Logs
```
November 29, 2024 at 03:08:44 PM UTC
2024-11-29T15:00:13.246488+00:00 genesis genunix: [ID 936769 kern.info] power0 is /pseudo/power@0
2024-11-29T15:00:13.246496+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: ramdisk1024
2024-11-29T15:00:13.246498+00:00 genesis genunix: [ID 936769 kern.info] ramdisk1024 is /pseudo/ramdisk@1024
2024-11-29T15:00:13.246500+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: zfs0
2024-11-29T15:00:13.246503+00:00 genesis genunix: [ID 936769 kern.info] zfs0 is /pseudo/zfs@0
2024-11-29T15:00:13.246505+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: srn0
2024-11-29T15:00:13.246507+00:00 genesis genunix: [ID 936769 kern.info] srn0 is /pseudo/srn@0
2024-11-29T15:00:13.246509+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: dcpc0
2024-11-29T15:00:13.246511+00:00 genesis genunix: [ID 936769 kern.info] dcpc0 is /pseudo/dcpc@0
2024-11-29T15:00:13.246513+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fasttrap0
2024-11-29T15:00:13.246515+00:00 genesis genunix: [ID 936769 kern.info] fasttrap0 is /pseudo/fasttrap@0
2024-11-29T15:00:13.246517+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fbt0
2024-11-29T15:00:13.246519+00:00 genesis genunix: [ID 936769 kern.info] fbt0 is /pseudo/fbt@0
2024-11-29T15:00:13.246521+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: profile0
2024-11-29T15:00:13.246522+00:00 genesis genunix: [ID 936769 kern.info] profile0 is /pseudo/profile@0
2024-11-29T15:00:13.246524+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: lockstat0
2024-11-29T15:00:13.246526+00:00 genesis genunix: [ID 936769 kern.info] lockstat0 is /pseudo/lockstat@0
2024-11-29T15:00:13.246528+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: sdt0
2024-11-29T15:00:13.246530+00:00 genesis genunix: [ID 936769 kern.info] sdt0 is /pseudo/sdt@0
2024-11-29T15:00:13.246532+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: systrace0
2024-11-29T15:00:13.246538+00:00 genesis genunix: [ID 936769 kern.info] systrace0 is /pseudo/systrace@0
2024-11-29T15:00:13.246543+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fcp0
2024-11-29T15:00:13.246545+00:00 genesis genunix: [ID 936769 kern.info] fcp0 is /pseudo/fcp@0
2024-11-29T15:00:13.246547+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fcsm0
2024-11-29T15:00:13.246549+00:00 genesis genunix: [ID 936769 kern.info] fcsm0 is /pseudo/fcsm@0
2024-11-29T15:00:13.246551+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: ipd0
2024-11-29T15:00:13.246553+00:00 genesis genunix: [ID 936769 kern.info] ipd0 is /pseudo/ipd@0
2024-11-29T15:00:13.246555+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: stmf0
2024-11-29T15:00:13.246557+00:00 genesis genunix: [ID 936769 kern.info] stmf0 is /pseudo/stmf@0
2024-11-29T15:00:13.246559+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fssnap0
2024-11-29T15:00:13.246562+00:00 genesis genunix: [ID 936769 kern.info] fssnap0 is /pseudo/fssnap@0
2024-11-29T15:00:13.246563+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: kvm0
2024-11-29T15:00:13.246565+00:00 genesis genunix: [ID 936769 kern.info] kvm0 is /pseudo/kvm@0
2024-11-29T15:00:13.246567+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: pool0
2024-11-29T15:00:13.246569+00:00 genesis genunix: [ID 936769 kern.info] pool0 is /pseudo/pool@0
2024-11-29T15:00:13.246571+00:00 genesis ipf: [ID 774698 kern.info] IP Filter: v4.1.9, running.
2024-11-29T15:00:13.246573+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: bpf0
2024-11-29T15:00:13.246575+00:00 genesis genunix: [ID 936769 kern.info] bpf0 is /pseudo/bpf@0
2024-11-29T15:00:13.246577+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: pm0
2024-11-29T15:00:13.246579+00:00 genesis genunix: [ID 936769 kern.info] pm0 is /pseudo/pm@0
2024-11-29T15:00:13.246587+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: nsmb0
2024-11-29T15:00:13.246589+00:00 genesis genunix: [ID 936769 kern.info] nsmb0 is /pseudo/nsmb@0
2024-11-29T15:00:13.246602+00:00 genesis tap: [ID 654818 kern.info] Universal TUN/TAP device driver ver 1.3.0 11/28/2024 (C) 1999-2000 Maxim Krasnyansky
2024-11-29T15:00:13.246605+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: tap0
2024-11-29T15:00:13.246607+00:00 genesis genunix: [ID 936769 kern.info] tap0 is /pseudo/tap@0
2024-11-29T15:00:13.246610+00:00 genesis tun: [ID 654818 kern.info] Universal TUN/TAP device driver ver 1.3.0 11/28/2024 (C) 1999-2000 Maxim Krasnyansky
2024-11-29T15:00:13.246612+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: tun0
2024-11-29T15:00:13.246614+00:00 genesis genunix: [ID 936769 kern.info] tun0 is /pseudo/tun@0
2024-11-29T15:00:13.246616+00:00 genesis blkdev: [ID 643073 kern.info] NOTICE: blkdev0: dynamic LUN expansion
2024-11-29T15:00:13.246618+00:00 genesis blkdev: [ID 348765 kern.info] Block device: blkdev@1,0, blkdev0
2024-11-29T15:00:13.246620+00:00 genesis genunix: [ID 936769 kern.info] blkdev0 is /pci@0,0/pci8086,1901@1/pci1c5c,0@0/blkdev@1,0
2024-11-29T15:00:13.246622+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci8086,1901@1/pci1c5c,0@0/blkdev@1,0 (blkdev0) online
2024-11-29T15:00:13.246624+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: lx_systrace0
2024-11-29T15:00:13.246626+00:00 genesis genunix: [ID 936769 kern.info] lx_systrace0 is /pseudo/lx_systrace@0
2024-11-29T15:00:13.246628+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: inotify0
2024-11-29T15:00:13.246630+00:00 genesis genunix: [ID 936769 kern.info] inotify0 is /pseudo/inotify@0
2024-11-29T15:00:13.246632+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: eventfd0
2024-11-29T15:00:13.246634+00:00 genesis genunix: [ID 936769 kern.info] eventfd0 is /pseudo/eventfd@0
2024-11-29T15:00:13.246641+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: timerfd0
2024-11-29T15:00:13.246646+00:00 genesis genunix: [ID 936769 kern.info] timerfd0 is /pseudo/timerfd@0
2024-11-29T15:00:13.246648+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: signalfd0
2024-11-29T15:00:13.246650+00:00 genesis genunix: [ID 936769 kern.info] signalfd0 is /pseudo/signalfd@0
2024-11-29T15:00:13.246652+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: vmm0
2024-11-29T15:00:13.246654+00:00 genesis genunix: [ID 936769 kern.info] vmm0 is /pseudo/vmm@0
2024-11-29T15:00:13.246656+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: viona0
2024-11-29T15:00:13.246658+00:00 genesis genunix: [ID 936769 kern.info] viona0 is /pseudo/viona@0
2024-11-29T15:00:13.246660+00:00 genesis sata: [ID 663010 kern.info] /pci@0,0/pci8086,43bc@1c/pci197b,0@0 :
2024-11-29T15:00:13.246662+00:00 genesis sata: [ID 761595 kern.info] #011SATA disk device at port 0
2024-11-29T15:00:13.246664+00:00 genesis sata: [ID 846691 kern.info] #011model ST500LM021-1KJ152
2024-11-29T15:00:13.246666+00:00 genesis sata: [ID 693010 kern.info] #011firmware 0002YXM1
2024-11-29T15:00:13.246668+00:00 genesis sata: [ID 163988 kern.info] #011serial number 00000000
2024-11-29T15:00:13.246670+00:00 genesis sata: [ID 594940 kern.info] #011supported features:
2024-11-29T15:00:13.246672+00:00 genesis sata: [ID 981177 kern.info] #011 48-bit LBA, DMA, Native Command Queueing, SMART, SMART self-test
2024-11-29T15:00:13.246674+00:00 genesis sata: [ID 996592 kern.info] #011SATA Gen3 signaling speed (6.0Gbps)
2024-11-29T15:00:13.246676+00:00 genesis sata: [ID 349649 kern.info] #011Supported queue depth 32
2024-11-29T15:00:13.246678+00:00 genesis sata: [ID 349649 kern.info] #011capacity = 976773168 sectors
2024-11-29T15:00:13.246680+00:00 genesis scsi: [ID 583861 kern.info] sd5 at ahci1: target 0 lun 0
2024-11-29T15:00:13.246682+00:00 genesis genunix: [ID 936769 kern.info] sd5 is /pci@0,0/pci8086,43bc@1c/pci197b,0@0/disk@0,0
2024-11-29T15:00:13.246690+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci8086,43bc@1c/pci197b,0@0/disk@0,0 (sd5) online
2024-11-29T15:00:13.246693+00:00 genesis sata: [ID 663010 kern.info] /pci@0,0/pci8086,43bc@1c/pci197b,0@0 :
2024-11-29T15:00:13.246695+00:00 genesis sata: [ID 761595 kern.info] #011SATA disk device at port 1
2024-11-29T15:00:13.246697+00:00 genesis sata: [ID 846691 kern.info] #011model ST500LM021-1KJ152
2024-11-29T15:00:13.246699+00:00 genesis sata: [ID 693010 kern.info] #011firmware 0002YXM1
2024-11-29T15:00:13.246701+00:00 genesis sata: [ID 163988 kern.info] #011serial number 00000000
2024-11-29T15:00:13.246703+00:00 genesis sata: [ID 594940 kern.info] #011supported features:
2024-11-29T15:00:13.246705+00:00 genesis sata: [ID 981177 kern.info] #011 48-bit LBA, DMA, Native Command Queueing, SMART, SMART self-test
2024-11-29T15:00:13.246707+00:00 genesis sata: [ID 996592 kern.info] #011SATA Gen3 signaling speed (6.0Gbps)
2024-11-29T15:00:13.246709+00:00 genesis sata: [ID 349649 kern.info] #011Supported queue depth 32
2024-11-29T15:00:13.246711+00:00 genesis sata: [ID 349649 kern.info] #011capacity = 976773168 sectors
2024-11-29T15:00:13.246724+00:00 genesis scsi: [ID 583861 kern.info] sd6 at ahci1: target 1 lun 0
2024-11-29T15:00:13.246727+00:00 genesis genunix: [ID 936769 kern.info] sd6 is /pci@0,0/pci8086,43bc@1c/pci197b,0@0/disk@1,0
2024-11-29T15:00:13.246728+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci8086,43bc@1c/pci197b,0@0/disk@1,0 (sd6) online
2024-11-29T15:00:13.246730+00:00 genesis sata: [ID 663010 kern.info] /pci@0,0/pci8086,43bc@1c/pci197b,0@0 :
2024-11-29T15:00:13.246733+00:00 genesis sata: [ID 761595 kern.info] #011SATA disk device at port 2
2024-11-29T15:00:13.246734+00:00 genesis sata: [ID 846691 kern.info] #011model WDC WD5000BPVT-24HXZT1
2024-11-29T15:00:13.246736+00:00 genesis sata: [ID 693010 kern.info] #011firmware 02.01A02
2024-11-29T15:00:13.246738+00:00 genesis sata: [ID 163988 kern.info] #011serial number      000000000000000
2024-11-29T15:00:13.246747+00:00 genesis sata: [ID 594940 kern.info] #011supported features:
2024-11-29T15:00:13.246749+00:00 genesis sata: [ID 981177 kern.info] #011 48-bit LBA, DMA, Native Command Queueing, SMART, SMART self-test
2024-11-29T15:00:13.246751+00:00 genesis sata: [ID 643337 kern.info] #011SATA Gen2 signaling speed (3.0Gbps)
2024-11-29T15:00:13.246753+00:00 genesis sata: [ID 349649 kern.info] #011Supported queue depth 32
2024-11-29T15:00:13.246755+00:00 genesis sata: [ID 349649 kern.info] #011capacity = 976773168 sectors
2024-11-29T15:00:13.246757+00:00 genesis scsi: [ID 583861 kern.info] sd7 at ahci1: target 2 lun 0
2024-11-29T15:00:13.246759+00:00 genesis genunix: [ID 936769 kern.info] sd7 is /pci@0,0/pci8086,43bc@1c/pci197b,0@0/disk@2,0
2024-11-29T15:00:13.246761+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci8086,43bc@1c/pci197b,0@0/disk@2,0 (sd7) online
2024-11-29T15:00:13.246763+00:00 genesis sata: [ID 663010 kern.info] /pci@0,0/pci8086,43bc@1c/pci197b,0@0 :
2024-11-29T15:00:13.246765+00:00 genesis sata: [ID 761595 kern.info] #011SATA disk device at port 3
2024-11-29T15:00:13.246767+00:00 genesis sata: [ID 846691 kern.info] #011model ST500LM034-2GH17A
2024-11-29T15:00:13.246769+00:00 genesis sata: [ID 693010 kern.info] #011firmware LXM4
2024-11-29T15:00:13.246773+00:00 genesis sata: [ID 163988 kern.info] #011serial number             00000000
2024-11-29T15:00:13.246775+00:00 genesis sata: [ID 594940 kern.info] #011supported features:
2024-11-29T15:00:13.246779+00:00 genesis sata: [ID 981177 kern.info] #011 48-bit LBA, DMA, Native Command Queueing, SMART, SMART self-test
2024-11-29T15:00:13.246781+00:00 genesis sata: [ID 996592 kern.info] #011SATA Gen3 signaling speed (6.0Gbps)
2024-11-29T15:00:13.246783+00:00 genesis sata: [ID 349649 kern.info] #011Supported queue depth 32
2024-11-29T15:00:13.246785+00:00 genesis sata: [ID 349649 kern.info] #011capacity = 976773168 sectors
2024-11-29T15:00:13.246787+00:00 genesis scsi: [ID 583861 kern.info] sd8 at ahci1: target 3 lun 0
2024-11-29T15:00:13.246793+00:00 genesis genunix: [ID 936769 kern.info] sd8 is /pci@0,0/pci8086,43bc@1c/pci197b,0@0/disk@3,0
2024-11-29T15:00:13.246798+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci8086,43bc@1c/pci197b,0@0/disk@3,0 (sd8) online
2024-11-29T15:00:13.246800+00:00 genesis sata: [ID 663010 kern.info] /pci@0,0/pci8086,43bc@1c/pci197b,0@0 :
2024-11-29T15:00:13.246802+00:00 genesis sata: [ID 761595 kern.info] #011SATA disk device at port 4
2024-11-29T15:00:13.246804+00:00 genesis sata: [ID 846691 kern.info] #011model ST500LM012 HN-M500MBB
2024-11-29T15:00:13.246806+00:00 genesis sata: [ID 693010 kern.info] #011firmware 2AR10001
2024-11-29T15:00:13.246808+00:00 genesis sata: [ID 163988 kern.info] #011serial number 00000000000000
2024-11-29T15:00:13.246810+00:00 genesis sata: [ID 594940 kern.info] #011supported features:
2024-11-29T15:00:13.246812+00:00 genesis sata: [ID 981177 kern.info] #011 48-bit LBA, DMA, Native Command Queueing, SMART, SMART self-test
2024-11-29T15:00:13.246814+00:00 genesis sata: [ID 643337 kern.info] #011SATA Gen2 signaling speed (3.0Gbps)
2024-11-29T15:00:13.246816+00:00 genesis sata: [ID 349649 kern.info] #011Supported queue depth 32
2024-11-29T15:00:13.246818+00:00 genesis sata: [ID 349649 kern.info] #011capacity = 976773168 sectors
2024-11-29T15:00:13.246820+00:00 genesis scsi: [ID 583861 kern.info] sd9 at ahci1: target 4 lun 0
2024-11-29T15:00:13.246822+00:00 genesis genunix: [ID 936769 kern.info] sd9 is /pci@0,0/pci8086,43bc@1c/pci197b,0@0/disk@4,0
2024-11-29T15:00:13.246824+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci8086,43bc@1c/pci197b,0@0/disk@4,0 (sd9) online
2024-11-29T15:00:13.246830+00:00 genesis genunix: [ID 454863 kern.info] dump on /dev/zvol/dsk/zones/dump size 1024 MB
2024-11-29T15:00:13.246841+00:00 genesis genunix: [ID 127566 kern.info] device pciclass,030000@2(display#0) keeps up device sd@0,0(disk#0), but the former is not power managed
2024-11-29T15:00:13.246851+00:00 genesis mac: [ID 435574 kern.info] NOTICE: e1000g0 link up, 1000 Mbps, full duplex
2024-11-29T15:00:13.246855+00:00 genesis genunix: [ID 390243 kern.info] Creating /etc/devices/devid_cache
2024-11-29T15:00:13.246857+00:00 genesis genunix: [ID 390243 kern.info] Creating /etc/devices/pci_unitaddr_persistent
2024-11-29T14:59:57+00:00 genesis /sbin/dhcpagent[2395]: [ID 778557 daemon.warning] configure_v4_lease: no IP broadcast specified for e1000g0, making best guess
2024-11-29T15:00:21.255907+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: devinfo0
2024-11-29T15:00:21.255944+00:00 genesis genunix: [ID 936769 kern.info] devinfo0 is /pseudo/devinfo@0
2024-11-29T15:00:21.289070+00:00 genesis fmd: [ID 377184 daemon.error] SUNW-MSG-ID: PCIEX-8000-KP, TYPE: Fault, VER: 1, SEVERITY: Major#012EVENT-TIME: Fri Nov 29 15:00:21 UTC 2024#012PLATFORM: MS-7D22, CSN: Default-string, HOSTNAME: genesis#012SOURCE: eft, REV: 1.16#012EVENT-ID: 704825e5-6713-4ffe-8d20-5ef368feaebb#012DESC: Too many recovered bus errors have been detected, which indicates a problem with the specified bus or with the specified transmitting device. This may degrade into an unrecoverable fault.#012  Refer to http://illumos.org/msg/PCIEX-8000-KP for more information.#012AUTO-RESPONSE: One or more device instances may be disabled#012#012IMPACT: Loss of services provided by the device instances associated with this fault#012#012REC-ACTION: If a plug-in card is involved check for badly-seated cards or bent pins. Otherwise schedule a repair procedure to replace the affected device.  Use fmadm faulty to identify the device or contact your illumos distribution team for support.#012
2024-11-29T15:00:21.365611+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: llc10
2024-11-29T15:00:21.365622+00:00 genesis genunix: [ID 936769 kern.info] llc10 is /pseudo/llc1@0
2024-11-29T15:00:21.366697+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: ramdisk1024
2024-11-29T15:00:21.366704+00:00 genesis genunix: [ID 936769 kern.info] ramdisk1024 is /pseudo/ramdisk@1024
2024-11-29T15:00:21.366706+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: ucode0
2024-11-29T15:00:21.366708+00:00 genesis genunix: [ID 936769 kern.info] ucode0 is /pseudo/ucode@0
2024-11-29T15:00:21.369360+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: dcpc0
2024-11-29T15:00:21.369374+00:00 genesis genunix: [ID 936769 kern.info] dcpc0 is /pseudo/dcpc@0
2024-11-29T15:00:21.369592+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fbt0
2024-11-29T15:00:21.369596+00:00 genesis genunix: [ID 936769 kern.info] fbt0 is /pseudo/fbt@0
2024-11-29T15:00:21.369764+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: profile0
2024-11-29T15:00:21.369773+00:00 genesis genunix: [ID 936769 kern.info] profile0 is /pseudo/profile@0
2024-11-29T15:00:21.369921+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: lockstat0
2024-11-29T15:00:21.369928+00:00 genesis genunix: [ID 936769 kern.info] lockstat0 is /pseudo/lockstat@0
2024-11-29T15:00:21.370127+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: sdt0
2024-11-29T15:00:21.370132+00:00 genesis genunix: [ID 936769 kern.info] sdt0 is /pseudo/sdt@0
2024-11-29T15:00:21.370269+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: systrace0
2024-11-29T15:00:21.370272+00:00 genesis genunix: [ID 936769 kern.info] systrace0 is /pseudo/systrace@0
2024-11-29T15:00:21.373143+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fcp0
2024-11-29T15:00:21.373150+00:00 genesis genunix: [ID 936769 kern.info] fcp0 is /pseudo/fcp@0
2024-11-29T15:00:21.373666+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fcsm0
2024-11-29T15:00:21.373669+00:00 genesis genunix: [ID 936769 kern.info] fcsm0 is /pseudo/fcsm@0
2024-11-29T15:00:21.373954+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: ipd0
2024-11-29T15:00:21.373963+00:00 genesis genunix: [ID 936769 kern.info] ipd0 is /pseudo/ipd@0
2024-11-29T15:00:21.373965+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: stmf0
2024-11-29T15:00:21.373966+00:00 genesis genunix: [ID 936769 kern.info] stmf0 is /pseudo/stmf@0
2024-11-29T15:00:21.395857+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fssnap0
2024-11-29T15:00:21.395871+00:00 genesis genunix: [ID 936769 kern.info] fssnap0 is /pseudo/fssnap@0
2024-11-29T15:00:21.400997+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: kvm0
2024-11-29T15:00:21.401017+00:00 genesis genunix: [ID 936769 kern.info] kvm0 is /pseudo/kvm@0
2024-11-29T15:00:21.401908+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: bpf0
2024-11-29T15:00:21.401912+00:00 genesis genunix: [ID 936769 kern.info] bpf0 is /pseudo/bpf@0
2024-11-29T15:00:21.402341+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: pm0
2024-11-29T15:00:21.402345+00:00 genesis genunix: [ID 936769 kern.info] pm0 is /pseudo/pm@0
2024-11-29T15:00:21.404447+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: nsmb0
2024-11-29T15:00:21.404451+00:00 genesis genunix: [ID 936769 kern.info] nsmb0 is /pseudo/nsmb@0
2024-11-29T15:00:21.422378+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: lx_systrace0
2024-11-29T15:00:21.422386+00:00 genesis genunix: [ID 936769 kern.info] lx_systrace0 is /pseudo/lx_systrace@0
2024-11-29T15:00:21.422839+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: inotify0
2024-11-29T15:00:21.422844+00:00 genesis genunix: [ID 936769 kern.info] inotify0 is /pseudo/inotify@0
2024-11-29T15:00:21.423066+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: eventfd0
2024-11-29T15:00:21.423070+00:00 genesis genunix: [ID 936769 kern.info] eventfd0 is /pseudo/eventfd@0
2024-11-29T15:00:21.423298+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: timerfd0
2024-11-29T15:00:21.423301+00:00 genesis genunix: [ID 936769 kern.info] timerfd0 is /pseudo/timerfd@0
2024-11-29T15:00:21.423602+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: signalfd0
2024-11-29T15:00:21.423605+00:00 genesis genunix: [ID 936769 kern.info] signalfd0 is /pseudo/signalfd@0
2024-11-29T15:00:21.425248+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: viona0
2024-11-29T15:00:21.425252+00:00 genesis genunix: [ID 936769 kern.info] viona0 is /pseudo/viona@0
2024-11-29T15:00:21.428196+00:00 genesis scsi: [ID 583861 kern.info] sd0 at scsa2usb0: target 0 lun 0
2024-11-29T15:00:21.428201+00:00 genesis genunix: [ID 936769 kern.info] sd0 is /pci@0,0/pci1462,7d22@14/storage@2/disk@0,0
2024-11-29T15:00:21.437744+00:00 genesis genunix: [ID 127566 kern.info] device pciclass,030000@2(display#0) keeps up device sd@0,0(disk#0), but the former is not power managed
2024-11-29T15:00:21.469510+00:00 genesis usba: [ID 912658 kern.info] USB 1.10 interface (usbif2a7a,9007.config1.1) operating at low speed (USB 1.x) on USB 2.0 external hub: input@1, hid2 at bus address 7
2024-11-29T15:00:21.469520+00:00 genesis usba: [ID 349649 kern.info] CASUE USB KB
2024-11-29T15:00:21.469521+00:00 genesis genunix: [ID 936769 kern.info] hid2 is /pci@0,0/pci1462,7d22@14/hub@9/device@3/input@1
2024-11-29T15:00:21.638233+00:00 genesis genunix: [ID 631017 kern.notice] NOTICE: Device: already retired: /pci@0,0/pci8086,43bc@1c/pci197b,0@0
2024-11-29T15:00:30.916045+00:00 genesis zfs: [ID 961531 kern.warning] WARNING: Pool 'filepool' has encountered an uncorrectable I/O failure and has been suspended; `zpool clear` will be required before the pool can be written to.
2024-11-29T15:00:31.142291+00:00 genesis fmd: [ID 377184 daemon.error] SUNW-MSG-ID: ZFS-8000-HC, TYPE: Error, VER: 1, SEVERITY: Major#012EVENT-TIME: Fri Nov 29 15:00:31 UTC 2024#012PLATFORM: MS-7D22, CSN: Default-string, HOSTNAME: genesis#012SOURCE: zfs-diagnosis, REV: 1.0#012EVENT-ID: a057d0ce-147b-4ef6-9293-2f05739c049b#012DESC: The ZFS pool has experienced currently unrecoverable I/O#012#011    failures.  Refer to http://illumos.org/msg/ZFS-8000-HC for more information.#012AUTO-RESPONSE: No automated response will be taken.#012IMPACT: Read and write I/Os cannot be serviced.#012REC-ACTION: Make sure the affected devices are connected, then run#012#011    'zpool clear'.
2024-11-29T15:00:31.567363+00:00 genesis genunix: [ID 390243 kern.info] Creating /etc/devices/retire_store
2024-11-29T15:00:31.567450+00:00 genesis genunix: [ID 390243 kern.info] Creating /etc/devices/devname_cache
2024-11-29T15:02:28.294454+00:00 genesis zfs: [ID 961531 kern.warning] WARNING: Pool 'filepool' has encountered an uncorrectable I/O failure and has been suspended; `zpool clear` will be required before the pool can be written to.
```
