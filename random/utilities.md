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