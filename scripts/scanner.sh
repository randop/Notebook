#!/usr/bin/env bash
set -euo pipefail

echo -e "Image Scannner version 1.0 (2023-12-29 build 5)\nInitializing...\n"

echo -e "$(/usr/bin/scanimage -L)\n"

export NOWTS="$(date '+%Y-%m-%dT%H-%M-%S')"

#############################################################################
#
# Options specific to device `pixma:04A91912_4B8E1E':
#  Scan mode:
#  Scan mode:
#    --resolution auto||75|150|300|600|1200|2400|4800dpi [75]
#        Sets the resolution of the scanned image.
#    --mode auto|Color|Gray|48 bits color|16 bits gray|Lineart [Color]
#        Selects the scan mode (e.g., lineart, monochrome, or color).
#    --source Flatbed [Flatbed]
#        Selects the scan source (such as a document-feeder). Set source before
#        mode and resolution. Resets mode and resolution to auto values.
#    --button-controlled[=(yes|no)] [no]
#        When enabled, scan process will not start immediately. To proceed,
#        press "SCAN" button (for MP150) or "COLOR" button (for other models).
#        To cancel, press "GRAY" button.
#  Gamma:
#    --custom-gamma[=(auto|yes|no)] [no]
#        Determines whether a builtin or a custom gamma-table should be used.
#    --gamma-table auto|0..65535,... [inactive]
#        Gamma-correction table with 1024 entries. In color mode this option
#        equally affects the red, green, and blue channels simultaneously (i.e.,
#        it is an intensity gamma table).
#    --gamma auto|0.299988..5 [2.2]
#        Changes intensity of midtones
#  Geometry:
#    -l auto|0..216.069mm [0]
#        Top-left x position of scan area.
#    -t auto|0..297.011mm [0]
#        Top-left y position of scan area.
#    -x auto|0..216.069mm [216.069]
#        Width of scan-area.
#    -y auto|0..297.011mm [297.011]
#        Height of scan-area.
#  Buttons:
#    --button-update
#        Update button state
#  Extras:
#    --threshold auto|0..100% (in steps of 1) [inactive]
#        Select minimum-brightness to get a white point
#    --threshold-curve auto|0..127 (in steps of 1) [inactive]
#        Dynamic threshold curve, from light to dark, normally 50-65
#    --adf-wait auto|0..3600 (in steps of 1) [inactive]
#        When set, the scanner waits upto the specified time in seconds for a
#        new document inserted into the automatic document feeder.
#
#############################################################################

i=0

for i in $(seq -f "%05g" 1 5000)
do
  filename="${NOWTS}_${i}.jpg"
  echo "File: ${filename}"
  /usr/bin/scanimage -d pixma:04A91912_4B8E1E -p -v --format=jpeg --gamma=auto --mode=auto --resolution=600 -o "${HOME}/Documents/scans/${filename}"
  read -n 1 -s -r -p "Press any key to continue"
  echo -e "\n"
done

echo "DONE!"
