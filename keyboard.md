# Keyboard
### December 22, 2025 10:59PM

The **global vendor ID** is the **USB Vendor ID (VID)**, a unique 16-bit identifier assigned by the **USB Implementers Forum (USB-IF)** to companies manufacturing USB devices, including keyboards. This ensures devices are properly identified by operating systems (you assign your own Product IDs (PIDs) under your VID for specific models).

### How to Obtain an Official USB Vendor ID
The official process is managed by the USB-IF. There are a few options:

1. **Purchase a VID directly** (no membership required):  
   Costs **US$6,000** (one-time fee).  
   You cannot use the official USB logo on your products.  
   Contact USB-IF and submit the Vendor ID request form.

2. **Become a USB-IF member**:  
   Annual membership fee: **US$5,000**.  
   Includes a free VID assignment (if your company doesn't already have one), plus other benefits like access to specifications and compliance tools.  
   Download and submit the membership application from their site.

3. **If planning USB logo certification**:  
   Additional logo license fee (around US$3,500 for 2 years, waived for members), plus VID if needed.

Visit the official page for details and forms: https://www.usb.org/getting-vendor-id  
Email admin@usb.org for inquiries. Note that VIDs are non-transferable and tied to your company permanently.

### Alternatives for Lower-Cost or Hobby/Open-Source Projects
Many small vendors, hobbyists, and mechanical keyboard makers avoid the high cost by using shared or sublicensed IDs, especially since standard HID keyboards/mice work with generic OS drivers (no custom drivers needed):

- **Chip manufacturer sublicenses** — Some MCU vendors (e.g., Microchip, Atmel (now Microchip), NXP, Espressif, TI) offer free or low-cost sublicenses of their VID with assigned PIDs, often limited to low-volume production (e.g., <10,000 units). Check your USB controller chip's vendor for application forms.

- **Open-source/shared VIDs** — For open-source hardware:  
  Use **pid.codes** (VID: 0x1209) – free PID assignments under a shared VID for open-source projects (requires public repo and open license).

- **Other shared pools** — Some communities use VIDs like Openmoko (0x1d50) or others for hobby projects.

Many cheap/commercial USB keyboards use the chip maker's default VID/PID or even unofficial ones without issues, as long as they follow HID standards.

If you're producing commercial keyboards in volume and want full compliance/logo use, the official USB-IF route is recommended for professionalism and avoiding potential conflicts. For prototypes or small runs, start with a chip vendor sublicense. Let me know more details about your project (e.g., volume, custom features) for tailored advice!
