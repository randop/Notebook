# Seastar v25.05 (C++23) SMTP Service  
## systemd Hardening Compatibility Report

---

## Overview

This report evaluates the interaction between a highly restricted `systemd` service configuration and a Seastar v25.05-based SMTP application written in C++23. The original unit file applies aggressive sandboxing controls that conflict with Seastar’s runtime model, particularly in areas such as asynchronous I/O, memory handling, networking, and filesystem access.

Seastar is designed for high-performance, low-latency applications and relies heavily on kernel features such as `io_uring`, direct I/O (DMA), and CPU affinity. These requirements impose constraints on how far system-level isolation can be applied without breaking functionality.

---

## Key Conflict Areas

### 1. Direct I/O and Filesystem Access

Seastar uses direct I/O (`O_DIRECT`) with strict alignment requirements. The configuration enforces:

- `ProtectSystem=strict`
- Limited writable paths (`ReadWritePaths`)
- `PrivateDevices=yes`

These restrictions interfere with:
- Access to block devices
- Proper DMA operation
- Filesystem alignment guarantees

**Observed Effects:**
- `dma_write()` failures (`EINVAL`)
- Segmentation faults during file operations
- Silent fallback to buffered I/O with degraded performance

---

### 2. io_uring Compatibility

Seastar’s reactor depends on `io_uring`. The following setting introduces incompatibility:

- `RestrictNamespaces=yes`

This may block kernel features required for submission/completion queues.

**Observed Effects:**
- Reactor fallback to less efficient backends
- Initialization failures
- Reduced throughput without explicit errors

---

### 3. Networking Limitations

The service drops all capabilities:

- `CapabilityBoundingSet=`

This prevents binding to privileged ports (e.g., SMTP on port 25).

**Observed Effects:**
- Failure to bind listening socket
- Service startup failure

---

### 4. Memory Execution Restrictions

The configuration includes:

- `MemoryDenyWriteExecute=yes`

While generally safe, this can affect:
- TLS libraries (e.g., OpenSSL)
- Runtime code paths requiring executable memory regions

**Observed Effects:**
- TLS handshake failures
- Segmentation faults in cryptographic routines

---

### 5. Device Isolation

The use of:

- `PrivateDevices=yes`

removes access to `/dev`, including:

- `/dev/urandom`
- `/dev/random`

**Observed Effects:**
- Entropy starvation
- TLS delays or failures
- Slow initialization

---

### 6. Filesystem Visibility

With:

- `ProtectSystem=strict`
- `ProtectHome=yes`

the application has restricted visibility of system paths.

**Observed Effects:**
- Failure to load TLS certificates
- Configuration access issues
- Inconsistent file resolution

---

### 7. Resource Limits

Default limits are insufficient for high-concurrency workloads.

**Observed Effects:**
- File descriptor exhaustion
- Connection scaling limitations

---

### 8. Runtime Behavior Constraints

The following settings restrict runtime behavior:

- `RestrictRealtime=yes`
- `NoNewPrivileges=yes`

**Observed Effects:**
- Reduced scheduling efficiency
- Increased latency under load
- Uneven CPU utilization

---

## Corrected systemd Unit Configuration

The following configuration maintains strong isolation while preserving Seastar compatibility.

```ini
[Unit]
Description=SMTP Seastar Service (Hardened)
After=network.target

[Service]
User=smtp
Group=users

WorkingDirectory=/var/jail/smtp/inbox
ExecStart=/path/to/your/smtp-service-binary-or-script

Restart=on-failure
RestartSec=5s

# Filesystem isolation
ProtectSystem=strict
ProtectHome=yes
ReadWritePaths=/var/jail/smtp/inbox
ReadOnlyPaths=/etc/ssl /etc/resolv.conf /etc/hosts
PrivateTmp=yes

# Device access
PrivateDevices=no

# Networking
CapabilityBoundingSet=CAP_NET_BIND_SERVICE
AmbientCapabilities=CAP_NET_BIND_SERVICE
NoNewPrivileges=yes

# Kernel protections
ProtectKernelTunables=yes
ProtectControlGroups=yes
ProtectKernelModules=yes
LockPersonality=yes
RestrictNamespaces=no

# Memory and execution
MemoryDenyWriteExecute=yes
RestrictRealtime=yes
RestrictSUIDSGID=yes

# System calls
SystemCallArchitectures=native
SystemCallFilter=@system-service

# Resource limits
LimitNOFILE=1048576
TasksMax=infinity

# Logging
StandardOutput=journal
StandardError=journal

[Install]
WantedBy=multi-user.target
```

---

## Configuration Rationale

### Capability Restoration

Adding `CAP_NET_BIND_SERVICE` allows binding to privileged ports without granting broader privileges.

---

### Device Access Adjustment

Disabling `PrivateDevices` restores access to essential kernel interfaces and entropy sources required for stable runtime behavior.

---

### Namespace Restriction Relaxation

Disabling namespace restrictions ensures compatibility with `io_uring` and related kernel mechanisms.

---

### Filesystem Access Refinement

Explicitly allowing read access to:

* `/etc/ssl`
* `/etc/resolv.conf`
* `/etc/hosts`

ensures TLS and DNS functionality.

---

### System Call Filtering

Using `@system-service` provides a baseline filter without blocking critical syscalls required by Seastar.

---

### Resource Scaling

Increasing `LimitNOFILE` and removing task limits ensures the service can scale to high connection counts without hitting OS-imposed ceilings.

---

## Residual Risks and Considerations

* Seastar’s architecture assumes a relatively permissive execution environment compared to typical sandboxed services.
* Further restriction of system calls or devices may introduce subtle runtime failures.
* TLS-related crashes should first be investigated by disabling `MemoryDenyWriteExecute`.
* High I/O workloads require careful validation of filesystem and mount options.

---

## Conclusion

The original configuration imposed restrictions incompatible with Seastar’s execution model, particularly around I/O, device access, and networking. The revised configuration maintains strong isolation while enabling required kernel features, ensuring stable operation under high concurrency and I/O-intensive workloads.

Further hardening should be approached incrementally, with validation under realistic traffic conditions.
