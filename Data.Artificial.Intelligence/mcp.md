# MCP

## MCP Server

**MCP Server** refers to a server component within the **Model Context Protocol (MCP)**, an open-source standard introduced by Anthropic in November 2024.

MCP standardizes communication between AI applications (such as large language models or LLM-based tools like Claude) and external systems, including data sources, tools, and workflows. An MCP server acts as the provider side of this client-server architecture.

### Core Structure of MCP
MCP operates as a client-server model with these primary roles:

- **MCP Host**: The AI application or environment (for example, Claude Desktop, VS Code with an extension, or another LLM interface) that initiates interactions.
- **MCP Client**: The intermediary layer within or connected to the host that handles connection to one or more MCP servers, manages discovery, and routes requests.
- **MCP Server**: The external service that exposes capabilities to the AI system. It runs as a lightweight, standalone process and communicates with clients over standardized transports.

### What an MCP Server Exposes
An MCP server publishes a discoverable set of capabilities through three main primitives:

- **Resources**: Read-only data or content retrieval (for example, file contents, database rows, documents, API responses, or search results). These function like file-like objects that the AI can access on demand.
- **Tools**: Callable functions or actions that the AI can invoke (for example, executing a search, performing a calculation, interacting with an API, or running a workflow). The AI selects and calls them autonomously based on the task.
- **Prompts (or instruction templates)**: Predefined prompt structures or contextual instructions that can be applied to guide the AI's behavior in specific scenarios.

Servers may also support notifications or hooks for event-driven interactions and streaming for long-running operations.

### Communication and Implementation Details
- **Protocol**: MCP uses JSON-RPC 2.0 for method calls, notifications, and responses. This enables structured, bidirectional communication.
- **Transports**: Common options include stdio (for local processes), HTTP/SSE (Server-Sent Events) for remote or cloud-based servers, or other streamable mechanisms. Servers can run locally (alongside the AI host) or remotely (on separate machines, on-premises, or in the cloud).
- **Discovery**: At connection time, the server advertises its capabilities in a machine-readable format, so the AI client does not require prior hard-coded knowledge of available tools or data.
- **Implementation**: MCP servers are typically built in languages such as Python, TypeScript, Java, C#, or others. Reference implementations and SDKs exist, and many open-source examples target specific integrations (for example, GitHub, Slack, Google Drive, PostgreSQL, or custom business systems). A server can wrap existing APIs, databases, filesystems, or services and translate interactions into the MCP format.

### Role in Operation
The MCP server functions as an intermediary or bridge:
1. The AI (via the host and client) discovers available resources, tools, and prompts on the server.
2. During a session, the model decides which capabilities to use based on the user's query or task.
3. The client sends standardized requests to the server.
4. The server executes the action against the underlying system (data retrieval, tool execution, etc.), formats the result, and returns it in a form usable by the LLM.
5. This process supports secure, controlled access without requiring custom per-model integrations.

MCP servers are designed to be modular—each typically focuses on a specific domain or service (for example, one for filesystem access, another for a database, or a third for a particular enterprise tool). Multiple servers can connect to a single AI client simultaneously.

This structure allows AI systems to access up-to-date external context and perform actions beyond their internal training data, using a consistent interface across different models and applications.

---

## MCP Client

**MCP Client** is a component within the **Model Context Protocol (MCP)**, an open standard for connecting AI applications to external systems. It serves as the intermediary on the consumer side of the client-server architecture.

MCP uses a structured architecture with these primary roles:

- **MCP Host**: The AI-powered application or environment (for example, Claude Desktop, an IDE like VS Code or Cursor, or a custom LLM-based agent) that manages user interaction and overall coordination.
- **MCP Client**: The protocol-level component instantiated by the host to handle communication with a specific MCP server. Multiple clients can exist within one host, each maintaining a dedicated, isolated connection to one server.
- **MCP Server**: The external service that exposes capabilities (resources, tools, and prompts) to the AI system.

### Role and Responsibilities of the MCP Client
The MCP client functions as the bridge and adapter between the host/LLM and external MCP servers. Its core functions include:

- **Connection Management**: Establishes and maintains a stateful connection to a single MCP server using supported transports (commonly stdio for local processes or HTTP/SSE for remote connections).
- **Capability Discovery**: Performs initial handshake and negotiation to retrieve the server's advertised capabilities in a machine-readable format. This includes listing available resources, tools, and prompts without requiring prior hard-coded knowledge.
- **Message Routing and Translation**: Handles bidirectional communication using JSON-RPC 2.0 for requests, responses, notifications, and errors. It translates internal host/LLM requests into standardized MCP protocol messages and converts server responses back into forms usable by the host or model.
- **Invocation Handling**:
  - Fetches **resources** (read-only data or content, such as file contents or query results).
  - Invokes **tools** (callable functions or actions), often by loading tool definitions into the model's context and orchestrating calls where the model decides usage, with the client executing the request and returning results.
  - Retrieves and applies **prompts** (predefined templates or instruction sets), typically under user control, by fetching their contents and interpolating arguments.
- **Security and Isolation**: Enforces boundaries between servers, supporting sandboxing and controlled access as managed by the host. It handles authentication, subscriptions, and notifications where supported.
- **Lifecycle Management**: The host creates, coordinates, and terminates individual clients as needed, allowing the host to connect to multiple servers simultaneously (one client per server).

### Communication Flow
1. The host configures and launches or connects to one or more MCP servers.
2. For each server, the host instantiates an MCP client.
3. The client performs discovery, exchanging capabilities with the server.
4. During operation, the LLM (via the host) identifies needed capabilities; the client sends the corresponding JSON-RPC requests.
5. The server processes the request against its underlying systems and returns results.
6. The client relays formatted results back to the host/LLM, supporting streaming for long operations where applicable.

This setup enables the AI system to dynamically access up-to-date external context and perform actions through a consistent, standardized interface.

### Implementation Aspects
MCP clients are typically integrated directly into host applications as libraries or built-in modules. SDKs and reference implementations exist in languages such as Python and TypeScript to facilitate building or extending client support. The protocol emphasizes modularity, with each client dedicated to one server for isolation and scalability.

The MCP client does not itself expose capabilities; it consumes those published by servers and facilitates their integration into the host's AI workflows.

---

**MCP (Model Context Protocol)** is an open standard (introduced by Anthropic in late 2024) that lets LLM-powered applications (hosts/clients) connect to external tools, data sources, and services in a standardized way. It acts like a "USB-C for AI agents."

Securing MCP is critical because it expands the attack surface: AI agents can dynamically discover and invoke tools, often with access to sensitive data or actions. Poorly secured MCP servers can lead to prompt injection, unauthorized access, token leakage, tool poisoning, privilege escalation, and more.

### Key Security Principles for MCP
Follow these foundational practices:

- **Least Privilege**: Grant each MCP server and tool the *minimum* permissions needed. Use scoped credentials (e.g., narrow OAuth scopes like `mail.readonly`). Avoid sharing tokens across servers.
- **User Consent & Control**: Always require explicit user approval for data access or actions. Provide clear UIs for reviewing authorizations.
- **Sandboxing & Isolation**: Run servers in containers, restrict filesystem/network access, and prefer `stdio` transport for local servers.
- **Treat Tool Schemas & Descriptions as Untrusted**: Inspect/pin them before use. They are potential injection surfaces.

### Core Security Controls for MCP Servers
1. **Authentication & Authorization (OAuth 2.1 Recommended)**  
   - Use **OAuth 2.1** with features like Protected Resource Metadata (`.well-known/oauth-protected-resource`), dynamic client registration, and resource indicators to bind tokens to specific servers.
   - Respond to unauthorized requests with HTTP 401 and proper `WWW-Authenticate` headers.
   - For enterprise: Add **mutual TLS (mTLS)** for strong client authentication.
   - Example (Python with uvicorn for mTLS):
     ```python
     import ssl
     import uvicorn
     ssl_context = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
     ssl_context.load_cert_chain("server.crt", "server.key")
     ssl_context.load_verify_locations("client_ca.crt")
     ssl_context.verify_mode = ssl.CERT_REQUIRED
     uvicorn.run("app:app", ssl_context=ssl_context)
     ```

2. **Credential Protection**  
   - Never hard-code or expose tokens in prompts/environment variables. Use secret managers with short-lived, scoped credentials.
   - Implement token validation, revocation, and rotation.

3. **Transport Security**  
   - Always use HTTPS. For remote servers, enforce mTLS where possible.

4. **Prompt Injection & Tool Safety Defenses**  
   - Sanitize/guard user inputs and external content.
   - Use structured data extraction instead of raw outputs.
   - Add policy enforcement layers (e.g., parameter scoping, human-in-the-loop for high-risk actions).

5. **Monitoring, Logging & Auditing**  
   - Log every tool invocation with context.
   - Implement runtime guardrails, rate limiting, and anomaly detection.

### Additional Best Practices & Tools
- **OWASP MCP Security Cheat Sheet**: Excellent reference for detailed dos/don’ts.
- Consider libraries/packages like `mcp-secure` (adds cryptographic passports, message signing, replay protection) or secure proxies/control planes (e.g., from n8n, Speakeasy, 42Crunch, Clerk).
- For production remote servers: Follow GitHub’s guide on scalable, secure MCP setups.
- Sandbox high-risk tools and verify manifests/signatures.

### Common Pitfalls to Avoid
- Over-privileged credentials or broad scopes.
- Exposing servers without auth (default-open ports).
- Dynamic registration without verification.
- Passing raw tokens or sensitive data in LLM contexts.
