#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$PG_USER" --dbname "alerts" <<-EOSQL
CREATE TABLE IF NOT EXISTS alerts
(
    alert_id SERIAL4 NOT NULL UNIQUE,
    location_name VARCHAR NOT NULL,
    latitude FLOAT8 NOT NULL,
    longitude FLOAT8 NOT NULL,
    radius INT4 NOT NULL,
    message VARCHAR NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP
);
EOSQL