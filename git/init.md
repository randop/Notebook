#### Initial configurations
```
ssh-keygen -t rsa -b 4096 -C "randop@me.com"
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa
git config --global user.name "Randolph"
git config --global user.email "randop@me.com"
```

#### Run ssh-agent during system start-up
```
systemctl --user enable ssh-agent
```