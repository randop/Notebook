# fix email

## install package
```bash
pacman -S git git-filter-repo
```

## apply fix and sign commits
```bash
# replace with accurate email
git filter-repo --email-callback 'return b"new@email.com" if email == b"old@email.com" else email' --force
git rebase --root --rebase-merges --exec 'git commit --amend --no-edit -S'
# check signature
git log -1 --show-signature
git push --force
```
