# GPG

```
ssh-keygen -t ed25519 -C "randolph+gitlab@hotmail.com"
gpg --full-gen-key
gpg --list-secret-keys --keyid-format LONG randolph+gitlab@hotmail.com
gpg --armor --export B3D30DE0F4D86779
git config --global user.signingkey B3D30DE0F4D86779
git config --global commit.gpgsign true
git config --global user.email randolph+gitlab@hotmail.com
git config --global user.name Randolph
ssh -T git@gitlab.com
chmod 400 ~/.ssh/config
```
