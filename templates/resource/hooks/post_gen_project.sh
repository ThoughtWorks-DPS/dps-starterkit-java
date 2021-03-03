#! /usr/bin/env bash

pwd=`pwd`

cd flyway/src/main/resources/database/migrations
latestVersion=$(ls | sort | head -1 | sed -e 's/__.*//')
nextVersion="${latestVersion}.1"
mv {VNEXT,${nextVersion}}__Initial_{{cookiecutter.RESOURCE_NAME}}.sql

cd "${pwd}"
