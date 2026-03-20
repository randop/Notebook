# SQLite

## Is it safe to copy sqlite database while being used?

**Short answer:** No, **not safely / reliably** in the general case — especially not with a simple `cp`, `rsync` or similar when the database is actively **being written to**.

On Linux + ext4 there is **nothing magical** that makes a plain file copy atomic or crash-consistent when the file is changing during the copy. SQLite itself does **not** guarantee that a byte-range copy of the db file stays consistent if pages are being modified mid-copy.

### Realistic outcomes when you just `cp` / `rsync` a live database

| Situation                              | Probability          | Result in copied file                  | Usable?          |
|----------------------------------------|----------------------|----------------------------------------|------------------|
| Only readers (SELECTs), no writers     | —                    | Usually consistent                     | Yes              |
| Very low write rate + small & fast copy| high                 | Usually ok by luck                     | Probably         |
| Writes happening + copy takes > few seconds | medium–high     | Mix of old & new pages → malformed db  | No               |
| Write in progress exactly during copy of critical pages | low–medium   | Corrupt / unopenable or wrong data     | No               |
| Database using WAL mode + no checkpoint during copy | medium     | Often ok-ish if WAL is small, but still risky | Risky            |

Official SQLite documentation and developers are very clear:

> It is **not safe** to copy the database file while a transaction is in progress.  
> The backup copy might contain a mix of old and new content → **corrupt**.

### Safe & recommended ways on Linux (ext4)

Ranked from most convenient → most robust:

1. **Best & cleanest** — use SQLite's own backup facilities (works while database is in use)

   ```bash
   sqlite3 /path/to/live.db ".backup /backup/live-backup.db"
   ```

   or from code: use the **sqlite3_backup_*** API (or its wrappers in your language).

   This is the only method officially described as safe while writers are active.

2. **Very good** (if you can afford brief writer pause)

   ```sql
   BEGIN IMMEDIATE;   -- blocks new writers
   -- (copy happens here very quickly)
   ROLLBACK;          -- or COMMIT if you want
   ```

   Then do ordinary `cp` / `rsync`. Writers queue up for a short time.

3. **Good enough for many low-write workloads** — force a checkpoint (WAL mode only) + quick copy

   ```sql
   PRAGMA wal_checkpoint(FULL);
   ```

   Then copy very quickly (`ionice -c3 cp …` helps a bit).

4. **Most robust / production grade** (no writer pause, large DBs)

   - LVM snapshot (if your volume is on LVM)
     1. `lvcreate --snapshot …`
     2. Mount snapshot (or just copy files from it)
     3. Drop snapshot

   - Filesystem with native CoW/reflink + atomic-ish copy (but **ext4 does not support reflink** — you need btrfs / xfs with reflink support)

   - Litestream, LiteFS, rqlite, sqlite3_rsync (newer tool), or similar replication tools

### Quick summary – what to do

- Want **zero risk** and still allow concurrent writes?  
  → Use **`.backup`** command or **sqlite3_backup_init** API

- Can accept very short writer stall?  
  → `BEGIN IMMEDIATE; cp …; ROLLBACK;`

- Just hoping for the best (small & quiet DB)?  
  → `cp` will probably work… until the day it doesn't.
