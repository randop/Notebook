# E-mail

### Standard Email Server Ports

Email protocols like SMTP (for sending), POP3 (for receiving/downloading), and IMAP (for receiving/managing) operate over **TCP** (Transmission Control Protocol), not UDP. TCP is used because these protocols require reliable, connection-oriented communication to ensure messages are delivered in order without loss. While SMTP could theoretically run over UDP per some RFCs, in practice, all standard implementations use TCP exclusively.

Here's a summary of the most common ports:

| Protocol | Port | Purpose | Security |
|----------|------|---------|----------|
| SMTP    | 25   | Standard outgoing mail server (unencrypted by default) | Insecure (supports opportunistic STARTTLS) |
| SMTP    | 587  | Mail submission (client to server) | Secure (requires STARTTLS) |
| SMTPS   | 465  | Secure outgoing mail (implicit TLS) | Secure (TLS from the start) |
| POP3    | 110  | Standard incoming mail retrieval (downloads to client) | Insecure (supports opportunistic STARTTLS) |
| POP3S   | 995  | Secure incoming mail retrieval | Secure (TLS from the start) |
| IMAP    | 143  | Standard incoming mail management (syncs with server) | Insecure (supports opportunistic STARTTLS) |
| IMAPS   | 993  | Secure incoming mail management | Secure (TLS from the start) |

These ports are assigned by IANA and widely used across email services.

### Developing a Node.js SMTP Email Server with StartTLS Support

Implementing a full email server from scratch in Node.js is complex, involving parsing SMTP commands, handling sessions, and managing TLS upgrades manually. A more efficient approach is to use the `smtp-server` module from Nodemailer, which provides a robust, production-ready SMTP server implementation that natively supports StartTLS (upgrading from plaintext to TLS on the `STARTTLS` command). This allows the server to start in non-TLS mode (`secure: false`) and advertise StartTLS in the EHLO response, enabling clients to request encryption.

#### Step 1: Installation
First, create a new Node.js project and install the required dependency:
```
npm init -y
npm install smtp-server
```

#### Step 2: Generate Self-Signed TLS Certificates
StartTLS requires TLS certificates for the upgrade. For development/testing, generate self-signed certificates using OpenSSL (install OpenSSL if needed). Run these commands in your project directory:

```
# Generate private key
openssl genrsa -out private.key 2048

# Generate certificate (valid for 365 days; replace 'mail.example.com' with your domain)
openssl req -new -x509 -key private.key -out server.crt -days 365 -subj "/CN=mail.example.com"
```

In production, use certificates from a trusted CA like Let's Encrypt.

#### Step 3: Server Implementation
Here's a complete example of an SMTP server that listens on port 25 (standard SMTP port), supports StartTLS, and logs incoming emails. It handles basic email reception but discards the data (you can extend the `onData` handler to store emails in a database or file).

**server.js**
```javascript
const fs = require('fs');
const { SMTPServer } = require('smtp-server');

const server = new SMTPServer({
  // Start in non-TLS mode to allow StartTLS upgrade
  secure: false,
  // Provide TLS certificates for the upgrade
  key: fs.readFileSync('./private.key'),
  cert: fs.readFileSync('./server.crt'),
  // Optional: Hide StartTLS from EHLO if not needed (default: false, so it's advertised)
  hideSTARTTLS: false,
  // Optional: Validate SNI hostname after StartTLS (uncomment and customize)
  // onSecure(socket, session, callback) {
  //   if (session.servername !== 'mail.example.com') {
  //     return callback(new Error('Invalid SNI hostname'));
  //   }
  //   callback();
  // },
  // Handle client connection
  onConnect(session, callback) {
    console.log(`Client connected: ${session.remoteAddress}`);
    // Reject connections if needed (e.g., rate limiting)
    // return callback(new Error('Connection rejected'));
    callback();
  },
  // Authenticate users (optional; here we allow anonymous for simplicity)
  onAuth(auth, session, callback) {
    if (auth.username !== 'user' || auth.password !== 'pass') {
      return callback(new Error('Invalid credentials'));
    }
    callback(null, { user: auth.username });
  },
  // Handle mail data stream (process the email content)
  onData(stream, session, callback) {
    let emailBody = '';
    stream.on('data', (chunk) => {
      emailBody += chunk.toString();
    });
    stream.on('end', () => {
      console.log(`Email received from ${session.envelope.from} to ${session.envelope.rcptTo}:`);
      console.log(emailBody.slice(0, 200) + '...'); // Log first 200 chars
      // TODO: Save to file/DB, forward, etc.
      callback(null, 'Message queued'); // SMTP response code 250
    });
  },
  // Handle errors
  onError(e, callback) {
    console.error('Server error:', e.message);
    callback();
  }
});

// Start listening
server.listen(25, () => {
  console.log('SMTP server with StartTLS listening on port 25');
});

// Graceful shutdown
process.on('SIGINT', () => {
  server.close(() => {
    console.log('Server closed');
    process.exit(0);
  });
});
```

#### Step 4: Running and Testing
1. Run the server: `node server.js`
2. Test with a tool like `telnet` or an email client (e.g., Thunderbird configured for your server).
   - Connect via telnet: `telnet localhost 25`
   - Example SMTP session:
     ```
     EHLO client.example.com  (Server responds with capabilities, including STARTTLS)
     STARTTLS  (Server responds 220 Ready to start TLS)
     (Connection upgrades to TLS; telnet won't show this—use openssl s_client for testing)
     MAIL FROM:<sender@example.com>
     RCPT TO:<recipient@example.com>
     DATA
     Subject: Test
     
     Hello from Node.js SMTP!
     .
     QUIT
     ```
3. For TLS testing: Use `openssl s_client -connect localhost:25 -starttls smtp` to simulate a client upgrade.
4. Auth test: If enabling auth, clients must send `AUTH LOGIN` with base64-encoded username/password.

#### Explanation
- **StartTLS Flow**: On EHLO, the server advertises `250-STARTTLS`. When the client sends `STARTTLS`, the server responds `220` and upgrades the socket using the provided `key`/`cert`. Post-upgrade, the `onSecure` callback (if used) validates the session.
- **Security**: Use `secure: true` and port 465 for direct TLS (no StartTLS). Always validate certificates in production.
- **Extensions**: Add `onRcptTo` for recipient validation, `size` option for email size limits, or integrate with a mail storage like Maildir.
- **Limitations**: This is a receiving server (MTA). For sending, pair with Nodemailer's transport. For full-stack (IMAP/POP3), consider libraries like `node-imap`.

This setup provides a functional email server in under 50 lines. For a from-scratch implementation without libraries, it involves manually parsing SMTP with `net`/`tls` modules and upgrading sockets via `TLSSocket`—see advanced examples for that.

### Enhancing Security for the Node.js SMTP Email Server

To secure the server while allowing incoming emails specifically from legitimate providers like Gmail, Yahoo Mail, Outlook (Hotmail), and others, we'll focus on key best practices:

- **Enforce StartTLS**: Require clients to upgrade to TLS, rejecting plaintext connections after the initial EHLO.
- **Prevent Open Relay**: Validate recipients to only accept mail for your domain (e.g., `@yourdomain.com`), blocking unauthorized relaying.
- **Sender Validation via SPF**: Implement Sender Policy Framework (SPF) checks to verify that the sending IP is authorized for the sender's domain. This allows emails from legitimate providers (which publish SPF records) while blocking spoofed senders. We'll use the `mailauth` library for this, as it's a lightweight Node.js tool for SPF/DKIM/DMARC validation.

IP whitelisting (e.g., Gmail's ranges like `108.177.16.0/24`) is an alternative but less flexible, as ranges change and don't cover "other legitimate" providers. SPF is more robust and dynamic.

#### Step 1: Additional Installation
```
npm install mailauth
```

#### Step 2: Updated Server Implementation
Here's the enhanced `server.js`. Changes:
- `requireTLS: true`: Forces StartTLS; clients must issue `STARTTLS` after EHLO or get rejected.
- `onRcptTo`: Restricts to `@yourdomain.com` (replace with your actual domain).
- `onMailFrom`: Performs async SPF check using `mailauth`. Rejects if not "pass".
- Retained auth for relaying (optional; disable by omitting `onAuth` if not needed).
- Added error handling for SPF failures.

**server.js**
```javascript
const fs = require('fs');
const { SMTPServer } = require('smtp-server');
const { spf } = require('mailauth');

const YOUR_DOMAIN = 'yourdomain.com'; // Replace with your domain

const server = new SMTPServer({
  // Require StartTLS upgrade (rejects if not used)
  requireTLS: true,
  // Provide TLS certificates
  key: fs.readFileSync('./private.key'),
  cert: fs.readFileSync('./server.crt'),
  // Handle client connection
  onConnect(session, callback) {
    console.log(`Client connected from ${session.remoteAddress}`);
    callback();
  },
  // Validate sender via SPF
  onMailFrom(address, session, callback) {
    if (!address.domain) {
      return callback(new Error('Invalid sender domain'));
    }
    spf.verify(address.domain, session.remoteAddress)
      .then((result) => {
        if (result.pass) {
          console.log(`SPF pass for ${address.domain} from ${session.remoteAddress}`);
          callback();
        } else {
          console.error(`SPF fail for ${address.domain}: ${result.reason}`);
          callback(new Error(`SPF validation failed: ${result.reason}`));
        }
      })
      .catch((err) => {
        console.error('SPF check error:', err.message);
        callback(new Error('SPF check failed'));
      });
  },
  // Prevent open relay: only accept @yourdomain.com
  onRcptTo(address, session, callback) {
    if (address.address.endsWith(`@${YOUR_DOMAIN}`)) {
      callback();
    } else {
      callback(new Error(`Only ${YOUR_DOMAIN} recipients allowed`));
    }
  },
  // Authenticate users (optional; for authenticated relaying)
  onAuth(auth, session, callback) {
    if (auth.username !== 'user' || auth.password !== 'pass') {
      return callback(new Error('Invalid credentials'));
    }
    callback(null, { user: auth.username });
  },
  // Handle mail data stream
  onData(stream, session, callback) {
    let emailBody = '';
    stream.on('data', (chunk) => {
      emailBody += chunk.toString();
    });
    stream.on('end', () => {
      console.log(`Email received from ${session.envelope.from} to ${session.envelope.rcptTo[0]}`);
      console.log(emailBody.slice(0, 200) + '...');
      // TODO: Save to file/DB
      callback(null, 'Message queued');
    });
  },
  // Handle errors
  onError(e, callback) {
    console.error('Server error:', e.message);
    callback();
  }
});

// Start listening on port 25
server.listen(25, () => {
  console.log('Secure SMTP server (StartTLS + SPF) listening on port 25');
});

// Graceful shutdown
process.on('SIGINT', () => {
  server.close(() => {
    console.log('Server closed');
    process.exit(0);
  });
});
```

#### Step 3: Testing Security
1. Run: `node server.js`
2. **Test StartTLS Requirement**: Use `telnet localhost 25`, send `EHLO test`, then skip `STARTTLS` and try `MAIL FROM:<test@gmail.com>`—it should reject with a TLS error. Proper test: `openssl s_client -connect localhost:25 -starttls smtp`, then proceed with SMTP commands.
3. **Test Open Relay Prevention**: Try `RCPT TO:<external@other.com>`—rejected. Use `@yourdomain.com`—accepted.
4. **Test SPF**: Send from a Gmail/Yahoo/Outlook account (their IPs pass SPF). Spoof a Gmail address from a non-Google IP—rejected.
   - Example with swaks (install via `apt install swaks`): `swaks --to test@yourdomain.com --from spoof@gmail.com --server localhost:25 --tls-on-connect` (should fail SPF).
5. Monitor logs for SPF results.

#### Additional Security Notes
- **DKIM/DMARC**: For fuller validation, extend `onData` to parse the email and use `mailauth`'s DKIM verifier (requires body/header access).
- **Rate Limiting**: Add in `onConnect` (e.g., using a Map to track IPs).
- **Production**: Use valid CA certs, monitor with tools like Fail2Ban, and publish your own SPF/DKIM for outbound.
- **Limitations**: SPF checks TXT records (may timeout on slow DNS); fallback to allow if needed. For IPv6, ensure your server handles it.

This setup blocks spam/abuse while permitting legitimate providers.
