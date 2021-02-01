#!/usr/bin/env bash

checkDirDiffs() {
  randomDir=$1
  diff -r . ./out/"$randomDir"/dps-multi-modules-starterkit-java \
    -x .git \
    -x .idea \
    -x .gradle \
    -x build \
    -x template \
    -x .circleci \
    -x .pre-commit-config.yaml \
    -x catalog-info.yaml \
    -x mkdocs.yml \
    -x verify-generated-proj.sh \
    -x out

    # The circleci config and pre-commit-config files contain differences; the source starter is testing
    # the template on circleci which does not make sense to have in our generated template project.
    # The pre-commit is checking the diffs between a generated project and the source.
}

randDir=$(openssl rand -base64 12)

cookiecutter --no-input --output-dir out/"$randDir" template

if ! checkDirDiffs "$randDir";
  then
    echo "There are some differences between the template and the source starter code, either exclude the files or add them to their respective directories."
    echo -e "Exclude files here: " $PWD/scripts/verify-generated-proj.sh
    exit 1
fi

rm -rf out
