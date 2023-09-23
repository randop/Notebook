# Perf

## Installing perf
```bash
su
apt install -y linux-perf
perf --version
# perf version 5.10.191
```

## Running perf
```bash
root@local:/home/projects# perf stat tail /var/log/syslog
Sep 23 15:32:42 user dbus-daemon[788]: [system] Activation via systemd failed for unit 'packagekit.service': Unit packagekit.service is masked.
Sep 23 15:34:15 user rtkit-daemon[1355]: Supervising 14 threads of 6 processes of 1 users.
Sep 23 15:34:15 user rtkit-daemon[1355]: Supervising 14 threads of 6 processes of 1 users.
Sep 23 15:34:19 user rtkit-daemon[1355]: Supervising 14 threads of 6 processes of 1 users.
Sep 23 15:34:19 user rtkit-daemon[1355]: Supervising 14 threads of 6 processes of 1 users.
Sep 23 15:34:19 user systemd[1]: Started Run anacron jobs.
Sep 23 15:34:19 user anacron[25414]: Anacron 2.3 started on 2023-09-23
Sep 23 15:34:19 user anacron[25414]: Normal exit (0 jobs run)
Sep 23 15:34:19 user systemd[1]: anacron.service: Succeeded.
Sep 23 15:35:01 user CRON[25471]: (root) CMD (command -v debian-sa1 > /dev/null && debian-sa1 1 1)

 Performance counter stats for 'tail /var/log/syslog':

              1.14 msec task-clock                #    0.523 CPUs utilized          
                 0      context-switches          #    0.000 K/sec                  
                 0      cpu-migrations            #    0.000 K/sec                  
                78      page-faults               #    0.069 M/sec                  
         1,574,510      cycles                    #    1.387 GHz                    
           114,900      stalled-cycles-frontend   #    7.30% frontend cycles idle   
           321,695      stalled-cycles-backend    #   20.43% backend cycles idle    
         1,539,773      instructions              #    0.98  insn per cycle         
                                                  #    0.21  stalled cycles per insn
           351,516      branches                  #  309.675 M/sec                  
     <not counted>      branch-misses                                                 (0.00%)

       0.002171854 seconds time elapsed

       0.002341000 seconds user
       0.000000000 seconds sys


Some events weren't counted. Try disabling the NMI watchdog:
        echo 0 > /proc/sys/kernel/nmi_watchdog
        perf stat ...
        echo 1 > /proc/sys/kernel/nmi_watchdog
```
