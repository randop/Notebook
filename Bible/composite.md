**Bible Verse Social Media Graphic — Image Compositing Prompt**

**Background image:** An open Bible with golden-glowing pages, shot from a low angle on a dark wooden table, soft bokeh background, warm ambient lighting. Photorealistic, high resolution. *(1024×731px — do not resize or alter canvas dimensions under any circumstance.)*

**Post-processing pipeline** (applied in order):

1. **Gaussian blur** — σ = 3.0px applied to the entire background image
2. **Vertical black gradient overlay** (full width × full height): fully transparent at the top, gradually darkening downward, reaching ~92% black opacity at the bottom. Gradient ramp: `t = max(0, (y − H×0.25) / (H×0.75))`, alpha = `t^1.5 × 235`
3. **Horizontal black gradient overlay** (full width, restricted to text block rows only): dark on both left and right edges (30% of width each side), fading to transparent at center. Ramp: `t^1.4 × 180` max alpha

**Text content:**

> And the sea gave up the dead which were in it; and death and hell delivered up the dead which were in them: and they were judged every man according to their works.
>
> — Revelation 20:13 KJV

**Text rendering:**

- **Verse text:** DejaVu Sans Book, **57px**, white `#FFFFFF`, 2px black drop shadow, centered horizontally
- Word-wrapped to fit within canvas width minus horizontal padding; text block height expands dynamically upward to accommodate all wrapped lines — do **not** reduce font size to fit, do **not** widen the canvas
- Line spacing: natural line height + 10px extra between lines
- **Reference line:** DejaVu Sans Bold, **46px**, white `#FFFFFF`, 2px black drop shadow, right-aligned, 8px below the last verse line

**Padding:**

- 30px on top, left, and right sides of the text block
- 45px on the bottom (30px base + 15px extra)

**Gradient overlay height** is computed dynamically: it starts at `text_block_y` (the top of the text block) and extends to the bottom of the image, hugging the text block tightly rather than being a fixed height.

**Output:** PNG, RGB, 1024×731px, saved to disk.

---

**Implementation notes for code generation:**
- Use Python + Pillow (`PIL`)
- Use `numpy` for gradient array construction
- Fonts at `/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf` and `DejaVuSans-Bold.ttf`
- Measure line height from a tall-character sample (`"Ágjpq|"`) rather than individual line bboxes to ensure consistent vertical rhythm
- Word-wrap must be greedy left-to-right, breaking only on spaces, fitting within `W − 2×30 = 964px`
- Apply overlays via `Image.alpha_composite` in RGBA mode; convert to RGB only on final save

