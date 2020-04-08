#!/bin/sh
docker images --format "{{.Repository}}" |
while read image
do
    echo "Saving $image""..."
    docker save -o $(echo "$image"".tar" | sed -e 's/[^A-Za-z0-9._-]/_/g') $image
done
echo "Docker images backed up :)\n"