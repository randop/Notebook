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
***
#### Merging video and audio, with audio re-encoding
http://crazedmuleproductions.blogspot.com/2005/12/using-ffmpeg-to-combine-audio-and.html
```
ffmpeg -i video.mp4 -i audio.wav -c:v copy -c:a aac output.mp4
```