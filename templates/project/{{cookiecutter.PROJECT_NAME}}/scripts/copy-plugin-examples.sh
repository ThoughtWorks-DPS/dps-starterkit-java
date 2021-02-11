#!/usr/bin/env bash


src=${1:-../dps-starter-boot/plugins/src/main/groovy}
dst=./buildSrc/src/main/groovy

if [ ! -d "${dst}" ]
then
  echo "No [$dst] path exists"
  exit -1
fi

function copy_files {
  local srcPath=$1
  local dstPath=$2

  for i in "${srcPath}"/*.gradle
  do
    file=$(basename "${i}")
    echo "[$i] [$file]"
    cp $i "${dstPath}"/"${file/starter/local}".example
  done
}

copy_files "${src}" "${dst}"
copy_files "${src}" "templates/project/${dst}"
