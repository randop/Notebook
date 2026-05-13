# DMA

**Seastar v25.05: DMA Write Corruption (Phantom Nulls) with TCP Buffer Sources**

**Observed Issue**  
Intermittent partial zero-byte insertion occurs in data written via DMA paths when the source buffer originates from the native TCP stack (temporary_buffer from socket input). Corruption appears as arbitrary byte ranges replaced with nulls in the output file, despite correct source content at submission time. The problem is load- and timing-dependent.

**Root Cause Characteristics**  
- DMA file writes (O_DIRECT style) require source buffers to remain valid and unmodified until DMA completion.
- TCP receive buffers are frequently recycled by the network stack immediately after `input_stream::read()` or `read_some()` returns.
- Race window exists between `dma_write()` submission and kernel completion, particularly under high concurrency or with small/medium I/O sizes.
- Alignment mismatches or partial DMA units exacerbate the issue.

**Mandatory Mitigation: file_output_stream**  
All TCP-to-file paths in v25.05 **must** use `seastar::make_file_output_stream` (with `file_data_sink_impl`). Direct `file::dma_write()` calls on TCP buffers are unsupported for this use case.

**Recommended Implementation**

```cpp
seastar::file_output_stream_options opts;
opts.buffer_size = 128 * 1024;                    // Multiple of disk_write_dma_alignment()
opts.write_behind = 4;                            // Adjust based on concurrency and memory budget
opts.preallocation_size = 4 << 20;

auto file = co_await seastar::open_file_dma(filename, 
    seastar::open_flags::rw | seastar::open_flags::create | seastar::open_flags::truncate);

auto out = co_await seastar::make_file_output_stream(std::move(file), std::move(opts));

// Preferred high-level path
auto in = conn.input();
co_await seastar::copy(std::move(in), std::move(out));

// Manual path (if required)
while (auto buf = co_await in.read_some(128 << 10)) {
    co_await out.write(std::move(buf));           // Ownership-taking overload preferred
}
co_await out.close();                             // Mandatory
```

**Critical Usage Rules (v25.05)**
- Always invoke `close()` (or explicit `flush()`) before stream destruction.
- Use the `temporary_buffer&&` overload of `output_stream::write()` when possible to avoid extra copies.
- Buffer size must respect `file::disk_write_dma_alignment()`.
- `write_behind` > 1 enables concurrent DMA submissions; monitor memory usage.
- Preallocation recommended for sustained sequential writes.

**Additional Controls**
- Filesystem: XFS with proper alignment.
- Kernel: Verify no io_uring / O_DIRECT conflicts.
- Debugging: Enable `seastar=debug` logging; inspect DMA submission vs. completion paths.
- Validation: Compare source buffer checksums immediately before stream write against final file content.

**Workarounds if Corruption Persists**
- Force internal copy by using `out.write(const char*, size_t)` overload.
- Increase stream buffer size to reduce submission frequency.
- Downgrade to POSIX write path for validation (non-production).

This configuration aligns with the intended DMA lifetime and alignment management in Seastar v25.05 file sink implementation.
