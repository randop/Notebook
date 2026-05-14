# C and C++ Coding Convention

This document defines project coding rules. It is intentionally strict: code must
be readable, portable, auditable, and boring in the best sense. Clever code that
cannot be reviewed quickly is not acceptable.

Influences:

- Seastar: async lifetime discipline, explicit namespace use, and ownership-aware
  continuation/coroutine style.
- Boost: clarity before optimization, ISO language use, good-neighbor headers,
  lowercase underscore naming, and conservative formatting.
- MongoDB C++ driver: enforced formatting, clear contribution workflow, stable
  public API boundaries, and driver-quality error handling.

Sources:

- Seastar tutorial: https://docs.seastar.io/master/tutorial.html
- Boost Library Requirements and Guidelines:
  https://www.boost.org/doc/libs/1_34_1/more/lib_guide.htm
- MongoDB C++ driver contributing guide:
  https://github.com/mongodb/mongo-cxx-driver/blob/master/CONTRIBUTING.md

## Core Rules

- Correctness comes first. Performance work must preserve readability unless a
  measured bottleneck justifies otherwise.
- Prefer ISO C and ISO C++. Do not use compiler extensions unless isolated behind
  a portability layer.
- Keep interfaces small, explicit, and hard to misuse.
- Avoid hidden ownership, hidden blocking, hidden allocation, and hidden global
  state.
- Every non-trivial function must have a clear failure model.
- Code must be easy to test without real network, filesystem, clock, or process
  dependencies unless the test is explicitly an integration test.
- Do not add dependencies casually. A dependency must remove real complexity or
  provide a proven implementation of a hard domain problem.
- Do not write "framework code" until repeated real use proves the abstraction.

## Language Standards

- C code uses C17 or newer when the build target supports it.
- C++ code uses C++23.
- Public APIs must not depend on implementation-only compiler behavior.
- Use feature-test macros or Meson checks for platform-specific functionality.
- Avoid mixed C and C++ ownership conventions at boundaries. Wrap C resources in
  C++ RAII types before they enter C++ business logic.

## Formatting

- Use spaces, never tabs.
- Indent with 4 spaces for C and C++.
- Keep lines at or below 100 columns. Exceed only when breaking the line hurts
  readability, such as long URLs or diagnostics.
- Put one declaration per line.
- Put braces on the same line for functions, control blocks, lambdas, classes,
  structs, enums, and namespaces.
- Always use braces for `if`, `else`, `for`, `while`, and `do`, even for one-line
  bodies.
- Keep include blocks separated in this order:
  1. Matching public header for a source file.
  2. C system headers.
  3. C++ standard library headers.
  4. Third-party headers.
  5. Project headers.
- Sort includes alphabetically within each block where practical.
- Do not align columns manually. Alignment churn creates noisy diffs.
- Let formatter output win for whitespace disputes.

## Naming

- Use lowercase names with underscores for namespaces, files, functions,
  variables, data members, enum values, and build targets.
- Use PascalCase only for public C++ types when matching an established local API.
  Otherwise prefer lowercase type names for library-style code.
- Template parameters use short PascalCase names when the role is conventional:
  `T`, `Allocator`, `Clock`, `Executor`.
- Macros are uppercase with the project prefix: `ENTPX_*`.
- Private data members end with `_`.
- Avoid abbreviations unless they are standard in the domain: `fd`, `dns`, `ntp`,
  `udp`, `tcp`, `tls`, `id`.
- Acronyms in identifiers are ordinary words: `dns_resolver`, not `DNSResolver`.
- Boolean names must read as predicates: `is_open`, `has_value`, `should_retry`.
- Names must say what the value means, not what type it has. Use `deadline`, not
  `time_point_value`.

## Files

- C headers use `.h`.
- C sources use `.c`.
- C++ headers use `.hpp`.
- C++ sources use `.cpp`.
- Public headers live under `include/<project>/`.
- Private implementation files live under `src/`.
- File names use lowercase underscores.
- A source file should include its matching header first.
- Headers must be self-contained: a user can include any public header alone and
  compile.
- Use `#pragma once` for C++ headers.
- Use include guards for C headers.

## Header Hygiene

- Headers must include what they use.
- Do not rely on transitive includes.
- Public headers should minimize dependencies. Prefer forward declarations when
  they do not make ownership or layout unclear.
- Do not put `using namespace` in headers.
- Do not expose implementation-only third-party types in public APIs unless the
  project is explicitly a binding for that library.
- Public headers must be good neighbors: no global macros except documented
  feature/config macros, no surprising pragmas, no global state initialization.
- Keep inline code in headers short and obvious.

## C++ Conventions

- Prefer RAII for every resource: file descriptors, sockets, memory, handles,
  locks, and library channels.
- Prefer values and move-only types over raw owning pointers.
- Use raw pointers only for non-owning nullable references.
- Use references for non-null borrowed objects.
- Use `std::unique_ptr` for unique heap ownership.
- Use `std::shared_ptr` only when shared lifetime is real and documented.
- Prefer `std::span`, `std::string_view`, and iterator pairs for borrowed ranges.
- Prefer `std::array` for fixed-size protocol buffers.
- Prefer `std::vector` for dynamic contiguous storage.
- Prefer `enum class` over unscoped enums.
- Prefer `constexpr` and `const` when they make invariants visible.
- Prefer `auto` when the type is obvious from the right-hand side or prevents
  repetition. Spell out types when they document protocol or ownership.
- Avoid inheritance unless modelling a stable polymorphic interface.
- Mark overriding functions with `override`.
- Mark single-argument constructors `explicit`.
- Mark non-throwing destructors and move operations `noexcept` where correct.
- Do not use C-style casts in C++.
- Do not use `new` or `delete` directly outside low-level ownership primitives.
- Do not use exceptions across C ABI boundaries.
- Do not use exception specifications other than `noexcept`.
- Avoid macros. Prefer constants, templates, inline functions, and scoped enums.

## C Conventions

- C APIs use a project prefix for all public symbols: `entpx_*`.
- Public structs exposed by C APIs must be stable, documented, and versioned when
  ABI stability matters.
- Prefer opaque handles for resources:
  `typedef struct entpx_resolver entpx_resolver;`.
- Every owning C resource must have a matching destroy function.
- Functions return explicit status codes for expected failures.
- Output parameters must be last.
- Pointer parameters must document ownership and nullability.
- Do not hide allocation in functions unless ownership is obvious from the name
  or documented in the API contract.
- Use `size_t` for sizes and indexes.
- Use fixed-width integer types for protocol fields.
- Avoid preprocessor conditionals in function bodies. Isolate platform code in
  small files or small helper functions.
- Do not use global mutable state without a documented initialization and
  shutdown contract.

## Error Handling

- Expected operational failures must carry enough context to diagnose the failed
  operation.
- C++ code may use exceptions for construction failures and unrecoverable local
  operation failures.
- C interfaces return status codes and expose error details through explicit
  error objects or caller-provided buffers.
- Never ignore return values from system calls, allocator-like APIs, parsers, DNS,
  network I/O, or serialization functions.
- Preserve `errno` before calling anything that might overwrite it.
- Retrying must be bounded and must respect deadlines.
- Error messages should name the operation, target, and relevant OS/library error.
- Do not log and throw for the same error unless crossing a process boundary.

## Async, Coroutine, and Event-Loop Rules

- Async functions must make ownership and lifetime explicit.
- If an async function takes a reference, the caller must keep that object alive
  until the returned operation completes.
- Do not capture stack references in coroutines or continuations unless lifetime
  is proven by structure and local review.
- Prefer moving state into async work over borrowing state.
- Keep coroutine awaiters small and single-purpose.
- Cancellation and timeout behavior must be explicit at every network boundary.
- Do not block inside event-loop code.
- Do not call process-exit functions from library code. Return errors to the
  caller and let the program entry point decide.
- Avoid detached work. If work is detached, document its shutdown path.
- Never let a coroutine silently swallow exceptions unless it is a top-level task
  converting failures to process-level output.

## Networking and Protocol Code

- Treat all network input as hostile.
- Validate packet length before reading fields.
- Use fixed-width integers for wire-format data.
- Keep byte-order conversion local and obvious.
- Do not use packed structs for network packets unless alignment, endian, and ABI
  behavior are proven and tested.
- Do not trust DNS results. Validate family, length, socket type, and protocol.
- Deadlines must apply to DNS, connect, send, receive, and shutdown paths.
- Prefer monotonic clocks for deadlines and system clocks only for timestamps that
  represent wall time.
- Keep protocol parsing separate from transport I/O.

## Memory and Lifetime

- Ownership transfer must be visible in the type system where possible.
- Avoid raw owning memory.
- Avoid long-lived references to mutable shared state.
- Avoid storing references in classes unless lifetime is an obvious constructor
  dependency.
- Avoid custom allocators until measurement proves need.
- Zero sensitive buffers before release when they contain secrets.
- Do not return references or views into temporary objects.

## Concurrency

- Shared mutable state requires an explicit synchronization strategy.
- Prefer message passing, ownership transfer, or single-thread confinement.
- Do not mix blocking synchronization primitives with event-loop code.
- Avoid data races by design, not by comment.
- Thread ownership must be clear in API names or documentation.
- Shutdown must be deterministic: no leaked background work, no abandoned handles.

## API Design

- Public APIs should be stable, minimal, and unsurprising.
- Prefer explicit parameter objects when a function has multiple related options.
- Avoid boolean parameters in public APIs when an enum would name the behavior.
- Do not expose partially initialized objects.
- Constructors establish invariants or throw.
- Destructors release resources and must not throw.
- Separate configuration from execution when it improves testability.
- Public API names should read like normal code at call sites.

## Comments and Documentation

- Comment why, not what.
- Document invariants, ownership, lifetime, blocking behavior, thread affinity,
  error behavior, and protocol assumptions.
- Avoid comments that repeat the code.
- Every public type and public function needs documentation when its behavior is
  not self-evident from name and signature.
- Keep examples small and executable in spirit.
- Update documentation in the same change as behavior changes.

## Tests

- New behavior needs tests unless the change is mechanical and risk-free.
- Protocol parsers need malformed-input tests.
- Network code needs timeout, cancellation, DNS failure, and short-read/write
  tests.
- Error paths need coverage.
- Tests must be deterministic. Avoid real external services in unit tests.
- Integration tests may use real services only when opt-in and clearly marked.
- Regression tests should name the bug class, not only the incident.

## Build and Tooling

- Meson build files use lowercase underscore variable names.
- Build targets use project names or domain names, not vague names like `app`.
- Keep warning level high. Do not silence warnings globally.
- Formatting must be automated. Manual style debates lose to tooling.
- Static analysis findings must be triaged, not ignored by habit.
- Generated files must say they are generated and name the generator.
- Do not vendor dependencies without a clear update and security policy.

## Security

- Validate all external input.
- Keep parsing code small and reviewable.
- Do not log secrets, tokens, credentials, or raw sensitive payloads.
- Prefer fail-closed behavior.
- Use constant-time comparison for secrets.
- Initialize memory before use.
- Avoid undefined behavior even if current compilers seem tolerant.
- Treat integer conversion, truncation, sign changes, and overflow as security
  review points.

## Review Checklist

- Is ownership obvious?
- Is lifetime safe across async boundaries?
- Are all errors handled or intentionally propagated?
- Are timeouts and cancellation explicit?
- Are packet lengths and integer conversions checked?
- Are public headers self-contained and low-dependency?
- Are names descriptive and consistent?
- Is code portable ISO C or C++?
- Is the simplest correct design used?
- Are tests focused on real risk?
