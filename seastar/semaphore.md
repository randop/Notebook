# Semaphore

**Seastar C++23 Coroutine TCP Server Notes: Concurrency Control with Semaphores**

Seastar operates on a shared-nothing, thread-per-core model. Each shard runs a single reactor thread with cooperative scheduling. Coroutines (C++20/23) suspend at `co_await` points without kernel involvement, enabling high concurrency within a shard. A typical TCP listener spawns connection handlers that execute concurrently on the same core. Without bounds, this leads to resource exhaustion, reactor stalls, and memory pressure under load.

### Primary Races and Overload Vectors
- Unbounded concurrent request processing consumes excessive memory for buffers, parser state, and temporary objects.
- Shared per-shard structures (counters, caches, connection pools) accessed across suspension points create data races or visibility issues.
- Slow clients or backend operations hold resources indefinitely, starving other connections.
- Cross-shard access requires explicit message passing; intra-shard races need local synchronization.

### Semaphore-Based Concurrency Limiting
`seastar::semaphore` provides efficient user-space counting and waiting primitives tailored for the reactor model. It prevents overload by enforcing hard limits on in-flight work while preserving cooperative behavior.

**Core Pattern: with_semaphore in Request Handlers**

```cpp
thread_local seastar::semaphore request_limit{512};  // Tune to memory/CPU budget

future<> handle_request(connected_socket sock) {
    return with_semaphore(request_limit, 1, [&]() -> future<> {
        // Safe to co_await here; limit enforced
        auto req = co_await parse_request(sock.input());
        auto resp = co_await process(req);           // CPU or backend work
        co_await send_response(sock.output(), resp);
        co_return;
    });
}
```

**Accept Loop Limiting (Preferred for Connection Backpressure)**

```cpp
future<> run_server(server_socket listener) {
    semaphore conn_limit{1024};

    return keep_doing([&] {
        return get_units(conn_limit, 1).then([&](semaphore_units<> units) {
            return listener.accept().then([u = std::move(units)](accept_result ar) mutable {
                // Handler runs under limit; units released on completion
                (void)handle_connection(std::move(ar.connection))
                    .finally([u = std::move(u)]() mutable {});

                return make_ready_future<stop_iteration>(stop_iteration::no);
            });
        });
    });
}
```

`with_semaphore` internally uses `get_units` + `finally` for exception safety and works directly with coroutine lambdas.

### Performance Characteristics
- **Uncontended path**: Single integer decrement/check. Minimal overhead — a handful of instructions plus function call cost. Negligible compared to network I/O or coroutine frame allocation.
- **Contended path**: Waiters placed in an expiring FIFO queue. Wakeups are efficient promise fulfillments with no kernel transitions.
- High-contention scenarios increase queue management cost and tail latency but still outperform naive unbounded execution, which causes cache thrashing and allocator pressure.
- Real-world systems (ScyllaDB, Redpanda) routinely apply similar limits in hot paths and achieve millions of operations per second across cores with stable latency profiles.
- Binary semaphore (count=1) for shared state acts as a lightweight mutex. Use sparingly; it serializes execution and reduces effective concurrency.

**Tuning Guidelines**:
- Base limit on per-request memory footprint and available DRAM per core.
- Start at 256–1024 concurrent units for typical request/response workloads.
- Monitor reactor utilization, memory allocation rates, and semaphore wait queues under load.
- Combine with `seastar::gate` for shutdown: close gate first, then wait for in-flight work.

### Limitations and Trade-offs
- Semaphores address only per-shard concurrency. Cross-shard coordination requires `smp::submit_to` or foreign pointers.
- Global mutex-style usage kills parallelism; prefer isolated per-connection state or lock-free structures where possible.
- Deadlock risk exists with multiple semaphores if acquisition order is inconsistent.
- Overly tight limits under-utilize CPU; overly loose limits allow overload. Profiling with Seastar's built-in metrics and stall detector is mandatory.

