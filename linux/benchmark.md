# Benchmarking

## speed of disk
```bash
sudo apt install --no-install-recommends -y fio

fio --name=iotest4k --rw=randwrite --direct=1 --ioengine=libaio --bs=4k --numjobs=4 --iodepth=128 --size=100M --runtime=600 --group_reporting
```

### disk results
> AWS
```
iotest4k: (g=0): rw=randwrite, bs=(R) 4096B-4096B, (W) 4096B-4096B, (T) 4096B-4096B, ioengine=libaio, iodepth=128
...
fio-3.25
Starting 4 processes
iotest4k: Laying out IO file (1 file / 100MiB)
iotest4k: Laying out IO file (1 file / 100MiB)
iotest4k: Laying out IO file (1 file / 100MiB)
iotest4k: Laying out IO file (1 file / 100MiB)
Jobs: 4 (f=4): [w(4)][100.0%][w=13.3MiB/s][w=3394 IOPS][eta 00m:00s]
iotest4k: (groupid=0, jobs=4): err= 0: pid=1074367: Mon Sep 25 12:42:01 2023
  write: IOPS=3177, BW=12.4MiB/s (13.0MB/s)(400MiB/32231msec); 0 zone resets
    slat (usec): min=4, max=39912, avg=1249.88, stdev=3428.52
    clat (msec): min=10, max=247, avg=159.50, stdev=29.83
     lat (msec): min=10, max=247, avg=160.75, stdev=30.21
    clat percentiles (msec):
     |  1.00th=[   23],  5.00th=[  123], 10.00th=[  146], 20.00th=[  157],
     | 30.00th=[  157], 40.00th=[  163], 50.00th=[  167], 60.00th=[  167],
     | 70.00th=[  167], 80.00th=[  176], 90.00th=[  178], 95.00th=[  186],
     | 99.00th=[  199], 99.50th=[  207], 99.90th=[  222], 99.95th=[  226],
     | 99.99th=[  234]
   bw (  KiB/s): min=11368, max=33089, per=99.43%, avg=12636.59, stdev=663.86, samples=256
   iops        : min= 2842, max= 8272, avg=3159.12, stdev=165.96, samples=256
  lat (msec)   : 20=0.44%, 50=3.21%, 100=0.49%, 250=95.86%
  cpu          : usr=0.36%, sys=0.88%, ctx=16203, majf=0, minf=47
  IO depths    : 1=0.1%, 2=0.1%, 4=0.1%, 8=0.1%, 16=0.1%, 32=0.1%, >=64=99.8%
     submit    : 0=0.0%, 4=100.0%, 8=0.0%, 16=0.0%, 32=0.0%, 64=0.0%, >=64=0.0%
     complete  : 0=0.0%, 4=100.0%, 8=0.0%, 16=0.0%, 32=0.0%, 64=0.0%, >=64=0.1%
     issued rwts: total=0,102400,0,0 short=0,0,0,0 dropped=0,0,0,0
     latency   : target=0, window=0, percentile=100.00%, depth=128

Run status group 0 (all jobs):
  WRITE: bw=12.4MiB/s (13.0MB/s), 12.4MiB/s-12.4MiB/s (13.0MB/s-13.0MB/s), io=400MiB (419MB), run=32231-32231msec

Disk stats (read/write):
  xvda: ios=71/101557, merge=1/2271, ticks=948/1935105, in_queue=1936052, util=99.77%
```

> Oracle
```
iotest4k: (g=0): rw=randwrite, bs=(R) 4096B-4096B, (W) 4096B-4096B, (T) 4096B-4096B, ioengine=libaio, iodepth=128
...
fio-3.25
Starting 4 processes
iotest4k: Laying out IO file (1 file / 100MiB)
iotest4k: Laying out IO file (1 file / 100MiB)
iotest4k: Laying out IO file (1 file / 100MiB)
iotest4k: Laying out IO file (1 file / 100MiB)
Jobs: 4 (f=4): [w(4)][100.0%][w=13.3MiB/s][w=3394 IOPS][eta 00m:00s]
iotest4k: (groupid=0, jobs=4): err= 0: pid=1074367: Mon Sep 25 12:42:01 2023
  write: IOPS=3177, BW=12.4MiB/s (13.0MB/s)(400MiB/32231msec); 0 zone resets
    slat (usec): min=4, max=39912, avg=1249.88, stdev=3428.52
    clat (msec): min=10, max=247, avg=159.50, stdev=29.83
     lat (msec): min=10, max=247, avg=160.75, stdev=30.21
    clat percentiles (msec):
     |  1.00th=[   23],  5.00th=[  123], 10.00th=[  146], 20.00th=[  157],
     | 30.00th=[  157], 40.00th=[  163], 50.00th=[  167], 60.00th=[  167],
     | 70.00th=[  167], 80.00th=[  176], 90.00th=[  178], 95.00th=[  186],
     | 99.00th=[  199], 99.50th=[  207], 99.90th=[  222], 99.95th=[  226],
     | 99.99th=[  234]
   bw (  KiB/s): min=11368, max=33089, per=99.43%, avg=12636.59, stdev=663.86, samples=256
   iops        : min= 2842, max= 8272, avg=3159.12, stdev=165.96, samples=256
  lat (msec)   : 20=0.44%, 50=3.21%, 100=0.49%, 250=95.86%
  cpu          : usr=0.36%, sys=0.88%, ctx=16203, majf=0, minf=47
  IO depths    : 1=0.1%, 2=0.1%, 4=0.1%, 8=0.1%, 16=0.1%, 32=0.1%, >=64=99.8%
     submit    : 0=0.0%, 4=100.0%, 8=0.0%, 16=0.0%, 32=0.0%, 64=0.0%, >=64=0.0%
     complete  : 0=0.0%, 4=100.0%, 8=0.0%, 16=0.0%, 32=0.0%, 64=0.0%, >=64=0.1%
     issued rwts: total=0,102400,0,0 short=0,0,0,0 dropped=0,0,0,0
     latency   : target=0, window=0, percentile=100.00%, depth=128

Run status group 0 (all jobs):
  WRITE: bw=12.4MiB/s (13.0MB/s), 12.4MiB/s-12.4MiB/s (13.0MB/s-13.0MB/s), io=400MiB (419MB), run=32231-32231msec

Disk stats (read/write):
  xvda: ios=71/101557, merge=1/2271, ticks=948/1935105, in_queue=1936052, util=99.77%
```
```
iotest4k: (g=0): rw=randwrite, bs=(R) 4096B-4096B, (W) 4096B-4096B, (T) 4096B-4096B, ioengine=libaio, iodepth=128
fio-3.28
Starting 1 process
iotest4k: Laying out IO file (1 file / 100MiB)
Jobs: 1 (f=1): [w(1)][100.0%][w=11.7MiB/s][w=3001 IOPS][eta 00m:00s]
iotest4k: (groupid=0, jobs=1): err= 0: pid=1095434: Mon Sep 25 13:07:33 2023
  write: IOPS=4123, BW=16.1MiB/s (16.9MB/s)(100MiB/6208msec); 0 zone resets
    slat (usec): min=6, max=65397, avg=47.39, stdev=895.44
    clat (usec): min=432, max=228382, avg=30980.78, stdev=43163.43
     lat (usec): min=650, max=228424, avg=31028.51, stdev=43168.93
    clat percentiles (usec):
     |  1.00th=[   734],  5.00th=[   955], 10.00th=[  1549], 20.00th=[  3064],
     | 30.00th=[  4883], 40.00th=[  6980], 50.00th=[  8225], 60.00th=[  9372],
     | 70.00th=[ 18744], 80.00th=[ 68682], 90.00th=[103285], 95.00th=[120062],
     | 99.00th=[162530], 99.50th=[166724], 99.90th=[181404], 99.95th=[217056],
     | 99.99th=[225444]
   bw (  KiB/s): min=11048, max=32336, per=100.00%, avg=16581.42, stdev=7399.94, samples=12
   iops        : min= 2762, max= 8084, avg=4145.17, stdev=1849.95, samples=12
  lat (usec)   : 500=0.01%, 750=1.36%, 1000=4.30%
  lat (msec)   : 2=7.03%, 4=12.75%, 10=36.07%, 20=8.96%, 50=3.06%
  lat (msec)   : 100=14.35%, 250=12.12%
  cpu          : usr=4.01%, sys=19.49%, ctx=5022, majf=0, minf=11
  IO depths    : 1=0.1%, 2=0.1%, 4=0.1%, 8=0.1%, 16=0.1%, 32=0.1%, >=64=99.8%
     submit    : 0=0.0%, 4=100.0%, 8=0.0%, 16=0.0%, 32=0.0%, 64=0.0%, >=64=0.0%
     complete  : 0=0.0%, 4=100.0%, 8=0.0%, 16=0.0%, 32=0.0%, 64=0.0%, >=64=0.1%
     issued rwts: total=0,25600,0,0 short=0,0,0,0 dropped=0,0,0,0
     latency   : target=0, window=0, percentile=100.00%, depth=128

Run status group 0 (all jobs):
  WRITE: bw=16.1MiB/s (16.9MB/s), 16.1MiB/s-16.1MiB/s (16.9MB/s-16.9MB/s), io=100MiB (105MB), run=6208-6208msec

Disk stats (read/write):
  sda: ios=0/25374, merge=0/72, ticks=0/736249, in_queue=736249, util=90.93%
```
