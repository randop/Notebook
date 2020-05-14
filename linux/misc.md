#### Copy files on background process
+ recursive
+ verbose
+ preserve all (mode, ownership, timestamps, context, links, xattr, etc.)
+ log progress and results
+ confirm overwrite silently
```
yes | nohup cp -Rfav /home/sites/* /home/backup >> /tmp/cplog.log 2>&1&
```
***
#### Git: Adding your SSH key to the ssh-agent
```
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa
```
***
#### Find in content of PHP files
+ exclude vendor folder
```
find . -type f -name '*.php' -not -path "*/vendor/*" | xargs egrep -n -i "(fwrite|fputs|fputcsv) *\("
```
***
#### Lint PHP files that are staged on git
```
( ( (git diff --name-only origin/master $GIT_COMMIT ) | grep .php$ ) | xargs -n1 echo php -l | bash ) | grep -v "No syntax errors detected" && echo "PHP Syntax error(s) detected"
```
***
#### Mount VMWare shared filesystem
```
sudo vmhgfs-fuse .host:/ /mnt/hgfs/ -o allow_other -o uid=1000
```
***
#### Discover IP address
```
ip a
```
***
#### Check disk caching
[https://eventstore.org/blog/20131218/disabling-disk-caching-in-ubuntu/](https://eventstore.org/blog/20131218/disabling-disk-caching-in-ubuntu/)
```
sudo hdparm -i /dev/sda
```
```
/dev/sda:

 Model=WDC WD10SPZX-24Z10T0, FwRev=01.01A01, SerialNo=WD-WX21A387T0E2
 Config={ HardSect NotMFM HdSw>15uSec SpinMotCtl Fixed DTR>5Mbs FmtGapReq }
 RawCHS=16383/16/63, TrkSize=0, SectSize=0, ECCbytes=0
 BuffType=unknown, BuffSize=unknown, MaxMultSect=16, MultSect=16
 (maybe): CurCHS=16383/16/63, CurSects=16514064, LBA=yes, LBAsects=1953525168
 IORDY=on/off, tPIO={min:120,w/IORDY:120}, tDMA={min:120,rec:120}
 PIO modes:  pio0 pio3 pio4 
 DMA modes:  mdma0 mdma1 mdma2 
 UDMA modes: udma0 udma1 udma2 udma3 udma4 udma5 *udma6 
 AdvancedPM=yes: unknown setting WriteCache=enabled
 Drive conforms to: unknown:  ATA/ATAPI-1,2,3,4,5,6,7

 * signifies the current active mode
```
***
#### Merging video and audio, with audio re-encoding
http://crazedmuleproductions.blogspot.com/2005/12/using-ffmpeg-to-combine-audio-and.html
```
ffmpeg -i video.mp4 -i audio.wav -c:v copy -c:a aac output.mp4
```
***
#### Convert and merge 4K HDR to H.264 video
```
ffmpeg -i video.webm -i audio.m4a -crf 0 -c:a aac output.mp4
ffmpeg -i video.webm -i audio.m4a -c:v libx265 -tag:v hvc1 -crf 22 -x265-params "level=5.2:colorprim=bt2020:colormatrix=bt2020nc:transfer=smpte2084" -c:a aac output.mkv
```