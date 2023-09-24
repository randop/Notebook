# install qemu
```bash
sudo apt install qemu-kvm libvirt-daemon-system libvirt-clients bridge-utils libguestfs-tools
```

# download debian
```bash
curl -LO https://cdimage.debian.org/debian-cd/current/amd64/iso-cd/debian-12.1.0-amd64-netinst.iso
```

# create disk
```bash
qemu-img create -f qcow2 debian.qcow2 8G
```

# boot vm for installation
```bash
> IMPORTANT: Do not install GUI
> IMPORTANT: Install only SSH server and standard system utilities
qemu-system-x86_64 \
  -m 4G -enable-kvm \
  -cdrom debian-12.1.0-amd64-netinst.iso \
  -drive file=debian.qcow2,format=qcow2 \
  -boot order=d
```

# copy boot files
```bash
virt-copy-out -a debian.qcow2 /boot/vmlinuz-6.1.0-10-amd64 /boot/initrd.img-6.1.0-10-amd64 .
```

# Run system
```bash
qemu-system-x86_64 \
  -enable-kvm -cpu host -m 4G -smp 4 \
  -machine accel=kvm \
  -nic user,hostfwd=tcp::2222-:22,hostfwd=tcp::2333-:3000 \
  -drive file=debian.qcow2,format=qcow2 \
  -initrd initrd.img-6.1.0-12-amd64 \
  -kernel vmlinuz-6.1.0-12-amd64 -append "root=/dev/sda1 console=ttyS0" \
  -nographic
```

# Fix floppy boot issue
> IMPORTANT: ssh on the system
```bash
ssh -p 2222 user@127.0.0.1
su
echo "blacklist floppy" | tee /etc/modprobe.d/floppy.conf
update-initramfs -u
/sbin/poweroff

virt-copy-out -a debian.qcow2 /boot/vmlinuz-6.1.0-12-amd64 /boot/initrd.img-6.1.0-12-amd64 .
```

# Setup Qemu ARM
```bash
apt install qemu-system-arm qemu-system-mips qemu-efi-aarch64 qemu-kvm qemu-efi
```

# Install ARM system
```bash
qemu-system-aarch64 -M virt -cpu cortex-a57 -m 4G \
    -kernel vmlinuz-arm64 -initrd initrd-arm64.gz \
    -hda disk.qcow2 -append "console=ttyAMA0" \
    -drive "file=debian-12.1.0-arm64-DVD-1.iso,id=cdrom,if=none,media=cdrom" \
    -device virtio-scsi-device -device scsi-cd,drive=cdrom -nographic
```

# Copy ARM boot files
> ⚠️ IMPORTANT: Poweroff the vm
```bash
virt-ls -a disk.qcow2 /boot/
virt-copy-out -a disk.qcow2 /boot/vmlinuz-6.1.0-12-arm64 /boot/initrd.img-6.1.0-12-arm64 .
```

# Running ARM system
```bash
qemu-system-aarch64 -M virt -cpu cortex-a57 -m 8G -smp 4 \
    -initrd initrd.img-6.1.0-12-arm64 \
    -kernel vmlinuz-6.1.0-12-arm64 \
    -append "root=/dev/vda2 console=ttyAMA0" \
    -drive if=virtio,file=disk.qcow2,format=qcow2,id=hd \
    -nic user,hostfwd=tcp::2424-:22,hostfwd=tcp::3232-:3000 \
    -device intel-hda -device hda-duplex -nographic
```
