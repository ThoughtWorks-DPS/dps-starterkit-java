FROM flyway/flyway:7.5.4
COPY database /flyway/sql
COPY flyway.conf /flyway/conf

CMD [ "migrate", "-X" ]
