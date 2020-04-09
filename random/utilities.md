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

#### Pretty-print JSON in Python
```
python -m json.tool < file.json
```

#### Allow SSH in firewall (iptables)
```
sudo iptables -A INPUT -p tcp --dport 22 -m conntrack --ctstate NEW,ESTABLISHED -j ACCEPT
```

#### FRPS
```
sudo iptables -I INPUT 5 -i ens3 -p tcp --dport 8888 -m state --state NEW,ESTABLISHED -j ACCEPT
sudo iptables -I INPUT 5 -i ens3 -p tcp --dport 7777 -m state --state NEW,ESTABLISHED -j ACCEPT
sudo iptables -I INPUT 5 -i ens3 -p udp --dport 7777 -m state --state NEW,ESTABLISHED -j ACCEPT
sudo iptables -I INPUT 5 -i ens3 -p tcp --dport 7474 -m state --state NEW,ESTABLISHED -j ACCEPT
sudo iptables -I INPUT 5 -i ens3 -p udp --dport 7474 -m state --state NEW,ESTABLISHED -j ACCEPT
sudo iptables -I INPUT 5 -i ens3 -p tcp --dport 8484 -m state --state NEW,ESTABLISHED -j ACCEPT
nohup /home/ubuntu/frp/frps -c /home/ubuntu/frp/frps.ini >> /home/ubuntu/frps.log 2>&1&
```

#### Who is using the port?
```
lsof -i tcp:3000 
```

#### Run ShadowFatJar (vertx)
```
java -jar gateway-1.0.0-SNAPSHOT-fat.jar run com.example.gateway.Server
```