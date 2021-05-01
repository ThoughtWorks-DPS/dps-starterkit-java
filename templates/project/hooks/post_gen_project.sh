#! /usr/bin/env bash

{% if cookiecutter.projectDir != "" %}
pwd=`pwd`
outputPath=${pwd}/..

ccProjectPath={{cookiecutter.projectDir}}
templatePath=${ccProjectPath:-.}

ccBinary={{cookiecutter.binary}}
binary=${ccBinary:-cookiecutter}

ccOutputPath={{cookiecutter.outputPath}}
outputPath=${ccOutputPath:-${pwd}/..}

cmdline="${binary} -f --no-input -o ${outputPath} ${templatePath}/resource RESOURCE_NAME=SubAccount CREATE_PARENT_RESOURCE=y PARENT_RESOURCE_NAME=Account CREATE_SUB_RESOURCE=n"
echo "pwd: [$(pwd)] [${cmdline}]"
${cmdline}

cmdline="${binary} -f --no-input -o ${outputPath} ${templatePath}/resource CREATE_SUB_RESOURCE=y"
echo "pwd: [$(pwd)] [${cmdline}]"
${cmdline}

cd "${pwd}"
{% endif %}
