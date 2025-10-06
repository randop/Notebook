# Flatpak
```bash
flatpak --user remote-add --if-not-exists flathub https://flathub.org/repo/flathub.flatpakrepo
flatpak --user install flathub org.onlyoffice.desktopeditors
flatpak --user install flathub us.zoom.Zoom
flatpak --user install flathub com.microsoft.Teams
flatpak --user install flathub io.dbeaver.DBeaverCommunity
flatpak --user install flathub com.getpostman.Postman
flatpak --user install flathub com.vscodium.codium
flatpak --user install flathub org.gnome.meld
flatpak --user install flathub net.oz9aec.Gpredict
flatpak --user install flathub org.openhantek.OpenHantek6022
flatpak --user install flathub app.resp.RESP
flatpak --user install flathub com.github.alecaddd.sequeler
flatpak --user install flathub org.zealdocs.Zeal
flatpak --user install flathub org.twinery.Twine
flatpak --user install flathub org.gnome.Maps
flatpak --user install flathub org.qgis.qgis
flatpak --user install flathub org.kicad.KiCad
flatpak --user install flathub com.discordapp.Discord
flatpak --user install flathub com.slack.Slack
flatpak --user install flathub io.github.mimbrero.WhatsAppDesktop
flatpak --user install flathub org.telegram.desktop
flatpak --user install flathub org.videolan.VLC
flatpak --user install flathub com.kavilgroup.gestimator
flatpak --user install flathub org.filezillaproject.Filezilla
flatpak --user install flathub org.gnome.GTG
flatpak --user install flathub org.gnome.Weather
flatpak --user install flathub org.chromium.Chromium
flatpak --user install flathub com.github.PintaProject.Pinta
flatpak --user install flathub de.haeckerfelix.Shortwave
flatpak --user install flathub cc.arduino.arduinoide
flatpak --user install flathub art.taunoerik.tauno-serial-plotter
flatpak --user install flathub org.gnome.Solanum
flatpak --user install flathub org.homelinuxserver.vance.biblereader
flatpak --user install flathub com.github.johnfactotum.Foliate
flatpak --user install flathub com.github.tenderowl.frog
flatpak --user install flathub nl.hjdskes.gcolor3
flatpak --user install flathub fr.romainvigier.MetadataCleaner
flatpak --user install flathub org.gnome.Builder
flatpak --user install flathub com.github.liferooter.textpieces
flatpak --user install flathub org.cvfosammmm.Setzer
flatpak --user install flathub rest.insomnia.Insomnia
flatpak --user install flathub com.belmoussaoui.Authenticator
flatpak --user install flathub org.gnome.SoundRecorder
flatpak --user install flathub com.github.maoschanz.drawing
flatpak --user install flathub org.mozilla.Thunderbird
flatpak --user install flathub fr.handbrake.ghb
flatpak --user install flathub org.gaphor.Gaphor
flatpak --user install flathub com.github.Eloston.UngoogledChromium
flatpak --user install flathub com.ultimaker.cura
flatpak --user install flathub io.github.congard.qnvsm
flatpak --user install flathub com.leinardi.gwe
flatpak --user install flathub io.github.arunsivaramanneo.GPUViewer
flatpak --user install flathub com.github.liferooter.textpieces
flatpak --user install flathub com.github.rafostar.Clapper
flatpak --user install flathub org.gnome.moserial
flatpak --user install flathub net.codeindustry.MasterPDFEditor
flatpak --user install flathub org.gnome.gitlab.somas.Apostrophe
flatpak --user install flathub com.brave.Browser
flatpak --user install flathub com.axosoft.GitKraken
flatpak --user install flathub org.nickvision.tubeconverter
flatpak --user install flathub org.kde.kget
flatpak --user install flathub io.gitlab.zehkira.Monophony
flatpak --user install flathub com.logseq.Logseq
flatpak --user install flathub org.kde.kommit
flatpak --user install flathub org.kde.kdiff3
flatpak --user install flathub org.kde.umbrello
```

## Games
```bash
flatpak --user install flathub com.github._0negal.Viper
flatpak --user install flathub org.wesnoth.Wesnoth
```

## (SOLVED) Fix Error: Permission denied
> [https://ask.fedoraproject.org/t/flatpak-install-org-signal-signal-error-permission-denied/10362](https://ask.fedoraproject.org/t/flatpak-install-org-signal-signal-error-permission-denied/10362)
```bash
find ~/.local/share/flatpak -user root -or -group root
```

## Update app
```bash
flatpak update --user --app com.google.Chrome
```

## Delete app and data
```bash
flatpak uninstall --delete-data com.microsoft.Teams
flatpak uninstall --delete-data com.skype.Client
```

---

To resolve the "Your browser might prevent calls from ringing. Change your browser settings to allow voice.google.com to play sound" error in Flatpak-installed Chromium (likely related to Google Voice web calls not producing ringtone audio), you'll need to address two potential causes: (1) Chromium's site-specific sound permissions and (2) Flatpak's sandbox restrictions on audio access. These steps assume you're on a Linux distribution using Flatpak (e.g., Fedora, Ubuntu) and a sound server like PulseAudio or PipeWire (which emulates PulseAudio compatibility).

### Step 1: Enable Site-Specific Sound Playback in Chromium
This directly fixes the browser's blocking of audio from Google Voice.

1. Open Chromium and navigate to `chrome://settings/content/sound` in the address bar.
2. Under the "Allowed to play sound" section, click **Add**.
   - Enter `voice.google.com` in the site box and click **Add**.
3. Repeat: Click **Add** again.
   - Enter `mail.google.com` in the site box and click **Add** (this covers related Gmail notifications that may tie into Voice alerts).
4. Close the tab, then reload `voice.google.com`. The warning should disappear, and test an incoming call to confirm ringing.

If audio still doesn't play (e.g., no sound from YouTube videos in Chromium), proceed to Step 2—Flatpak sandboxing is likely interfering.

### Step 2: Grant Audio Permissions to Flatpak Chromium Using Flatseal
Flatpak sandboxes apps by default, which can block audio sockets. Use Flatseal (a GUI tool) to toggle the necessary permission without command-line hassle.

1. Install Flatseal via Flatpak (if not already installed):  
   Open a terminal and run:  
   ```
   flatpak install flathub com.github.tchx84.Flatseal
   ```
2. Launch Flatseal:  
   ```
   flatpak run com.github.tchx84.Flatseal
   ```
3. In Flatseal, select **Chromium** (app ID: `org.chromium.Chromium`) from the left sidebar.
4. Scroll to the **Socket** section and toggle **Pulseaudio** to **On** (this enables audio access; PipeWire systems will auto-compat).
5. Close Flatseal and restart Chromium.
6. Test audio playback (e.g., a video on `voice.google.com` or YouTube). If calls still don't ring, also check the headset icon in Google Voice settings (top of the calls page) to ensure output devices are assigned correctly.

### Additional Troubleshooting
- **Verify general audio in Chromium**: Play a test sound/video. If no audio system-wide, ensure your distro's sound server (PulseAudio/PipeWire) is running: `systemctl --user status pulseaudio` (or `pipewire`).
- **Alternative without Flatseal**: Run Chromium temporarily with audio enabled via:  
  ```
  flatpak run --socket=pulseaudio org.chromium.Chromium
  ```
  To make it permanent, edit the app's override: `flatpak override --socket=pulseaudio org.chromium.Chromium`.
- **If using system-wide PulseAudio**: Switch to per-user mode for better Flatpak compatibility (edit `/etc/pulse/client.conf` to uncomment `autospawn = yes`, then restart).
- Update everything: `flatpak update` for Chromium and Flatseal.

This should fully resolve the issue. If problems persist (e.g., mic input fails too), provide more details like your distro and sound server.

```sh
flatpak override --socket=pulseaudio io.github.ungoogled_software.ungoogled_chromium
# error: Permission denied
```

The "Permission denied" error when running `flatpak override` typically occurs because the command requires elevated privileges for system-wide Flatpak installations (the default for most apps from Flathub, including ungoogled-chromium). User-specific installations can use the `--user` flag instead, but let's confirm and fix this step by step.

### Step 1: Check Your Installation Type
Run these commands to see if ungoogled-chromium is installed system-wide or per-user:
```
flatpak list | grep ungoogled_chromium
```
- If it appears here, it's a **system install** (most common).
```
flatpak list --user | grep ungoogled_chromium
```
- If it appears only here, it's a **user install**.

### Step 2: Apply the Override
- **For system installs** (requires `sudo`):
  ```
  sudo flatpak override --socket=pulseaudio io.github.ungoogled_software.ungoogled_chromium
  ```
  Enter your password when prompted. This grants the app access to your PulseAudio (or PipeWire-compatible) sound server.

- **For user installs** (no `sudo` needed):
  ```
  flatpak override --user --socket=pulseaudio io.github.ungoogled_software.ungoogled_chromium
  ```

### Step 3: Verify and Test
- Restart ungoogled-chromium.
- Confirm the override took effect:
  ```
  flatpak info --show-permissions io.github.ungoogled_software.ungoogled_chromium
  ```
  Look for `socket=pulseaudio` under the sockets section.
- Test audio (e.g., play a video in the browser or simulate a Google Voice call). If issues persist, ensure your sound server is running (`systemctl --user status pipewire-pulse` or `pulseaudio --check`).

