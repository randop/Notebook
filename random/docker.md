### My Docker Machine
```
docker-machine env defaultdev
```
> IP: 192.168.186.189
***
#### google appengine
```
docker run --name gaedev -ti -v $(pwd):/project -p 0.0.0.0:9191:8080 gae:latest /bin/bash
```

#### Nodejs
```
docker run --name nodedev -ti -v /c/Users/Rax/devshare/:/devshare -p 8080:8080 node:latest /bin/bash
```

#### Jekyll
```
docker run -it --name jekyll -p 0.0.0.0:9000:9000 -v /c/Users/Rax/devshare/sites:/var/www/html ruby:2.3.7-alpine3.7 /bin/sh
```

#### PHP
```
docker run -it --name php7dev -p 0.0.0.0:9000:9000 -v /c/Users/Rax/devshare/randop:/var/www/html php:7-alpine /bin/sh
```

#### Sonic
```
docker run -p 1491:1491 -v /c/Users/Rax/devshare/sonic/config.cfg:/etc/sonic.cfg -v /c/Users/Rax/devshare/sonic/db/:/var/lib/sonic/store/ valeriansaliou/sonic:v1.2.3
```

#### Mailcatcher
```
docker run -d -p 1080:1080 -p 1025:1025 --name mailcatcher schickling/mailcatcher
docker run -d -p 1081:1080 -p 1026:1025 --name mailcatcher2 schickling/mailcatcher
```