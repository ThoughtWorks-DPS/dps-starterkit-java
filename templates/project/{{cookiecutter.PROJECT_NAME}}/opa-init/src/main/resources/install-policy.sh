#! /bin/sh


file="/policy.rego"
url="opa:8181"
path="/v1/policies/authz"
retries=10
delay=10

function usage {
  echo "$0 [--url <OPA base URL>] [--file <rego file>] [--path <policy path>]"
  echo "  --url         base url of OPA server (${url})"
  echo "  --file        Rego policy file to process (${file})"
  echo "  --path        OPA document path to install policy file (${path})"
  echo "  --retries     how many retries while we wait for OPA server (${retries})"
  echo "  --delay       delay in between retries (${delay})"
  echo "  --help        display this help"
}

while [ $# -gt 0 ]
do
  case $1 in
  --url) shift; url=$1;;
  --file) shift; file=$1;;
  --path) shift; path=$1;;
  --help) usage; exit 0;;
  *) usage; exit -1;;
  esac
  shift;
done

result=1
while [[ ${result} -neq 0 ]]
do
  sleep ${delay}
  curl -X PUT --data-binary @"${file}" ${url}${path}
  result = $?
done
