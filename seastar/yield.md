# Yield

## What `seastar::yield()` Accomplishes

### The Core Mechanic

```cpp
co_await seastar::yield();
```

This suspends the current coroutine **immediately and unconditionally**, posts a resumption task onto the reactor's task queue, and returns control to the reactor loop — which then processes all other pending tasks/I/O completions before picking yours back up. It produces no I/O; it is a **pure cooperative scheduling primitive**.

---

### Why This Matters in a TCP Accept Loop

Seastar's threading model is **run-to-completion** on each shard. There is no preemption. The reactor only gets control when a coroutine suspends at a `co_await` point. Consider:

```cpp
seastar::future<> accept_loop(seastar::server_socket listener) {
    while (true) {
        auto conn = co_await listener.accept();       // suspends on I/O → reactor runs
        handle_connection(conn.connection, conn.remote_address); // fire-and-forget
    }
}
```

Under high connection rates, `listener.accept()` may resolve **immediately** from an already-queued kernel event — meaning the loop body executes without ever truly yielding. This starves:

- In-flight connection handlers on the same shard
- Timer callbacks, background tasks, `seastar::sharded<>` RPC handlers

Inserting a yield breaks the monopoly:

```cpp
seastar::future<> accept_loop(seastar::server_socket listener) {
    while (true) {
        auto conn = co_await listener.accept();
        co_await seastar::yield();                    // explicit cooperative yield
        handle_connection(conn.connection, conn.remote_address);
    }
}
```

---

### Internal Mechanism

`yield()` is essentially:

```cpp
seastar::future<> yield() {
    seastar::promise<> p;
    auto f = p.get_future();
    seastar::engine().add_task(seastar::make_task([p = std::move(p)]() mutable {
        p.set_value();
    }));
    return f;
}
```

It enqueues a task on the **current shard's task queue** and returns a future that resolves only when the reactor drains ahead-of-queue work and reaches that task. The coroutine's frame is kept alive by the awaiting machinery — no copies, no heap allocation beyond the task node.

---

### `yield()` vs. Related Primitives

| Primitive | Behavior | Cost |
|---|---|---|
| `co_await seastar::yield()` | Always yields, resumes next reactor iteration | ~1 task queue round-trip |
| `co_await seastar::maybe_yield()` | Yields **only if** `seastar::need_preempt()` is true (quota exceeded) | Near-zero if not needed |
| `co_await seastar::sleep(0ms)` | Goes through timer infrastructure | Heavier; timer wheel involvement |
| `co_await seastar::later()` | Older alias; same intent as `yield()`, now deprecated in favor of it | Same |

**`maybe_yield()` is generally preferred in tight production loops** (read loops, processing loops) because it avoids the round-trip cost when the reactor isn't actually saturated. `yield()` is the right tool when you want a guaranteed scheduling point regardless of quota state — e.g., after accepting a connection where you specifically want handlers to get a turn.

---

### In a Per-Connection Read Loop

```cpp
seastar::future<> handle_connection(seastar::connected_socket s) {
    auto in  = s.input();
    auto out = s.output();

    while (true) {
        auto buf = co_await in.read();
        if (!buf) break;

        // CPU-bound processing here could monopolize the shard
        auto response = process(buf);

        co_await out.write(std::move(response));
        co_await out.flush();

        co_await seastar::maybe_yield(); // yield if shard is under pressure
    }
    co_await in.close();
    co_await out.close();
}
```

Without the yield point after CPU work, a single connection doing heavy processing blocks all other connections on that shard indefinitely — the pathological case of Seastar's cooperative model biting you.

---

### Summary

`seastar::yield()` is how you manually give the reactor a breath. In Seastar's share-nothing, no-preemption architecture, it is the **explicit contract** between your coroutine and the scheduler: *"I'm voluntarily suspending so others can run."* In TCP accept/handle loops specifically, it prevents a fast-arriving connection stream or a CPU-hungry handler from monopolizing a shard and causing latency spikes or starvation across all concurrent connections.
