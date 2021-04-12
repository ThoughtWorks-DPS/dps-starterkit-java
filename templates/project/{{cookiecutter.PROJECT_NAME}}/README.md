[![Maintainability](https://api.codeclimate.com/v1/badges/<fixme>>/maintainability)](https://codeclimate.com/repos/<fixme>>/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/<fixme>>/test_coverage)](https://codeclimate.com/repos/<fixme>>/test_coverage)
[![CircleCI](https://circleci.com/gh/ThoughtWorks-DPS/dps-multi-modules-starterkit-java.svg?style=shield&circle-token=<fixme>)](https://app.circleci.com/pipelines/github/ThoughtWorks-DPS/dps-multi-modules-starterkit-java?branch=master)

# dps-multi-module-starterkit-java

This is the repository for the Java API Starter from Template.

## Developer environment 

### Build requirements
- Java 11+

### Dev Tools Setup
- `cd scripts`
- Run `sh mac-dev-tools.sh`

### Running locally
- Run: `./gradlew run`
- To pass in secrets from secrethub as environment variables to docker: `secrethub run -- ./gradlew run`.
  - secrethub will look within `secrethub.env` for defined secrets to map to environment variables.
- If run without secrethub before gradle, the environment variables defined with `build.gradle` will default to `override-me`

### Running locally in docker
- First, build the image by running `./gradlew docker`
- Then, run `./gradlew dockerRun`

### Running tests
- Run: `./gradlew test`

### Running performance tests
- With the server running (either via `./gradlew bootRun` or `./gradlew run` for Docker), run: `./gradlew :app:gatlingRun`
- At the end of test execution, html file should be created.
Open up to see test results.
- Add new simulations in `app/src/gatling/scala`
- Add configuration in `app/src/gatling/resources/gatling.conf`

### Watching Kafka queues
- With the docker-compose running (`./gradlew :app:dockerComposeUp`)
- Run the gatling load test to generate traffic (`./gradlew :app:gatlingRun`)
- Run the command-line consumer within a shell inside the kafka container

```bash
docker exec -it -u root docker_kafka_1 /bin/bash -c \
  '$KAFKA_HOME/bin/kafka-console-consumer.sh --from-beginning --bootstrap-server kafka:9092 --topic=example-entity-lifecycle'
```

### Setting up SecretHub credentials
SecretHub is used both locally, in CircleCI, and our deployments.
For local development, developers will need to install and setup their workstation with the `secrethub-cli`:
- If you ran the `mac-dev-tools.sh` script, `secrethub-cli` should be installed.
Otherwise, run `brew install secrethub/tools/secrethub-cli`.
- Sign up for a SecretHub account through [https://signup.secrethub.io/signup].
- After signing up, select `MacOS` and you should see code for installation.
Copy the setup code and run `secrethub init --setup-code <SETUP_CODE>`.
- SecretHub recommends after setting up your account, generating a backup code in case you need to set up your credentials again (on current or other machine) [https://secrethub.io/docs/reference/cli/credential/#backup].
- To generate a backup-code, run: `secrethub credential backup [options]`.
Keep this secret, keep it safe (don't commit nor share)!
- Add SecretHub username for which credentials are initialized to `secrethub.user` in `local.properties`.

### Adding to Architecture Decision Record (ADR)
- First, install adr-tools: https://github.com/npryce/adr-tools/blob/master/INSTALL.md
- ADR has already been initialized on the template project.
You can see an example of it in the initial ADR record in `<root>/docs/architecture-decisions/template/0001-record-architecture-decisions.md`
- To add a record, run `adr new <description-of-adr-record-to-add>`
- To see additional commands, run `adr help`

### Linting
- Linting using spotless locally
  - Allows autocorrection of lint offenders
  - https://github.com/diffplug/spotless/tree/master/plugin-gradle#java
  - https://github.com/google/google-java-format
- Run: `./gradlew spotlessCheck` to execute linting checks
- Run: `./gradlew spotlessApply` to attempt to auto-format and correct linting errors
- Reports for test coverage: `/build/jacoco/test/index.html`
- Run: `codeclimate -f html > codeClimateReport.html` to execute Code Climate scanning locally and generate a html report# Starter Structure

### Release

- `./gradlew release` will create a tag based on the current SNAPSHOT version
- `secrethub run -- ./gradlew release` will add the credentials for pushing the release tag to github
- if the creds are not in place, `git push --tags origin` will also push the release tag to github

## buildSrc plugins

The `buildSrc` directory normally holds plugins for common gradle functionality.
Copies of the plugins published in the `io.twdps.starter:plugins` package are renamed as `local.*.example` files (at whatever version existed when the project was created from the starter template).
These files are included both to show what the starter plugins are doing, and to provide an easy way to customize small parts of the build functionality while still allowing the project to rely as much as possible on the published standards.

### Updates to example scripts

There exists a script `./scripts/copy-plugin-examples.sh` that will the `local.*.example` from the `io.twdps.starter:plugins` project.
It assumes that the project source exists in a sibling directory path, although that can be specified as a command line parameter.

## Project Structure

The project skeleton is organized into three tiers of functionality, API, Service (business logic), and Persistence.
Each tier should define interfaces for the functionality, with implementations of the interfaces as separate packages.
This maximized long-term maintainability and promotes the ability to migrate each of the levels independently.

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
This pattern easily accommodates plugin architectures at the SPI level, supported by factory objects (<--Ranbir;).

This has responsibility for translating from the SPI model to the Persistence model.

This should only depend on the Persistence model, and perhaps other services SPIs.

### persistence/api

Explicitly separating persistence into api and impl may seem like a bit of overkill to some.
Many prefer just using Spring JPA interfaces for the repositories.

That's fine as far as it goes, but occasionally we may have need for implementing other capabilities (e.g. C*).
Keeping with the interface-first design pattern preserves optionality and promotes testability.

The persistence model objects also live here in the persistence/api package.


### persistence/impl

Depending on the choice on the persistence/api side (i.e. Spring JPA), we may or may not need to provide any implementations.
However, it's still useful to have in cases where custom queries need to be implemented.


### app

The main Spring Boot Application, which contains whatever specific configuration classes and includes the components which make up the application (persistence, api, spi, spi-implementation).

SPI Implementations may be either internal or external.
Spring affords an easy way of dynamically constructing the Factory by auto-registering implementations of an interface via configuration beans based on packages included on the classpath.
