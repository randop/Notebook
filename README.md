<h1 align="center">Welcome to my Notebook 👋</h1>
<p>
  <img src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
  <a href="https://gitlab.com/randop/notebook">
    <img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" target="_blank" />
  </a>
  <a href="https://gitlab.com/randop/notebook/-/commits/main">
    <img alt="Maintenance" src="https://img.shields.io/badge/Maintained%3F-yes-green.svg" target="_blank" />
  </a>
</p>

### 🏠 [Homepage](https://gitlab.com/randop/notebook)
#### The repository of my notes on various topics and interest. This is for learning and sharing purposes.

## Author

👤 **Randolph Ledesma**

* 📱 +1 (415) 754-3092
* 🌐 [https://linktr.ee/randolphledesma](https://linktr.ee/randolphledesma)
* 🌐 [https://gitlab.com/randop](https://gitlab.com/randop)
* 👷 [https://www.linkedin.com/in/randop/](https://www.linkedin.com/in/randop/)
* 📍 🇵🇭 Philippines

## Table of contents
* [Types of screws, screw heads, washers and nuts](#types-of-screws-screw-heads-washers-and-nuts)
* [Hex screws sizes](#hex-screws-sizes)
* [PC M3 screw set specifications](#personal-computer-pc-m3-screw-set-specifications)
* [How to code using NASM assembler](#how-to-code-using-nasm-assembler-assembly-programming-language)
* [How to save the pricess using 8 programming languages](#how-to-save-the-pricess-using-8-programming-languages)
* [License](#-license)

### Types of screws, screw heads, washers and nuts
![Picture of Types of screws, screw heads, washers and nuts](https://gitlab.com/randop/notebook/-/raw/main/assets/types-of-screws-screw-heads-washers-and-nuts.jpg?inline=true)

### How to read a metric screw
![Picture of How to read a metric screw](https://gitlab.com/randop/notebook/-/raw/main/assets/How-to-Read-a-Metric-Screw-Thread-Callout.png?inline=true)

### Hex screws sizes
![Picture of hex screws sizes](https://gitlab.com/randop/notebook/-/raw/main/assets/hex-screws-sizes.jpg?inline=true)

### Personal Computer (PC) M3 screw set specifications
![Picture of Personal Computer (PC) M3 screw set specifications](https://gitlab.com/randop/notebook/-/raw/main/assets/pc-m3-screws.png?inline=true)
---
## How to code using NASM assembler (Assembly programming language)
```asm
; Copyright© 1998—2025 Randolph Ledesma (randop at me.com)
; $ nasm -g -f elf64 hello.asm
; $ ld -o hello hello.o
global _start

section .data
        str: db "Hello World, Randolph Ledesma", 0xA
        STRSIZE: equ $ - str
        STDOUT: equ 1

section .text
_start:
        mov rax, 1
        mov rdi, STDOUT
        mov rsi, str
        mov rdx, STRSIZE
        syscall

        mov rax, 60
        syscall

```
#### Linux NASM
```bash
nasm -g -f elf64 hello.asm
ld -o hello hello.o

nasm -g -f elf64 demo.asm
ld -o demo demo.o
gcc -o [-no-pie] demo demo.o -v
```

#### NVIDIA System Management Interface -- v525.60.11
```bash
user@linux:~/Downloads$ nvidia-smi
Mon Apr 10 12:15:48 2023
+-----------------------------------------------------------------------------+
| NVIDIA-SMI 525.60.11    Driver Version: 525.60.11    CUDA Version: 12.0     |
|-------------------------------+----------------------+----------------------+
| GPU  Name        Persistence-M| Bus-Id        Disp.A | Volatile Uncorr. ECC |
| Fan  Temp  Perf  Pwr:Usage/Cap|         Memory-Usage | GPU-Util  Compute M. |
|                               |                      |               MIG M. |
|===============================+======================+======================|
|   0  Quadro P400         Off  | 00000000:02:00.0  On |                  N/A |
| 34%   42C    P8    N/A /  N/A |    753MiB /  2048MiB |     31%      Default |
|                               |                      |                  N/A |
+-------------------------------+----------------------+----------------------+

+-----------------------------------------------------------------------------+
| Processes:                                                                  |
|  GPU   GI   CI        PID   Type   Process name                  GPU Memory |
|        ID   ID                                                   Usage      |
|=============================================================================|
|    0   N/A  N/A      1266      G   /usr/lib/xorg/Xorg                308MiB |
|    0   N/A  N/A      1530      G   /usr/bin/kwin_x11                  73MiB |
|    0   N/A  N/A      1599      G   /usr/bin/plasmashell               36MiB |
|    0   N/A  N/A      8851      G   /app/lib/firefox/firefox          164MiB |
|    0   N/A  N/A     48822      G   ...in,WebAssemblyTrapHandler       94MiB |
|    0   N/A  N/A     81537      G   ...in,WebAssemblyTrapHandler       68MiB |
+-----------------------------------------------------------------------------+
```

#### Curl
##### View contents of remote file
```bash
curl -1sLf 'https://gitlab.com/randop/notebook/-/raw/main/README.md?inline=false'
```

#### Downloading using aria2
```bash
aria2c --continue=true \
    --max-concurrent-downloads=1 \
    --max-connection-per-server=12 \
    --file-allocation=none \
    --input-file=debian-iso-dvd.txt
```

#### Download a 1080p youtube video as mp4 format
```sh
yt-dlp --merge-output-format mp4 -f "bestvideo[height=1080]+bestaudio/best" "https://www.youtube.com/watch?v=EWvNQjAaOHw"
yt-dlp --merge-output-format mp4 -f "bestvideo[height=1080]+bestaudio/best" "https://www.youtube.com/watch?v=T75MME5a9zA"
yt-dlp --merge-output-format mp4 -f "bestvideo[height=1080]+bestaudio/best" "https://www.youtube.com/watch?v=LPZh9BOjkQs"
yt-dlp --merge-output-format mp4 -f "bestvideo[height=1080]+bestaudio/best" "https://www.youtube.com/watch?v=PaCmpygFfXo"
yt-dlp --merge-output-format mp4 -f "bestvideo[height=1080]+bestaudio/best" "https://www.youtube.com/watch?v=kCc8FmEb1nY"
yt-dlp --merge-output-format mp4 -f "bestvideo[height=1080]+bestaudio/best" "https://www.youtube.com/watch?v=7xTGNNLPyMI"
yt-dlp --merge-output-format mp4 -f "bestvideo[height=1080]+bestaudio/best" "https://www.youtube.com/watch?v=zjkBMFhNj_g"
yt-dlp --merge-output-format mp4 -f "bestvideo[height=1080]+bestaudio/best" "https://www.youtube.com/watch?v=aircAruvnKk"
```

## Git 
![Git the princess by Mart Virkus, Toggl Goon Squad](https://gitlab.com/randop/notebook/-/raw/main/assets/strip_01_title.jpg?inline=true)
![Javascript](https://gitlab.com/randop/notebook/-/raw/main/assets/strip_02_javascript.jpg?inline=true)
![C](https://gitlab.com/randop/notebook/-/raw/main/assets/strip_03_c.jpg?inline=true)
![C#](https://gitlab.com/randop/notebook/-/raw/main/assets/strip_04_csharp.jpg?inline=true)
![Java](https://gitlab.com/randop/notebook/-/raw/main/assets/strip_05_java.jpg?inline=true)
![Lisp](https://gitlab.com/randop/notebook/-/raw/main/assets/strip_06_lisp.jpg?inline=true)
![Go](https://gitlab.com/randop/notebook/-/raw/main/assets/strip_07_go.jpg?inline=true)
![Pascal](https://gitlab.com/randop/notebook/-/raw/main/assets/strip_08_pascal.jpg?inline=true)
![Php](https://gitlab.com/randop/notebook/-/raw/main/assets/strip_09_php.jpg?inline=true)

---
## 📝 License

Copyright © 2010 — 2026 [Randolph Ledesma](https://gitlab.com/randop).

Last updated on 2026-05-19T05:54:11Z
