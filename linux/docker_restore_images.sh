#!/bin/sh
ls *.tar |
while read image
do
    echo "Restoring $image""..."
    docker load --input $image
done
echo "Docker images restored :)\n"