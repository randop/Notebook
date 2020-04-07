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
docker run -it --name phpdev -p 0.0.0.0:8080:8080 -v /c/Users/Rax/devshare/randop:/projects php /bin/sh
```

#### Python
```
docker run --name pydev -it python /bin/bash
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

#### MySQL
```
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql
docker run --name mysqldev -e MYSQL_ROOT_PASSWORD=mysql -e MYSQL_ROOT_PASSWORD=mysql -e MYSQL_DATABASE=bookman -p 0.0.0.0:3306:3306 -d mysql
```

#### Adminer
```
docker run -p 8336:8080 -e ADMINER_DEFAULT_SERVER=192.168.186.192 adminer
```

#### Eventstore
```
docker run --name eventstore-node -it -p 2113:2113 -p 1113:1113 eventstore/eventstore
```

#### Gradle
```
docker volume create --name gradle-cache
docker run -it --name gradledev -p 0.0.0.0:8080:8080 -v gradle-cache:/home/gradle/.gradle -v /c/Users/Rax/devshare/microservices:/microservices gradle /bin/bash
```

#### Redis
```
docker run --name redisdev -p 0.0.0.0:6379:6379 -d redis
docker run -it --rm redis redis-cli -h 192.168.186.192
```

#### Export image
```
docker save -o eventstore.tar eventstore/eventstore
```

#### Load exported image
```
docker load --input evenstore.tar
```