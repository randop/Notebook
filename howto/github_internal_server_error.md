# Work around to fix Github Internal Server Error when trying to push changes
### ERROR
```
! [remote rejected] (Internal Server Error)
error: failed to push some refs to 'github.com:randop/project.git'
```
### SOLUTION
```
git for-each-ref --format="%(refname)" --sort='authordate' refs/replace | xargs git push origin
```