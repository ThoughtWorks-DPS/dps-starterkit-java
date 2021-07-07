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

The `devloop`, `restartloop`, and `resetloop` tasks are also available in the `skeleton/` tree .
These tasks are also part of the generated skeleton, and help the development teams as they extend the skeleton to build the services.

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

### Adding to Architecture Decision Record (ADR)

- First, install adr-tools: [https://github.com/npryce/adr-tools/blob/master/INSTALL.md](https://github.com/npryce/adr-tools/blob/master/INSTALL.md)
- ADR has already been initialized on the template project.
  You can see an example of it in the initial ADR record in `<root>/docs/architecture-decisions/template/0001-record-architecture-decisions.md`
- To add a record, run `adr new <description-of-adr-record-to-add>`
- To see additional commands, run `adr help`

### Linting

Linting via Spotless is set to execute automatically prior to committing via pre-commit.

- Linting using spotless locally
    - Allows autocorrection of lint offenders
    - [https://github.com/diffplug/spotless/tree/master/plugin-gradle#java](https://github.com/diffplug/spotless/tree/master/plugin-gradle#java)
    - [https://github.com/google/google-java-format](https://github.com/google/google-java-format)
- Run: `./gradlew spotlessCheck` in the `skeleton/` directory to execute linting checks
- Run: `./gradlew spotlessApply` in the `skeleton/` directory to attempt to auto-format and correct linting errors
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

## Skeleton Project Structure

The project skeleton is organized into three tiers of functionality, API, Service (business logic), and Persistence.
Each tier should define interfaces for the functionality, with implementations of the interfaces as separate packages.
This maximized long-term maintainability and promotes the ability to migrate each of the levels independently.

```text
project
└─skeleton/
   │ .pre-commit-config.yaml
   │ README.md
   │ build.gradle    
   │ gradle.properties
   │ settings.gradle
   └─api/
   └─app/
   └─buildSrc/
   └─controller/
   └─data/
   └─docs/
   └─flyway/
   └─helmchart/
   └─init-container/
   └─opa-init/
   └─persistence/
      └─impl/
      └─model/
   └─scripts/
   └─docs/
   └─service/
      └─provider/
      └─spi/
```

### api

This is the interface for the service, which should include the Request and Response payload objects.

We are currently using Lombok to minimize the boilerplate code necessary for the payload objects.
There exist arguments in favor of the use of Immutables, which provides much the same benefits of Lombok without such invasive implementation requirements.

### controller

The implementation of the API interface, should only depend on the service SPI.
This has responsibility for transforming the SPI model to the API model.

### service/spi Service Provider Interface

This is an optional component, depending on the structure and domain of what is being built.
Assume you should have it, you should be able to justify why you don't.
This pattern forces you to think in terms of interfaces for the various components of your service.

An example may help.
Take Notifications as an example.
At a high level, as a consumer of this API, I just want to notify someone.
At the lower level, there may be different ways of notifying someone, configurable by the person, including email or SMS.
Email and SMS would be individual SPI implementations, because at the higher level of the service, I just want to track that the notification was sent out.

Further, there may be a future mechanism for notifications, which would only require a configuration capability for the user to select the new method, and a new SPI implementation registered with the service.

It also helps a lot with testability, and migrating underlying services in the future.

### service/provider

This is the main implementation package, which implements the service.
Ideally, it is a thin glue layer between the REST API and the SPI implementations.
Data validation, security (if not already delegated out to Service Mesh), logging and metrics capture should happen here.

Business logic should primarily reside behind the SPI interface(s), and the specific implementations which execute the business logic.
This pattern easily accommodates plugin architectures at the SPI level, supported by factory objects.

This has responsibility for translating from the SPI model to the Persistence model.

This should only depend on the Persistence model, and perhaps other services SPIs.

### persistence/model

Explicitly separating persistence into model and impl may seem like a bit of overkill to some.
Many prefer just using Spring JPA interfaces for the repositories.

That's fine as far as it goes, but occasionally we may have need for implementing other capabilities (e.g. C*).
Keeping with the interface-first design pattern preserves optionality and promotes testability.

The persistence model objects also live here in the persistence/model package.

### persistence/impl

Depending on the choice on the persistence/api side (i.e. Spring JPA), we may or may not need to provide any implementations.
However, it's still useful to have in cases where custom queries need to be implemented.

### app

The main Spring Boot Application, which contains whatever specific configuration classes and includes the components which make up the application (persistence, api, spi, spi-implementation).

SPI Implementations may be either internal or external.
Spring affords an easy way of dynamically constructing the Factory by auto-registering implementations of an interface via configuration beans based on packages included on the classpath.
