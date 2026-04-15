#!/bin/sh

# RFC 1870 - SIZE
wget --header "Accept: text/plain" --header "Accept-Encoding: identity" \
  --no-http-keep-alive \
  https://www.rfc-editor.org/rfc/rfc1870.txt -O rfc1870.txt
sleep 3

# RFC 6152 - 8BITMIME
wget --header "Accept: text/plain" --header "Accept-Encoding: identity" \
  --no-http-keep-alive \
  https://www.rfc-editor.org/rfc/rfc6152.txt -O rfc6152.txt
sleep 3

# RFC 4954 - AUTH
wget --header "Accept: text/plain" --header "Accept-Encoding: identity" \
  --no-http-keep-alive \
  https://www.rfc-editor.org/rfc/rfc4954.txt -O rfc4954.txt
sleep 3

# RFC 3207 - STARTTLS
wget --header "Accept: text/plain" --header "Accept-Encoding: identity" \
  --no-http-keep-alive \
  https://www.rfc-editor.org/rfc/rfc3207.txt -O rfc3207.txt
sleep 3

# RFC 3461 - DSN
wget --header "Accept: text/plain" --header "Accept-Encoding: identity" \
  --no-http-keep-alive \
  https://www.rfc-editor.org/rfc/rfc3461.txt -O rfc3461.txt
sleep 3

# RFC 3463 - ENHANCEDSTATUSCODES
wget --header "Accept: text/plain" --header "Accept-Encoding: identity" \
  --no-http-keep-alive \
  https://www.rfc-editor.org/rfc/rfc3463.txt -O rfc3463.txt
sleep 3

# RFC 2920 - PIPELINING
wget --header "Accept: text/plain" --header "Accept-Encoding: identity" \
  --no-http-keep-alive \
  https://www.rfc-editor.org/rfc/rfc2920.txt -O rfc2920.txt
sleep 3

# RFC 3030 - CHUNKING + BINARYMIME
wget --header "Accept: text/plain" --header "Accept-Encoding: identity" \
  --no-http-keep-alive \
  https://www.rfc-editor.org/rfc/rfc3030.txt -O rfc3030.txt
sleep 3

# RFC 6531 - SMTPUTF8
wget --header "Accept: text/plain" --header "Accept-Encoding: identity" \
  --no-http-keep-alive \
  https://www.rfc-editor.org/rfc/rfc6531.txt -O rfc6531.txt
sleep 3

# RFC 9422 - LIMITS
wget --header "Accept: text/plain" --header "Accept-Encoding: identity" \
  --no-http-keep-alive \
  https://www.rfc-editor.org/rfc/rfc9422.txt -O rfc9422.txt
sleep 3
