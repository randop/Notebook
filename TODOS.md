# TODOS

## Solaris System Administration Guide: Devices and File Systems
#### How to Use a Non-Complaint USB Mass Storage Device
[http://www.grosbein.net/docs/docs.oracle.com/cd/E19253-01/817-5093/gafha/index.html](http://www.grosbein.net/docs/docs.oracle.com/cd/E19253-01/817-5093/gafha/index.html)

## how to disable Firefox prompt
[https://support.mozilla.org/en-US/questions/1201904](https://support.mozilla.org/en-US/questions/1201904)
```
That is a message from the "Decoder Doctor' that is displayed as a notification bar if there is no suitable codec found to play the media file.

    decoder.noCodecs.message = To play video, you may need to install Microsoft’s Media Feature Pack. 

Does the Web Console (or Browser Console) show more detail about what codec is missing?

I see see this pref present on the about:config page to switch verbose mode on/off.

    media.decoder-doctor.verbose 

The messages that are allows are specified via this pref:

    media.decoder-doctor.notifications-allowed 

I think that you can set this pref to an empty value (right-click and clear the current String value) to suppress the notifications.

You can open the about:config page via the location/address bar. You can accept the warning and click "I accept the risk!" to continue.

    http://kb.mozillazine.org/about:config 

Read this answer in context 👍 1
```

## Silero Models: pre-trained enterprise-grade STT / TTS models
[https://github.com/snakers4/silero-models.git](https://github.com/snakers4/silero-models.git)

## nodegui
```
git clone https://github.com/nodegui/nodegui-starter
cd nodegui-starter
npm install
export QT_QPA_PLATFORM=wayland
export QT_QPA_PLATFORM=
```

## Libvpx
> [https://github.com/webmproject/libvpx](https://github.com/webmproject/libvpx)
```
wget https://vault.centos.org/centos/8/AppStream/x86_64/os/Packages/libvpx-1.7.0-8.el8.x86_64.rpm
```
