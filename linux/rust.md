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


**Assisted-by:** z.AI GLM 5.3 Deep Think 

---

# Profiling and Benchmarking Rust on Linux

## 1. Methodology

1. **Define the goal and metric first**: latency (p50/p99), throughput, memory, binary size, or tail behavior. Different goals call for different tools.
2. **Benchmark before you profile.** Build a reproducible workload, take a baseline, then profile to find *where* time goes.
3. **Work top-down.** Use sampling profilers (low overhead) to find hot spots, then drill in with instruction-level tools (`perf annotate`, callgrind, cachegrind) or hardware counters.
4. **Change one thing at a time**, re-measure, and keep the baseline for comparison.
5. **Classify the bottleneck**: CPU-bound, memory/cache-bound, allocation-heavy, lock contention, I/O or syscall-bound, or async scheduling. Hardware counters (IPC, cache misses, branch misses) usually tell you which.
6. **Prove it statistically.** Use multiple runs and confidence intervals, not a single timing.

## 2. Build setup (this matters a lot)

Profile **release** builds with symbols and frame information:

```toml
# Cargo.toml
[profile.release]
debug = "line-tables-only"   # or true for full DWARF
# optional, for better stack traces / less inlining blur:
# lto = "thin"
# codegen-units = 1

[profile.bench]
inherits = "release"
debug = "line-tables-only"
```

```bash
# frame pointers give cheap, reliable stack unwinding for perf
RUSTFLAGS="-C force-frame-pointers=yes" cargo build --release

# optionally tune for the host CPU (be aware this reduces portability)
RUSTFLAGS="-C target-cpu=native -C force-frame-pointers=yes" cargo build --release
```

Notes:
- `-Z build-std` (nightly) rebuilds std with frame pointers if you need std frames to unwind cleanly.
- Consider `-C symbol-mangling-version=v0` for more readable symbols.
- Keep `panic`, `lto`, and `codegen-units` identical to what you ship, or you're profiling something different.

## 3. Benchmarking tools

| Tool | Use for |
|---|---|
| **Criterion.rs** (`criterion`) | Statistical micro/macro benchmarks, regression detection, HTML reports. Works on stable. |
| **Divan** | Newer, ergonomic benchmark framework with generics, allocation counting, and low-overhead timing. |
| **libtest `#[bench]`** | Built-in but nightly-only and basic. Mostly superseded. |
| **iai-callgrind** (recently renamed **Gungraun**) | Deterministic benchmarks measuring instruction counts and cache simulation via Valgrind. Great for noisy CI. |
| **hyperfine** | Black-box CLI benchmarking (whole-process wall time, warmup, comparisons, exports). |
| **Bencher** / **criterion-compare** / `critcmp` | Tracking and comparing results across commits or CI. |

Criterion example:

```rust
use criterion::{black_box, criterion_group, criterion_main, Criterion};

fn bench(c: &mut Criterion) {
    let input = make_input();
    c.bench_function("parse", |b| b.iter(|| parse(black_box(&input))));
}
criterion_group!(benches, bench);
criterion_main!(benches);
```

```bash
cargo bench -- --save-baseline main
# make changes
cargo bench -- --baseline main
critcmp main new
```

**Pitfalls:**
- Use `std::hint::black_box` on inputs and outputs so the optimizer doesn't delete the work.
- Keep setup out of the timed loop (`iter_batched`).
- Benchmark realistic data sizes. Cache effects change everything.

## 4. Reducing measurement noise

```bash
sudo cpupower frequency-set -g performance          # fixed governor
echo 1 | sudo tee /sys/devices/system/cpu/intel_pstate/no_turbo   # disable turbo (Intel)
taskset -c 2 ./target/release/app                   # pin to a core
# stronger: boot with isolcpus=, nohz_full=, or use cset/systemd slices
echo 0 | sudo tee /proc/sys/kernel/randomize_va_space   # optionally disable ASLR
```

Also consider disabling SMT for the test, closing other workloads, and warming caches. For CI, prefer instruction-count tools (iai-callgrind), which are deterministic.

## 5. CPU profiling (sampling)

### perf (the foundation on Linux)
```bash
# allow unprivileged profiling (or run as root)
sudo sysctl kernel.perf_event_paranoid=1
sudo sysctl kernel.kptr_restrict=0

perf stat -d ./target/release/app              # IPC, cache/branch misses, etc.
perf record -F 99 -g ./target/release/app      # frame-pointer unwinding
perf record --call-graph dwarf ./target/release/app   # DWARF unwinding (bigger data, works without fp)
perf record --call-graph lbr ./target/release/app     # Intel LBR (low overhead)
perf report --no-children
perf annotate                                   # instruction-level hot spots
```

Demangle Rust symbols with `perf report` (modern versions handle this) or pipe through `rustfilt`.

### Flamegraphs
```bash
cargo install flamegraph
cargo flamegraph --bin myapp -- <args>          # wraps perf
cargo flamegraph --bench my_bench -- --bench    # profile a benchmark
```
Also see `inferno` (Rust port of Brendan Gregg's FlameGraph tools).

### samply
```bash
cargo install samply
samply record ./target/release/app
```
It opens the Firefox Profiler UI, with good thread timelines and no kernel config fuss.

### Others
- **pprof-rs**: in-process sampling profiler that exports pprof or flamegraph output, useful in long-running services.
- **Intel VTune / AMD uProf**: microarchitecture analysis (top-down, memory bound, etc.).
- **Coz**: causal profiling ("what would speed up if this line were faster?"), via the `coz` crate.

## 6. Instrumenting / deterministic profilers (Valgrind family)

```bash
valgrind --tool=callgrind ./target/release/app
callgrind_annotate callgrind.out.<pid>          # or kcachegrind / qcachegrind
valgrind --tool=cachegrind --branch-sim=yes ./target/release/app
```
Callgrind and cachegrind are slow (20-50x) but exact and repeatable. Use them for small, focused workloads.

## 7. Memory and allocation profiling

| Tool | Purpose |
|---|---|
| **heaptrack** | Allocation hot spots, leaks, and peak memory, with a GUI. Low overhead. |
| **Valgrind massif** + `ms_print` | Heap usage over time. |
| **Valgrind DHAT** / **`dhat-rs`** crate | Allocation counts, lifetimes, and short-lived allocation churn. `dhat-rs` is native Rust and works as a global allocator. |
| **bytehound** | Detailed allocation timelines. |
| **jemalloc profiling** (`tikv-jemallocator` + `prof`) | Production-friendly heap profiling. |
| **`/usr/bin/time -v`**, `smem`, `/proc/<pid>/smaps` | Quick RSS/peak-memory checks. |
| **Divan** `AllocProfiler` / **`stats_alloc`** | Count allocations per benchmark iteration. |

Also try swapping the global allocator (mimalloc, jemalloc) as a quick experiment.

## 8. Concurrency, cache, and system-level tools

- `perf c2c`: finds **false sharing** and cache-line contention.
- `perf lock`, `perf sched`, and `perf trace`: lock contention, scheduling latency, and syscall tracing.
- `strace -c -f` / `strace -T`: syscall counts and latencies. `ltrace` for library calls.
- **bpftrace / BCC / `bpftool`**: dynamic tracing and custom probes (off-CPU analysis, latency histograms) with little overhead. USDT probes are available via the `probe` crates.
- **uftrace**: function tracer; with `-pg` / `-Z instrument-mcount` on nightly it can trace Rust functions.
- `iostat`, `pidstat`, `vmstat`, `iotop`, `ss`: spot resource-level issues.
- **Off-CPU flamegraphs** (via `perf sched` or eBPF) for time spent *blocked* rather than running.

## 9. Application-level instrumentation

**`tracing`** is the idiomatic Rust way:
- `tracing` + `tracing-subscriber`: structured spans and events.
- `tracing-flame`: flamegraphs from spans (works for async, where stack-based profilers struggle).
- `tracing-chrome`: output for `chrome://tracing` / Perfetto.
- `tracing-opentelemetry` / `tracing-tracy`: distributed traces and the **Tracy** frame profiler.
- `#[tracing::instrument]` for cheap per-function spans.

**Async / Tokio:**
- **tokio-console** (needs `--cfg tokio_unstable`): live view of tasks, polls, waker behavior, and long-running or blocked tasks.
- `tokio-metrics` and runtime metrics.
- Common culprits: blocking calls on the executor, too many tiny tasks, and lock contention. Sample stacks are hard to read through async state machines, so combine them with `tracing`.

**Metrics:** `metrics` or `prometheus` crates with Grafana, for production monitoring of latency histograms (`hdrhistogram`).

## 10. Compile-time and binary-size analysis

```bash
cargo build --release --timings          # compile-time breakdown
cargo llvm-lines                         # monomorphization bloat
cargo bloat --release --crates           # binary size by crate/function
cargo asm / cargo show-asm / cargo-expand   # inspect generated assembly / macros
```
Godbolt (Compiler Explorer) is great for small snippets. Also look at `cargo-llvm-cov` for coverage when deciding what to benchmark.

## 11. Putting it together: a typical workflow

1. Build release with debug line tables and frame pointers.
2. Write a representative workload (Criterion/Divan for functions, hyperfine for whole programs).
3. Control the environment (pin cores, performance governor).
4. `perf stat` → decide if you're CPU-, memory-, or stall-bound.
5. `perf record` / `cargo flamegraph` / `samply` → find hot paths.
6. Drill down with `perf annotate`, callgrind, or `cargo asm`; check allocations with heaptrack or DHAT.
7. For async or concurrent code, add `tracing` / tokio-console / `perf c2c`.
8. Make one change, rerun the benchmark with a saved baseline, and confirm statistical significance.
9. Add the benchmark (ideally with iai-callgrind for deterministic counts) to CI to catch regressions.

## 12. Further reading
- *The Rust Performance Book* (Nicholas Nethercote), the best Rust-specific optimization guide
- Brendan Gregg's *Systems Performance* and perf/flamegraph/eBPF pages
- Documentation for `criterion`, `divan`, `samply`, `tokio-console`, and `tracing`

**Assisted-by:** Anthropic Claude Sonnet 5.5

---


