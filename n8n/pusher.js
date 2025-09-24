// n8n Code Node (JavaScript) - Trigger Pusher Event

const crypto = require('crypto');

// Replace with your Pusher credentials (or pull from input: e.g., const appId = $input.first().json.app_id;)
const appId = '<PUT HERE>';
const key = '<PUT HERE>';
const secret = '<PUT HERE>';
const cluster = 'mt1';

const now = new Date();
const isoString = now.toISOString();

// Event details
const channel = 'my-channel';
const event = 'my-event';
const payload = { message: `Merry Christmas (${isoString})` };

// Build the full request body for single event trigger
const dataString = JSON.stringify(payload);  // Stringify the payload for 'data' field
const requestBody = {
  name: event,
  channel: channel,
  data: dataString
};
const bodyString = JSON.stringify(requestBody);

// Compute timestamp
const timestamp = Math.floor(Date.now() / 1000).toString();

// Compute body MD5 (of the full request body)
const bodyMd5 = crypto.createHash('md5').update(bodyString).digest('hex');

// Build path and query string (no channel/event in QS for single trigger)
const path = `/apps/${appId}/events`;
function buildQueryString(params) {
  return Object.keys(params)
    .map(key => encodeURIComponent(key) + '=' + encodeURIComponent(params[key]))
    .join('&');
}
const qsParamsObj = {
  auth_key: key,
  auth_timestamp: timestamp,
  auth_version: '1.0',
  body_md5: bodyMd5
};
const qsString = buildQueryString(qsParamsObj);

// Signature string (method\npath\nquery)
const signatureString = `POST\n${path}\n${qsString}`;

// Generate HMAC-SHA256 signature
const hmac = crypto.createHmac('sha256', secret);
hmac.update(signatureString);
const authSignature = hmac.digest('hex');

// Full query with signature
const fullQs = qsString + '&auth_signature=' + encodeURIComponent(authSignature);

// Build base URL
let host = 'api-mt1.pusher.com';
const baseUrl = `https://${host}`;
const fullUrl = `${baseUrl}${path}?${fullQs}`;

// Send the HTTP request using n8n's helper
const response = await this.helpers.httpRequest({
  method: 'POST',
  url: fullUrl,
  headers: {
    'Content-Type': 'application/json'
  },
  body: bodyString,
  returnFullResponse: true
});

// Return json n8n-compatible response
return { 
  json: { 
    success: response.statusCode === 200, 
    response: response, 
    triggeredEvent: { channel, event, data: payload} 
  } 
};
