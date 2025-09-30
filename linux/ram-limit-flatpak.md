# Limit flatpak application RAM usage

To limit the memory usage of a specific Flatpak application (installed from Flathub) to 1GB on Arch Linux, you can use systemd's resource control features. Flatpak runs apps in systemd user scopes, which allow overrides for limits like memory. This requires systemd (standard on Arch) and cgroup v2 support (enabled by default on recent kernels).

### Step 1: Identify the Flatpak App ID
The app ID is in the format `com.example.App` (e.g., `com.spotify.Client` for Spotify). Find it with:
```
flatpak list
```
Look for your app in the output under the "Application ID" column.

### Step 2: Create the Systemd Override Directory and File
Create a drop-in configuration file for the scope. Replace `com.example.App` with your actual app ID.

```
mkdir -p ~/.config/systemd/user/app-flatpak-com.example.App-.scope.d/
```

Then create the file `~/.config/systemd/user/app-flatpak-com.example.App-.scope.d/memory.conf` with the following content (use a text editor like `nano` or `vim`):
```
[Scope]
MemoryMax=1G
```
- `MemoryMax=1G` sets a hard limit of 1GB (1073741824 bytes). The app will be killed if it exceeds this.
- For a softer limit (throttling before killing), use `MemoryHigh=1G` instead.

### Step 3: Reload Systemd and Verify
Reload the user systemd daemon:
```
systemctl --user daemon-reload
```

To verify the limit applies, run your Flatpak app:
```
flatpak run com.example.App
```

Check the active scope (replace with your app ID) while the app is running:
```
systemctl --user status "app-flatpak-com.example.App-*.scope"
```
Look for the `MemoryMax` line in the output—it should show `1.0G`.

### Notes
- This applies to all instances of the app.
- If the app still exceeds limits unexpectedly, monitor with `htop` or `systemd-cgtop --user`.
- For CPU limits, add `CPUQuota=50%` (or similar) under `[Scope]` in the same file.
- Revert by deleting the `.conf` file and running `systemctl --user daemon-reload` again.

This method is reliable on Arch Linux with Flatpak 1.14+.
