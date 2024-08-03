#!/bin/bash
set -e

echo "Looking for .env file containing environment variables..."

# Grab environment variables from .env
test -e ".env" && echo "Found .env file" || echo "Could not find .env file"
# Remove Windows line feeds
source ".env" &> /dev/null || sed -i 's/\r//g' .env && source ".env"



#
# REQUIRED ENVIRONMENT VARIABLES
#
if [ -z "$PG_USER" ] || [ -z "$PG_DB_NAME" ] || [ -z "$PG_PORT" ] || [ -z "$PG_PASSWORD" ] || [ -z "$JWT_SECRET_KEY" ] || [ -z "$MAPS_API_KEY" ]; then
    echo "Missing one or more required environment variables. See db_setup.sh"
    exit 1
fi
echo " - PG_USER set to '$PG_USER'"
echo " - PG_DB_NAME set to '$PG_DB_NAME'"
echo " - PG_PORT set to '$PG_PORT'"
echo " - PG_PASSWORD set to '$PG_PASSWORD'"
echo " - JWT_SECRET_KEY set to '$JWT_SECRET_KEY'"
echo " - MAPS_API_KEY set to '$MAPS_API_KEY'"


# Check for docker
echo "Checking for docker command..."
command -v docker &> /dev/null || (echo "Docker engine not installed!" && exit)
echo "Found docker command"
# Check for psql
echo "Checking for psql command..."
command -v psql &> /dev/null || (echo "PostgreSQL not installed! Could not find psql in PATH: \n $PATH" && exit)
echo "Found psql command"

# Boot up for the first time...
echo "Loading Postgres"
docker compose up -d

TIMEOUT=0

until PGPASSWORD="$PG_PASSWORD" psql -h "localhost" --username "$PG_USER" -p "$PG_PORT" --dbname "$PG_DB_NAME" -c '\q' &> /dev/null || [ $TIMEOUT == 30 ]; do
  echo "Waiting for Postgres to load... ($TIMEOUT tries)"
  TIMEOUT=$((TIMEOUT + 1))
  sleep 1
done

echo "Postgres loaded! Creating tables..."

PGPASSWORD="$PG_PASSWORD" psql -h "localhost" -v ON_ERROR_STOP=1 --username "$PG_USER" -p "$PG_PORT" --dbname "$PG_DB_NAME" <<-EOSQL
CREATE TABLE IF NOT EXISTS alerts
(
    alert_id SERIAL4 NOT NULL UNIQUE,
    location_name VARCHAR NOT NULL,
    latitude FLOAT8 NOT NULL,
    longitude FLOAT8 NOT NULL,
    radius INT4 NOT NULL,
    message VARCHAR NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP,

    PRIMARY KEY (alert_id)
);
CREATE TABLE IF NOT EXISTS users
(
    user_id SERIAL4 NOT NULL UNIQUE,
    email CHARACTER VARYING(50) NOT NULL,
    password_hash CHARACTER VARYING(72) NOT NULL,
    created_at TIMESTAMP,

    PRIMARY KEY (user_id)
);
EOSQL

echo "Tables created. Running migrations..."

./migration-7-20-2024.sh
./migration-7-28-2024.sh

echo "All migrations complete. Shutting down docker..."

docker compose down

echo "Postgres Database setup is complete. Run \`docker compose up -d\` in WSL/Bash, then run TestAppApplication to start backend."
