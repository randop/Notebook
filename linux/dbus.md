# D-Bus

**dbus-broker** is an implementation of a D-Bus message bus as defined by the D-Bus specification. It functions as a drop-in replacement for the reference `dbus-daemon` on Linux systems, providing message mediation, access control, subscriptions, and bus control.

It is written primarily in C (with some Rust components), targets Linux exclusively (requiring kernel >= 4.17), and leverages modern kernel features. The project is hosted at https://github.com/bus1/dbus-broker under the Apache License 2.0.

### Core Architecture: Pure Bus Implementation
dbus-broker separates the core message broker from policy and external interactions:

- **dbus-broker** (the broker binary) is a *pure* implementation. It handles only message mediation according to the D-Bus spec. It has no external dependencies beyond the kernel and runs in isolation. It communicates with a controlling/launcher process solely via a private socketpair for setup and control.

- A separate **controller/launcher** (e.g., `dbus-broker-launch`) handles bus setup, configuration parsing (e.g., policy files), socket listening, service activation, and integration with systemd. This launcher provides compatibility with `dbus-daemon` behaviors.

This design follows principles such as "No IPC to implement IPC" — message transactions rely only on local data, avoiding external calls (e.g., no NSS lookups inside the broker, no file reads during hot paths) to prevent deadlocks or side effects.

### Key Internal Design Elements

**No Shared Medium Model**: Unlike traditional bus designs with global shared state and queues, dbus-broker treats the bus as a set of distinct peers and transactions. Messages are handled as point-to-point (or multicast) transactions between involved peers, minimizing global data structures. Global elements required by the spec (e.g., name registry, broadcast matching) are minimized or made conditional on actual usage.

**Data Structures and Scalability**: Lookups and operations use structures that enable O(log n) time complexity for many operations (vs. O(n) in some cases for dbus-daemon). Global data structures are avoided unless spec-mandated and in active use. Peers and objects are indexed per-peer where possible.

**Message Handling and Reliability Guarantees**: Messages are judged by type (method calls, replies, signals, errors) for proper transaction tracking. The broker aims to never silently drop messages. Error conditions are handled explicitly; in unrecoverable cases, it may exit rather than drop data or put peers in unexpected states. It provides guarantees around message delivery, queuing, and error reporting (e.g., handling of unsolicited vs. solicited messages).

**Accounting and Resource Management**:
- Resources (memory, file descriptors, matches, objects) are accounted **per-UID** (user-based), not per-peer or in multi-tier hierarchies.
- Single-tier accounting prevents resource-chaining exhaustion (where multiple connections/objects bypass limits).
- Inter-user quotas apply: one UID's consumption of another's resources (e.g., via queued messages) is limited dynamically (e.g., fair-share logic like `m/(n+1)` where `m` is available resources and `n` is active consumers).
- Limits are configurable; exceeding them leads to disconnection or errors depending on context.

**Policy and Access Control**: The broker enforces D-Bus policy (e.g., SELinux, capabilities, audit if enabled). Policy handling is managed via the controller; the core broker receives pre-processed rules. It supports listener and name objects with specific control interfaces (e.g., on `/org/bus1/DBus/Controller`, listeners, names).

**Integration with Systemd**: The launcher integrates tightly with systemd for service activation (e.g., transient units), avoiding launching services into the broker's cgroup and using the manager's environment.

### Components and Build Structure
From the source layout:
- `src/`: Core broker implementation.
- `docs/`: Documentation (including man pages and internals).
- Controller/launcher components for compatibility.
- Dependencies: Minimal runtime (glibc, kernel features; optional libaudit, libcap-ng, libselinux). Build requires meson, etc.

It supports both system and user buses via systemd units (`dbus-broker.service` triggered by `dbus.socket`).

### Compatibility and Deviations
It maintains compatibility with the D-Bus specification and existing clients/libraries. Any deviations from reference behavior (e.g., in edge cases for reliability or accounting) are documented in the project wiki. It can run as a private/isolated bus without filesystem access.

In summary, dbus-broker is structured around a minimal, efficient, Linux-native core broker focused on mediation and transactions, controlled by a separate launcher for deployment and policy, with emphasis on per-user resource accounting, reduced global state, and strong reliability properties in message handling.
