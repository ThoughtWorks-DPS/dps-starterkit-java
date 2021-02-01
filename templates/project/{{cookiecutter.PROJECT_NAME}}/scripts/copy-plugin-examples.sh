#!/usr/bin/env bash


src=${1:-../dps-starter-boot/plugins/src/main/groovy}
dst=./buildSrc/src/main/groovy

if [ ! -d "${dst}" ]
then
  echo "No [$dst] path exists"
  exit -1
fi

for i in $src/*.gradle
do
  file=$(basename $i)
  echo "[$i] [$file]"
  cp $i $dst/${file/starter/local}.example
done
