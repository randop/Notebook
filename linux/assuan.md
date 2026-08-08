# Libassuan

**Libassuan** is a small, general-purpose IPC (Inter-Process Communication) library that implements the **Assuan protocol**. It is primarily used by the GnuPG (GNU Privacy Guard) ecosystem and related tools.

### What it does
It provides both **client** and **server** side functions so that processes can talk to each other in a structured, reliable way. Typical communication happens over pipes or Unix domain sockets after forking a subprocess (or connecting to an already-running server).

The Assuan protocol is a text-based, point-to-point, transaction-oriented protocol. It supports:
- Commands and responses
- Status messages
- Data inquiries (for larger data transfers)
- Optional confidentiality for parts of the communication
- Passing of file descriptors for bulk data

### Why it exists (especially for GnuPG)
GnuPG is deliberately modular: different components handle specialized tasks (encryption/decryption, key management, pinentry for passphrases, directory services, etc.). These run as separate processes for security and reliability reasons.

Libassuan (and the Assuan protocol) lets a client (for example a mail client or GPGME) talk to a non-persistent server (for example `gpg-agent` or `dirmngr`) without sharing the same address space. This has important benefits:
- A buggy or malicious client cannot directly corrupt the server’s memory or secret keys.
- Servers that handle sensitive material stay smaller and easier to audit.
- Front-ends and back-ends can be developed and updated independently.
- The well-defined protocol makes debugging and testing easier.

In an ideal world the protocol would be unnecessary, but process isolation is valuable when dealing with secret keys and cryptographic operations.

### Key points
- **Not limited to GnuPG** — it is designed to be general enough for other transaction-based client/server setups with non-persistent servers.
- **Dependencies** — it depends on `libgpg-error` (the common error-handling library used across GnuPG components).
- **License** — mainly LGPL-2.1-or-later (library) with some GPL parts for documentation.
- **Current status** — actively maintained as part of the GnuPG project (recent versions are in the 3.0.x series, e.g. 3.0.2). Source is available from the GnuPG Git repositories and download mirrors.

### Where you encounter it
- As a dependency of GnuPG, GPGME, pinentry, Kleopatra, and many other tools that talk to the GnuPG agent or related daemons.
- On Linux distributions, macOS (via Homebrew), FreeBSD, etc., it is usually packaged as `libassuan` (runtime) and `libassuan-dev` / `libassuan-devel` (headers and development files).

**libassuan is the IPC plumbing that lets the modular pieces of GnuPG (and similar systems) communicate safely and cleanly.**

---

## Assuan protocol details

Assuan is a simple, extensible, **line-oriented, text-based IPC protocol** designed for point-to-point communication between a client and a (usually non-persistent) server. It is the protocol implemented by **libassuan**.

### Core design goals and criteria
- Common framework for module communication (especially in modular systems like GnuPG)
- Easy debugging and testing
- Extensible
- Optional authentication / encryption
- Usable to talk to external hardware
- Client/server model **with a back channel** (server can proactively send status or inquiries)
- Mainly text-based
- Escape certain control characters
- Support for arbitrary-length data
- Ability to request confidentiality for parts of the conversation
- Support for both inline data and file-descriptor passing for bulk transfers
- No need for DoS protection or protection against subliminal channels (it is intended for local use)

It does **not** provide a naming/discovery system. The client must already know how to reach the server (well-known Unix domain socket, pipe, or by starting the server process itself).

### Transport and basic rules
- Default transport: **Unix domain sockets** (pipes are also common, especially for transient servers).
- Line-based protocol with a hard limit of **1000 octets per line**.
- All textual messages are assumed to be **UTF-8**.
- Lines end with LF or CR/LF.
- On connection the server responds with `OK`, an error, or an inquiry (for authentication). The server should verify the peer’s credentials to prevent attacks based on incorrect socket permissions.
- The server never actively closes the connection under normal circumstances (the underlying transport may close it after inactivity or errors).

### Message types

#### Server → Client responses
| Prefix | Meaning |
|--------|---------|
| `OK` | Success (request completed) |
| `ERR <code> [text]` | Error (codes come from libgpg-error) |
| `S <keyword> [text]` | Status / informational message while still processing |
| `# <text>` | Comment (debugging only, completely ignored) |
| `D <raw data>` | Data line (part of a data stream) |
| `INQUIRE <keyword> [args]` | Server needs more information from the client |

**Data lines (`D`)**:
- Exactly one space after the `D`.
- `%`, CR and LF must be percent-escaped (`%25`, `%0D`, `%0A`).
- Only uppercase hex digits should be used.
- Multiple `D` lines form one continuous data stream until an `OK` or `ERR`.
- Status and Inquiry messages may be interleaved with data.

**Inquiry example**:
```
S: INQUIRE foo
C: D some data
C: D more data
C: END
```
or the client can cancel with `CAN`.

#### Client → Server requests
A normal command looks like:
```
COMMAND [parameters]
```
- Command name is a single word (no leading whitespace).
- Parameters are command-specific; CR, LF and `%` should be percent-escaped.
- Long parameter lists can (in theory) be continued with a trailing unescaped backslash (not fully implemented in early versions).

**Special / common commands** (supported by most Assuan servers and partly by the library itself):

| Command | Purpose |
|---------|---------|
| `BYE` | Close the connection (server replies `OK`) |
| `RESET` | Reset connection state (but keep authentication) |
| `END` | Mark the end of a data stream |
| `HELP` | List available commands (as comment/status lines) |
| `OPTION name [= value]` | Set connection options |
| `NOP` | No operation – just returns `OK` |
| `CANCEL` / `CAN` | Cancel an ongoing inquiry/operation |

Raw data from client to server uses the same `D ...` lines, terminated by `END`.

### Data transfer styles
1. **Inline** – data sent as a series of `D` lines.
2. **File descriptor passing** – for large bulk data over sockets (more efficient).

### Error handling
Errors use the standard **libgpg-error** codes (`ERR <code> [human text]`). It is recommended that applications set a proper error source early via `assuan_set_gpg_err_source()`.

### Typical usage patterns
- **Transient server**: Client forks the server process and communicates over a pipe (libassuan has helpers for this).
- **Long-running server**: Client connects to a well-known Unix domain socket (e.g. `gpg-agent`, `dirmngr`).
- Servers register a table of command handlers; the library’s main loop (`assuan_accept` + `assuan_process`) dispatches incoming commands.
- Both sides can interleave status messages and inquiries, which is useful for progress reporting or requesting secrets (e.g. pinentry).

### Security model
Because client and server run in separate address spaces, a buggy or compromised client cannot directly touch the server’s memory or secret keys. This is the main reason GnuPG uses it extensively. The protocol itself is not encrypted by default (it is meant for local IPC), though higher-level components can add protection.

### Official documentation
The complete reference is the **“Developing with Assuan”** manual:
- https://www.gnupg.org/documentation/manuals/assuan/
- (Also available as PDF and info pages)

It covers the protocol itself, the C API for writing clients and servers, external event-loop integration, and utility functions.

Assuan is a deliberately simple, robust, text-based request/response + inquiry protocol optimized for secure, modular local IPC rather than high performance or network use.
