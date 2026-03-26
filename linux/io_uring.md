# io_uring

io_uring is an asynchronous I/O interface in the Linux kernel, introduced in kernel version 5.1. It enables user-space applications to submit I/O requests to the kernel and receive completions without the overhead of traditional system calls for each operation.

The core structure consists of two ring buffers shared between user space and the kernel via memory mapping: the **submission queue (SQ)** and the **completion queue (CQ)**. These rings are single-producer, single-consumer structures with power-of-two sizes, enabling lock-free access coordinated by memory barriers. The SQ holds **submission queue entries (SQEs)**, which describe operations to perform. The CQ holds **completion queue entries (CQEs)**, which report results.

### Setup
An io_uring instance is created with the `io_uring_setup(2)` system call. It takes a requested number of entries (power of two, up to 4096 by default) and a `struct io_uring_params` that specifies configuration options. The kernel returns a file descriptor for the instance and populates the params structure with details such as actual queue sizes, offsets for ring fields, and supported features.

After setup, the application maps three regions into its address space using `mmap(2)` on the returned file descriptor:
- SQ ring metadata (head, tail, mask, flags, etc.) at offset `IORING_OFF_SQ_RING`.
- CQ ring metadata (and its array of CQEs) at offset `IORING_OFF_CQ_RING`.
- The array of SQEs at offset `IORING_OFF_SQES`.

Some kernels support a single mmap for SQ and CQ rings via the `IORING_FEAT_SINGLE_MMAP` feature. The rings use atomic updates to head and tail pointers for coordination. User space advances the SQ tail after filling SQEs; the kernel advances the SQ head after consuming them. The kernel advances the CQ tail after adding CQEs; user space advances the CQ head after processing them.

### Submission Queue Entries (SQEs)
Each SQE is a structure (`struct io_uring_sqe`) containing:
- `opcode`: The type of operation (e.g., `IORING_OP_READV`, `IORING_OP_WRITEV`, `IORING_OP_ACCEPT`, `IORING_OP_SENDMSG`, `IORING_OP_NOP`, timeouts, fsync, etc.).
- `fd`: The target file descriptor.
- `off` / `addr` / `len`: Offset, buffer address, and length (or other operation-specific uses).
- `flags`: Modifiers such as `IOSQE_IO_LINK` (links to the next SQE for chained execution), `IOSQE_FIXED_FILE` (uses registered files), `IOSQE_IO_DRAIN`, etc.
- `user_data`: A 64-bit value for application-specific correlation with the corresponding CQE.
- Operation-specific fields (e.g., `iovec` arrays for vectored I/O, poll events, or msg flags).

SQEs are filled by user space at the current SQ tail index (modulo the ring mask) and then the tail pointer is atomically updated. Multiple SQEs can be prepared before submission.

### Submission
Submission occurs via the `io_uring_enter(2)` (or `io_uring_enter2(2)`) system call on the ring's file descriptor. Parameters include:
- `to_submit`: Number of SQEs to submit from the tail.
- `min_complete`: Minimum number of CQEs to wait for before returning (0 for non-blocking).
- `flags`: Options such as `IORING_ENTER_GETEVENTS` (wait for completions), `IORING_ENTER_SQ_WAKEUP`, or signal masking.

This call can both submit new requests and/or wait for completions in one invocation. In certain configurations (e.g., with a kernel worker thread via `IORING_SETUP_SQPOLL`), submission can occur with reduced or zero system calls after initial setup. The kernel processes SQEs asynchronously, often using its own worker threads or direct execution paths.

### Completion Queue Entries (CQEs)
Each completed operation produces exactly one CQE (`struct io_uring_cqe`) containing:
- `user_data`: Matches the value from the originating SQE.
- `res`: Result of the operation (e.g., bytes transferred, file descriptor returned, or negative error code).
- `flags`: Additional information (e.g., `IORING_CQE_F_BUFFER` for provided-buffer usage, or buffer ID).

User space reads CQEs from the CQ head, processes them, and advances the head pointer. The kernel may coalesce or handle batches efficiently.

### Registration
The `io_uring_register(2)` system call registers resources to reduce per-operation overhead:
- Files (fixed file table for fast lookup via index instead of fd).
- User buffers (fixed buffers for zero-copy-like access in some operations).
- Eventfd for notification.
- Personalities (credential sets).
- Restrictions (to limit allowed operations for security).
- Provided buffer rings (for receive operations to supply buffers directly).

Registered resources allow operations like `IORING_OP_READ_FIXED` or `IORING_OP_WRITE_FIXED`.

### Operation Types and Features
Supported opcodes cover a broad range of system calls, including but not limited to:
- File I/O: readv/writev, read/write with fixed buffers, fsync, fdatasync, sync_file_range.
- Network: accept, connect, sendmsg/recvmsg, send/recv (including zero-copy variants), shutdown.
- Other: openat/close, statx, poll/epoll-like, timeouts, nop, cancel, splice/tee, rename/unlink, mkdir, etc.

Features include:
- **Linking**: `IOSQE_IO_LINK` chains SQEs so the next executes only after the prior completes successfully (or with `IOSQE_IO_HARDLINK` for unconditional chaining). This enables ordered sequences without user-space round-trips.
- **Timeouts**: Per-operation or absolute timeouts linked to SQEs.
- **Cancellation**: Asynchronous or synchronous cancel of in-flight requests via dedicated opcodes.
- **Provided buffers**: Pre-registered buffer rings for operations like recv to avoid dynamic allocation.
- **Polling modes**: Kernel-side polling for certain file types to reduce latency.
- **Multishot**: Some operations (e.g., accept multishot) produce multiple CQEs from one SQE.

The kernel executes operations in a manner that respects dependencies from linking and drain flags, while allowing parallelism where possible. Each SQE maps to one CQE upon completion (success or failure).

### Memory and Access Model
All communication uses the shared rings and mapped SQE array, minimizing data copies between user and kernel space for the request descriptors themselves. Actual I/O buffers remain in user space (or registered regions) and are accessed directly by the kernel where supported. Access to ring pointers requires appropriate memory barriers for correctness across threads or CPU cores. The design supports both single-threaded and multi-threaded use, with the kernel handling concurrency internally.

This structure allows batching of submissions and completions, reducing syscall frequency and context-switch overhead while maintaining asynchronous semantics. The interface is defined in kernel headers (`linux/io_uring.h`) and supported by user-space helpers in libraries like liburing.
