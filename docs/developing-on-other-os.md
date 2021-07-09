# Developing on Other Operating Systems

## Overview

In order to build the application on a non-Mac OS, you will need to have the dependencies listed in the [build dependencies section](#build-dependencies) of this document available in your local environment.

In order to customize the application and test your changes, you will need an additional set of dependencies available in your local environment.
These dependencies are listed in the [development requirements section](#development-dependencies) of this document.

## Setup

### Build Dependencies

This is a list of dependencies required to build and run the application:

- [java 16](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)
- [docker](https://docs.docker.com/get-docker/)
- [haodolint](https://github.com/hadolint/hadolint#install)
- [shellcheck](https://github.com/koalaman/shellcheck#readme)

### Development Dependencies

This is a list of dependencies required to make and test changes to the application while doing routine development:

- [pre-commit](https://pre-commit.com/#install)
- [adr-tools](https://github.com/npryce/adr-tools#adr-tools)
- [kubectl](https://kubernetes.io/docs/tasks/tools/)
- [circleci](https://circleci.com/docs/2.0/local-cli)
- [helm](https://helm.sh/docs/intro/install/)
- [istioctl](https://istio.io/latest/docs/ops/diagnostic-tools/istioctl)
- [minikube](https://minikube.sigs.k8s.io/docs/start/)
- [codeclimate/formulae/codeclimate](https://github.com/codeclimate/codeclimate#code-climate-cli)
- [mkdocs](https://www.mkdocs.org/getting-started/)
- [scala](https://docs.scala-lang.org/getting-started/index.html)

## Commonly Encountered Issues

### Cloning a repo using Git for Windows

Git has a limit of 4096 characters for a filename, except on Windows when Git is compiled with msys.
It uses an older version of the Windows API and there's a limit of 260 characters for a filename.
Cloning this repo will result in an error stating:

```text
GitHub.IO.ProcessException: fatal: unable to stat '<filename>': Filename too long
```

And the repo will not be successfully cloned.
You can circumvent this by using another Git client on Windows or set core.longpaths to true.

```bash
git config --system core.longpaths true
```

Once completed, the repo can be successfully cloned.
