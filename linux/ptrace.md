# ptrace

**ptrace** is a Linux system call that allows one process (the tracer) to observe and control the execution of another process or thread (the tracee), and to examine and change the tracee's memory and registers.

### Interface
```c
#include <sys/ptrace.h>

long ptrace(enum __ptrace_request op, pid_t pid,
            void *addr, void *data);
```

The `op` argument selects the operation. `pid` identifies the target thread (Linux treats threads as the unit of attachment; a multithreaded process may have individual threads attached independently). The meaning of `addr` and `data` depends on the operation.

### Attachment
A tracee must be attached before most operations can be performed.

- **PTRACE_TRACEME**: Issued by the process that wishes to be traced. It indicates that its parent is the tracer. Typically followed by an `execve`.
- **PTRACE_ATTACH**: Tracer attaches to an existing process/thread. The tracee is stopped (as if by `SIGSTOP`).
- **PTRACE_SEIZE** (Linux 3.4+): Attaches without stopping the tracee and without the side effects of `PTRACE_ATTACH` (no forced `SIGSTOP`). Options can be supplied at attach time. Stopping is later performed with `PTRACE_INTERRUPT`.

Attachment is per-thread. Once attached, the tracer becomes the effective parent for wait notifications related to the tracee.

### Stopping and notification
While traced, the tracee stops on:
- Delivery of any signal except `SIGKILL`.
- Certain events configured via options (clone, fork, vfork, exec, exit, etc.).
- System-call entry/exit when using `PTRACE_SYSCALL`.
- Single-instruction execution when using `PTRACE_SINGLESTEP`.

The tracer is notified via `waitpid` (or related wait calls). The status value indicates the reason for the stop (`WIFSTOPPED`, `WSTOPSIG`, event bits in the high byte, etc.). While the tracee is stopped, the tracer may inspect or modify it, then resume it.

### Memory and register access
- **PTRACE_PEEKTEXT / PTRACE_PEEKDATA**: Read one word from the tracee's address space at `addr`. On Linux these are equivalent (no separate text/data spaces).
- **PTRACE_POKETEXT / PTRACE_POKEDATA**: Write one word (`data`) into the tracee's address space at `addr`.
- **PTRACE_PEEKUSER / PTRACE_POKEUSER**: Read/write a word in the tracee's USER area (registers and related state, defined in `<sys/user.h>`). Offsets are architecture-dependent and typically must be word-aligned. Some USER-area modifications are disallowed by the kernel.
- **PTRACE_GETREGS / PTRACE_SETREGS** (and floating-point variants): Copy general-purpose or floating-point registers to/from a buffer pointed to by `data`.
- **PTRACE_GETREGSET / PTRACE_SETREGSET** (Linux 2.6.34+): More general register-set interface using `struct iovec`. `addr` selects the register set type (e.g., `NT_PRSTATUS`).

### Control operations
- **PTRACE_CONT**: Resume the stopped tracee. If `data` is nonzero it is treated as a signal number to deliver.
- **PTRACE_SYSCALL**: Resume, but stop again on the next system-call entry or exit. The stops appear as `SIGTRAP` (or `SIGTRAP|0x80` if `PTRACE_O_TRACESYSGOOD` is set).
- **PTRACE_SINGLESTEP**: Resume and stop after the next instruction.
- **PTRACE_SYSEMU / PTRACE_SYSEMU_SINGLESTEP**: Variants that emulate system calls (do not actually execute them).
- **PTRACE_INTERRUPT**: Stop a seized tracee without signal side effects.
- **PTRACE_DETACH**: Detach and resume the tracee in untraced mode (optionally delivering a signal).
- **PTRACE_KILL**: Send `SIGKILL` to the tracee (largely superseded by other methods).

### Signal handling
The tracer intercepts signals that would be delivered to the tracee. It can:
- Inspect the signal via `PTRACE_GETSIGINFO`.
- Modify or suppress it via `PTRACE_SETSIGINFO` or by choosing what to deliver on resume.
- Examine queued signals with `PTRACE_PEEKSIGINFO`.
- Read or change the blocked-signal mask with `PTRACE_GETSIGMASK` / `PTRACE_SETSIGMASK`.

### Options (`PTRACE_SETOPTIONS`)
A bit-mask that enables additional event stops and behaviors, including:
- `PTRACE_O_TRACECLONE`, `PTRACE_O_TRACEFORK`, `PTRACE_O_TRACEVFORK`, `PTRACE_O_TRACEEXEC`, `PTRACE_O_TRACEEXIT`, `PTRACE_O_TRACEVFORKDONE`
- `PTRACE_O_TRACESYSGOOD` (makes syscall stops distinguishable by setting the high bit of the signal)
- `PTRACE_O_EXITKILL` (send `SIGKILL` to the tracee if the tracer exits)
- `PTRACE_O_TRACESECCOMP`, etc.

Event stops produce a status of the form `(SIGTRAP | (event << 8))`. Additional information (new PID, exit status, former thread ID, etc.) is obtained with `PTRACE_GETEVENTMSG`.

### Kernel-side relationship
The kernel records the tracer in the task structure (`task_struct->ptrace` and related lists). Stops are coordinated with the signal-delivery and wait paths. Architecture-specific code handles register layout, single-step hardware support, and USER-area access. Access-mode checks (credentials, capabilities, dumpable state, LSMs such as Yama) gate whether a process may attach to or inspect another.

### Return value
On success, most operations return 0. `PTRACE_PEEK*` operations return the requested data word. On error, -1 is returned and `errno` is set. Because a successful peek can legitimately return the value -1, callers must clear `errno` before the call and check it afterward.

---

## ptrace scope

**Ptrace scopes** are controlled by the Yama Linux Security Module (LSM) via the sysctl `kernel.yama.ptrace_scope` (exposed at `/proc/sys/kernel/yama/ptrace_scope`).

Yama is compiled into the kernel when `CONFIG_SECURITY_YAMA` is enabled. The setting is writable only by a process holding `CAP_SYS_PTRACE`. It applies system-wide and governs operations that perform a `PTRACE_MODE_ATTACH` check (including `PTRACE_ATTACH`, `PTRACE_SEIZE`, and related access paths such as certain uses of `process_vm_readv`). `PTRACE_TRACEME` is also affected under higher values.

### Scope values (kernel documentation)

- **0 (classic ptrace permissions)**  
  A process may perform attach-style operations on any other process that runs under the same UID and is dumpable (has not transitioned UIDs, started privileged, or called `prctl(PR_SET_DUMPABLE, …)`). No additional Yama relationship check is applied. `PTRACE_TRACEME` is unrestricted beyond ordinary credentials.

- **1 (restricted ptrace)**  
  Attach-style operations require either:
  - the caller holds `CAP_SYS_PTRACE` in the user namespace of the target, or
  - a predefined relationship exists between caller and target.  
  By default the relationship is that the target is a descendant of the caller. A target may additionally declare an allowed tracer PID with `prctl(PR_SET_PTRACER, debugger_pid, …)` (or `PR_SET_PTRACER_ANY`). `PTRACE_TRACEME` remains unchanged from classic behaviour.

- **2 (admin-only attach)**  
  Only a process that holds `CAP_SYS_PTRACE` in the user namespace of the target may perform attach-style operations or receive a `PTRACE_TRACEME` from a child.

- **3 (no attach)**  
  No process may perform attach-style operations or use `PTRACE_TRACEME`. Once the value is written, it cannot be lowered until the next reboot.

The kernel’s built-in default (when no userspace override is present) is 1.

### Distribution defaults and configuration

**Ubuntu 22.04 LTS and 24.04 LTS (latest LTS)**  
Both ship `/etc/sysctl.d/10-ptrace.conf` (from the `procps` package) containing  
`kernel.yama.ptrace_scope = 1`.  
This is the active value on a stock installation. The same file has been used for many years.

**Arch Linux**  
The kernel default of 1 is used. No distribution package overrides it to 0. Arch is frequently cited alongside Ubuntu and openSUSE as following the upstream kernel default of 1.

**Fedora**  
Historically the package `elfutils-default-yama-scope` installed `/usr/lib/sysctl.d/10-default-yama-scope.conf` (or equivalent) that set the value to 0, and the package was pulled in by default via dependencies (including through `elfutils-libs` / systemd). As a result many Fedora systems ran with scope 0.  
Change proposals in the Fedora 44 timeframe remove or obsolete that package so that the kernel default of 1 becomes the system-wide value. On current systems the live value can still be 0 when the override package is present.

**Red Hat Enterprise Linux (RHEL) 8 / 9 and Oracle Linux**  
RHEL 9 and its rebuilds (including Oracle Linux corresponding releases) ship a file under `/usr/lib/sysctl.d/` (commonly from `elfutils-default-yama-scope` or an equivalent) that sets `kernel.yama.ptrace_scope = 0`. Consequently a stock installation reports 0. The same pattern appears in related rebuilds. CIS and STIG profiles for these releases commonly require the value to be raised to 1 (or higher).

### Additional notes on behaviour

- The setting is independent of SELinux (present on Fedora/RHEL/Oracle). SELinux may impose further restrictions on ptrace, but the Yama scope is evaluated as part of the ptrace access-mode checks.
- Containers inherit the host’s value; the sysctl is not namespaced.
- A process can call `prctl(PR_SET_PTRACER, …)` to declare an allowed tracer even when the global scope is 1.
- Changing the value at runtime (as root) takes effect immediately for subsequent attach attempts; persistence is achieved by writing a `.conf` file under `/etc/sysctl.d/` (or editing an existing one) and applying it with `sysctl --system` or a reboot.
