#!/usr/bin/env sh

set -e  # Exit on error

echo -n "Enter starting sequence: "
read MY_VAR
expr $MY_VAR + 0

echo "New sequence: $MY_VAR"
