#!/usr/bin/env bash

# prepare the full URL based on the hostname from AWS

export SPRING_APPLICATION_DATASOURCE_URL="postgres://$POSTGRES_HOST:5432/$POSTGRES_DBNAME"

java -jar /starer-java.jar