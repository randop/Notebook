```
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
git config --global alias.unstage 'reset HEAD --'
git config --global alias.last 'log -1 HEAD'
git config --global alias.hist "log --pretty=format:'%h %ad | %s%d [%an]' --graph --date=short"
git config --global alias.type 'cat-file -t'
git config --global alias.dump 'cat-file -p'
git config --global alias.mg 'merge --no-ff'
git config --global alias.brdate 'branch --sort=-committerdate'
git config --global alias.mgm 'merge --no-ff --no-commit'
git config --global alias.dfc 'diff-tree --no-commit-id --name-only -r'
git config --global alias.au '!git ls-files -v | grep "^[[:lower:]]"'
```