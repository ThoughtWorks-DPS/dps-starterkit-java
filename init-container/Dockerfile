FROM alpine:3.7
RUN apk --no-cache add postgresql-client
COPY init_pg.sql .
CMD sleep 20;echo "attempting to connect"; psql "postgres://$POSTGRES_ROOT_USER:$POSTGRES_ROOT_PASSWORD@$POSTGRES_HOST:5432/postgres" -v username="$POSTGRES_USER" -v dbname="$POSTGRES_DBNAME" -v password="$POSTGRES_PASSWORD" -f init_pg.sql
