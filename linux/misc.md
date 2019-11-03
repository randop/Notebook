#### Copy files on background process
+ recursive
+ verbose
+ preserve all (mode, ownership, timestamps, context, links, xattr, etc.)
+ log progress and results
+ confirm overwrite silently
```
yes | nohup cp -Rfav /home/sites/* /home/backup >> /tmp/cplog.log 2>&1&
```
***
#### Git: Adding your SSH key to the ssh-agent
```
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa
```
***
#### Find in content of PHP files
+ exclude vendor folder
```
find . -type f -name '*.php' -not -path "*/vendor/*" | xargs egrep -n -i "(fwrite|fputs|fputcsv) *\("
```
***