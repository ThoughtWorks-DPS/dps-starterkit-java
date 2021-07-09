# Getting Started

Here is a more in-depth description of the structure of the skeleton, as well as an effective developer workflow.

## Dev Tools Setup

After you've cloned the repository, ensure that the required developer tools are installed:

- `cd scripts`
- Run `sh mac-dev-tools.sh`

## Setting up SecretHub credentials

SecretHub is used both locally, in CircleCI, and our deployments.
For local development, developers will need to install and setup their workstation with the `secrethub-cli`:

- If you ran the `mac-dev-tools.sh` script, `secrethub-cli` should be installed.
  Otherwise, run `brew install secrethub/tools/secrethub-cli`.
- Sign up for an account through the [signup page](https://signup.secrethub.io/signup).
- After signing up, select `MacOS` and you should see code for installation.
  Copy the setup code and run `secrethub init --setup-code <SETUP_CODE>`.
- After setting up your account, generate a [backup code](https://secrethub.io/docs/reference/cli/credential/#backup) in case you need to set up your credentials again (on current or other machine).
- To generate a backup-code, run: `secrethub credential backup [options]`.
  Keep this secret, keep it safe (don't commit nor share)!
- Add SecretHub username for which credentials are initialized to `secrethub.user` in `local.properties`.

### Pulling artifacts

There are necessary dependencies which are supplied by the `dps-starter-boot` project.
These dependencies are published to the Github packages site, and require the appropriate Github credentials.

- To pass environment variables to docker:
    - `secrethub run -- ./gradlew build`.
    - secrethub will look within `secrethub.env` for defined secrets to map to environment variables.
- If run without secrethub before gradle, the environment variables defined with `build.gradle` will default to `override-me`

## Project Structure

To facilitate the development process, the project directory structure separates the application code from the template code.

```text
project
│ .pre-commit-config.yaml
│ README.md
│ build.gradle    
│ gradle.properties
│ settings.gradle
└─skeleton/
└─docs/
└─scripts/
└─templates
   └─build.gradle
   └─project
      └─hooks/
      └─{{cookiecutter.PROJECT_NAME}}/
      └─cookiecutter.json
      └─template.yaml
   └─project
      └─hooks/
      └─{{cookiecutter.PROJECT_NAME}}/
      └─cookiecutter.json
      └─template.yaml
```

The root `build.gradle` file provides a few convenience tasks that help with the developer workflow:

- devloop - builds the code, builds the docker images, and restarts containers via `docker-compose`
- restartloop - stops the docker containers, deletes unused images, then proceeds with `devloop`
- resetloop - stops the docker containers, deletes unused images and volumes, then proceeds with `devloop`.
  This effectively re-initializes the database from scratch.
- ccdiff - regenerates a skeleton from the template, formats via `spotlessApply`.
  This task runs a recursive `diff` to compare the generated skeleton with the canonical `skeleton/` code tree.
  Once the `diff` is clean, we can be assured that the templates are accurately generating identical to the `skeleton/` source.
- ccloop - runs ccdiff task, then build/test the generated project.

The `devloop`, `restartloop`, and `resetloop` tasks are also available in the `skeleton/` tree.
These tasks are also part of the generated skeleton, and help the development teams as they extend the skeleton to build the services.

See the docs on [Project Structure](project-structure.md) for details on the structure of the `skeleton/` tree.

### Development Process

To make changes to the generic Java Spring Boot application, we follow a general development flow:

- update the `skeleton/` code with desired changes (this is the canonical version of the skeleton)
- test the updates to the code
- update the `templates/project` and `templates/resource` cookiecutter templates to match the intended changes
- commit when the templates can generate a new skeleton identical to the canonical `skeleton/` source

In order to support this flow, the build.gradle files have many convenience tasks which consolidate multiple steps.  
Here are some specific instructions to help you along this developer workflow:

1. Pull latest `git pull --rebase origin main`  
1. Implement changes in the `skeleton/` directory
1. Build and test skeleton (from `skeleton/` directory)

    ```bash
    gradlew spotlessApply
    gradlew devloop
    gradlew :app:gatlingRun
    ## in separate terminal
    scripts/consume-kafka.sh 
    ```

1. Generate, build, and diff the project skeleton from the template (from project root directory)

    ```bash
    gradlew ccloop
    ```

1. Compare the changes that have been made between skeleton and template
1. Copy (or update) the relevant changes from the `skeleton/` to `templates/project/{{cookiecutter.PROJECT_NAME}}/` or `templates/resources/{{cookiecutter.PROJECT_NAME}}/`
1. Repeat from step #4 until no diffs remain
1. Validate the template by [starting the service](docs/tutorial.md#Starting-service), ensuring that the service works and the changes have been implemented as expected.

> Note: You may run into issues with the availability of dependencies provided by the `dps-starter-boot` project.
> Set up `secrethub` so that you can access the Github Packages artifact repository.
> As an alternative, you can just clone that repo and run `gradlew clean build check publishToMavenLocal`.
> Don't forget to run `gradlew cV` to determine the current version of the starter-boot and update `gradle.propertes` files in the starterkit-java appropriately.

### Adding to the Architecture Decision Record (ADR)

We recommend that you use [Architecture Decision Records](https://adr.github.io/) to keep a record of significant decisions that impact the architecture of your application.
Using [adr-tools](https://github.com/npryce/adr-tools#adr-tools) makes this even simpler to do.

The generated project comes with a directory and template for ADRs already included.
You can view the example decision by going to:
`<project-root>/docs/architecture-decisions/template/0001-record-architecture-decisions.md`

To add additional architecture decision records, do the following:

1. Make sure you already have [adr-tools](https://github.com/npryce/adr-tools/blob/master/INSTALL.md) installed
1. Add a record by running:

   ```bash
   adr new <description-of-adr-record-to-add>
   ```

1. View additional commands by running: `adr help`

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

The full suite of tests covers [several sections](https://github.com/docker/docker-bench-security/tree/master/tests).
The steps above only focus on the **[Container Images and Build File](https://github.com/docker/docker-bench-security/blob/master/tests/4_container_images.sh)** section.

- Reports for test coverage: `./build/jacoco/test/index.html`
- Run: `codeclimate -f html > codeClimateReport.html` to execute Code Climate scanning locally and generate a html report# Starter Structure

### Release

- `./gradlew release` will create a tag based on the current SNAPSHOT version
- `secrethub run -- ./gradlew release` will add the credentials for pushing the release tag to github
- if the creds are not in place, `git push --tags origin` will also push the release tag to github

## buildSrc plugins

The `buildSrc` directory normally holds plugins for common gradle functionality.
Copies of the plugins published in the `io.twdps.starter:plugins` package are renamed as `local.*.example` files (at whatever version existed when the project was created from the starter template).
These files are included both to show what the starter plugins are doing.
They also provide an easy way to customize small parts of the build functionality while still allowing the project to rely as much as possible on the published standards.

### Updates to example scripts

There exists a script `./scripts/copy-plugin-examples.sh` that will the `local.*.example` from the `io.twdps.starter:plugins` project.
It assumes that the project source exists in a sibling directory path, although that can be specified as a command line parameter.
