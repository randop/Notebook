# Genesis (node) server

## SmartOS (build: 20241128T004404Z)

```bash
dmesg
```

November 29, 2024 at 10:30:27 AM UTC
2024-11-29T10:28:12.136243+00:00 genesis sata: [ID 163988 kern.info] #011serial number            000000000
2024-11-29T10:28:12.136245+00:00 genesis sata: [ID 594940 kern.info] #011supported features:
2024-11-29T10:28:12.136247+00:00 genesis sata: [ID 981177 kern.info] #011 48-bit LBA, DMA, Native Command Queueing, SMART, SMART self-test
2024-11-29T10:28:12.136249+00:00 genesis sata: [ID 996592 kern.info] #011SATA Gen3 signaling speed (6.0Gbps)
2024-11-29T10:28:12.136251+00:00 genesis sata: [ID 349649 kern.info] #011Supported queue depth 32
2024-11-29T10:28:12.136253+00:00 genesis sata: [ID 349649 kern.info] #011capacity = 1953525168 sectors
2024-11-29T10:28:12.136255+00:00 genesis usba: [ID 912658 kern.info] USB 1.10 interface (usbif2a7a,9007.config1.1) operating at low speed (USB 1.x) on USB 2.0 external hub: input@1, hid1 at bus address 6
2024-11-29T10:28:12.136257+00:00 genesis usba: [ID 349649 kern.info] CASUE USB KB
2024-11-29T10:28:12.136259+00:00 genesis genunix: [ID 936769 kern.info] hid1 is /pci@0,0/pci1462,7d22@14/hub@9/device@3/input@1
2024-11-29T10:28:12.136261+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci1462,7d22@14/hub@9/device@3/input@1 (hid1) online
2024-11-29T10:28:12.136263+00:00 genesis scsi: [ID 583861 kern.info] sd2 at ahci0: target 1 lun 0
2024-11-29T10:28:12.136265+00:00 genesis genunix: [ID 936769 kern.info] sd2 is /pci@0,0/pci1462,7d22@17/disk@1,0
2024-11-29T10:28:12.136273+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci1462,7d22@17/disk@1,0 (sd2) online
2024-11-29T10:28:12.136275+00:00 genesis sata: [ID 663010 kern.info] /pci@0,0/pci1462,7d22@17 :
2024-11-29T10:28:12.136277+00:00 genesis sata: [ID 761595 kern.info] #011SATA disk device at port 2
2024-11-29T10:28:12.136279+00:00 genesis sata: [ID 846691 kern.info] #011model Ramsta SSD R600 mSATA 128GB
2024-11-29T10:28:12.136281+00:00 genesis sata: [ID 693010 kern.info] #011firmware RI18V0
2024-11-29T10:28:12.136283+00:00 genesis sata: [ID 163988 kern.info] #011serial number 00000000000000
2024-11-29T10:28:12.136285+00:00 genesis sata: [ID 594940 kern.info] #011supported features:
2024-11-29T10:28:12.136287+00:00 genesis sata: [ID 981177 kern.info] #011 48-bit LBA, DMA, Native Command Queueing, SMART, SMART self-test
2024-11-29T10:28:12.136290+00:00 genesis sata: [ID 996592 kern.info] #011SATA Gen3 signaling speed (6.0Gbps)
2024-11-29T10:28:12.136292+00:00 genesis sata: [ID 349649 kern.info] #011Supported queue depth 32
2024-11-29T10:28:12.136294+00:00 genesis sata: [ID 349649 kern.info] #011capacity = 250069680 sectors
2024-11-29T10:28:12.136296+00:00 genesis scsi: [ID 583861 kern.info] sd3 at ahci0: target 2 lun 0
2024-11-29T10:28:12.136298+00:00 genesis genunix: [ID 936769 kern.info] sd3 is /pci@0,0/pci1462,7d22@17/disk@2,0
2024-11-29T10:28:12.136300+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci1462,7d22@17/disk@2,0 (sd3) online
2024-11-29T10:28:12.136302+00:00 genesis sata: [ID 663010 kern.info] /pci@0,0/pci1462,7d22@17 :
2024-11-29T10:28:12.136304+00:00 genesis sata: [ID 761595 kern.info] #011SATA disk device at port 3
2024-11-29T10:28:12.136318+00:00 genesis sata: [ID 846691 kern.info] #011model Hitachi HTS545025B9A300
2024-11-29T10:28:12.136321+00:00 genesis sata: [ID 693010 kern.info] #011firmware PB2OCA0G
2024-11-29T10:28:12.136323+00:00 genesis sata: [ID 163988 kern.info] #011serial number 00000000000000000000
2024-11-29T10:28:12.136332+00:00 genesis sata: [ID 594940 kern.info] #011supported features:
2024-11-29T10:28:12.136336+00:00 genesis sata: [ID 981177 kern.info] #011 48-bit LBA, DMA, Native Command Queueing, SMART, SMART self-test
2024-11-29T10:28:12.136338+00:00 genesis sata: [ID 643337 kern.info] #011SATA Gen2 signaling speed (3.0Gbps)
2024-11-29T10:28:12.136340+00:00 genesis sata: [ID 349649 kern.info] #011Supported queue depth 32
2024-11-29T10:28:12.136343+00:00 genesis sata: [ID 349649 kern.info] #011capacity = 488397168 sectors
2024-11-29T10:28:12.136345+00:00 genesis scsi: [ID 583861 kern.info] sd4 at ahci0: target 3 lun 0
2024-11-29T10:28:12.136347+00:00 genesis genunix: [ID 936769 kern.info] sd4 is /pci@0,0/pci1462,7d22@17/disk@3,0
2024-11-29T10:28:12.136349+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci1462,7d22@17/disk@3,0 (sd4) online
2024-11-29T10:28:12.136351+00:00 genesis mac: [ID 469746 kern.info] NOTICE: e1000g0 registered
2024-11-29T10:28:12.136353+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: ucode0
2024-11-29T10:28:12.136355+00:00 genesis genunix: [ID 936769 kern.info] ucode0 is /pseudo/ucode@0
2024-11-29T10:28:12.136357+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: devinfo0
2024-11-29T10:28:12.136359+00:00 genesis genunix: [ID 936769 kern.info] devinfo0 is /pseudo/devinfo@0
2024-11-29T10:28:12.136361+00:00 genesis rootnex: [ID 349649 kern.info] iscsi0 at root
2024-11-29T10:28:12.136363+00:00 genesis genunix: [ID 936769 kern.info] iscsi0 is /iscsi
2024-11-29T10:28:12.136365+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: pseudo1
2024-11-29T10:28:12.136367+00:00 genesis genunix: [ID 936769 kern.info] pseudo1 is /pseudo/zconsnex@1
2024-11-29T10:28:12.136369+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: pseudo2
2024-11-29T10:28:12.136371+00:00 genesis genunix: [ID 936769 kern.info] pseudo2 is /pseudo/zfdnex@2
2024-11-29T10:28:12.136374+00:00 genesis isa: [ID 202937 kern.info] ISA-device: asy0
2024-11-29T10:28:12.136382+00:00 genesis genunix: [ID 936769 kern.info] asy0 is /pci@0,0/isa@1f/asy@1,3f8
2024-11-29T10:28:12.136385+00:00 genesis isa: [ID 202937 kern.info] ISA-device: pit_beep0
2024-11-29T10:28:12.136387+00:00 genesis genunix: [ID 936769 kern.info] pit_beep0 is /pci@0,0/isa@1f/pit_beep
2024-11-29T10:28:12.136388+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: SNDW@, acpinex2
2024-11-29T10:28:12.136390+00:00 genesis genunix: [ID 936769 kern.info] acpinex2 is /fw/sb@0/SNDW
2024-11-29T10:28:12.136392+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: UAOL@, acpinex3
2024-11-29T10:28:12.136394+00:00 genesis genunix: [ID 936769 kern.info] acpinex3 is /fw/sb@0/UAOL
2024-11-29T10:28:12.136396+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: usbroothub@XHCI, acpinex4
2024-11-29T10:28:12.136399+00:00 genesis genunix: [ID 936769 kern.info] acpinex4 is /fw/sb@0/usbroothub@XHCI
2024-11-29T10:28:12.136401+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@1, acpinex5
2024-11-29T10:28:12.136403+00:00 genesis genunix: [ID 936769 kern.info] acpinex5 is /fw/sb@0/usbroothub@XHCI/port@1
2024-11-29T10:28:12.136405+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@2, acpinex6
2024-11-29T10:28:12.136406+00:00 genesis genunix: [ID 936769 kern.info] acpinex6 is /fw/sb@0/usbroothub@XHCI/port@2
2024-11-29T10:28:12.136409+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@3, acpinex7
2024-11-29T10:28:12.136411+00:00 genesis genunix: [ID 936769 kern.info] acpinex7 is /fw/sb@0/usbroothub@XHCI/port@3
2024-11-29T10:28:12.136412+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@4, acpinex8
2024-11-29T10:28:12.136414+00:00 genesis genunix: [ID 936769 kern.info] acpinex8 is /fw/sb@0/usbroothub@XHCI/port@4
2024-11-29T10:28:12.136416+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@5, acpinex9
2024-11-29T10:28:12.136418+00:00 genesis genunix: [ID 936769 kern.info] acpinex9 is /fw/sb@0/usbroothub@XHCI/port@5
2024-11-29T10:28:12.136427+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@6, acpinex10
2024-11-29T10:28:12.136429+00:00 genesis genunix: [ID 936769 kern.info] acpinex10 is /fw/sb@0/usbroothub@XHCI/port@6
2024-11-29T10:28:12.136431+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@7, acpinex11
2024-11-29T10:28:12.136433+00:00 genesis genunix: [ID 936769 kern.info] acpinex11 is /fw/sb@0/usbroothub@XHCI/port@7
2024-11-29T10:28:12.136435+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@8, acpinex12
2024-11-29T10:28:12.136437+00:00 genesis genunix: [ID 936769 kern.info] acpinex12 is /fw/sb@0/usbroothub@XHCI/port@8
2024-11-29T10:28:12.136439+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@9, acpinex13
2024-11-29T10:28:12.136452+00:00 genesis genunix: [ID 936769 kern.info] acpinex13 is /fw/sb@0/usbroothub@XHCI/port@9
2024-11-29T10:28:12.136455+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@10, acpinex14
2024-11-29T10:28:12.136457+00:00 genesis genunix: [ID 936769 kern.info] acpinex14 is /fw/sb@0/usbroothub@XHCI/port@10
2024-11-29T10:28:12.136459+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@11, acpinex15
2024-11-29T10:28:12.136461+00:00 genesis genunix: [ID 936769 kern.info] acpinex15 is /fw/sb@0/usbroothub@XHCI/port@11
2024-11-29T10:28:12.136463+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@12, acpinex16
2024-11-29T10:28:12.136465+00:00 genesis genunix: [ID 936769 kern.info] acpinex16 is /fw/sb@0/usbroothub@XHCI/port@12
2024-11-29T10:28:12.136467+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@13, acpinex17
2024-11-29T10:28:12.136469+00:00 genesis genunix: [ID 936769 kern.info] acpinex17 is /fw/sb@0/usbroothub@XHCI/port@13
2024-11-29T10:28:12.136471+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@14, acpinex18
2024-11-29T10:28:12.136473+00:00 genesis genunix: [ID 936769 kern.info] acpinex18 is /fw/sb@0/usbroothub@XHCI/port@14
2024-11-29T10:28:12.136475+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@0, acpinex19
2024-11-29T10:28:12.136481+00:00 genesis genunix: [ID 936769 kern.info] acpinex19 is /fw/sb@0/usbroothub@XHCI/port@0
2024-11-29T10:28:12.136486+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@17, acpinex20
2024-11-29T10:28:12.136488+00:00 genesis genunix: [ID 936769 kern.info] acpinex20 is /fw/sb@0/usbroothub@XHCI/port@17
2024-11-29T10:28:12.136490+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@18, acpinex21
2024-11-29T10:28:12.136492+00:00 genesis genunix: [ID 936769 kern.info] acpinex21 is /fw/sb@0/usbroothub@XHCI/port@18
2024-11-29T10:28:12.136494+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@19, acpinex22
2024-11-29T10:28:12.136496+00:00 genesis genunix: [ID 936769 kern.info] acpinex22 is /fw/sb@0/usbroothub@XHCI/port@19
2024-11-29T10:28:12.136498+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@20, acpinex23
2024-11-29T10:28:12.136500+00:00 genesis genunix: [ID 936769 kern.info] acpinex23 is /fw/sb@0/usbroothub@XHCI/port@20
2024-11-29T10:28:12.136502+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@21, acpinex24
2024-11-29T10:28:12.136504+00:00 genesis genunix: [ID 936769 kern.info] acpinex24 is /fw/sb@0/usbroothub@XHCI/port@21
2024-11-29T10:28:12.136506+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@22, acpinex25
2024-11-29T10:28:12.136508+00:00 genesis genunix: [ID 936769 kern.info] acpinex25 is /fw/sb@0/usbroothub@XHCI/port@22
2024-11-29T10:28:12.136510+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@23, acpinex26
2024-11-29T10:28:12.136512+00:00 genesis genunix: [ID 936769 kern.info] acpinex26 is /fw/sb@0/usbroothub@XHCI/port@23
2024-11-29T10:28:12.136513+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@24, acpinex27
2024-11-29T10:28:12.136515+00:00 genesis genunix: [ID 936769 kern.info] acpinex27 is /fw/sb@0/usbroothub@XHCI/port@24
2024-11-29T10:28:12.136518+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@25, acpinex28
2024-11-29T10:28:12.136520+00:00 genesis genunix: [ID 936769 kern.info] acpinex28 is /fw/sb@0/usbroothub@XHCI/port@25
2024-11-29T10:28:12.136522+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@26, acpinex29
2024-11-29T10:28:12.136530+00:00 genesis genunix: [ID 936769 kern.info] acpinex29 is /fw/sb@0/usbroothub@XHCI/port@26
2024-11-29T10:28:12.136532+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@1, acpinex30
2024-11-29T10:28:12.136534+00:00 genesis genunix: [ID 936769 kern.info] acpinex30 is /fw/sb@0/usbroothub@XHCI/port@9/port@1
2024-11-29T10:28:12.136536+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@2, acpinex31
2024-11-29T10:28:12.136538+00:00 genesis genunix: [ID 936769 kern.info] acpinex31 is /fw/sb@0/usbroothub@XHCI/port@9/port@2
2024-11-29T10:28:12.136540+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@3, acpinex32
2024-11-29T10:28:12.136542+00:00 genesis genunix: [ID 936769 kern.info] acpinex32 is /fw/sb@0/usbroothub@XHCI/port@9/port@3
2024-11-29T10:28:12.136544+00:00 genesis acpinex: [ID 328922 kern.info] acpinex: port@4, acpinex33
2024-11-29T10:28:12.136546+00:00 genesis genunix: [ID 936769 kern.info] acpinex33 is /fw/sb@0/usbroothub@XHCI/port@9/port@4
2024-11-29T10:28:12.136550+00:00 genesis nvme: [ID 259564 kern.info] nvme0: NVMe spec version 1.2
2024-11-29T10:28:12.136553+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: llc10
2024-11-29T10:28:12.136557+00:00 genesis genunix: [ID 936769 kern.info] llc10 is /pseudo/llc1@0
2024-11-29T10:28:12.136559+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: power0
2024-11-29T10:28:12.136561+00:00 genesis genunix: [ID 936769 kern.info] power0 is /pseudo/power@0
2024-11-29T10:28:12.136576+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: ramdisk1024
2024-11-29T10:28:12.136579+00:00 genesis genunix: [ID 936769 kern.info] ramdisk1024 is /pseudo/ramdisk@1024
2024-11-29T10:28:12.136581+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: zfs0
2024-11-29T10:28:12.136583+00:00 genesis genunix: [ID 936769 kern.info] zfs0 is /pseudo/zfs@0
2024-11-29T10:28:12.136585+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: srn0
2024-11-29T10:28:12.136594+00:00 genesis genunix: [ID 936769 kern.info] srn0 is /pseudo/srn@0
2024-11-29T10:28:12.136597+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: dcpc0
2024-11-29T10:28:12.136599+00:00 genesis genunix: [ID 936769 kern.info] dcpc0 is /pseudo/dcpc@0
2024-11-29T10:28:12.136601+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fasttrap0
2024-11-29T10:28:12.136603+00:00 genesis genunix: [ID 936769 kern.info] fasttrap0 is /pseudo/fasttrap@0
2024-11-29T10:28:12.136605+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fbt0
2024-11-29T10:28:12.136607+00:00 genesis genunix: [ID 936769 kern.info] fbt0 is /pseudo/fbt@0
2024-11-29T10:28:12.136609+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: profile0
2024-11-29T10:28:12.136611+00:00 genesis genunix: [ID 936769 kern.info] profile0 is /pseudo/profile@0
2024-11-29T10:28:12.136613+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: lockstat0
2024-11-29T10:28:12.136615+00:00 genesis genunix: [ID 936769 kern.info] lockstat0 is /pseudo/lockstat@0
2024-11-29T10:28:12.136617+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: sdt0
2024-11-29T10:28:12.136619+00:00 genesis genunix: [ID 936769 kern.info] sdt0 is /pseudo/sdt@0
2024-11-29T10:28:12.136621+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: systrace0
2024-11-29T10:28:12.136623+00:00 genesis genunix: [ID 936769 kern.info] systrace0 is /pseudo/systrace@0
2024-11-29T10:28:12.136625+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fcp0
2024-11-29T10:28:12.136627+00:00 genesis genunix: [ID 936769 kern.info] fcp0 is /pseudo/fcp@0
2024-11-29T10:28:12.136629+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fcsm0
2024-11-29T10:28:12.136631+00:00 genesis genunix: [ID 936769 kern.info] fcsm0 is /pseudo/fcsm@0
2024-11-29T10:28:12.136637+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: ipd0
2024-11-29T10:28:12.136641+00:00 genesis genunix: [ID 936769 kern.info] ipd0 is /pseudo/ipd@0
2024-11-29T10:28:12.136644+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: stmf0
2024-11-29T10:28:12.136646+00:00 genesis genunix: [ID 936769 kern.info] stmf0 is /pseudo/stmf@0
2024-11-29T10:28:12.136648+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: fssnap0
2024-11-29T10:28:12.136650+00:00 genesis genunix: [ID 936769 kern.info] fssnap0 is /pseudo/fssnap@0
2024-11-29T10:28:12.136652+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: kvm0
2024-11-29T10:28:12.136654+00:00 genesis genunix: [ID 936769 kern.info] kvm0 is /pseudo/kvm@0
2024-11-29T10:28:12.136657+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: pool0
2024-11-29T10:28:12.136659+00:00 genesis genunix: [ID 936769 kern.info] pool0 is /pseudo/pool@0
2024-11-29T10:28:12.136661+00:00 genesis ipf: [ID 774698 kern.info] IP Filter: v4.1.9, running.
2024-11-29T10:28:12.136663+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: bpf0
2024-11-29T10:28:12.136665+00:00 genesis genunix: [ID 936769 kern.info] bpf0 is /pseudo/bpf@0
2024-11-29T10:28:12.136667+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: pm0
2024-11-29T10:28:12.136668+00:00 genesis genunix: [ID 936769 kern.info] pm0 is /pseudo/pm@0
2024-11-29T10:28:12.136671+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: nsmb0
2024-11-29T10:28:12.136673+00:00 genesis genunix: [ID 936769 kern.info] nsmb0 is /pseudo/nsmb@0
2024-11-29T10:28:12.136675+00:00 genesis tap: [ID 654818 kern.info] Universal TUN/TAP device driver ver 1.3.0 11/28/2024 (C) 1999-2000 Maxim Krasnyansky
2024-11-29T10:28:12.136677+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: tap0
2024-11-29T10:28:12.136684+00:00 genesis genunix: [ID 936769 kern.info] tap0 is /pseudo/tap@0
2024-11-29T10:28:12.136689+00:00 genesis blkdev: [ID 643073 kern.info] NOTICE: blkdev0: dynamic LUN expansion
2024-11-29T10:28:12.136691+00:00 genesis blkdev: [ID 348765 kern.info] Block device: blkdev@1,0, blkdev0
2024-11-29T10:28:12.136693+00:00 genesis genunix: [ID 936769 kern.info] blkdev0 is /pci@0,0/pci8086,1901@1/pci1c5c,0@0/blkdev@1,0
2024-11-29T10:28:12.136695+00:00 genesis genunix: [ID 408114 kern.info] /pci@0,0/pci8086,1901@1/pci1c5c,0@0/blkdev@1,0 (blkdev0) online
2024-11-29T10:28:12.136697+00:00 genesis tun: [ID 654818 kern.info] Universal TUN/TAP device driver ver 1.3.0 11/28/2024 (C) 1999-2000 Maxim Krasnyansky
2024-11-29T10:28:12.136699+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: tun0
2024-11-29T10:28:12.136713+00:00 genesis genunix: [ID 936769 kern.info] tun0 is /pseudo/tun@0
2024-11-29T10:28:12.136716+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: lx_systrace0
2024-11-29T10:28:12.136719+00:00 genesis genunix: [ID 936769 kern.info] lx_systrace0 is /pseudo/lx_systrace@0
2024-11-29T10:28:12.136721+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: inotify0
2024-11-29T10:28:12.136723+00:00 genesis genunix: [ID 936769 kern.info] inotify0 is /pseudo/inotify@0
2024-11-29T10:28:12.136725+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: eventfd0
2024-11-29T10:28:12.136727+00:00 genesis genunix: [ID 936769 kern.info] eventfd0 is /pseudo/eventfd@0
2024-11-29T10:28:12.136729+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: timerfd0
2024-11-29T10:28:12.136732+00:00 genesis genunix: [ID 936769 kern.info] timerfd0 is /pseudo/timerfd@0
2024-11-29T10:28:12.136733+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: signalfd0
2024-11-29T10:28:12.136736+00:00 genesis genunix: [ID 936769 kern.info] signalfd0 is /pseudo/signalfd@0
2024-11-29T10:28:12.136738+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: vmm0
2024-11-29T10:28:12.136744+00:00 genesis genunix: [ID 936769 kern.info] vmm0 is /pseudo/vmm@0
2024-11-29T10:28:12.136749+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: viona0
2024-11-29T10:28:12.136752+00:00 genesis genunix: [ID 936769 kern.info] viona0 is /pseudo/viona@0
2024-11-29T10:28:12.136759+00:00 genesis genunix: [ID 454863 kern.info] dump on /dev/zvol/dsk/zones/dump size 1024 MB
2024-11-29T10:28:12.136761+00:00 genesis genunix: [ID 127566 kern.info] device pciclass,030000@2(display#0) keeps up device sd@0,0(disk#0), but the former is not power managed
2024-11-29T10:28:12.136763+00:00 genesis mac: [ID 435574 kern.info] NOTICE: e1000g0 link up, 1000 Mbps, full duplex
2024-11-29T10:28:12.136767+00:00 genesis genunix: [ID 390243 kern.info] Creating /etc/devices/devid_cache
2024-11-29T10:28:12.136769+00:00 genesis genunix: [ID 390243 kern.info] Creating /etc/devices/pci_unitaddr_persistent
2024-11-29T10:28:08+00:00 genesis /sbin/dhcpagent[2304]: [ID 778557 daemon.warning] configure_v4_lease: no IP broadcast specified for e1000g0, making best guess
2024-11-29T10:28:24.486266+00:00 genesis genunix: [ID 390243 kern.info] Creating /etc/devices/devname_cache
2024-11-29T10:28:33.477141+00:00 genesis pseudo: [ID 129642 kern.info] pseudo-device: devinfo0
2024-11-29T10:28:33.477151+00:00 genesis genunix: [ID 936769 kern.info] devinfo0 is /pseudo/devinfo@0
2024-11-29T10:28:33.524585+00:00 genesis scsi: [ID 583861 kern.info] sd0 at scsa2usb0: target 0 lun 0
2024-11-29T10:28:33.524596+00:00 genesis genunix: [ID 936769 kern.info] sd0 is /pci@0,0/pci1462,7d22@14/storage@2/disk@0,0
2024-11-29T10:28:33.534094+00:00 genesis genunix: [ID 127566 kern.info] device pciclass,030000@2(display#0) keeps up device sd@0,0(disk#0), but the former is not power managed
