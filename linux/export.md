# Export

### Understanding `export` in Linux Shell Scripts

In Linux shell scripting (primarily Bash, the default on most distributions), the `export` command is used to make environment variables available to child processes, such as subshells or commands invoked from your script. Without exporting, a variable is local to the current shell session and won't be inherited by subprocesses.

#### Basic Syntax
- Set a variable (local only):  
  ```
  VAR="some_value"
  ```
- Export a variable (makes it an environment variable):  
  ```
  export VAR="some_value"
  ```
  This is equivalent to:  
  ```
  VAR="some_value"
  export VAR
  ```

#### Why Use `export`?
- **Inheritance**: Child processes (e.g., another script, command, or function) can access the variable.
- **Common Use Cases**:
  - Passing configuration (e.g., API keys, paths) to tools like `git`, `docker`, or other scripts.
  - Setting up environment for builds or deployments.
- **Note**: Exported variables persist only for the duration of the parent process. They don't survive the script's exit unless the script is sourced (e.g., via `. script.sh`).

#### Example: Simple Script Demonstrating Export
Create a file `demo.sh`:

```bash
#!/bin/bash

# Set a local variable (not exported)
LOCAL_VAR="I'm local only"

# Set and export a variable
export EXPORTED_VAR="I'm visible to children"

# Print in current script
echo "In parent: LOCAL_VAR=$LOCAL_VAR"
echo "In parent: EXPORTED_VAR=$EXPORTED_VAR"

# Call a child process (another script or command)
bash -c 'echo "In child: LOCAL_VAR=$LOCAL_VAR"'
bash -c 'echo "In child: EXPORTED_VAR=$EXPORTED_VAR"'
```

Run it:
```bash
chmod +x demo.sh
./demo.sh
```

**Output**:
```
In parent: LOCAL_VAR=I'm local only
In parent: EXPORTED_VAR=I'm visible to children
In child: LOCAL_VAR=
In child: EXPORTED_VAR=I'm visible to children
```

- `LOCAL_VAR` is empty in the child because it's not exported.
- `EXPORTED_VAR` is inherited.

#### Exporting Multiple Variables
```bash
export VAR1="value1" VAR2="value2" VAR3="value3"
```
Or export a list:
```bash
export VAR1 VAR2 VAR3
VAR1="value1"
VAR2="value2"
VAR3="value3"
```

#### Unexporting (Removing from Environment)
```bash
unset VAR  # Removes the variable entirely
# Or, to keep the variable but make it local:
export -n VAR
```

#### Best Practices
- **Quote Values**: Always quote to handle spaces: `export PATH="$PATH:/new/dir"`.
- **Avoid Over-Exporting**: Only export what's needed to prevent polluting child environments.
- **Sourcing vs. Executing**: If you run `./script.sh`, exports are lost on exit. Use `source script.sh` (or `. script.sh`) to apply changes to the current shell.
- **Debugging**: Use `env` or `printenv VAR` to list exported variables. `declare -p VAR` shows if it's exported.
- **Shell-Specific**: This works in Bash/Zsh. For POSIX sh, it's similar but lacks some Bash extensions.

#### Common Pitfalls
- **No Spaces**: `export VAR = value` fails—use `export VAR=value`.
- **Read-Only**: Can't export read-only vars: `readonly VAR; export VAR` errors.
- **Functions**: Variables set inside functions are local unless exported explicitly.

For more advanced usage (e.g., in systemd or cron jobs), check `man bash` under "ENVIRONMENT VARIABLES"
