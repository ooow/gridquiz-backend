#!/usr/bin/env bash
psql -U postgres -c "CREATE ROLE gridquizuser WITH CREATEDB LOGIN;" &&
createdb -h localhost -p 5432 -U gridquizuser gridquiz              &&
psql -U gridquizuser -d gridquiz -c "INSERT INTO users VALUES (0, 'admin@hr.com', 'admin', '', 1, 'eooYPagGx2tr1f343726b9bb43a0')"