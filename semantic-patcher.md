# Source code semantic patcher

### Tool Recommendation for C/C++

For modifying C or C++ source code semantically (i.e., based on code structure and patterns rather than simple text searches/replacements), the recommended Linux command-line tool is **Coccinelle**. It's an open-source program matching and transformation engine specifically designed for C (with partial C++ support via its SmPL language). It uses "semantic patches" to identify and rewrite code patterns while preserving syntax and semantics, avoiding brittle text-based edits.

#### Installation on Linux
Coccinelle is available in most major distributions' repositories:
- On Ubuntu/Debian: `sudo apt install coccinelle`
- On Fedora: `sudo dnf install coccinelle`
- On Arch: `sudo pacman -S coccinelle`

The main command is `spatch` (semantic patch applicator).

#### Example: Updating `a = 0` to `a = 6` in `main.c`
Given your `main.c`:
```
#include<stdio.h>
int main() {
   int a = 0;
   return 0;
}
```

1. Create a semantic patch file named `update_a.sp` with the following content. This matches the declaration `int a = 0;` and replaces the initializer with `6`:
   ```
   @@
   identifier a;
   @@
   -int a = 0;
   +int a = 6;
   ```

   - The `-` line indicates what to match and remove.
   - The `+` line indicates the replacement.
- This is semantic: it understands the variable declaration structure, so it won't accidentally match unrelated "0"s elsewhere.

2. Apply the patch to your file:
   ```
   spatch --sp-file update_a.sp main.c --dir .
   ```
   - `--sp-file`: Specifies the patch file.
   - `--dir .`: Applies to files in the current directory (you can specify paths or patterns like `*.c`).
   - This will output the modified `main.c` (use `--in-place` to overwrite directly: `spatch --sp-file update_a.sp --in-place main.c`).

3. Resulting `main.c`:
   ```
   #include<stdio.h>
   int main() {
      int a = 6;
      return 0;
   }
   ```

This works reliably even if formatting changes or the code grows, as long as the pattern matches the AST-like structure. For more complex transformations (e.g., function calls, loops), SmPL supports metavariables, expressions, and conditions—see the [Coccinelle documentation](https://coccinelle.gitlabpages.inria.fr/website/) for advanced patterns.

### Tool Recommendation for JavaScript

For JavaScript, the recommended Linux CLI tool is **jscodeshift**. It's a codemod toolkit from Facebook that transforms JS code via AST (Abstract Syntax Tree) manipulations, ensuring structural changes without regex/text hacks. It preserves code style and handles modern JS (ES6+).

#### Installation on Linux
Requires Node.js (install via `sudo apt install nodejs npm` or equivalent). Then:
```
npm install -g jscodeshift
```

#### Example Usage
For a similar update in a JS file (e.g., `let a = 0;` to `let a = 6;`), you'd write a transform script in JS. Coccinelle doesn't support JS, so this is separate.

1. Create a transform file `update_a.js`:
   ```javascript
   module.exports = function(fileInfo, api) {
     const j = api.jscodeshift;
     return j(fileInfo.source)
       .find(j.VariableDeclarator, {
         id: { name: 'a' },
         init: { value: 0 }
       })
       .replaceWith(path => j.variableDeclarator(path.value.id, j.literal(6)))
       .toSource();
   };
   ```

2. Run it:
   ```
   jscodeshift -t update_a.js yourfile.js
   ```
   - `-t`: Path to the transform script.
   - Use `--dry` to preview changes without applying.

This semantically targets the variable declaration. For batches, add `--extensions=js,jsx`. Docs: [jscodeshift GitHub](https://github.com/facebook/jscodeshift).

These tools integrate well into scripts or CI pipelines for repeatable "on-command" updates.

### Refining the Semantic Patch for Scoped Editing in `main()`

To target only the `int a = 0;` declaration *inside* the `main` function (while ignoring similarly named variables in other functions), you need to leverage Coccinelle's SmPL syntax for **context matching**. This uses the function declaration as a "virtual node" to scope the replacement. The `...` metavariable matches arbitrary code (statements, etc.) between points, and `when != a` ensures no intervening uses of `a` that could confuse the match (optional but good for precision).

Assuming your extended `main.c` looks something like this (with multiple `a`s for illustration):
```
#include<stdio.h>

int other_func() {
   int a = 1;  // Ignore this
   return 0;
}

int main() {
   int a = 0;  // Target this
   return 0;
}

int another_func() {
   int a = 2;  // Ignore this
   return 0;
}
```

#### Step 1: Create the Semantic Patch File
Save the following as `update_main_a.sp`. This matches the *entire* `main` function body, identifies the declaration `int a = 0;` within it (with optional whitespace/code around it), and replaces only that initializer. The function header anchors the scope.

```
@@
identifier a;
@@

int main(...) {
  ...
  -int a = 0;
  +int a = 6;
  ...
}
```

- **Explanation**:
  - `int main(...)`: Matches the `main` function signature exactly (the `...` allows for any parameters, though `main` typically has `int argc, char** argv`, it flexes).
  - `...` (before): Matches any code *before* the target declaration.
  - `-int a = 0;`: Matches and removes the exact declaration pattern (Coccinelle understands C syntax, so it handles minor whitespace variations).
  - `+int a = 6;`: Inserts the replacement in the same spot.
  - `...` (after): Matches any code *after* the declaration.
  - This won't trigger on `a` declarations outside `main` because the function context isn't matched.

For even stricter matching (e.g., if the declaration is the first statement and you want to avoid false positives with complex bodies), add `when` clauses:
```
@@
identifier a;
@@

int main(...) {
  ...
  when != a  // No 'a' references between start and target
  -int a = 0;
  +int a = 6;
  ...
  when != a  // No 'a' references between target and end
}
```
- `when != a`: Ensures the `...` doesn't contain any uses of `a`, making the match more precise (e.g., if `a` is used in expressions elsewhere in `main`).

#### Step 2: Apply the Patch
Run the command (same as before):
```
spatch --sp-file update_main_a.sp main.c --dir .
```
- Use `--in-place` to overwrite: `spatch --sp-file update_main_a.sp --in-place main.c`.
- For dry-run (preview changes): Add `--very-quiet --no-show-diff` or just run without `--in-place` to see output.

#### Expected Result
The modified `main.c` would be:
```
#include<stdio.h>

int other_func() {
   int a = 1;  // Unchanged
   return 0;
}

int main() {
   int a = 6;  // Updated
   return 0;
}

int another_func() {
   int a = 2;  // Unchanged
   return 0;
}
```

#### Tips for Edge Cases
- **If `main` has parameters**: The `...` in `int main(...)` handles it. For exact match: `int main(int argc, char **argv) { ... }`.
- **Multiple declarations in `main`**: If there's another `a` inside `main` (e.g., a nested block), the basic patch might match the first one. Use line numbers or more context (e.g., `when strict`)—but SmPL isn't line-aware; consider adding unique markers like comments near the target.
- **C++ support**: Works for C++; if your file is `.cpp`, Coccinelle parses it similarly, but test for edge cases like templates.
- **Debugging**: Run with `--debug` or `--trace` flags to see match details: `spatch --sp-file update_main_a.sp --debug main.c`.
- **Batch on multiple files**: Use `--include *.c` or a directory path in `--dir`.

For more SmPL patterns, check the Coccinelle manual with `man spatch` or online docs.
