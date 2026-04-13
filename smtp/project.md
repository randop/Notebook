# SMTP

Here's the **fixed Mermaid diagram**. The parse error was caused by unquoted node labels containing parentheses `()` and special characters — Mermaid requires quotes around such text.

```mermaid
flowchart TD
    subgraph "Project Structure (C++20)"
        A["main.cpp"] --> B["uv_loop_t"]
        B --> C["SMTP Server"]
        C --> D["SmtpSession per client"]
        
        style A fill:#e1f5fe
        style C fill:#f3e5f5
    end

    subgraph "libuv Core"
        B --> E["uv_tcp_t Server Handle\n(uv_tcp_init + uv_tcp_bind + uv_listen)"]
        E --> F["uv_tcp_t Client Handle\n(uv_accept)"]
        F --> G["uv_read_start / uv_write"]
        G --> H["uv_close on QUIT or error"]
    end

    subgraph "SMTP Protocol States (No TLS)"
        I["1. Connection\n→ 220 Service Ready"] 
        --> J["2. Greeting\nHELO / EHLO"]
        --> K["3. Mail Transaction\nMAIL FROM:<sender>"]
        --> L["4. Recipients\nRCPT TO:<recipient> ..."]
        --> M["5. Data\nDATA\n→ 354 End with <CRLF>.<CRLF>"]
        --> N["6. Message Content\n(Headers + Body)"]
        --> O["7. End\n.\n→ 250 OK"]
        --> P["8. QUIT\n→ 221 Bye"]
        
        style I fill:#e8f5e8
        style M fill:#fff3e0
    end

    %% Connections
    D -.-> I
    F -.-> D
    G -.-> J
    G -.-> K
    G -.-> L
    G -.-> M
    G -.-> N
    G -.-> O
    G -.-> P

    classDef libuv fill:#fff3e0,stroke:#f57c00
    classDef smtp fill:#e8f5e8,stroke:#388e3c
    classDef core fill:#f3e5f5,stroke:#7b1fa2

    class E,F,G,H libuv
    class I,J,K,L,M,N,O,P smtp
    class B,C core
```

### Alternative: Cleaner Version (Recommended)

If you prefer even simpler labels:

```mermaid
flowchart TD
    subgraph "C++20 SMTP Server with libuv (No TLS)"
        Main["main.cpp"] --> Loop["Event Loop (uv_loop_t)"]
        Loop --> Server["TCP Server\n(uv_tcp_t)"]
        Server --> Session["Per-Client Session\n(SmtpSession)"]
        
        Session --> States["SMTP State Machine"]
    end

    States --> Conn["Connection → 220 Ready"]
    States --> Helo["HELO/EHLO"]
    States --> Mail["MAIL FROM"]
    States --> Rcpt["RCPT TO"]
    States --> Data["DATA → 354"]
    States --> Body["Message Body\n(ends with .)"]
    States --> Quit["QUIT → 221 Bye"]

    style Conn fill:#e8f5e8
    style Data fill:#fff3e0
```

