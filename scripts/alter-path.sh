#!/usr/bin/env bash

path="."

function usage {
  echo "$0 [--path <path>]"
  echo "  --path   path to process ($path)"
  echo "  --help   display this help"
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
  cd $path

  for i in $(find . -type d -depth 1)
  do
    local name=$(basename $i)
    [[ "${name}" = "starter" ]] && { mkdir starter/servicepath && mv starter/* starter/servicepath 2>&1 > /dev/null;  }
    process_dir $name
    [[ "${name}" = "io" ]] && rename_dir ${name} "{{cookiecutter.PKG_TL_NAME}}"
    [[ "${name}" = "twdps" ]] && rename_dir ${name} "{{cookiecutter.PKG_ORG_NAME}}"
    [[ "${name}" = "starter" ]] && rename_dir ${name} "{{cookiecutter.PKG_GROUP_NAME}}"
    [[ "${name}" = "servicepath" ]] && rename_dir ${name} "{{cookiecutter.PKG_SERVICE_NAME}}"
    [[ "${name}" = "account" ]] && rename_dir ${name} "{{cookiecutter.PKG_RESOURCE_NAME}}"
  done
  cd $pwd
}

while [ $# -gt 0 ]
do
  case $1 in
  --path) shift; path=$1;;
  --help) usage; exit 0;;
  *) usage; exit -1;;
  esac
  shift;
done

pwd=$(pwd)

process_dir $path

cd $pwd
