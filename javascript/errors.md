# Errors

Here's how to safely convert an **unknown** value in TypeScript into an actual `Error` instance (or at least extract a meaningful message from it).

### Most common & recommended patterns (2024–2026 style)

```ts
// ────────────────────────────────────────────────
// Pattern 1: The cleanest & most popular today
// ────────────────────────────────────────────────
function getErrorMessage(err: unknown): string {
  if (err instanceof Error) {
    return err.message;
  }

  // Handle common non-Error cases people throw
  if (typeof err === "string") {
    return err;
  }

  // Try to get something useful from objects
  if (err && typeof err === "object" && "message" in err) {
    return String((err as any).message);
  }

  // Fallback
  return String(err ?? "Unknown error");
}

// Usage examples:
try {
  // ... some code ...
} catch (err: unknown) {
  console.error(getErrorMessage(err));
  // or
  throw new Error(getErrorMessage(err), { cause: err });
}
```

### Pattern 2: Convert to real Error instance

```ts
function toError(err: unknown): Error {
  if (err instanceof Error) {
    return err;
  }

  if (typeof err === "string") {
    return new Error(err);
  }

  if (err && typeof err === "object" && "message" in err) {
    return new Error(String((err as any).message), { cause: err });
  }

  // Everything else → generic error
  return new Error(String(err ?? "Unknown error"), { cause: err });
}

// Very clean usage:
try {
  // ...
} catch (e: unknown) {
  const error = toError(e);
  
  console.error(error.message);
  console.error(error.stack);
  // You can now safely do error instanceof Error → true
}
```

### Quick one-liner versions (when you're in a hurry)

```ts
// Very concise (popular in 2025+ codebases)
const getMsg = (e: unknown) => e instanceof Error ? e.message : String(e);

// With cause preserved
const ensureError = (e: unknown): Error =>
  e instanceof Error ? e : new Error(String(e), { cause: e });
```

### Summary – Pick the one that matches your style

| Pattern                        | When to use                                 | Result type     | Preserves cause |
|-------------------------------|---------------------------------------------|------------------|------------------|
| `getErrorMessage(err)`        | You just need the message                   | `string`         | no              |
| `toError(err)` / `ensureError`| You want a real `Error` object              | `Error`          | yes             |
| One-liner `e instanceof Error ? e.message : String(e)` | Quick inline usage         | `string`         | no              |

Most modern TypeScript codebases in 2026 tend to use either:

```ts
catch (err: unknown) {
  const message = err instanceof Error ? err.message : String(err);
  // or
  const error = err instanceof Error ? err : new Error(String(err), { cause: err });
}
```
Here's a clean, modern, and reusable `toError` utility you can export from a file (for example `error-utils.ts`):

```ts
/**
 * Converts any caught value (usually `unknown`) into a proper Error instance.
 * 
 * - If it's already an Error → returns it as-is
 * - If it's a string → wraps it in new Error()
 * - If it's an object with .message → uses that message
 * - Otherwise → creates a generic Error with String() representation
 * 
 * The original value is always preserved as the `cause`.
 */
export function toError(maybeError: unknown): Error {
  // Already an Error → pass through
  if (maybeError instanceof Error) {
    return maybeError;
  }

  // Most common manual throw: throw "something went wrong"
  if (typeof maybeError === "string") {
    return new Error(maybeError);
  }

  // Some libraries throw plain objects with message property
  if (
    maybeError != null &&
    typeof maybeError === "object" &&
    "message" in maybeError &&
    typeof (maybeError as any).message === "string"
  ) {
    const err = new Error((maybeError as any).message);
    err.cause = maybeError;
    return err;
  }

  // Fallback: everything else
  const fallbackMessage = String(maybeError ?? "Unknown error");
  const error = new Error(fallbackMessage);
  error.cause = maybeError;

  return error;
}
```

### Usage examples

```ts
// 1. Basic catch
try {
  // ...
} catch (err: unknown) {
  const error = toError(err);
  console.error(error.message);
  console.error(error.stack);
  // error instanceof Error === true
}

// 2. Throw with original cause preserved
try {
  JSON.parse("invalid");
} catch (e: unknown) {
  throw toError(e);           // ← keeps the original SyntaxError as .cause
}

// 3. In async code / Promise rejection
async function fetchData() {
  try {
    const res = await fetch("/api/data");
    return await res.json();
  } catch (err: unknown) {
    throw toError(err);
  }
}
```

### Even more compact version (if you prefer one-liners)

```ts
export function toError(e: unknown): Error {
  return e instanceof Error
    ? e
    : new Error(
        typeof e === "string"
          ? e
          : e && typeof e === "object" && "message" in e
            ? String((e as any).message)
            : String(e ?? "Unknown error"),
        { cause: e }
      );
}
```
