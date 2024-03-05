## Segfaults 
> OS: Clear Linux
> Java: 19
> When doing export LD_PRELOAD="/usr/lib/x86_64-linux-gnu/libjemalloc.so"
```
[31777.661666] java[26606]: segfault at 7ffcdffecff0 ip 00007f2021bd600e sp 00007ffcdffecfd0 error 6 cpu 48 in ld-2.39.so[7f2021bc3000+29000] likely on CPU 48 (core 0, socket 1)
[31777.661682] Code: 5d ff 25 2d 2a 02 00 0f 1f 44 00 00 48 83 c4 08 5b 5d 41 5c 41 5d c3 0f 1f 44 00 00 41 57 41 56 41 55 41 54 55 53 48 83 ec 38 <48> 89 7c 24 20 64 4c 8b 04 25 08 00 00 00 49 39 30 0f 83 61 01 00
```
