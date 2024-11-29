# Genesis (node) server

## SmartOS (build: 20241128T004404Z)

### System
```
uname -a
SunOS genesis 5.11 joyent_20241128T004404Z i86pc i386 i86pc
```

### Command
```bash
prtconf -dD
```

### Logs
```
System Configuration:  Micro-Star International Co., Ltd.  i86pc
Memory size: 16207 Megabytes
System Peripherals (Software Nodes):

i86pc (driver name: rootnex)
    scsi_vhci, instance #0 (driver name: scsi_vhci)
    ramdisk, instance #0 (driver name: ramdisk)
    pci, instance #0 (driver name: npe)
        pci1462,7d22 (pciex8086,9b73) [Intel Corporation unknown device]
        pci8086,1901 (pciex8086,1901) [Intel Corporation 6th-10th Gen Core Processor PCIe Controller (x16)], instance #0 (driver name: pcieb)
            pci1c5c,0 (pciex1c5c,1327) [SK hynix BC501 NVMe Solid State Drive], instance #0 (driver name: nvme)
                blkdev, instance #0 (driver name: blkdev)
        display (pciex8086,9ba8) [Intel Corporation CometLake-S GT1 [UHD Graphics 610]], instance #0 (driver name: vgatext)
        pci1462,7d22 (pciex8086,1911) [Intel Corporation Xeon E3-1200 v5/v6 / E3-1500 v5 / 6th/7th/8th Gen Core Processor Gaussian Mixture Model]
        pci1462,7d22 (pciex8086,43ed) [Intel Corporation Tiger Lake-H USB 3.2 Gen 2x1 xHCI Host Controller], instance #0 (driver name: xhci)
            storage, instance #0 (driver name: scsa2usb)
                disk, instance #0 (driver name: sd)
            device, instance #0 (driver name: usb_mid)
            hub, instance #0 (driver name: hubd)
            hub, instance #1 (driver name: hubd)
                mouse, instance #0 (driver name: hid)
                device, instance #1 (driver name: usb_mid)
                    keyboard, instance #1 (driver name: hid)
                    input, instance #2 (driver name: hid)
        pci8086,43ef (pciex8086,43ef) [Intel Corporation Tiger Lake-H Shared SRAM]
        pci1462,7d22 (pciex8086,43e0) [Intel Corporation Tiger Lake-H Management Engine Interface]
        pci1462,7d22 (pciex8086,43d2) [Intel Corporation unknown device], instance #0 (driver name: ahci)
            disk, instance #1 (driver name: sd)
            disk, instance #2 (driver name: sd)
            disk, instance #3 (driver name: sd)
            disk, instance #4 (driver name: sd)
        pci8086,43bc (pciex8086,43bc) [Intel Corporation Tiger Lake-H PCI Express Root Port #5], instance #1 (driver name: pcieb)
            pci197b,0 (pciex197b,585) [JMicron Technology Corp. JMB58x AHCI SATA controller], instance #1 (driver name: ahci)
                disk, instance #5 (driver name: sd)
                disk, instance #6 (driver name: sd)
                disk, instance #7 (driver name: sd)
                disk, instance #8 (driver name: sd)
                disk, instance #9 (driver name: sd)
        pci8086,43b2 (pciex8086,43b2) [Intel Corporation unknown device], instance #2 (driver name: pcieb)
            pci1a56,1435 (pciex168c,3e) [Qualcomm Atheros QCA6174 802.11ac Wireless Network Adapter]
        pci8086,43b3 (pciex8086,43b3) [Intel Corporation unknown device], instance #3 (driver name: pcieb)
            pci10ec,123 (pciex10ec,8125) [Realtek Semiconductor Co., Ltd. RTL8125 2.5GbE Controller]
        isa (pciex8086,4388) [Intel Corporation H510 LPC/eSPI Controller], instance #0 (driver name: isa)
            asy, instance #0 (driver name: asy)
            pit_beep, instance #0 (driver name: pit_beep)
        pci1462,9d22 (pciex8086,f0c8) [Intel Corporation unknown device]
        pci1462,7d22 (pciex8086,43a3) [Intel Corporation Tiger Lake-H SMBus Controller]
        pci1462,7d22 (pciex8086,43a4) [Intel Corporation Tiger Lake-H SPI Controller]
        pci1462,7d22 (pciex8086,d4f) [Intel Corporation Ethernet Connection (10) I219-V], instance #0 (driver name: e1000g)
    fw, instance #0 (driver name: acpinex)
        sb, instance #1 (driver name: acpinex)
            SNDW, instance #2 (driver name: acpinex)
            UAOL, instance #3 (driver name: acpinex)
            cpu, instance #0 (driver name: cpudrv)
            cpu, instance #1 (driver name: cpudrv)
            usbroothub, instance #4 (driver name: acpinex)
                port, instance #5 (driver name: acpinex)
                port, instance #6 (driver name: acpinex)
                port, instance #7 (driver name: acpinex)
                port, instance #8 (driver name: acpinex)
                port, instance #9 (driver name: acpinex)
                port, instance #10 (driver name: acpinex)
                port, instance #11 (driver name: acpinex)
                port, instance #12 (driver name: acpinex)
                port, instance #13 (driver name: acpinex)
                    port, instance #30 (driver name: acpinex)
                    port, instance #31 (driver name: acpinex)
                    port, instance #32 (driver name: acpinex)
                    port, instance #33 (driver name: acpinex)
                port, instance #14 (driver name: acpinex)
                port, instance #15 (driver name: acpinex)
                port, instance #16 (driver name: acpinex)
                port, instance #17 (driver name: acpinex)
                port, instance #18 (driver name: acpinex)
                port, instance #19 (driver name: acpinex)
                port (driver name: acpinex)
                port, instance #20 (driver name: acpinex)
                port, instance #21 (driver name: acpinex)
                port, instance #22 (driver name: acpinex)
                port, instance #23 (driver name: acpinex)
                port, instance #24 (driver name: acpinex)
                port, instance #25 (driver name: acpinex)
                port, instance #26 (driver name: acpinex)
                port, instance #27 (driver name: acpinex)
                port, instance #28 (driver name: acpinex)
                port, instance #29 (driver name: acpinex)
    used-resources
    iscsi, instance #0 (driver name: iscsi)
    options, instance #0 (driver name: options)
    pseudo, instance #0 (driver name: pseudo)
```
