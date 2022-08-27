for drv in qemu interface network nodedev nwfilter secret storage proxy; do
systemctl start virt${drv}d.socket; done
