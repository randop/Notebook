#### Copy files on background process
+ recursive
+ verbose
+ preserve all
+ log progress and results
+ confirm overwrite silently
```
yes | nohup cp -Rfav /home/sites/* /home/backup >> /tmp/cplog.log 2>&1&
```