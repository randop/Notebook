# PostgreSQL

> PostgreSQL, also known as Postgres, is a free and open-source relational database management system emphasizing extensibility and SQL compliance. It was originally named POSTGRES, referring to its origins as a successor to the Ingres database developed at the University of California, Berkeley. https://en.wikipedia.org/wiki/PostgreSQL

## Creating database backup
> This will create a sql dump with the filename formatted like 20770727_local.sql
```
pg_dump \
  'postgresql://user@127.0.0.1:5432/db' \
  --no-owner --verbose \
  --format=plain --blobs \
  --section=pre-data --section=data --section=post-data \
  --clean --inserts --column-inserts \
  --no-subscriptions --no-publications \
  --no-tablespaces --no-security-labels \
  --no-privileges --no-acl \
  --exclude-table-data=large_table \
  --file=$(date +"%Y%m%d")_local.sql
```

## Restoring database backup
> ⚠ CRITICAL: Make sure to perform this operation on local machine only to avoid overwriting any production data.
```
psql \
  "postgresql://user:password@127.0.0.1/db" \
  -verbose \
  -f 20770727_local.sql
```

