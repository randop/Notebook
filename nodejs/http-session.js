/**
 * Node.JS http application using cookie session
 * This implementation uses signed session IDs for security (prevents tampering)
 * and stores minimal data server-side in memory.
 * The cookie is HttpOnly to mitigate XSS risks.
 **/
const http = require('http');
const crypto = require('crypto');
const url = require('url');

// Simple in-memory session store
const sessions = new Map();

// Secret for signing cookies
const SECRET = 'your-secret-key-change-me';

// Generate a random session ID
function generateSessionId() {
  return crypto.randomBytes(32).toString('hex');
}

// Sign a session ID
function signSessionId(sessionId) {
  const hmac = crypto.createHmac('sha256', SECRET);
  hmac.update(sessionId);
  return `${sessionId}.${hmac.digest('hex')}`;
}

// Verify and parse signed session ID from cookie
function parseSignedSessionId(cookie) {
  if (!cookie) return null;
  const parts = cookie.split('.');
  if (parts.length !== 2) return null;
  const [sessionId, signature] = parts;
  const hmac = crypto.createHmac('sha256', SECRET);
  hmac.update(sessionId);
  const expectedSignature = hmac.digest('hex');
  if (signature === expectedSignature) {
    return sessionId;
  }
  return null;
}

// Parse cookies from header
function parseCookies(headers) {
  const cookies = {};
  if (headers.cookie) {
    headers.cookie.split(';').forEach(cookie => {
      const [name, value] = cookie.trim().split('=');
      cookies[name] = value;
    });
  }
  return cookies;
}

// Simple response helper
function sendResponse(res, status, contentType, body) {
  res.writeHead(status, { 'Content-Type': contentType });
  res.end(body);
}

// Create server
const server = http.createServer((req, res) => {
  const parsedUrl = url.parse(req.url, true);
  const path = parsedUrl.pathname;
  const cookies = parseCookies(req.headers);
  let sessionId = parseSignedSessionId(cookies.session);

  if (!sessionId) {
    sessionId = generateSessionId();
    const signedSessionId = signSessionId(sessionId);
    res.setHeader('Set-Cookie', `session=${signedSessionId}; HttpOnly; Path=/`);
  }

  // Initialize session data if not exists
  if (!sessions.has(sessionId)) {
    sessions.set(sessionId, { visits: 0 });
  }

  let sessionData = sessions.get(sessionId);

  if (path === '/') {
    // Home page - increment visits
    sessionData.visits += 1;
    sessions.set(sessionId, sessionData);
    sendResponse(res, 200, 'text/html', `
      <html>
        <body>
          <h1>Welcome!</h1>
          <p>You have visited this page <strong>${sessionData.visits}</strong> times.</p>
          <a href="/visit">Go to visit page</a>
        </body>
      </html>
    `);
  } else if (path === '/visit') {
    // Visit page - show session data
    sendResponse(res, 200, 'text/html', `
      <html>
        <body>
          <h1>Visit Page</h1>
          <p>Total visits: <strong>${sessionData.visits}</strong></p>
          <a href="/">Back to home</a>
        </body>
      </html>
    `);
  } else {
    sendResponse(res, 404, 'text/plain', 'Not Found');
  }
});

const PORT = 3000;
server.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
