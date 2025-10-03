# Variables

### Variable Interpolation in Linux Shell Commands

In Linux shell scripting (e.g., Bash), **variable interpolation** (also called expansion) allows you to embed the value of a variable directly into commands, strings, or expressions. This makes scripts dynamic and reusable. The shell replaces `$VAR` (or similar syntax) with the variable's actual value before executing the command.

#### Basic Syntax and Rules
- **Standard Variable Expansion**: `$VAR` or `${VAR}` (the braces are optional but recommended for clarity or to avoid ambiguity, e.g., `${VAR}_suffix`).
- **Context Matters**:
  - **Double Quotes (`"`)**: Allows interpolation. E.g., `echo "$VAR"` expands the variable.
  - **Single Quotes (`'`)**: Prevents interpolation. E.g., `echo '$VAR'` prints literally `$VAR`.
  - **No Quotes**: Expands but can cause issues with spaces (word splitting) or globbing (e.g., `*` expands to files).
- **Other Expansions**:
  - **Command Substitution**: `$(command)` or legacy `` `command` `` (e.g., `ls $(pwd)` lists files in the current directory).
  - **Arithmetic Expansion**: `$((expression))` (e.g., `echo $((2 + 3))` outputs `5`).
  - **Parameter Expansion**: Advanced forms like `${VAR:-default}` (use default if unset) or `${VAR#prefix}` (remove prefix).

#### Example: Interpolating Variables in Commands
Create a script `interp.sh`:

```bash
#!/bin/bash

# Set variables
NAME="Alice"
AGE=30
DIR="/tmp/myapp"

# Interpolation in echo (double quotes)
echo "Hello, $NAME! You are ${AGE} years old."

# In a command (ls with path)
echo "Listing files in $DIR:"
ls "$DIR"  # Quotes prevent word splitting if DIR has spaces

# Command substitution
CURRENT_DATE=$(date +%Y-%m-%d)
echo "Today is $CURRENT_DATE"

# Arithmetic
NEXT_AGE=$((AGE + 1))
echo "Next year, you'll be $NEXT_AGE"

# Without quotes: risky if var has spaces
FILES="file1 file2"  # Simulating a space-separated list
echo $FILES  # Expands to: file1 file2 (two words, may break commands)
```

Run it:
```bash
chmod +x interp.sh
./interp.sh
```

**Sample Output** (assuming /tmp/myapp exists):
```
Hello, Alice! You are 30 years old.
Listing files in /tmp/myapp:
file1.txt
Today is 2025-10-03  # Based on current date
Next year, you'll be 31
file1 file2
```

- Note: The unquoted `echo $FILES` treats it as two arguments, which works here but could fail in pipes or loops.

#### Export vs. Interpolation
- From our previous discussion: `export` makes variables available to child processes. Interpolation happens regardless, but unexported vars won't be seen in subshells.
- Example: In a subshell, `bash -c 'echo $EXPORTED_VAR'` works if exported; local vars won't.

#### Best Practices
- **Always Quote**: Use `"$VAR"` to preserve spaces and prevent globbing/word splitting.
- **Use Braces**: `${VAR}` for safety, e.g., in `${VAR}world` vs. `$VARworld` (which might misparse).
- **Defaults and Checks**: 
  - `${VAR:-default}`: Use default if unset/empty.
  - `${VAR:?error}`: Exit with error if unset.
- **Debugging**: Run with `set -x` at the top of your script to trace expansions (shows `+ VAR=value` lines).
- **Security**: Avoid unquoted vars in user input to prevent injection (e.g., command substitution exploits).
- **Portability**: Stick to POSIX features; avoid Bashisms like `${VAR@Q}` if targeting sh.

#### Common Pitfalls
- **Empty Vars**: `$VAR` becomes empty string; use quotes to avoid extra spaces.
- **Nested Quotes**: `echo "It's $VAR's"`: Escapes needed for inner single quotes.
- **Arrays**: `${ARRAY[0]}` for elements; `@` or `*` for all (e.g., `"${ARRAY[@]}"`).
- **Leading $ in Vars**: If VAR starts with `$`, it expands prematurely—use single quotes or assign carefully.

For more, see `man bash` under "EXPANSION".
