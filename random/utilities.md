#### Ngrok
```
ngrok http -host-header=rewrite 192.168.186.192:8080
```

#### Swift
```
export PATH=/home/ubuntu/swift-5.1.4-RELEASE-ubuntu18.04/usr/bin:"${PATH}"
yes | nohup swift run Run --hostname 0.0.0.0 >> /home/ubuntu/bookmark.log 2>&1&
```

#### Iptables
```
iptables -I INPUT 5 -i ens3 -p tcp --dport 8080 -m state --state NEW,ESTABLISHED -j ACCEPT
```

#### Batch process convert mp4 to mp3
```
for i in *.mp4; do ffmpeg -i "$i" -b:a 192K -vn "${i%.*}.mp3"; done
```

#### Check package version (Ubuntu)
```
apt-cache policy redis-server
```

#### How to force kill process in Linux
Use pidof command to find the process ID of a running program or app
```
pidoff appname
```
To kill process in Linux with PID:
```
kill -9 pid
```
To kill process in Linux with application name:
```
killall -9 appname
```