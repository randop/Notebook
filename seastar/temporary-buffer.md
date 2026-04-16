# Temporary Buffer

`temporary_buffer<T>` is Seastar's **primary buffer type for I/O data transfer**. It's a non-copyable, move-only handle to a contiguous region of memory with shared ownership semantics under the hood.


### Core Design

```cpp
temporary_buffer<char> buf;
```

Internally it holds:
- A **raw pointer** to the data (`char*`)
- A **size** (how many bytes this view covers)
- A **`deleter`** — a type-erased destructor/reference-count mechanism that owns the underlying allocation

The key insight: multiple `temporary_buffer` instances can **share the same backing allocation** but present different *windows* into it (like a slice). The memory is freed only when the last deleter referencing it is destroyed.

---

### Ownership & Lifetime

```cpp
// Move-only — no copies
temporary_buffer<char> buf2 = std::move(buf);  // OK
temporary_buffer<char> buf3 = buf;             // ❌ won't compile
```

You can **share** the backing store by splitting:
```cpp
temporary_buffer<char> left  = buf.share(0, 10);   // bytes [0, 10)
temporary_buffer<char> right = buf.share(10, 20);  // bytes [10, 30)
// both are alive → backing memory lives on
```

`share()` increments an internal reference count. The allocation is freed when all shares are gone.

---

### Key Methods

| Method | Description |
|---|---|
| `buf.get()` | Raw `const char*` pointer to data |
| `buf.get_write()` | Mutable `char*` — only safe if you own the buffer uniquely |
| `buf.size()` | Byte count of this view |
| `buf.empty()` | `size() == 0` |
| `buf.share()` | Shared sub-view (ref-counted) |
| `buf.trim(n)` | Shrink to first `n` bytes (no allocation) |
| `buf.trim_front(n)` | Advance start pointer by `n` bytes |
| `buf.release()` | Detach the deleter — *you* now own the memory |

---

### Typical Usage in Seastar I/O

```cpp
// Reading from a socket/stream
return in.read().then([](temporary_buffer<char> buf) {
    if (buf.empty()) {
        // EOF
        return;
    }

    // Process bytes without copying
    std::string_view sv(buf.get(), buf.size());
    do_something(sv);

    // buf destroyed here → memory freed (or ref-counted away)
});
```

Because Seastar's network/disk APIs return `temporary_buffer`, you get **zero-copy** I/O paths — the buffer points directly into DMA or receive rings when possible.

---

### Why Not `std::vector<char>`?

| | `vector<char>` | `temporary_buffer<char>` |
|---|---|---|
| Copyable | ✅ (heap alloc) | ❌ (move-only) |
| Zero-copy slicing | ❌ | ✅ via `share()` |
| Custom allocator/deleter | ❌ | ✅ |
| Seastar DMA integration | ❌ | ✅ |
| Ownership model | Exclusive | Shared (ref-counted) |

---

### The `deleter` Mechanism

The deleter is what makes it flexible — it can represent:
- A `free()`/`delete[]` for heap buffers
- A no-op for static/stack data
- A reference count for shared buffers
- A custom destructor (e.g., returning a buffer to a pool)

This lets Seastar pass buffers across subsystems without knowing where the memory came from.

