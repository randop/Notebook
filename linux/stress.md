```bash
linux@server:~$ stress-ng --matrix -1 --tz -t 60
stress-ng: info:  [21466] setting to a 60 second run per stressor
stress-ng: info:  [21466] dispatching hogs: 24 matrix
stress-ng: info:  [21466] successful run completed in 60.01s (1 min, 0.01 secs)
stress-ng: info:  [21466] matrix:
stress-ng: info:  [21466]         x86_pkg_temp0   57.38 C (330.52 K)
stress-ng: info:  [21466]         x86_pkg_temp1   54.27 C (327.42 K)
linux@server:~$ stress-ng --matrix -1 --tz -t 300
stress-ng: info:  [23488] setting to a 300 second (5 mins, 0.00 secs) run per stressor
stress-ng: info:  [23488] dispatching hogs: 24 matrix
stress-ng: info:  [23488] successful run completed in 300.01s (5 mins, 0.01 secs)
stress-ng: info:  [23488] matrix:
stress-ng: info:  [23488]         x86_pkg_temp0   59.33 C (332.48 K)
stress-ng: info:  [23488]         x86_pkg_temp1   56.29 C (329.44 K)
```
