## SmartOS (build: 20241128T004404Z)

```
From noaccess@genesis.localdomain Fri Nov 29 13:52:14 2024
Date: Fri, 29 Nov 2024 13:52:14 GMT
From: No Access User <noaccess@genesis.localdomain>
Message-Id: <202411291352.4ATDqEwq006041@genesis.localdomain>
Subject: Fault Management Event: genesis:ZFS-8000-HC
Content-Length: 574

SUNW-MSG-ID: ZFS-8000-HC, TYPE: Error, VER: 1, SEVERITY: Major
EVENT-TIME: Fri Nov 29 13:52:14 UTC 2024
PLATFORM: MS-7D22, CSN: Default-string, HOSTNAME: genesis
SOURCE: zfs-diagnosis, REV: 1.0
EVENT-ID: 8c5850d5-2469-49e4-aa07-ebb1111cd631
DESC: The ZFS pool has experienced currently unrecoverable I/O
            failures.  Refer to http://illumos.org/msg/ZFS-8000-HC for more information.
AUTO-RESPONSE: No automated response will be taken.
IMPACT: Read and write I/Os cannot be serviced.
REC-ACTION: Make sure the affected devices are connected, then run
            'zpool clear'.

```
