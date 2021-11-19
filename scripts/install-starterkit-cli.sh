#!/bin/bash

rawSource="https://raw.githubusercontent.com/thoughtworks-dps/dps-starterkit-java/main/scripts/generate-skeleton.sh"
installPath="/usr/local/bin"
useSudo=n

function usage {
  echo "${0} [--install <path>] --sudo"
  echo "  --path         installation path for skcli ($installPath)"
  echo "  --sudo         use sudo to install"
}

function get_command {
  local cmd=$1

  if [[ $useSudo == "y" ]]
  then
    echo "sudo ${cmd}"
  else
    echo "${cmd}"
  fi
}

function ensure_path {
  local path=$1

  [[ -d "${path}" ]] || $(get_command mkdir) -p "${path}"
}

function update_permissions {
  local file=$1

  $(get_command chmod) a+x "${file}"
}

function use_wget {
  local path=$1
  $(get_command wget) -q -O "${installPath}/skcli" "${rawSource}"
  update_permissions "${installPath}/skcli"
}

function use_wget {
  local path=$1
  $(get_command curl) -s -o "${installPath}/skcli" "${rawSource}"
  update_permissions "${installPath}/skcli"
}

while [ $# -gt 0 ]
do
  case $1 in
  --install) shift; installPath=$1;;
  --sudo) useSudo=y;;
  --help) usage; exit 0;;
  --*) echo "Invalid option [$1]"; usage; exit 1;;
  esac
  shift
done

ensure_path "${installPath}"

if [[ -n "$(which wget)" ]]
then
  use_wget "${installPath}"
elif [[ -n "$(which curl)" ]]
then
  use_curl "${installPath}"
else
  echo "Error: wget or curl not available"
  exit 1
fi


