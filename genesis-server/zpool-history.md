# Genesis (node) server

## SmartOS (build: 20241128T004404Z)

```bash
zpool history zones
```

```
History for 'zones':
2024-11-29.10:24:23 zpool create zones raidz3 c1t1d0 c3t0d0 c3t1d0 c3t2d0 c3t3d0
2024-11-29.10:26:33 zfs create -V 1024mb -o checksum=noparity zones/dump
2024-11-29.10:26:34 zfs create zones/config
2024-11-29.10:26:35 zfs set mountpoint=legacy zones/config
2024-11-29.10:26:35 zfs create -o mountpoint=legacy zones/usbkey
2024-11-29.10:26:36 zfs create -o quota=10g -o mountpoint=/zones/global/cores -o compression=gzip zones/cores
2024-11-29.10:26:36 zfs create -o mountpoint=legacy zones/opt
2024-11-29.10:26:36 zfs create zones/var
2024-11-29.10:26:37 zfs set mountpoint=legacy zones/var
2024-11-29.10:26:37 zfs create -V 16207mb zones/swap
2024-11-29.10:27:52 zpool import -f zones
2024-11-29.10:27:52 zpool set feature@extensible_dataset=enabled zones
2024-11-29.10:27:53 zfs set checksum=noparity zones/dump
2024-11-29.10:27:53 zpool set feature@multi_vdev_crash_dump=enabled zones
2024-11-29.10:27:55 zfs destroy -r zones/cores
2024-11-29.10:27:55 zfs create -o compression=gzip -o mountpoint=none zones/cores
2024-11-29.10:28:01 zfs create -o quota=10g -o mountpoint=/zones/global/cores zones/cores/global
2024-11-29.10:28:24 zfs create -o compression=lzjb -o mountpoint=/zones/archive zones/archive

```
