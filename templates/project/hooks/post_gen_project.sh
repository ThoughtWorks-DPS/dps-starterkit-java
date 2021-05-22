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

cmdline="${binary} "
cmdline="${cmdline} --verbose"
cmdline="${cmdline} --debug-file {{cookiecutter.debugLog}}"
cmdline="${cmdline} -f"
cmdline="${cmdline} --no-input"
cmdline="${cmdline} -o ${outputPath}"
cmdline="${cmdline} ${templatePath}"
cmdline="${cmdline} --directory templates/resource"
cmdline="${cmdline} RESOURCE_VAR_NAME={{cookiecutter.SUB_RESOURCE_VAR_NAME}}"
cmdline="${cmdline} CREATE_PARENT_RESOURCE=y"
cmdline="${cmdline} PARENT_RESOURCE_VAR_NAME={{cookiecutter.RESOURCE_VAR_NAME}}"
cmdline="${cmdline} PROJECT_NAME={{cookiecutter.PROJECT_NAME}}"
cmdline="${cmdline} PKG_GROUP_NAME={{cookiecutter.PKG_GROUP_NAME}}"
cmdline="${cmdline} SERVICE_NAME={{cookiecutter.SERVICE_NAME}} "
cmdline="${cmdline} CREATE_SUB_RESOURCE=n"
echo "pwd: [$(pwd)] [${cmdline}]" >> {{cookiecutter.debugLog}}
${cmdline}

cmdline="${binary} "
cmdline="${cmdline} --verbose"
cmdline="${cmdline} --debug-file {{cookiecutter.debugLog}}"
cmdline="${cmdline} -f"
cmdline="${cmdline} --no-input"
cmdline="${cmdline} -o ${outputPath}"
cmdline="${cmdline} ${templatePath}"
cmdline="${cmdline} --directory templates/resource"
cmdline="${cmdline} RESOURCE_VAR_NAME={{cookiecutter.RESOURCE_VAR_NAME}}"
cmdline="${cmdline} SUB_RESOURCE_VAR_NAME={{cookiecutter.SUB_RESOURCE_VAR_NAME}}"
cmdline="${cmdline} PROJECT_NAME={{cookiecutter.PROJECT_NAME}}"
cmdline="${cmdline} PKG_GROUP_NAME={{cookiecutter.PKG_GROUP_NAME}}"
cmdline="${cmdline} SERVICE_NAME={{cookiecutter.SERVICE_NAME}}"
cmdline="${cmdline} CREATE_SUB_RESOURCE=y"
echo "pwd: [$(pwd)] [${cmdline}]" >> {{cookiecutter.debugLog}}
${cmdline}

cd "${pwd}"
{% endif %}
