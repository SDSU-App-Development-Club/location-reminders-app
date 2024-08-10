#!/bin/bash
set -e

echo "Running migration 7-28-2024..."

# Grab environment variables from .env
test -e ".env" && echo "Found .env file" || echo "Could not find .env file"
# Remove Windows line feeds
source ".env" &> /dev/null || sed -i 's/\r//g' .env && source ".env"

echo "Loading Postgres"
docker compose up -d

TIMEOUT=0

until PGPASSWORD="$PG_PASSWORD" psql -h "localhost" --username "$PG_USER" -p "$PG_PORT" --dbname "$PG_DB_NAME" -c '\q' &> /dev/null || [ $TIMEOUT == 30 ]; do
  echo "Waiting for Postgres to load... ($TIMEOUT tries)"
  TIMEOUT=$((TIMEOUT + 1))
  sleep 1
done

echo "Postgres loaded! Applying migration..."

PGPASSWORD="$PG_PASSWORD" psql -h "localhost" -v ON_ERROR_STOP=1 --username "$PG_USER" -p "$PG_PORT" --dbname "$PG_DB_NAME" <<-EOSQL
BEGIN;
ALTER TABLE alerts DROP COLUMN IF EXISTS alert_id;
ALTER TABLE alerts DROP COLUMN IF EXISTS place_id;
ALTER TABLE alerts DROP COLUMN IF EXISTS user_id;
ALTER TABLE alerts RENAME COLUMN alertId TO alert_id;
ALTER TABLE alerts RENAME COLUMN placeId TO place_id;
ALTER TABLE alerts RENAME COLUMN userId TO user_id;
ALTER TABLE users DROP COLUMN IF EXISTS user_id;
ALTER TABLE users DROP COLUMN IF EXISTS password_hash;
ALTER TABLE users DROP COLUMN IF EXISTS created_at;
ALTER TABLE users RENAME COLUMN userId TO user_id;
ALTER TABLE users RENAME COLUMN passwordHash TO password_hash;
ALTER TABLE users RENAME COLUMN createdAt TO created_at;
COMMIT;
EOSQL

echo "Postgres loaded! Applying migration..."
