# Futexes

**Futexes** (short for **"fast user-space mutex"**) are one of the most important low-level synchronization primitives in modern Linux.

They are **not** a mutex themselves — they are a **very clever building block** that allows libraries (like glibc/pthreads, Rust std, Go runtime, etc.) to build extremely efficient mutexes, condition variables, semaphores, barriers, and more.

### Core Idea in One Sentence

> A futex lets you **wait** or **wake** on a simple 32-bit integer located in **user-space memory**, and the kernel gets involved **only when there is actual contention**.

This is the key to great performance: **no system call = nanoseconds**, contended case with kernel = still microseconds (much better than old-style always-kernel mutexes).

### How It Works — The Classic Pattern (Mutex Example)

Most real-world implementations follow this pattern (simplified):

```c
// 0   = unlocked
// 1   = locked, no waiters
// ≥2  = locked, has waiters (or various other conventions)

void mutex_lock(atomic_uint32_t* f) {
    uint32_t old = 0;

    // Fast path: try to take it without contention
    if (atomic_compare_exchange_strong(f, &old, 1)) {
        return;               // Success! No syscall
    }

    // Slow path: someone has it → we need to wait
    do {
        if (old == 1) {
            old = 2;          // Mark "I will wait"
            atomic_compare_exchange_weak(f, &old, 2);
        }

        // Tell kernel: "put me to sleep if value is still 2"
        if (old != 0) {
            futex_wait(f, 2); // ← kernel syscall only if still contended
        }

        old = atomic_load(f);
    } while (old != 0);
}

void mutex_unlock(atomic_uint32_t* f) {
    uint32_t old = atomic_exchange(f, 0);  // Try simple unlock

    if (old >= 2) {                        // Someone was waiting!
        futex_wake(f, 1);                  // Wake 1 waiter (syscall)
    }
}
```

### The Two Most Important Operations

| Operation       | Syscall name       | What it does                                                                 | When called?           | Cost if no contention |
|-----------------|--------------------|-----------------------------------------------------------------------------|------------------------|-----------------------|
| `FUTEX_WAIT`    | futex(uaddr, FUTEX_WAIT, expected_val, ...) | Atomically check if `*uaddr == expected_val` → if yes → sleep in kernel queue | Almost always in slow path | — (syscall happens)   |
| `FUTEX_WAKE`    | futex(uaddr, FUTEX_WAKE, nr_wake)           | Wake up `nr_wake` waiters on this futex address                             | When unlocking + waiters present | — (syscall happens)   |

There are many more operations nowadays:

- `FUTEX_WAIT_BITSET`, `FUTEX_WAKE_BITSET`
- `FUTEX_REQUEUE` / `FUTEX_CMP_REQUEUE` (very useful for condvars)
- `FUTEX_WAKE_OP` (atomic wake + modify another futex)
- Priority-inheritance variants (`FUTEX_LOCK_PI`, `FUTEX_UNLOCK_PI`, …)
- `FUTEX_WAITV` (wait on many futexes at once — since Linux 5.16, very useful for games/Wine)

### Performance Reality (2024–2026 era)

Uncontended (most common case in well-designed programs)  
→ **pure userspace atomic operations** → ~5–30 nanoseconds

Light contention  
→ few futex syscalls → microseconds

Heavy contention  
→ kernel scheduler decides fairly who wakes up → still reasonable

**Modern pthread_mutex_t on Linux** (NPTL) is basically a very carefully tuned futex wrapper → performance is **excellent**.

### Quick Comparison Table

| Primitive              | Uncontended cost       | Contended behavior                        | Who uses it under the hood?         |
|------------------------|------------------------|-------------------------------------------|-------------------------------------|
| Old kernel mutex       | Always syscall         | Kernel handles everything                 | Very old libraries                  |
| **Futex-based mutex**  | Pure userspace atomics | Syscall only on contention/sleep/wakeup   | **glibc pthread**, Go, Rust, Java, … |
| Spinlock               | Pure userspace         | Burns CPU while waiting                   | Kernel mostly, sometimes userspace  |
| Userspace ticket lock  | Pure userspace         | Fair, but can burn CPU on high contention | Sometimes in HPC                    |

### Visual Summary — Uncontended vs Contended Path

```
Uncontended lock (99%+ cases in good programs)
       ┌───────────────┐
       │ atomic cmpxchg │  ← pure userspace, super fast
       └───────┬───────┘
               │ success
               ▼
          Got the lock ✓

Contended case
       ┌───────────────┐
       │ atomic cmpxchg │  → failed
       └───────┬───────┘
               │
          ┌────▼────┐
          │ FUTEX_WAIT │  ← syscall → sleep
          └────┬─────┘
               │ someone unlocks
               ▼
          ┌──────────┐
          │ FUTEX_WAKE │  ← syscall → wake one thread
          └──────────┘
```

