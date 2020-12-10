-- "username" and "dbname" are variables that must be
-- passed in from the commandline
-- ex) psql "$POSTGRES_URL" -v username="$USERNAME" -v dbname="$DBNAME" -f init_pg.sql
CREATE USER :username WITH PASSWORD ':password' ;
CREATE DATABASE :dbname WITH OWNER :username;