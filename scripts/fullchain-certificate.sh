#!/bin/sh

# 1. Create Root CA
openssl genpkey -algorithm RSA -out rootCA.key -pkeyopt rsa_keygen_bits:4096
openssl req -x509 -new -nodes -key rootCA.key -days 3650 -out rootCA.crt \
  -subj "/C=PH/O=Dummy Root CA/CN=Root CA"

# 2. Create Intermediate CA
openssl genpkey -algorithm RSA -out intermediate.key -pkeyopt rsa_keygen_bits:2048
openssl req -new -key intermediate.key -out intermediate.csr \
  -subj "/C=PH/O=Dummy Org/CN=Intermediate CA"
openssl x509 -req -in intermediate.csr -CA rootCA.crt -CAkey rootCA.key \
  -CAcreateserial -out intermediate.crt -days 1825 -sha256 \
  -extfile <(echo "basicConstraints=critical,CA:TRUE")

# 3. Create Leaf/Server Certificate
openssl genpkey -algorithm RSA -out privkey.pem -pkeyopt rsa_keygen_bits:2048
openssl req -new -key privkey.pem -out server.csr \
  -subj "/C=PH/ST=National Capital Region/L=Quezon City/O=Dummy Org/CN=example.com" \
  -addext "subjectAltName = DNS:example.com,DNS:www.example.com"

openssl x509 -req -in server.csr -CA intermediate.crt -CAkey intermediate.key \
  -CAcreateserial -out server.crt -days 365 -sha256 \
  -extfile <(echo "subjectAltName = DNS:example.com,DNS:www.example.com")

# 4. Create fullchain.pem (Leaf + Intermediate)
cat server.crt intermediate.crt > fullchain.pem
