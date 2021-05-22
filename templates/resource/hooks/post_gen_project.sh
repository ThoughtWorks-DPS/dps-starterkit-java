#! /usr/bin/env bash

pwd=`pwd`

function remove {
  local file=$1

  echo "Removing file [${file}]" >> /tmp/cc.out
  rm "${file}"
}

cd flyway/src/main/resources/database/migrations
latestVersion=$(ls | sort | head -1 | sed -e 's/__.*//')
nextVersion="${latestVersion}.1"
mv {VNEXT,${nextVersion}}__Template_{{cookiecutter.RESOURCE_NAME}}.sql

cd "${pwd}"

tl="{{cookiecutter.PKG_TL_NAME}}"
org="{{cookiecutter.PKG_ORG_NAME}}"
group="{{cookiecutter.PKG_GROUP_NAME}}"
service="{{cookiecutter.PKG_SERVICE_NAME}}"
resource="{{cookiecutter.PKG_RESOURCE_NAME}}"
subresource="{{cookiecutter.SUB_RESOURCE_NAME}}"

if [ "{{cookiecutter.CREATE_SUB_RESOURCE}}" = "n" ]
then
  remove api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${resource}"/requests/${subresource}Request.java
  remove api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${resource}"/responses/${subresource}Response.java
  remove api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${resource}"/responses/Paged${subresource}Response.java
  remove service/spi/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/spi/"${resource}"/model/${subresource}.java
else
  echo "Not removing files" >> /tmp/cc.out
  echo api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${resource}"/requests/${subresource}Request.java >> /tmp/cc.out
  echo api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${resource}"/responses/${subresource}Response.java >> /tmp/cc.out
  echo api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${resource}"/responses/Paged${subresource}Response.java >> /tmp/cc.out
  echo service/spi/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/spi/"${resource}"/model/${subresource}.java >> /tmp/cc.out
fi

cd "${pwd}"
