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

cmdline="${binary} -f --no-input -o ${outputPath} ${templatePath}/resource"

#echo "pwd: [$(pwd)] [${cmdline}]"
${cmdline}
cd "${pwd}"
{% endif %}
