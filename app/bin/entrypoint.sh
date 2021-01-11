#!/bin/bash
# prepare the full URL based on the hostname from AWS

export SPRING_DATASOURCE_URL="jdbc:postgresql://$POSTGRES_HOST:5432/$POSTGRES_DBNAME"
echo $SPRING_DATASOURCE_URL
exec java -jar -Dspring.profiles.active=local -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS starter-java.jar