#!/usr/bin/env bash

path="."
origTl='io'
origOrg='twdps'
origGroup='starter'
origService='example'
origResource='account'
tl='{{cookiecutter.PKG_TL_NAME}}'
org='{{cookiecutter.PKG_ORG_NAME}}'
group='{{cookiecutter.PKG_GROUP_NAME}}'
service='{{cookiecutter.PKG_SERVICE_NAME}}'
resource='{{cookiecutter.PKG_RESOURCE_NAME}}'

function usage {
  echo "$0 [--path <path>]"
  echo "  --path        path to process ($path)"
  echo "  --tl          top level package name ($tl)}"
  echo "  --org         org level package name ($org)"
  echo "  --group       org level package name ($group)"
  echo "  --service     org level package name ($service)"
  echo "  --resource    org level package name ($resource)"
  echo "  --orig-*      original names of each tl|org|group|service|resource ($origTl:$origOrg:$origGroup:$origService:$origResource)"
  echo "  --help        display this help"
}

function rename_dir {
  local from=$1
  local to=$2
  if [ -d "${from}" ]
  then
    mv "${from}" "${to}"
  fi
}

function process_dir {
  local path=$1

  local pwd=$(pwd)
  cd "${path}"

  for i in $(find . -type d -depth 1)
  do
    local name=$(basename "$i")
    process_dir "${name}"
    [[ "${name}" = "${origTl}" ]] && rename_dir ${name} "${tl}"
    [[ "${name}" = "${origOrg}" ]] && rename_dir ${name} "${org}"
    [[ "${name}" = "${origGroup}" ]] && rename_dir ${name} "${group}"
    [[ "${name}" = "${origService}" ]] && rename_dir ${name} "${service}"
    [[ "${name}" = "${origResource}" ]] && rename_dir ${name} "${resource}"
  done
  cd "${pwd}"
}

while [ $# -gt 0 ]
do
  case $1 in
  --path) shift; path=$1;;
  --tl) shift; tl=$1;;
  --org) shift; org=$1;;
  --group) shift; group=$1;;
  --service) shift; service=$1;;
  --resource) shift; resource=$1;;
  --orig-tl) shift; origTl=$1;;
  --orig-org) shift; origOrg=$1;;
  --orig-group) shift; origGroup=$1;;
  --orig-service) shift; origService=$1;;
  --orig-resource) shift; origResource=$1;;
  --help) usage; exit 0;;
  *) usage; exit -1;;
  esac
  shift;
done

pwd=$(pwd)

process_dir "${path}"

cd "${pwd}"
