# IP v4

IPv4 uses 32-bit addresses, so the total address space is exactly \(2^{32} = 4{,}294{,}967{,}296\).

The private (RFC 1918) ranges are:
- `10.0.0.0/8` → 16,777,216 addresses  
- `172.16.0.0/12` → 1,048,576 addresses  
- `192.168.0.0/16` → 65,536 addresses  

**Total private addresses** = 17,891,328  

**Non-private addresses** = \(4{,}294{,}967{,}296 - 17{,}891{,}328 = 4{,}277{,}075{,}968\)

This count includes all other reserved/special-purpose ranges (loopback `127.0.0.0/8`, link-local `169.254.0.0/16`, multicast `224.0.0.0/4`, future-use `240.0.0.0/4`, documentation blocks, CGNAT shared space `100.64.0.0/10`, etc.).  

*Publicly routable / globally unique* addresses (excluding *all* special-purpose ranges) is roughly **3.7 billion**.
