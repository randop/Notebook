# segfault (Segmentation Fault)

```
[  552.187790] Isolated Web Co[5355]: segfault at 4000 ip 00000e470384ff31 sp 00007ffc3438ec18 error 6 likely on CPU 4 (core 4, socket 0)
[  552.187795] Code: 00 00 00 48 b9 01 00 00 00 00 00 f9 ff c3 48 8b 7f 10 ff 27 0f 0b 00 00 00 00 00 00 00 00 00 00 00 a8 c8 a5 df 6d 1d 00 00 83 <47> 08 01 49 89 cb 49 c1 eb 2f 41 81 fb fc ff 01 00 0f 85 77 00 00
```

This is a kernel segfault from Chromium (Isolated Web Co[ntent] process), likely related to Mesa/GL acceleration.

**Segfault Analysis:**

- **Address**: `4000` (very low address—classic null/near-null dereference)
- **Error code 6**: PAGE_TABLE_FAULT (accessed unmapped memory)
- **CPU**: Core 4 on your J4125
- **Instruction pointer**: `00000e470384ff31` (likely JIT or VDSO code)

The code bytes suggest a memory access pattern: `<47> 08 01` at the fault point could be an indirect jump or memory load that dereferenced a bad pointer.

**Most likely culprits on GeminiLake:**

1. **Mesa 26 regression** — You've already fought glamor/GL issues on your machine. This could be:
   - Shader compilation crash
   - GPU command buffer corruption
   - Bad pointer in DRI3/Present extension
   - UHD 600 driver state machine bug

2. **Chromium + Mesa interaction** — Isolated Web Content processes are heavy GPU users (canvas, WebGL, video decode). A race condition or memory leak in the GL stack could trigger this.

3. **VDSO/JIT issue** — The IP suggests this might be in dynamically generated code (VDSO syscall stub or V8 JIT), which could mean:
   - Stack corruption from a previous syscall
   - Bad return address
   - Heap corruption propagating into JIT'd code

**Debugging steps:**

```bash
# 1. Check Mesa version and GL driver state
glxinfo | grep -i "mesa\|version\|renderer"

# 2. Check if it's reproducible in specific web content
# (WebGL tests, video playback, canvas-heavy sites?)

# 3. Verify no OOM/memory pressure
free -h
dmesg | tail -30 | grep -i "memory\|oom\|allocation"

# 4. Check if reverting to Mesa 25 helps (if available in Artix)
pacman -Q mesa

# 5. Disable GPU acceleration in Chromium temporarily
# Launch with: chromium --disable-gpu --disable-software-rasterizer
```

**Quick mitigation:**

If this is blocking you, disable GPU in Chromium:
```bash
echo 'CHROMIUM_FLAGS="--disable-gpu"' >> ~/.config/chromium-flags.conf
```

**Diagnosis:**
#### ☠️ FAULTY motherboard
⚔️ Curses on gigabyte and asus!!!
