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

#### Write a new event at EventStore
```
curl -i -d '{"data":"Randolph"}' "http://192.168.186.192:2113/streams/test" -H "Content-Type:application/json" -H "ES-EventType: Programmed" -H "ES-EventId: FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF" -u admin:changeit
```

#### Convert media to mp3
https://opensource.com/article/17/6/ffmpeg-convert-media-file-formats
https://stackoverflow.com/questions/3255674/convert-audio-files-to-mp3-using-ffmpeg
```
ffmpeg -i audiowebm -acodec libmp3lame -b:a 160k audio.mp3
```

#### To permanently disable snap packages:
```
sudo systemctl stop snapd.service
sudo systemctl disable snapd.service
```

#### To reenable snap packages:
```
sudo systemctl reenable snapd.service
sudo systemctl start snapd.service
```

#### Convert audio to mp3 and slice by specified duration
```
ffmpeg -i Geto_Boys_-_Damn_It_Feels_Good_To_Be_A_Gangsta_Live_Looping_--_THROWBACK_THURSDAY_Week_30_audio_only.m4a -ss 00:00:38 -to 00:07:17 -acodec libmp3lame -b:a 160k Geto_Boys_-_Damn_It_Feels_Good_To_Be_A_Gangsta_Live.mp3
```

#### Download mp3 from YT
```
youtube-dl -f best -o '%(title)s.%(ext)s' --restrict-filenames -x --audio-format mp3 https://www.youtube.com/playlist?list=PLL4itf8rGtj5lwP2jQ_yuQFhDlY6q_bg8
```

#### Convert gif to mp4
https://unix.stackexchange.com/questions/40638/how-to-do-i-convert-an-animated-gif-to-an-mp4-or-mv4-on-the-command-line
```
ffmpeg -i animated.gif -movflags faststart -pix_fmt yuv420p -vf "scale=trunc(iw/2)*2:trunc(ih/2)*2" video.mp4
```

#### Test iOS Push notifications
```
curl --http2 --cert ./myapp-push-cert.pem \
-H "apns-topic: com.example.yourapp.bundleID" \
-d '{"aps":{"alert":"Hello from APNs!","sound":"default"}}' \
https://api.development.push.apple.com/3/device/ab8293ad24537c838539ba23457183bfed334193518edf258385266422013ac0d
```

### Download playlist from YT
```
youtube-dl -f best -o '%(title)s.%(ext)s' --yes-playlist --restrict-filenames "https://www.youtube.com/watch?v=oM6rec6pB8Q&list=PLBn46jL5tTbakD9loiOlSpB1uljRkB1UW"
```
