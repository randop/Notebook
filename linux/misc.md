#### Copy files on background process
+ recursive
+ verbose
+ preserve all (mode, ownership, timestamps, context, links, xattr, etc.)
+ log progress and results
+ confirm overwrite silently
```
yes | nohup cp -Rfav /home/sites/* /home/backup >> /tmp/cplog.log 2>&1&
```