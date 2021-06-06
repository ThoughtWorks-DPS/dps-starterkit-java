#!/bin/bash

repo=git+ssh://git@github.com/{{cookiecutter.GITHUB_ORG_NAME}}/dps-multi-module-starterkit-java.git
parent="fixmeParent"
child="fixmeChild"
resource="fixmeResource"
outputPath="."
projectName="{{cookiecutter.PROJECT_NAME}}"
serviceName="{{cookiecutter.SERVICE_NAME}}"
binary="cookiecutter"
tag=""

function usage {
  echo "${0} [--tag <tag>] [--parent <name>] [--child <name>] [--resource <name>] \ "
  echo "     [--gen-parent] [--gen-child] [--gen-resource] [--gen-link] \ "
  echo "     [--repo <repo>] [--output <path>]  [--binary <path>] \ "
  echo "     [--project <projectName>] [--service <serviceName>]"
  echo "  --tag          release tag to checkout ($tag)"
  echo "  --parent       name of parent resource variable name (${parent})"
  echo "  --child        name of child resource variable name (${child})"
  echo "  --resource     name of standalone / link resource variable name (${resource})"
  echo "  --repo         URL or path to template repository (${repo})"
  echo "  --binary       path to cookiecutter binary (${binary})"
  echo "  --project      name of project (${projectName})"
  echo "  --service      name of the service (${serviceName})"
  echo "  --output       path for generated output (${outputPath})"
  echo "  --gen-skeleton generate the core service structure"
  echo "  --gen-parent   generate the parent resource"
  echo "  --gen-child    generate the child resource"
  echo "  --gen-link     generate the link resource with both parent and child"
  echo "  --gen-resource generate the standalone resource"
}

function generate {
  local cmd=$*

  echo "[$(pwd)] cmd[${cmd}]" >> /tmp/cc.log
  ${cmd}
}

function get_baseline {
  local path=$1

  baseline="${binary} "
  baseline="${baseline} --verbose"
  baseline="${baseline} --debug-file /tmp/cc.out"
  baseline="${baseline} --no-input"
  baseline="${baseline} -f"
  baseline="${baseline} -o ${outputPath}"
  baseline="${baseline} ${repo}"
  [ -z "${path}" ] || baseline="${baseline} --directory ${path}"
  [ -z "${tag}" ] || baseline="${baseline} --checkout ${tag}"
  baseline="${baseline} PROJECT_NAME=${projectName}"
  baseline="${baseline} SERVICE_NAME=${serviceName}"

  echo "${baseline}"
}

function generate_skeleton {
  local resourceName=$1

  cmd="$(get_baseline templates/project) "
  cmd="${cmd} RESOURCE_VAR_NAME=${resourceName}"
  generate "${cmd}"
}

function generate_parent {
  local parentName=$1
  local childName=$2

  cmd="$(get_baseline templates/resource) "
  cmd="${cmd} RESOURCE_VAR_NAME=${parentName}"
  cmd="${cmd} SUB_RESOURCE_VAR_NAME=${childName}"
  cmd="${cmd} CREATE_SUB_RESOURCE=y"
  generate "${cmd}"
}

function generate_child {
  local parentName=$1
  local childName=$2

  cmd="$(get_baseline templates/resource)"
  cmd="${cmd} RESOURCE_VAR_NAME=${childName}"
  cmd="${cmd} CREATE_PARENT_RESOURCE=y"
  cmd="${cmd} PARENT_RESOURCE_VAR_NAME=${parentName}"
  cmd="${cmd} CREATE_SUB_RESOURCE=n"
  generate "${cmd}"
}

function generate_resource {
  local resourceName=$1

  cmd="$(get_baseline templates/resource)"
  cmd="${cmd} RESOURCE_VAR_NAME=${resourceName}"
  generate "${cmd}"
}

function generate_link {
  local parentName=$1
  local resourceName=$2
  local childName=$3

  cmd="$(get_baseline templates/resource)"
  cmd="${cmd} RESOURCE_VAR_NAME=${resourceName}"
  cmd="${cmd} CREATE_PARENT_RESOURCE=y"
  cmd="${cmd} PARENT_RESOURCE_VAR_NAME=${parentName}"
  cmd="${cmd} SUB_RESOURCE_VAR_NAME=${childName}"
  cmd="${cmd} CREATE_SUB_RESOURCE=y"
  generate "${cmd}"
}

while [ $# -gt 0 ]
do
  case $1 in
  --tag) shift; tag=$1;;
  --parent) shift; parent=$1;;
  --child) shift; child=$1;;
  --resource) shift; resource=$1;;
  --project) shift; projectName=$1;;
  --service) shift; serviceName=$1;;
  --binary) shift; binary=$1;;
  --repo) shift; repo=$1;;
  --output) shift; outputPath=$1;;
  --gen-skeleton) generate_skeleton "${resource}";;
  --gen-parent) generate_parent "${parent}" "${child}";;
  --gen-child) generate_child "${parent}" "${child}";;
  --gen-resource) generate_resource "${resource}";;
  --gen-link) generate_link "${parent}" "${resource}" "${child}";;
  --help) usage; exit 0;;
  --*) echo "Invalid option [$1]"; usage; exit 1;;
  esac
  shift
done

