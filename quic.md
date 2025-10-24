# QUIC

### Testing quic http3 get request
```sh
curl --http3-only -k --verbose https://quic.server-cloud.com:8888/index.html
```

> Command output:
```
* Host quic.server-cloud.com:8888 was resolved.
* IPv6: (none)
* IPv4: 127.0.0.1
*   Trying 127.0.0.1:8888...
* Server certificate:
*  subject: C=US; ST=State; L=City; O=Organization; CN=quic.server-cloud.com
*  start date: Oct 24 10:58:37 2025 GMT
*  expire date: Oct 24 10:58:37 2026 GMT
*  issuer: C=US; ST=State; L=City; O=Organization; CN=quic.server-cloud.com
*  SSL certificate verify result: self-signed certificate (18), continuing anyway.
*   Certificate level 0: Public key type RSA (4096/152 Bits/secBits), signed using sha256WithRSAEncryption
* Established connection to quic.server-cloud.com (127.0.0.1 port 8888) from 192.168.100.5 port 47112
* using HTTP/3
* [HTTP/3] [0] OPENED stream for https://quic.server-cloud.com:8888/index.html
* [HTTP/3] [0] [:method: GET]
* [HTTP/3] [0] [:scheme: https]
* [HTTP/3] [0] [:authority: quic.server-cloud.com:8888]
* [HTTP/3] [0] [:path: /index.html]
* [HTTP/3] [0] [user-agent: curl/8.16.0]
* [HTTP/3] [0] [accept: */*]
> GET /index.html HTTP/3
> Host: quic.server-cloud.com:8888
> User-Agent: curl/8.16.0
> Accept: */*
>
* Request completely sent off
< HTTP/3 200
< content-type: text/html
<
Hello Randolph
* Connection #0 to host quic.server-cloud.com:8888 left intact
```
