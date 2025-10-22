# Server-Sent Events (SSE)

### Server-Sent Events (SSE) over QUIC

Server-Sent Events (SSE) is a web standard that enables servers to push real-time updates to clients over a single, long-lived HTTP connection. Traditionally, SSE runs over HTTP/1.1 or HTTP/2, but running it over **QUIC** (Quick UDP Internet Connections)—the transport protocol underlying HTTP/3—offers potential improvements in speed, reliability, and latency due to QUIC's UDP-based design, which reduces connection setup time and handles packet loss better than TCP.

#### Key Benefits of SSE over QUIC
- **Lower Latency**: QUIC combines TLS encryption with transport-layer features, eliminating separate handshakes for TCP and TLS (0-RTT resumption possible).
- **Multiplexing**: Like HTTP/2, QUIC supports stream multiplexing without head-of-line blocking.
- **Better for Real-Time Apps**: Ideal for use cases like live notifications, stock tickers, or dashboards, where SSE's one-way server-to-client push shines.
- **Comparisons to Alternatives**:
  | Technique       | Connection Type | Bidirectional? | Overhead | Best For |
  |-----------------|-----------------|---------------|----------|----------|
  | **Long Polling** | HTTP Request-Response | No | High (repeated requests) | Simple fallbacks |
  | **SSE** | HTTP/1.1 or HTTP/2 | No (server → client only) | Low | Unidirectional real-time updates |
  | **WebSockets** | Persistent TCP | Yes | Medium | Interactive chat/gaming |
  | **SSE over QUIC (HTTP/3)** | UDP-based | No | Very Low | Fast, reliable pushes |
  | **gRPC over QUIC** | HTTP/3 Streams | Yes | Low | RPC-style services |

  SSE over QUIC edges out WebSockets in unidirectional scenarios due to simpler setup and lower resource use, though WebSockets remain more flexible for two-way communication.

#### Implementations and Examples
- **Standard SSE over HTTP/3 (QUIC)**: Most modern browsers (Chrome 91+, Firefox 88+, Safari 14.1+) support HTTP/3 natively, so SSE works out-of-the-box if your server (e.g., via Nginx or Cloudflare) exposes it. Example client-side JavaScript:
  ```javascript
  const eventSource = new EventSource('/events', { withCredentials: true });
  eventSource.onmessage = (event) => console.log('Data:', event.data);
  eventSource.onerror = (error) => console.error('SSE Error:', error);
  ```
  Server-side (Node.js with Express and http3 module):
  ```javascript
  const http3 = require('http3-server');
  const server = http3.createServer((req, res) => {
    if (req.url === '/events') {
      res.writeHead(200, {
        'Content-Type': 'text/event-stream',
        'Cache-Control': 'no-cache',
        'Connection': 'keep-alive',
      });
      // Push events periodically
      setInterval(() => res.write(`data: ${JSON.stringify({ time: Date.now() })}\n\n`), 1000);
    }
  });
  server.listen(443, { quic: true });
  ```

- **Direct QUIC SSE (qsse Library)**: For a custom, non-HTTP implementation, the Go library **qsse** provides SSE directly over QUIC streams, bypassing HTTP overhead for even faster performance. It's positioned as a "faster replacement for traditional SSE over HTTP/2" and supports topic-based pub/sub with auth. (Note: The repo is archived as of October 13, 2025, but the code remains usable.)

  **Installation**:
  ```
  go get github.com/snapp-incubator/qsse
  ```

  **Server Example** (Publishing to topics like "people" or "accounts"):
  ```go
  package main

  import (
      "github.com/snapp-incubator/qsse"
      // Assume Person and Account structs defined
  )

  var (
      people   = []Person{{Name: "Alice"}} // Example data
      accounts = []Account{{ID: 1}}        // Example data
  )

  func main() {
      topics := []string{"people", "accounts"}
      server, err := qsse.NewServer("localhost:4242", topics, nil)
      if err != nil {
          panic(err)
      }
      defer server.Close()

      // Publish events
      server.Publish("people", people[0])
      server.Publish("accounts", accounts[0])

      // Keep server running (e.g., select{} or wait group)
      select {}
  }
  ```

  **Client Example** (Subscribing and handling events):
  ```go
  package main

  import "github.com/snapp-incubator/qsse"

  func main() {
      topics := []string{"people", "accounts"}
      client, err := qsse.NewClient("localhost:4242", topics, nil)
      if err != nil {
          panic(err)
      }
      defer client.Close()

      // Set handlers
      client.SetEventHandler("people", func(data []byte) {
          // Process data (e.g., unmarshal JSON)
          println("Received people event:", string(data))
      })
      client.SetErrorHandler(func(code int, data map[string]any) {
          println("Error:", code, data)
      })

      // Keep client running
      select {}
  }
  ```

  **Security**: Add token-based auth:
  ```go
  server.SetAuthenticatorFunc(func(token string) bool {
      return token == "valid-secret" // Validate token
  })
  server.SetAuthorizerFunc(func(token, topic string) bool {
      return token == "valid-secret" && topic == "people" // Per-topic auth
  })
  ```

  Topics support patterns (e.g., subscribe to "ride.*" for "ride.passenger.start").

#### Potential Drawbacks
- **Browser Support**: Full QUIC/HTTP/3 is widespread but not universal (e.g., older devices may fall back to HTTP/2).
- **Debugging**: QUIC's UDP nature can complicate firewall traversal compared to TCP.
- **Overhead**: For low-volume events, the gains might be negligible vs. plain SSE.
