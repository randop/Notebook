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
```

## Games
```bash
flatpak install flathub com.github._0negal.Viper
flatpak install flathub org.wesnoth.Wesnoth
```

## (SOLVED) Fix Error: Permission denied
> [https://ask.fedoraproject.org/t/flatpak-install-org-signal-signal-error-permission-denied/10362](https://ask.fedoraproject.org/t/flatpak-install-org-signal-signal-error-permission-denied/10362)
```bash
find ~/.local/share/flatpak -user root -or -group root
```
