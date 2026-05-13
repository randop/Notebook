# SSL

**TLS/SSL Library Technical Assessment: OpenSSL, LibreSSL, BoringSSL, GnuTLS, wolfSSL, mbedTLS (2026)**

### Origins and Design Philosophy

**OpenSSL** remains the reference implementation for general-purpose TLS, with origins in the late 1990s. Its broad feature set supports servers, clients, command-line tools, and extensive hardware acceleration. The codebase is large and complex, reflecting decades of accumulated functionality.

**LibreSSL** (OpenBSD fork, 2014) prioritizes code simplification, removal of legacy components, and long-term auditability following Heartbleed. It maintains API compatibility while reducing attack surface.

**BoringSSL** (Google fork) emphasizes performance, modern protocols, and aggressive dead-code elimination. It powers Chrome and Android but offers no API stability guarantees for external consumers.

**GnuTLS** (GNU project) integrates with the GNU ecosystem and relies on external primitives such as nettle for cryptography. It targets POSIX environments but has shown recurring issues in certificate validation paths.

**wolfSSL** (formerly CyaSSL) targets embedded and resource-constrained systems with a focus on small footprint, speed, and hardware acceleration. Commercial support and FIPS modules are available.

**mbedTLS** (formerly PolarSSL, Arm-maintained) offers modular, readable code with strong configurability. It suits embedded and middleware use cases where code clarity and selective feature inclusion matter.

### Footprint and Resource Usage

Embedded and high-density deployments favor minimal memory and binary size.

**Typical Configured Sizes (approximate, optimized builds):**
- wolfSSL: 20–100 kB binary, 1–36 kB runtime per session.
- mbedTLS: ~30–420 kB binary (minimal to full), lower per-connection RAM (~24 kB reported in comparisons).
- BoringSSL/LibreSSL: Significantly reduced vs. baseline OpenSSL through code removal.
- OpenSSL: Multi-MB range depending on configuration; challenging for constrained targets without heavy stripping.
- GnuTLS: Larger, server/desktop-oriented footprint.

wolfSSL and mbedTLS deliver order-of-magnitude advantages in memory-constrained environments. OpenSSL derivatives benefit from cleanup but remain heavier than dedicated embedded libraries.

### Performance Characteristics

Server-side multi-threaded benchmarks (HAProxy team, 2025, high-core hardware) highlight scaling differences under TLS 1.3 load.

**End-to-End Connections per Second (approx. values from 64-thread tests):**
- AWS-LC (BoringSSL fork): ~183,000 (strongest scaling).
- wolfSSL 5.7+: ~150,000, linear scaling.
- OpenSSL 3.0.x: 1,500–3,700 (severe regression in some versions; poor multi-thread scaling).

wolfSSL often doubles connections per second versus OpenSSL 1.1.1 in web server stress tests and shows excellent embedded hardware acceleration (e.g., ARM Cortex, STM32).

mbedTLS performs well in constrained microcontrollers but trails wolfSSL in raw throughput on higher-end embedded targets. BoringSSL derivatives lead in modern server environments due to optimized locking and CPU-specific code.

**Handshake and Throughput Notes** (various 2025 benchmarks):
- Full/resumed handshakes favor BoringSSL and wolfSSL in optimized configurations.
- Symmetric crypto throughput (AES-GCM) remains comparable across libraries when assembly optimizations are enabled; differences arise mainly from protocol handling and locking.

### Security Track Record and Maintenance

Larger codebases correlate with higher exposure. OpenSSL has the longest history and most public vulnerabilities, including Heartbleed. Forks (LibreSSL, BoringSSL) reduced legacy code and improved practices.

GnuTLS has faced multiple certificate validation and DoS issues in 2025–2026, including resource exhaustion during verification and name constraint bypasses.

wolfSSL and mbedTLS maintain smaller attack surfaces. All libraries require careful configuration (cipher suites, validation callbacks, version restrictions). FIPS 140 modules exist for OpenSSL, wolfSSL, and others—verify current certifications per use case.

Memory safety remains a shared risk in C implementations. No production library here uses widespread formal verification.

### Feature Support Summary (2026)

All libraries support TLS 1.2/1.3 core functionality. Differences appear in edge protocols and extensions.

- **QUIC**: Strongest native support in BoringSSL and recent OpenSSL; wolfSSL competitive.
- **Post-Quantum/Hybrid**: wolfSSL and BoringSSL forks show advanced integration (ML-KEM hybrids); OpenSSL via OQS.
- **DTLS**: Full support across all, with 1.3 variants varying.
- **Legacy**: All can disable SSLv2/3 and TLS 1.0/1.1 at compile or runtime.
- **Hardware/PKCS#11**: OpenSSL ecosystem broadest; embedded libraries excel on specific MCUs.
- **API Style**: OpenSSL-compatible (wolfSSL, LibreSSL, BoringSSL); distinct but cleaner in mbedTLS and GnuTLS.

### Licensing

- OpenSSL / BoringSSL: Apache 2.0-style.
- LibreSSL: BSD/ISC permissive.
- GnuTLS: LGPL (copyleft considerations for static linking).
- wolfSSL: GPLv3 + commercial dual.
- mbedTLS: Apache 2.0 / GPLv2 + commercial.

### Recommendations by Deployment Class

**General-purpose servers and maximum compatibility**: OpenSSL (widest tooling) or BoringSSL derivatives for performance.

**High-security, auditable environments**: LibreSSL.

**Embedded / IoT / constrained devices**: wolfSSL (speed + features) or mbedTLS (modularity + documentation). wolfSSL frequently preferred for throughput on Cortex-class hardware.

**Google ecosystem or Chrome-aligned stacks**: BoringSSL.

**GNU/Linux tool integration**: GnuTLS, with attention to validation robustness.

**General guidance**: Minimize enabled features at compile time. Benchmark in target environment. Prioritize latest maintained versions. Configuration and certificate handling dominate security outcomes more than library choice alone.

These notes draw from vendor data, independent benchmarks (HAProxy, curl comparisons), and public vulnerability records as of mid-2026. Actual results depend on hardware, compiler flags, and workload.
