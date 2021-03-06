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
docker run --name esdev -it -p 2113:2113 -p 1113:1113 -d eventstore/eventstore
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

#### Minio
```
docker run -p 0.0.0.0:9000:9000 minio/minio server /data
docker volume create --name minio
docker run -it --rm -v minio:/root/.mc minio/mc config host add rfl http://192.168.186.192:9000 minioadmin minioadmin
docker run -it --rm -v minio:/root/.mc minio/mc config host ls rfl
docker run -it --rm -v minio:/root/.mc minio/mc mb rfl/mailbox
```

#### Export image
```
docker save -o eventstore.tar eventstore/eventstore
```

#### Load exported image
```
docker load --input evenstore.tar
```

#### Elasticsearch
```
docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.6.2
curl -X GET "192.168.186.192:9200/_cat/nodes?v&pretty"
```
#### Elasticsearch (daemon mode)
```
docker run --name elasticsearch -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.6.2
```

#### Syncthing
```
docker run --name sync -d -p 8384:8384 -p 22000:22000 -v /datapool:/var/syncthing syncthing/syncthing:latest
docker run --name sync -d -p 8384:8384 -p 22000:22000 -v /c/Users/Rax/datapool:/var/syncthing syncthing/syncthing:latest
```