# HAproxy

### Protecting HAProxy from DDoS Attacks

HAProxy, as a high-performance load balancer and proxy, is well-suited to mitigate DDoS attacks at both Layer 4 (transport) and Layer 7 (application) levels. It can protect your backend services from floods while also hardening itself against abuse through built-in features like rate limiting, connection tracking, ACLs (Access Control Lists), and timeouts. These defenses help absorb and filter malicious traffic early, preventing resource exhaustion on HAProxy or downstream servers. Below, I'll outline key strategies, best practices, and configuration examples based on established guides. Note that while HAProxy Community Edition supports core features, advanced modules (e.g., for bot detection) are available in HAProxy Enterprise.

#### 1. **Implement Rate Limiting with Stick Tables**
Stick tables are in-memory counters that track client behavior (e.g., requests per IP over time). They enable dynamic rate limiting to throttle or block abusers without affecting legitimate users.

- **Best Practice**: Define a dedicated stick table backend for tracking HTTP request rates, connection counts, and errors. Track clients in your frontend using `http-request track-sc0 src table <table_name>`. Set thresholds conservatively (e.g., 100 requests in 10 seconds for bursts) to avoid false positives on shared NAT IPs. Expire entries after 10-30 minutes to free memory.
  
- **Configuration Example** (Basic HTTP Rate Limiting):
  ```
  # Define stick table for IP-based tracking
  backend per_ip_rates
      stick-table type ip size 1m expire 10m store http_req_rate(10s)

  # In your frontend
  frontend fe_main
      bind *:80
      http-request track-sc0 src table per_ip_rates
      http-request deny deny_status 429 if { sc_http_req_rate(0) gt 100 }  # Block >10 req/sec
  ```
  This returns HTTP 429 (Too Many Requests) for violators. For TCP services like SMTP, track connections instead:
  ```
  backend per_ip_conns
      stick-table type ip size 1m expire 1m store conn_cur,conn_rate(1m)

  frontend fe_smtp
      mode tcp
      bind :25
      tcp-request content track-sc0 src table per_ip_conns
      tcp-request content reject if { sc_conn_cur(0) gt 1 } || { sc_conn_rate(0) gt 5 }  # 1 concurrent, 5/min
  ```

#### 2. **Enforce Connection and Timeout Limits**
DDoS attacks like Slowloris hold connections open with partial requests. Limit concurrency and set aggressive timeouts to drop idle or slow connections.

- **Best Practice**: Set global `maxconn` based on your server's memory (e.g., 50k-100k for 8GB RAM). Use `timeout http-request` to kill slow requests. Enable `option http-buffer-request` to buffer bodies for accurate timing. For high-load scenarios, add tarpitting to delay responses for suspects.

- **Configuration Example**:
  ```
  global
      maxconn 50000  # Adjust based on resources

  defaults
      maxconn 49000  # Slightly under global
      timeout http-request 5s  # Reject >5s requests (anti-Slowloris)
      timeout client 30s
      timeout server 10s
      option http-buffer-request
      timeout tarpit 5s  # Delay abusers for 5s

  # In frontend, tarpit high-rate IPs
  frontend fe_main
      http-request tarpit if { sc_http_req_rate(0) gt 100 }
  ```

#### 3. **Use ACLs to Block Suspicious Traffic**
ACLs filter based on headers, IPs, or patterns (e.g., bad User-Agents or missing headers). Combine with maps or files for dynamic blacklisting.

- **Best Practice**: Maintain IP blacklists/greylists in files (e.g., `/etc/haproxy/blacklist.lst` with CIDR ranges). Block outdated protocols or common bot signatures. For geo-blocking, integrate MaxMind GeoIP. Whitelist trusted IPs (e.g., internal networks) to bypass rules.

- **Configuration Example** (Blocking Bad User-Agents and IPs):
  ```
  # Block specific User-Agents or missing ones
  http-request deny if { req.hdr(user-agent) -i -m sub curl phantomjs }  # Case-insensitive substring
  http-request deny unless { req.hdr(user-agent) -m found }

  # Block from blacklist file
  http-request deny if { src -f /etc/haproxy/blacklist.lst }

  # Greylist: Stricter limits for risky IPs
  http-request deny if { src -f /etc/haproxy/greylist.lst } { sc_http_req_rate(0) gt 5 }
  ```
  Update blacklists dynamically in HAProxy Enterprise via `dynamic-update` from a remote URL.

#### 4. **Layered Defenses and Monitoring**
- **Edge Filtering**: Place HAProxy behind a firewall with SYN flood protection (e.g., iptables rules to drop excessive SYN packets):
  ```
  iptables -A INPUT -p tcp --syn --dport 80 -m recent --set
  iptables -A INPUT -p tcp --syn --dport 80 -m recent --update --seconds 10 --hitcount 10 -j DROP
  ```
- **Error and Scanner Detection**: Track HTTP errors to catch scanners:
  ```
  backend abuse_table
      stick-table type ip size 1m expire 30m store http_err_rate(20s)

  # In frontend
  acl scanner src_http_err_rate(abuse_table) ge 10
  http-request deny if scanner
  ```
- **Monitoring**: Use `socat` to inspect tables: `echo "show table per_ip_rates" | socat stdio /var/run/haproxy.sock`. Test with tools like Apache Bench (`ab -n 200 -c 10 http://your-vip/`).
- **Advanced/Enterprise Features**: For clustered setups, use Stick Table Aggregator for global counters across HAProxy instances. Add Antibot or reCAPTCHA modules for JavaScript challenges against bots. Integrate with CDNs like Cloudflare for volumetric attacks.

#### Additional Recommendations
- **Test Thoroughly**: Simulate attacks with tools like `hping3` or `slowhttptest`. Monitor for false positives, especially on mobile/shared IPs.
- **Scale and Redundancy**: Deploy multiple HAProxy instances in active-active mode with BGP for failover. For massive attacks (e.g., >1 Tbps), pair with dedicated DDoS services.
- **Limitations**: HAProxy excels at application-layer protection but may need upstream hardware for volumetric Layer 3/4 floods.

These steps provide a robust, cost-effective defense. Start with rate limiting and timeouts, then layer on ACLs. For production, consult HAProxy docs or community forums for tuning.
