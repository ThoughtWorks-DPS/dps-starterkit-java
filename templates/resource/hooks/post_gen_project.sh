#! /usr/bin/env bash

pwd=`pwd`

function remove {
  local file=$1

  echo "Removing file [${file}]" >> /tmp/cc.out
  rm "${file}"
}

cd db-init/src/main/resources/database/migrations
latestVersion=$(ls | sort | head -1 | sed -e 's/__.*//')
nextVersion="${latestVersion}.1"
mv {VNEXT,${nextVersion}}__Template_{{cookiecutter.RESOURCE_NAME}}.sql

cd "${pwd}"

tl="{{cookiecutter.PKG_TL_NAME}}"
org="{{cookiecutter.PKG_ORG_NAME}}"
group="{{cookiecutter.PKG_GROUP_NAME}}"
service="{{cookiecutter.PKG_SERVICE_NAME}}"
pkgResource="{{cookiecutter.PKG_RESOURCE_NAME}}"
resource="{{cookiecutter.RESOURCE_NAME}}"
subresource="{{cookiecutter.SUB_RESOURCE_NAME}}"

if [ "{{cookiecutter.CREATE_SUB_RESOURCE}}" = "n" ]
then
  remove api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${pkgResource}"/requests/${subresource}Request.java
  remove api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${pkgResource}"/resources/${resource}${subresource}Resource.java
  remove api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${pkgResource}"/responses/${subresource}Response.java
  remove api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${pkgResource}"/responses/Paged${subresource}Response.java
  remove controller/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/controller/"${pkgResource}"/${resource}${subresource}Controller.java
  remove controller/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/controller/"${pkgResource}"/mapper/${resource}${subresource}RequestMapper.java
  remove controller/src/test/java/"${tl}"/"${org}"/"${group}"/"${service}"/controller/"${pkgResource}"/${resource}${subresource}ControllerTest.java
  remove controller/src/test/java/"${tl}"/"${org}"/"${group}"/"${service}"/controller/"${pkgResource}"/mapper/${resource}${subresource}RequestMapperTest.java
  remove service/provider/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/provider/"${pkgResource}"/${resource}${subresource}ServiceImpl.java
  remove service/provider/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/provider/"${pkgResource}"/mapper/${resource}${subresource}EntityMapper.java
  remove service/provider/src/test/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/provider/"${pkgResource}"/${resource}${subresource}ServiceImplTest.java
  remove service/provider/src/test/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/provider/"${pkgResource}"/mapper/${resource}${subresource}EntityMapperTest.java
  remove service/spi/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/spi/"${pkgResource}"/${resource}${subresource}Service.java
  remove service/spi/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/spi/"${pkgResource}"/model/${subresource}.java
else
  echo "Not removing files" >> /tmp/cc.out
  echo api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${pkgResource}"/requests/${subresource}Request.java >> /tmp/cc.out
  echo api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${pkgResource}"/resources/${resource}${subresource}Resource.java
  echo api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${pkgResource}"/responses/${subresource}Response.java >> /tmp/cc.out
  echo api/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/api/"${pkgResource}"/responses/Paged${subresource}Response.java >> /tmp/cc.out
  echo controller/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/controller/"${pkgResource}"/${resource}${subresource}Controller.java
  echo controller/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/controller/"${pkgResource}"/mapper/${resource}${subresource}RequestMapper.java
  echo controller/src/test/java/"${tl}"/"${org}"/"${group}"/"${service}"/controller/"${pkgResource}"/${resource}${subresource}ControllerTest.java
  echo controller/src/test/java/"${tl}"/"${org}"/"${group}"/"${service}"/controller/"${pkgResource}"/mapper/${resource}${subresource}RequestMapperTest.java
  echo service/provider/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/provider/"${pkgResource}"/${resource}${subresource}ServiceImpl.java
  echo service/provider/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/provider/"${pkgResource}"/mapper/${resource}${subresource}EntityMapper.java
  echo service/provider/src/test/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/provider/"${pkgResource}"/${resource}${subresource}ServiceImplTest.java
  echo service/provider/src/test/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/provider/"${pkgResource}"/mapper/${resource}${subresource}EntityMapperTest.java
  echo service/spi/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/spi/"${pkgResource}"/${resource}${subresource}Service.java
  echo service/spi/src/main/java/"${tl}"/"${org}"/"${group}"/"${service}"/service/spi/"${pkgResource}"/model/${subresource}.java >> /tmp/cc.out
fi

cd "${pwd}"
