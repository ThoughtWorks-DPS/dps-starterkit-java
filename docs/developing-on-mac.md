# Developing on Mac OS

## Overview

In order to build the application on Mac OS, you will need to have the dependencies listed in the [build dependencies section](#build-dependencies) of this document available in your local environment.

In order to customize the application and test your changes, you will need an additional set of dependencies available in your local environment.
These dependencies are listed in the [development requirements section](#development-dependencies) of this document.

## Automated Setup

You can use [Homebrew](https://brew.sh/) and the included `mac-dev-tools.sh` script to quickly install both the required build and development dependencies.
To install all these dependencies, run the following command from the root of your generated application:

```bash
./scripts/mac-dev-tools.sh
```

## Manual Setup

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
- [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl-macos/)
- [circleci](https://circleci.com/docs/2.0/local-cli)
- [helm](https://helm.sh/docs/intro/install/)
- [istioctl](https://istio.io/latest/docs/ops/diagnostic-tools/istioctl)
- [minikube](https://minikube.sigs.k8s.io/docs/start/)
- [codeclimate/formulae/codeclimate](https://github.com/codeclimate/codeclimate#code-climate-cli)
- [mkdocs](https://www.mkdocs.org/getting-started/)
- [scala](https://docs.scala-lang.org/getting-started/index.html)

## Commonly Encountered Issues

### Running the correct version of Java

Mac users can execute the following command to install the latest version of Java:

```bash
brew install adoptopenjdk14
```

Verify that the correct Java version is being used by running:

```bash
java -version
```

If needed, set your machine to use the correct version of Java that was installed by
adding the following line to your `.bashrc` or `.zshrc`.
(Replace <JAVA_VERSION> with the correct version)

```bash
export JAVA_HOME=/usr/local/Cellar/openjdk/<JAVA_VERSION>/libexec/openjdk.jdk/Contents/Home
```

Reload the shell by running:

```bash
source .bashrc
#or
source .zshrc
```

### Running Pre-commit Hooks

To make sure that pre-commit runs successfully, you will need to install it first by running the following command in the root of your generated application:

```bash
pre-commit install && pre-commit install -t commit-msg
```

If you need to customize the commit message format enforced by pre-commit, you may do so in `scripts/commit-message.sh`
