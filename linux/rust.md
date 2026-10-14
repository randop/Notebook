# Profiling & Benchmarking Rust on Linux

## 0. Groundwork (do this first)

Most tools are useless without symbols and correct build flags:

```toml
# Cargo.toml
[profile.release]
debug = true   # or "line-tables-only" — embeds symbols, doesn't slow codegen
```

- **Don't** set `strip = true` — it destroys profiling symbols.
- Frame pointers make stack unwinding cheap: `RUSTFLAGS="-C force-frame-pointers=yes"` (tiny codegen cost), otherwise use DWARF unwinding in `perf`.
- Allow unprivileged `perf`: `sudo sysctl kernel.perf_event_paranoid=1`

---

## 1. Benchmarking tools

| Tool | What it does | Use when |
|---|---|---|
| **criterion** | Statistical micro-benchmarks, outlier detection, regression comparison vs. baselines | Default choice for functions/algorithms |
| **divan** | Lighter, attribute-driven benches, fast compile | Simpler iteration, big bench suites |
| **iai-callgrind** | Deterministic instruction/L1/LLC counts via valgrind | CI regression gates (immune to machine noise) |
| **hyperfine** | Whole-binary wall-clock comparison | Comparing executables/flags/whole programs |
| **wrk / oha / vegeta / k6** | HTTP load generation | Servers, end-to-end latency (p50/p99/p999) |

**Criterion workflow:**

```rust
use criterion::{criterion_group, criterion_main, Criterion};
use std::hint::black_box;

fn fib_bench(c: &mut Criterion) {
    c.bench_function("fib 20", |b| b.iter(|| fib(black_box(20))));
}
criterion_group!(benches, fib_bench);
criterion_main!(benches);
```

```bash
cargo bench -- --save-baseline before   # before changes
# ...make changes...
cargo bench -- --baseline before        # criterion reports statistically-significant deltas
```

`black_box` is critical — LLVM will happily optimize away the work you're measuring. Use `iter_batched` when setup/teardown must be excluded.

---

## 2. CPU profiling

**`perf`** — the Linux standard (sampling + hardware counters):

```bash
perf stat -d ./app                      # counters: IPC, cache misses, branch misses
perf record -F 199 --call-graph dwarf,16384 -o perf.data ./app
perf report --no-children --demangle    # self-time sorting, demangled Rust symbols
perf annotate my_func                   # per-line source attribution
perf c2c record -- ./app && perf c2c report   # detect false sharing (concurrent code!)
```

**cargo-flamegraph** — wraps `perf`, renders SVG flame graphs:

```bash
cargo install flamegraph
cargo flamegraph --bin myapp
# attach to running process:
flamegraph -p $(pidof myapp) -o out.svg
```

**Others:**
- **speedscope** (`npx speedscope perf.txt` from `perf script` output) — interactive, handles deep stacks well.
- **valgrind --tool=callgrind** — deterministic instruction/call counts (10–50× slowdown, but no sampling skew); view with **kcachegrind**.
- **pprof-rs** — embed a CPU profiler in your app, export Google pprof format; integrates with Pyroscope/Parca for continuous production profiling.

---

## 3. Memory profiling

| Tool | Strength |
|---|---|
| **valgrind --tool=massif** | Heap growth over time (add `--stacks=yes`); view with `ms_print` |
| **heaptrack** | Peak usage, allocation sites, flame graphs, GUI |
| **dhat-rs** | Allocation lifetimes, call paths, short-lived-alloc detection; runs in-process |
| **bytehound** | LD_PRELOAD-based; timeline, fragmentation, leak analysis |

```rust
// dhat-rs
#[global_allocator]
static ALLOC: dhat::Alloc = dhat::Alloc;

fn main() {
    let _profiler = dhat::Profiler::builder().testing().build();
    // ... code under test; reports total/peak blocks & bytes
}
```

---

## 4. Async-specific (Tokio)

- **tokio-console** — live task inspector: waker counts, poll durations, tasks stuck on wakers, self-wakes. The single most valuable tool for "why is my async app slow."
  ```bash
  RUSTFLAGS="--cfg tokio_unstable" cargo run   # app must call console_subscriber::init()
  tokio-console                                # TUI connects on :6669
  ```
- **tracing-chrome** — export span timelines, open in [ui.perfetto.dev](https://ui.perfetto.dev). Async call stacks often *don't* map to thread stacks, so timeline views beat flame graphs here.

---

## 5. Off-CPU & system-level

`perf` only shows **on-CPU** time. If the app is slow but CPU is idle, look here:

- `strace -c -f ./app` — syscall frequency + time summary (cheap triage)
- **BCC tools**: `offcputime` (blocked-time stacks), `biolatency`, `funclatency`, `tcpretrans`
- **bpftrace** for custom probes, e.g. latency histograms on arbitrary functions without recompiling
- **sysstat** suite: `pidstat`, `iostat`, `vmstat`

---

## 6. In-code instrumentation

- **`tracing`** crate — structured spans/events, the ecosystem standard:
  ```rust
  #[tracing::instrument]
  async fn handle(req: Request) -> Response { /* spans auto-timed */ }
  ```
  Use `tracing_subscriber::EnvFilter` + `RUST_LOG`, and compile-time level filters (`tracing::max_level_info!()`) to zero out disabled-level overhead in release.
- **`metrics`** crate — counters/gauges/histograms → Prometheus exporters.
- **OpenTelemetry** (`tracing-opentelemetry`) — distributed traces across services.

Adjacent but often needed: **sanitizers** on nightly — `RUSTFLAGS="-Zsanitizer=thread"` (data races), `-Zsanitizer=address` (memory errors).

---

## 7. Triage: symptom → tool

| Symptom | Reach for |
|---|---|
| CPU pegged | `perf` / flamegraph |
| Slow, low CPU | `offcputime`, `strace -c`, tokio-console |
| Memory grows / high RSS | massif, heaptrack, dhat |
| Throughput plateaus under load | perf + lock contention, `pidstat`, load-gen percentiles |
| Scaling across threads is bad | `perf c2c` (false sharing), tsan |

---

## 8. The process (repeatable workflow)

1. **Define the goal** — p99 latency, throughput, peak RSS. Vague targets produce vague benchmarks.
2. **Reproduce realistically** — representative data sizes, real serialization, actual contention. Benchmarks on `Vec<u8>` mislead production `HashMap<String, Vec<T>>` code.
3. **Baseline end-to-end** — `hyperfine` or a load generator; record percentiles, not just means.
4. **Triage** with the table above — CPU vs. off-CPU vs. memory vs. I/O.
5. **Profile to find hotspots** — flamegraph; drill down via `perf annotate`.
6. **Isolate the hypothesis** — write a `criterion` bench for just the suspected code path.
7. **Fix, then verify** — compare against the saved baseline. For micro-optimizations, check what codegen you actually got: `cargo asm path::to::func` (cargo-show-asm) or godbolt.
8. **Lock it in** — CI benchmark (iai-callgrind for determinism, or criterion baselines via `github-action-benchmark`/bencher.dev).

---

## 9. Methodology: rigor & pitfalls

**Environment control (for stable numbers):**

```bash
sudo cpupower frequency-set -g performance       # no frequency scaling
echo 1 | sudo tee /sys/devices/system/cpu/intel_pstate/no_turbo  # no turbo boost
taskset -c 2 ./bench                             # pin to one core
```

Plus: quiet machine (no browsers/cron), multiple runs to check variance, and consider disabling ASLR (`setarch $(uname -m) -R`) for cache-sensitive code.

**Statistical rules:**
- Distributions, not means — report median, MAD, and tail percentiles (criterion does this correctly by default).
- Warm-up first (JIT-less, but caches/page-tables/branch predictors still warm up).
- Watch variance before trusting deltas: a "10% improvement" with 15% run-to-run noise is nothing.

**Rust-specific gotchas:**
- Dead-code elimination eating your benchmark → `black_box`.
- Monomorphization: the same generic appears as many symbols in profiles; consider `--no-children` view.
- Heavy inlining flattens flame graphs — iterator chains vanish; use `perf annotate` / disassembly instead.
- The allocator is part of the measurement: try `mimalloc`/`jemalloc` via `#[global_allocator]` and benchmark it.
- Build config *is* a performance lever — benchmark `lto = "thin"`, `codegen-units = 1` independently.
- Async code on runtime threads vs. blocking syscalls: tokio-console exposes this; `spawn_blocking` fixes it.

---

**Minimal starter kit:** `perf` + `cargo-flamegraph` + `criterion` + `hyperfine` + `heaptrack` covers ~90% of cases. Add `tokio-console` and `tracing-chrome` for async apps, `dhat-rs` for allocation-heavy work, and `iai-callgrind` for CI regression gates.
