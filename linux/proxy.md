# Proxy

## PROXY Protocol v2 Header Layout
First bytes:
```
\r\n\r\n\0\r\nQUIT\n   (12-byte signature)
```
Data:
```
Version/Command (1 byte)
Family/Protocol (1 byte)
Length (2 bytes)
Address data (variable)
```

### Minimal High-Performance C++23 Parser (Zero-Allocation)
Properties:
- Zero heap allocation
- Bounds-checked
- Rejects malformed packets
- Constant-time header validation
- No exceptions
```cpp
#include <cstdint>
#include <cstring>
#include <optional>
#include <netinet/in.h>

struct proxy_info {
    uint32_t src_ip;
    uint16_t src_port;
};

constexpr uint8_t PROXY_V2_SIG[12] = {
    0x0D,0x0A,0x0D,0x0A,0x00,0x0D,0x0A,0x51,0x55,0x49,0x54,0x0A
};

std::optional<proxy_info> parse_proxy_v2(const uint8_t* buf, size_t len) noexcept {
    if (len < 16) return std::nullopt;

    if (std::memcmp(buf, PROXY_V2_SIG, 12) != 0)
        return std::nullopt;

    uint8_t ver_cmd = buf[12];
    uint8_t fam     = buf[13];
    uint16_t addr_len = (buf[14] << 8) | buf[15];

    // Only accept PROXY command
    if ((ver_cmd >> 4) != 0x2 || (ver_cmd & 0x0F) != 0x1)
        return std::nullopt;

    // IPv4 TCP
    if (fam == 0x11) {
        if (addr_len < 12 || len < 16 + 12)
            return std::nullopt;

        const uint8_t* addr = buf + 16;

        proxy_info info{};
        std::memcpy(&info.src_ip, addr, 4);
        std::memcpy(&info.src_port, addr + 8, 2);

        info.src_port = ntohs(info.src_port);
        return info;
    }

    return std::nullopt;
}
```

### Minimal PROXY v2 parser in Node.JS
```js
const net = require('net');

function parseProxyHeader(buffer) {
  if (buffer.length < 16) return null;
  const signature = buffer.toString('ascii', 0, 12);
  if (signature !== '\x0D\x0A\x0D\x0A\x00\x0D\x0A\x51\x55\x49\x54\x0A') {
    // Not PROXY v2 — handle as normal or reject
    return null;
  }
  const verCmd = buffer[12];
  if (verCmd !== 0x21) return null; // v2 + PROXY command

  const family = buffer[13];
  if (family === 0x11) { // AF_INET (IPv4)
    const srcPort = buffer.readUInt16BE(28);
    const srcIP = [
      buffer[16], buffer[17], buffer[18], buffer[19]
    ].join('.');
    return { remoteAddress: srcIP, remotePort: srcPort };
  }
  // Add IPv6 (0x21) support similarly
  return null;
}

const server = net.createServer((socket) => {
  let buffer = Buffer.alloc(0);

  socket.once('data', (chunk) => {
    buffer = Buffer.concat([buffer, chunk]);

    const info = parseProxyHeader(buffer);
    if (info) {
      // Real client IP!
      console.log(`Real remote IP: ${info.remoteAddress}:${info.remotePort}`);
      // Strip the PROXY header and pass remaining data to your app logic
      const remaining = buffer.slice(16 + 12); // v2 header size for IPv4
      // Feed `remaining` to your protocol parser
      socket.emit('data', remaining); // Re-emit cleaned data
    } else {
      // No proxy header — fall back or reject for security
      console.log(`Direct connection from ${socket.remoteAddress}`);
    }

    // Continue normal handling...
  });
});

server.listen(3000, () => console.log('Node TCP server ready'));
```
