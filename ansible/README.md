# Ansible

## Install using `pipx`
```shell

`pipx` installs into isolated venv but symlinks the executable to `~/.local/bin`.

```sh
# ensure pipx itself is available
python3 -m pip install --user pipx
python3 -m pipx ensurepath

# restart shell or source your rc file so PATH picks up ~/.local/bin
exec $SHELL

# install ansible
pipx install --include-deps ansible
```

`--include-deps` is needed because Ansible's CLI tools (`ansible-playbook`, `ansible-galaxy`, etc.) live in separate packages under the hood.

Alternative without pipx (plain pip, user site):

```sh
python3 -m pip install --user ansible
```

pip puts the entrypoints in `~/.local/bin` automatically on most distros — check `python3 -m site --user-base` to confirm the base path if it's not showing up.

Verify:

```sh
which ansible
ansible --version
```
---

## Install using `uv`
`uv tool install` is the pipx equivalent isolated venv, symlinks binaries out.

```sh
# install uv itself if not already present
curl -LsSf https://astral.sh/uv/install.sh | sh
exec $SHELL

# install ansible, binaries land in ~/.local/bin by default
uv tool install --with ansible-core --with-executables-from ansible-core ansible
```

Ansible's CLI entrypoints all ship in the `ansible` package itself when installed this way:

```sh
uv tool install ansible
```

Confirm bin dir target:

```sh
uv tool dir --bin
```

If it's not `~/.local/bin`, override:

```sh
export UV_TOOL_BIN_DIR="$HOME/.local/bin"
uv tool install ansible
```

Verify:

```sh
which ansible
ansible --version
```

