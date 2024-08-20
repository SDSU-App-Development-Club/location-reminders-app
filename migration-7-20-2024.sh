#!/bin/bash
set -e

echo "Running migration 7-20-2024..."

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
ALTER TABLE alerts RENAME COLUMN location_name TO title;
ALTER TABLE alerts RENAME COLUMN alert_id TO alertId;
ALTER TABLE alerts ADD COLUMN emoji TEXT;
ALTER TABLE alerts ADD COLUMN placeId TEXT;
ALTER TABLE alerts ADD COLUMN userId INT4;
ALTER TABLE alerts DROP COLUMN latitude;
ALTER TABLE alerts DROP COLUMN longitude;
ALTER TABLE alerts DROP COLUMN active;
ALTER TABLE alerts DROP COLUMN created_at;
ALTER TABLE users RENAME COLUMN user_id TO userId;
ALTER TABLE users RENAME COLUMN password_hash TO passwordHash;
ALTER TABLE users RENAME COLUMN created_at TO createdAt;
COMMIT;
EOSQL

echo "Postgres loaded! Applying migration..."
