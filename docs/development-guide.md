# Development Guide

- [About](#about)
- [Initial Setup](#initial-setup)
    - [Setting up a new Git repository](#setting-up-a-new-git-repository)
    - [Pushing changes to version control](#pushing-changes-to-version-control)
    - [Setting up a CI pipeline](#setting-up-a-ci-pipeline)
- [Building and running](#building-and-running)
    - [Building the application](#building-the-application)
    - [Running locally in docker](#running-locally-in-docker)
- [Running tests](#running-tests)
    - [Running unit tests](#running-unit-tests)
    - [Linting](#linting)
    - [Running performance tests](#running-performance-tests)
    - [Setting up and running Docker CIS Benchmark tests locally](#setting-up-and-running-docker-cis-benchmark-tests-locally)
- [Adding to the Architecture Decision Record (ADR)](#adding-to-the-architecture-decision-record-adr)
- [BuildSrc plugins](#buildsrc-plugins)
    - [Updates to example scripts](#updates-to-example-scripts)

---

## About

This guide describes how you can perform common tasks when building an application that has been generated using the Starter Kit.
The guide assumes that you already have all the dependencies required to build and develop your application.

## Initial Setup

### Setting up a new Git repository

In order to set up version control for your project, run the following commands:

```bash
% cd /path-to-generated-project/project-name
% git init --initial-branch=main
% git add .
% git commit --no-verify -m "initial commit"
```

### Pushing changes to version control

These instructions assume you are using GitHub as your version control tool. To push your changes to GitHub, do the following:

1. Follow [these instructions](https://docs.github.com/en/github/getting-started-with-github/quickstart/create-a-repo) to create a new repository for your project.
1. Run the following commands in your terminal to push your changes:

```bash
% git remote add origin git@github.com:department-of-veterans-affairs/<project-name>.git
% git push -u origin main
```

### Setting up a CI pipeline

While you can use other tools to set up and run your project's pipeline, this guide assumes that you are using [CircleCI](https://circleci.com/).

In order to successfully run your project in CircleCI, the Github tokens must be set.
Configure the CircleCI project environment variables `GITHUB_USERNAME` and `GITHUB_ACCESS_TOKEN` with the project's service account's GitHub access token and username.
You can add these environment variables by following these steps:

1. Login to CircleCI by going to [https://app.circleci.com/projects](https://app.circleci.com/projects)
1. Follow [these instructions](https://circleci.com/docs/2.0/project-build/#adding-projects) to add your project
1. Once the project has been added, click on `Project Settings` and then `Environment Variables`
1. Click `Add Environment Variable`
1. Provide the name and value for your environment variable
1. Click `Add Environment Variable`

## Building and running

### Building the application

To build the application, run the following command in your terminal:

```bash
./gradlew clean spotlessApply build check docker
````

This will build the application artifacts and docker images for both the application and its dependencies.

### Running locally in docker

To run the application locally in docker, run the following command in your terminal:

```bash
./gradlew :app:dockerComposeDown :app:dcPrune :app:dockerComposeUp
```

### Running tests

To run unit tests, run the following command:

```bash
./gradlew test
```

Reports for test coverage can be found here: `./build/jacoco/test/index.html`
Run `codeclimate -f html > codeClimateReport.html` to execute Code Climate scanning locally and generate a html report

The build has a lower bound of 80% test coverage, or else the build will fail.
This threshold can be modified by updating either of the following two properties in the root `build.gradle` file:

```groovy
ext {
    jacoco_enforce_violations = true
    jacoco_minimum_coverage = 0.8
}
```

### Running performance tests

With the application running (via `./gradlew :app:dockerComposeUp`), run the following command in a new terminal:

```bash
./gradlew :app:gatlingRun
```

At the end of test execution, an HTML file will be generated with the results.

To customize the simulations that are run during the performance test, edit `app/src/gatling/scala`.

To modify the performance test runner configurations (such as the output directory for the results), edit `app/src/gatling/resources/gatling.conf`.

### Full developer feedback loop

The root `build.gradle` file provides a few convenience tasks that help with the developer workflow:

- `devloop` - builds the code, builds the docker images, and restarts containers via `docker-compose`
- `restartloop` - stops the docker containers, deletes unused images, then proceeds with `devloop`
- `resetloop` - stops the docker containers, deletes unused images and volumes, then proceeds with `devloop`.
  This effectively re-initializes the database from scratch.

Use any of the above tasks to substitute for `devloop` in the workflow shown below:

```bash
gradlew spotlessApply
gradlew devloop
gradlew :app:gatlingRun
## in separate terminal
scripts/consume-kafka.sh 
```

### Linting

Multiple types of linting are integrated into the build.

#### Spotless

Linting via [Spotless](https://github.com/diffplug/spotless) is set to execute automatically prior to committing via pre-commit.
Using spotless locally allows autocorrection of lint offenders.

Spotless is currently set up to enforce the [google-java-format](https://github.com/google/google-java-format).

To execute the Spotless linting checks, run:

```bash
./gradlew spotlessCheck
```

To attempt to auto-format and correct linting errors, run:

```bash
./gradlew spotlessApply
```

#### Hadolint

Linting of Dockerfiles is currently being enforced through the use of [Hadolint](https://github.com/hadolint/hadolint).
This ensures that the images that are built are following [these Docker best practices](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/).
To initiate the Dockerfile check, run the following command:

```bash
./gradlew lintDockerFile
```

This will automatically run hadolint in any sub-module which imports the appropriate docker plugin.

If the Dockerfile linting task fails, an error indicating the affected file and the rule that was violated will be presented in the output (see example below):

```text
== BUILD FAILED ==

FAILURE: Build failed with an exception.

* What went wrong:
  Could not determine the dependencies of task ':app:check'.
> Could not create task ':app:lintDockerFile'.
> src/docker/DockerFile:5 DL3045 warning: `COPY` to a relative destination without `WORKDIR` set.
src/docker/DockerFile:7 DL3045 warning: `COPY` to a relative destination without `WORKDIR` set.
```

In this case, the rule that was violated is [DL3045](https://github.com/hadolint/hadolint/wiki/DL3045).
To see the complete set of rules, go to [https://github.com/hadolint/hadolint#rules](https://github.com/hadolint/hadolint#rules)

> **Note:** By default, the Dockerfile(s) that are linted are located in the general path of: `src/docker/DockerFile` in a given submodule.
> This path can easily be customized in the `build.gradle` of any submodule by modifying the `ext.targets` field:
>
> ```groovy
> tasks.named('lintDockerFile').configure {
>   ext.targets = [ "src/docker/DockerFile" ]
> }
> ```

#### Shellcheck

Linting via [Shellcheck](https://github.com/koalaman/shellcheck) is set to execute as part of the `check` task in the gradle build.
Shellcheck will run lint against bash scripts.
The task is activated via `build.gradle` plugin in the `./scripts` directory.

- Run: `./gradlew shellcheck` in the root directory to execute linting checks.
  Note: there are no automatic corrections, although the error messages generally suggest a solution.

#### Spectral

The Spectral linter will apply a set of rules to the OASv3.0 API specification document.

#### Markdownlint

Linting via [Markdownlint](https://github.com/DavidAnson/markdownlint) is set to execute automatically prior to committing via pre-commit.
Configuration for the markdown linting process is located in `.markdownlint.yaml`

### Setting up and running Docker CIS Benchmark tests locally

The [CIS Docker Benchmark](https://www.cisecurity.org/benchmark/docker/) is a set of guidelines describing common best-practices around deploying docker containers in production.
You can run a full suite of automated tests based on this Benchmark for your application's docker image using the [Docker Bench Security](https://github.com/docker/docker-bench-security) tool.
This runs against your prebuilt local Docker images and any running containers that you specify.

To run the tests, do the following:

1. Make sure you have already built docker images for your application by running the following command.
   This will build new images for your application if there aren't any, or rebuild any existing ones.

    ```bash
    % ./gradlew clean build check docker
    ```

1. Clone the docker-bench-security repo and run the tests, replacing _<PROJECT_NAME>_ with the name of your generated application:

    ```bash
    % git clone https://github.com/docker/docker-bench-security.git
    % cd docker-bench-security
    % sudo sh docker-bench-security.sh -c container_images -i <PROJECT_NAME>
    ```

1. Explore the results presented.
   The results will show a list of tests that were run and their status, using one of the following labels:

    - **Warn** - Test failed and should be fixed
    - **Pass** - Test passed, no need to do anything
    - **Info** - Information to pay attention to
    - **Note** - Are things that should be fixed

The results will be presented as Automated or Manual:

- **Automated** - Represents recommendations that can be fully automated and validated to a pass/fail state.
- **Manual** - Represents recommendations that cannot be full automated and requires all or some manual steps to validate that the configured state is set as expected.
  The expected state can vary depending on the environment.

The full suite of tests covers [several sections](https://github.com/docker/docker-bench-security/tree/main/tests).
The steps above only focus on the **[Container Images and Build File](https://github.com/docker/docker-bench-security/blob/main/tests/4_container_images.sh)** section.

### Release

- `./gradlew release` will create a tag based on the current SNAPSHOT version
- `secrethub run -- ./gradlew release` will add the credentials for pushing the release tag to github
- if the creds are not in place, the final `push` step will fail.
  Use `git push --tags origin` to push the release tag to github manually.

## Adding to the Architecture Decision Record (ADR)

We recommend that you use [Architecture Decision Records](https://adr.github.io/) to keep a record of significant decisions that impact the architecture of your application.
Using [adr-tools](https://github.com/npryce/adr-tools#adr-tools) makes this even simpler to do.

The generated project comes with a directory and template for ADRs already included.
You can view the example decision by going to:
`<project-root>/docs/architecture-decisions/template/0001-record-architecture-decisions.md`

To add additional architecture decision records, do the following:

1. Make sure you already have [adr-tools](https://github.com/npryce/adr-tools/blob/main/INSTALL.md) installed
1. Add a record by running:

   ```bash
   adr new <description-of-adr-record-to-add>
   ```

1. View additional commands by running: `adr help`

## BuildSrc plugins

The `buildSrc` directory normally holds plugins for common gradle functionality.
Copies of the plugins published in the `gov.va.starter:plugins` package are renamed as `local.*.example` files (at whatever version existed when the project was created from the starter template).
These files are included both to show what the starter plugins are doing.
They provide an easy way to customize small parts of the build functionality while still allowing the project to rely as much as possible on the published standards.
