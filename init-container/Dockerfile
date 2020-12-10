FROM alpine:3.7
RUN apk --no-cache add postgresql-client
COPY init_pg.sql .
CMD sleep 20;psql "$POSTGRES_URL" -v username="$USERNAME" -v dbname="$DBNAME" -v password="$PASSWORD" -f init_pg.sql
