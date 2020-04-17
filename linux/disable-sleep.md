# To prevent computer from sleeping or hibernating when inactive
```
sudo systemctl mask sleep.target suspend.target hibernate.target hybrid-sleep.target
```

## To enable sleep and hibernate
```
sudo systemctl unmask sleep.target suspend.target hibernate.target hybrid-sleep.target
```