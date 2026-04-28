# Design

Seastar v25.05 Notes — Futures, Coroutines, Sharding, and Local State

---

## 1. Returning values with `seastar::future<T>`

In Seastar, all asynchronous functions must return a `future<T>` rather than a raw value.

### ✔ Immediate return (no async work)

When there is no I/O or suspension, return a ready future:

```cpp
seastar::future<int> get_value() {
    return seastar::make_ready_future<int>(123);
}
```

---

## 2. Coroutine-style return (`co_return`)

Seastar supports C++ coroutines via `seastar/core/coroutine.hh`.

### ✔ Simple coroutine return

```cpp
#include <seastar/core/coroutine.hh>

seastar::future<int> get_value() {
    co_return 123;
}
```

This is equivalent to returning a ready future but expressed in coroutine form.

---

## 3. Awaiting a `future<int>`

A `future<int>` can be awaited inside a coroutine.

### ✔ Example

```cpp
seastar::future<int> get_value() {
    return seastar::make_ready_future<int>(123);
}

seastar::future<> run() {
    int value = co_await get_value();

    fmt::print("value = {}\n", value);

    co_return;
}
```

---

## 4. Functions with no delay or I/O

Even when a function performs no asynchronous work, it still must return a future.

### ✔ Recommended pattern

```cpp
seastar::future<int> get_value() {
    return seastar::make_ready_future<int>(123);
}
```

or:

```cpp
seastar::future<int> get_value() {
    co_return 123;
}
```

---

## 5. Incorrect patterns to avoid

### ❌ Returning raw values

```cpp
int get_value();  // invalid in Seastar async model
```

### ❌ Returning raw literals

```cpp
return 123; // invalid return type
```

---

## 6. Core model of Seastar futures

| Concept          | Representation            |
| ---------------- | ------------------------- |
| Immediate value  | `make_ready_future<T>(x)` |
| Coroutine return | `co_return x`             |
| Async pipeline   | `then(...)` chains        |

---

## 7. `sharded<>` with local state

Seastar sharding model is based on **per-core ownership**. Each shard owns its own instance of data.

### ✔ Local per-shard state (recommended pattern)

```cpp
#include <seastar/core/sharded.hh>
#include <seastar/core/lw_shared_ptr.hh>
#include <seastar/core/print.hh>

struct shard_state {
    seastar::lw_shared_ptr<seastar::sstring> buffer;
    uint64_t counter = 0;

    seastar::future<> start() {
        buffer = seastar::make_lw_shared<seastar::sstring>(
            fmt::format("shard-{}", this_shard_id())
        );
        return seastar::make_ready_future<>();
    }

    seastar::future<> stop() {
        buffer.reset();
        return seastar::make_ready_future<>();
    }

    void append(seastar::sstring x) {
        buffer->append(std::move(x));
        ++counter;
    }

    const seastar::sstring& get() const {
        return *buffer;
    }
};
```

---

## 8. Using `sharded<>`

Each shard executes independently on its own instance.

```cpp
seastar::sharded<shard_state> svc;
```

### ✔ Startup and execution

```cpp
return svc.start().then([&] {
    return svc.invoke_on_all([](shard_state& s) {
        s.append("::update");
    });
});
```

---

## 9. Memory and ownership rules

### ✔ Safe design rules

* Each shard owns its own memory
* No cross-shard references to mutable objects
* No shared mutable state
* Data stays local unless explicitly moved

---

## 10. `lw_shared_ptr<const T>` usage

Immutable shared objects can be safely distributed across shards.

### ✔ Pattern

```cpp
auto shared = seastar::make_lw_shared<const config_data>("name", 1);
```

All shards receive a reference to the same read-only object.

---

## 11. Important constraints for immutable shared data

* Object must not be modified after construction
* No `const_cast` usage
* No hidden mutation inside the structure
* Safe only for read-only workloads

---

## 12. Core Seastar design model

* Shards are ownership boundaries
* Futures represent asynchronous values
* Coroutines simplify control flow
* Shared mutable state across shards is avoided
* Each core operates independently

---

## Summary

Seastar programming is based on strict separation of:

* **Ownership (per shard)**
* **Asynchronous control flow (`future<T>`)**
* **Explicit immutability (`const` or read-only shared pointers)**

Correct design focuses on local state, explicit data movement, and non-blocking execution rather than shared mutation.

