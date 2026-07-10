# MQTT over QUIC

**MQTT over QUIC** (sometimes called MQTT/QUIC or MQTT-next) is an emerging approach that runs the MQTT protocol over the QUIC transport (UDP-based, from HTTP/3) instead of traditional TCP/TLS.

### Why use it?
QUIC offers big advantages for IoT/messaging:
- **Faster connections** — 0-RTT/1-RTT handshakes (built-in TLS 1.3).
- **No head-of-line blocking** — Multiple independent streams (great for multiple topics/QoS flows).
- **Better mobility** — Connection migration (survives network changes, NAT rebinding, mobile devices).
- **Improved performance** on lossy/unstable networks (common in IoT).
- Lower overhead and resource usage in many scenarios.

### Production-ready options (2026)

**Brokers / Servers:**
- **EMQX 5.0+** (most mature): Native MQTT over QUIC support. Listens on port 14567 by default (`mqtt-quic://`). Supports single-stream mode reliably; multi-stream is evolving.
- **NanoMQ**: Good for edge, with QUIC bridging.
- Others (Tencent Cloud, experimental implementations).

**Clients / SDKs:**
- **NanoSDK** (C, from EMQ): One of the first solid MQTT-over-QUIC clients. Bindings for Python, Java, etc.
- **Rust** → `mquictt` or `mqtt-lib` (supports multi-stream, datagrams).
- **Mobile** → Capacitor plugin (`@annadata/capacitor-mqtt-quic`).
- **Embedded** → Proofs-of-concept on ESP32 using ngtcp2 + wolfSSL + coreMQTT.
- Erlang (`emqtt`), and various research/experimental libs.

### How to get started quickly
1. **Run EMQX** (easiest broker):
   - Enable in `emqx.conf`:
     ```hocon
     listeners.quic.default {
       enabled = true
       bind = "0.0.0.0:14567"
     }
     ```
   - Use URL like `mqtt-quic://your-broker:14567`.

2. **Client example** (NanoSDK style):
   ```c
   nng_mqtt_quic_client_open(&socket, "mqtt-quic://broker.example.com:14567");
   ```

3. **Bridges** (cloud-edge): EMQX Edge / NanoMQ support MQTT-over-QUIC bridges easily.

### Standardization
There's an OASIS draft spec ("MQTT-next") for proper multi-stream, flows, datagrams (QoS 0 over unreliable QUIC datagrams), etc. It's not yet a final standard, but implementations (especially EMQX) are already widely used.

### Recommendations
- **For production/IoT** → Start with **EMQX 5.x + NanoSDK** (or its bindings).
- **Need multi-stream / advanced features** → Look at Rust libs like `mqtt-lib`.
- **Mobile/embedded** → Check the Capacitor plugin or ngtcp2-based solutions.

