# Docker

## Restart the Docker daemon
```bash
sudo pkill -SIGHUP dockerd
```

## Check current disk usage
```bash
sudo docker system df

# TYPE            TOTAL     ACTIVE    SIZE      RECLAIMABLE
# Images          0         0         0B        0B
# Containers      0         0         0B        0B
# Local Volumes   0         0         0B        0B
# Build Cache     0         0         0B        0B
```

## Remove unused Docker objects to free up disk space
```bash
# !!!WARNING!!! This will remove:
#  - all stopped containers
#  - all networks not used by at least one container
#  - all dangling images
#  - unused build cache
sudo docker system prune -a --volumes -f
```

## Remove all unused and unused named volumes
```bash
sudo docker volume prune -a -f
```
