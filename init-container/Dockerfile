FROM alpine:3.7
RUN apk --no-cache add postgresql-client
COPY init_pg.sql .
CMD sleep 20;PGPASSWORD=$POSTGRES_ROOT_PASSWORD psql "postgres://$POSTGRES_ROOT_USER@$POSTGRES_HOST:5432" -v username="$POSTGRES_USER" -v dbname="$POSTGRES_DBNAME" -v password="$POSTGRES_PASSWORD" -f init_pg.sql
