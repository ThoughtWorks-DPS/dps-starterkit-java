#!/bin/bash

path=$(dirname "$0")
binary="${path}"/generate-skeleton.sh
release=release-0.1.0
releaseOpt="--tag ${release}"
repoPath="--repo https://github.com/thoughtworks-dps/dps-starterkit-java"
#repoPath="--repo ~/Documents/src/gst/gst-starterkit-java"
tl="--tl com"
org="--org tw"
project="--project collector"
service="--service Collector"
output="--output ~/src/dps/tmp"

function cmdLineOptions {
  echo "${repoPath} ${project} ${service} ${output}"
}

function exec_cmd {
  echo "${binary}" $* >> /tmp/cc
  sh -c "${binary} $*"
}

function exec_std {
  exec_cmd $(cmdLineOptions) $*
}

exec_std --gen-skeleton
exec_std --parent producer --child channel --gen-parent
exec_std --parent channel --child job --gen-child
exec_std --parent channel --child artifact --gen-child
exec_std --parent producer --resource channel --child job --gen-link
exec_std --parent producer --resource channel --child artifact --gen-link
