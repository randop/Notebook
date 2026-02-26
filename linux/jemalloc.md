# jemalloc

**Implementing guidelines for jemalloc on Arch Linux for Node.js, Java (JVM), and Bun.** Jemalloc is an excellent drop-in replacement for glibc's `malloc` that often reduces memory fragmentation, lowers RSS, and improves performance in multi-threaded workloads.

### 1. Install jemalloc on Arch Linux
```bash
sudo pacman -S jemalloc
```

- Library path: `/usr/lib/libjemalloc.so.2` (with symlink `/usr/lib/libjemalloc.so`).
- Useful tools: `jeprof` (for profiling), `jemalloc-config` (helper script).

**Verify:**
```bash
pacman -Ql jemalloc | grep libjemalloc
ls /usr/lib/libjemalloc*
jemalloc-config --libdir   # should output /usr/lib
```

**Recommended portable LD_PRELOAD (works even if soname changes):**
```bash
export LD_PRELOAD=$(jemalloc-config --libdir)/libjemalloc.so.$(jemalloc-config --revision)
```

**Optional tuning via `MALLOC_CONF`** (add to the same shell or service):
- Basic good defaults: `MALLOC_CONF=background_thread:true,metadata_thp:auto,dirty_decay_ms:1000,muzzy_decay_ms:1000`
- Aggressive reclaim: `dirty_decay_ms:0,muzzy_decay_ms:0`
- Profiling (for later analysis): `MALLOC_CONF=prof:true,lg_prof_sample:19,prof_final:true`

**Never** put jemalloc in `/etc/ld.so.preload` (system-wide) unless you fully understand the risks—some apps break.

### 2. Node.js
Arch's official `nodejs` package is dynamically linked against glibc, so LD_PRELOAD works perfectly.

**One-off:**
```bash
LD_PRELOAD=/usr/lib/libjemalloc.so.2 node your-app.js
# or with portable + tuning:
MALLOC_CONF=background_thread:true LD_PRELOAD=$(jemalloc-config --libdir)/libjemalloc.so.$(jemalloc-config --revision) node your-app.js
```

**Permanent options:**
- In `~/.bashrc` or `~/.zshrc` (for interactive shells):  
  ```bash
  export LD_PRELOAD=/usr/lib/libjemalloc.so.2
  export MALLOC_CONF=background_thread:true
  ```
- For PM2 / systemd / Docker: add the `LD_PRELOAD` and `MALLOC_CONF` to your service file or `ecosystem.config.js`.
- In Docker: `ENV LD_PRELOAD=/usr/lib/libjemalloc.so.2`

**Expected benefits:** Many users see 20-50% lower RSS and far less fragmentation on long-running servers. Monitor with `node --inspect` + Chrome DevTools or `pm2 monit`.

### 3. Java / JVM (OpenJDK, Temurin, etc.)
Jemalloc only replaces *native* allocations (off-heap, JNI, direct buffers, internal JVM structures). The Java heap is still managed by the GC. This is exactly where most fragmentation/leak issues occur on Linux.

**Run any Java app:**
```bash
LD_PRELOAD=/usr/lib/libjemalloc.so.2 \
MALLOC_CONF=background_thread:true,dirty_decay_ms:1000 \
java -jar your-app.jar
```

**For systemd services** (recommended for production):
Create a drop-in:
```bash
sudo systemctl edit your-service.service
```
Add:
```ini
[Service]
Environment=LD_PRELOAD=/usr/lib/libjemalloc.so.2
Environment=MALLOC_CONF=background_thread:true,dirty_decay_ms:1000,muzzy_decay_ms:1000
```

**Profiling native memory leaks:**
```bash
MALLOC_CONF=prof:true,lg_prof_sample:19,prof_final:true \
LD_PRELOAD=/usr/lib/libjemalloc.so.2 \
java -jar your-app.jar
```
Then generate report: `jeprof --pdf java /tmp/jeprof.*.heap > profile.pdf`

Works great with OpenJDK 17/21/23 on Arch. Thousands of production services (including large Spring Boot apps) use this exact pattern.

### 4. Bun
**Bun already uses mimalloc** (Microsoft's high-performance allocator) for its native (non-JS) heap, and JavaScriptCore for the JS heap. It is tuned specifically for Bun and generally outperforms or matches jemalloc in Bun's workloads.

**Recommendation:** Do **not** force jemalloc on Bun. It is unnecessary and can cause compatibility issues (e.g., page-size errors on aarch64 in some packages that bundle Node binaries).

**If you still want to experiment:**
```bash
LD_PRELOAD=/usr/lib/libjemalloc.so.2 bun run your-app.ts
```
But expect no improvement (or even slight regressions), and some Bun internals may ignore it because they bypass libc malloc.

Use Bun's built-in tools instead:
- `MIMALLOC_SHOW_STATS=1 bun run ...` for native heap stats.
- `bun --inspect` or `bun:jsc` module for JS heap.

### 5. Testing & Monitoring (do this for all runtimes)
1. Run your workload before/after with `htop`, `pmap -x <pid>`, or `smem`.
2. Check fragmentation: look at RSS vs. VSS and how memory behaves under load.
3. Profile: `MALLOC_CONF=prof:true,...` + `jeprof`.
4. Reboot or restart the service after changes.
5. Roll back if you see any crashes or strange behavior (very rare with jemalloc 5.3+).

### Quick one-liner wrappers (optional)
```bash
# ~/bin/node-jemalloc
#!/bin/bash
exec env LD_PRELOAD=/usr/lib/libjemalloc.so.2 MALLOC_CONF=background_thread:true node "$@"
```

Make executable and use `node-jemalloc your-app.js`.
