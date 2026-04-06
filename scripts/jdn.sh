#!/bin/sh
# ===================================================================
# Calculate remaining days until December 31, 9999
#
# Uses the Julian Day Number (JDN) formula
# a standard astronomical algorithm that works for
# any Gregorian calendar date without external tools or loops
# -------------------------------------------------------------------

today=$(date +%Y-%m-%d)
target="9999-12-31"

# Convert a date (YYYY-MM-DD) to Julian Day Number
date_to_jdn() {
  y=$(echo "$1" | cut -d- -f1)
  m=$(echo "$1" | cut -d- -f2)
  d=$(echo "$1" | cut -d- -f3)

  # Remove leading zeros to avoid octal interpretation
  y=$((y + 0))
  m=$((m + 0))
  d=$((d + 0))

  # Julian Day Number formula
  a=$(((14 - m) / 12))
  y2=$((y + 4800 - a))
  m2=$((m + 12 * a - 3))

  jdn=$((d + (153 * m2 + 2) / 5 + 365 * y2 + y2 / 4 - y2 / 100 + y2 / 400 - 32045))
  echo "$jdn"
}

jdn_today=$(date_to_jdn "$today")
jdn_target=$(date_to_jdn "$target")

days_remaining=$((jdn_target - jdn_today))

echo "Today          : $today"
echo "Target date    : $target"
echo "Days remaining : $days_remaining"
