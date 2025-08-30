#!/bin/sh

# Check cryptographic algorithms supported
ssh -Q kex

# OpenSSH supports RSA keys up to 16384 bits,
# larger sizes are rarely used due to performance overhead,
# but this is the maximum practical size for RSA in most implementations
ssh-keygen -t rsa -b 16384 -f ~/.ssh/id_rsa -C "user@email"
