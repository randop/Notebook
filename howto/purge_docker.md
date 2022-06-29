# How to completely uninstall (purge) Docker on Ubuntu or Debian
```
sudo apt-get purge -y docker-engine docker docker.io docker-ce  
sudo apt-get autoremove -y --purge docker-engine docker docker.io docker-ce  
sudo umount /var/lib/docker/
sudo rm -rf /var/lib/docker /etc/docker
# NOTE: /etc/apparmor.d/docker is not always present
sudo rm /etc/apparmor.d/docker
sudo groupdel docker
sudo rm -rf /var/run/docker.sock
sudo rm -rf /usr/bin/docker-compose
```