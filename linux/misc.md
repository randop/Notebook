<!---
Copy files on background process
+ log progress and results
+ confirm overwrite silently
--->
```
yes | nohup cp -Rfav /home/sites/* /home/backup >> /tmp/cplog.log 2>&1&
```