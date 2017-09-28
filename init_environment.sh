#!/usr/bin/env bash
psql -U postgres -c "CREATE ROLE gridquizuser WITH CREATEDB LOGIN;" &&
createdb -h localhost -p 5432 -U gridquizuser gridquiz
