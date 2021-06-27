[![Maintainability](https://api.codeclimate.com/v1/badges/<fixme>>/maintainability)](https://codeclimate.com/repos/<fixme>>/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/<fixme>>/test_coverage)](https://codeclimate.com/repos/<fixme>>/test_coverage)
[![CircleCI](https://circleci.com/gh/ThoughtWorks-DPS/dps-multi-module-starterkit-java.svg?style=shield&circle-token=<fixme>)](https://app.circleci.com/pipelines/github/ThoughtWorks-DPS/dps-multi-modules-starterkit-java?branch=master)

# dps-multi-module-starterkit-java

An opinionated starter skeleton which is capable of building a service and deploying it into production.

As a developer, I would like to be able to create a new service quickly, and have all the common bits already done (for some definition of "common bits"). 
The purpose of this tool is to provide lift to the development process, where developers don't need to build their services from scratch.
Rather, this tool generates a generic project which can be customized based on the needs of the development team.

## Features

* Spring-Boot application structure
* Three-tier service architecture
    * API interface with SpringMVC and OpenAPI annotations
    * A Controller class implementing the API interface
    * A Service layer, consisting of:
        * Service Provider Interface (SPI)
        * Provider implementation
    * A Persistence layer
* Unit / Integration testing
    * DataFactory framework for loading/generating test data
* Architectural fitness functions (to enforce 3-tier arch)
* Flyway database migration support
* Code coverage metrics and minimum coverage thresholds
* Build execution time tracking
* Endpoint security via Open Policy Agent
* Kafka support
* Open Telemetry integration
* Gatling load / performance testing
* Model data lifecycle notifications (i.e. data mutation event stream)
* Documentation support, using MkDocs (deploy to Github Pages)
* OpenAPI v3.0 support for generating docs from code
* Versioning support for dependency updates
* Versioning support for building application
* Helm charts for deployment
* Container support
    * Docker container creation (app, db-init, opa-init)
* Local execution support
    * Docker-Compose definition for running service and dependencies
    * Support for Postgres, Kafka, Jaeger, OPA, Spring-Boot app


## Getting Started

Check the [Getting Started Guide](docs/getting-started.md) to see how to generate a generic Java Spring Boot API

## Contributing

The developer workflow describes the development process, as well as how to navigate the project structure.

### Dev Tools Setup

- `cd scripts`
- Run `sh mac-dev-tools.sh`

### Development Process

To make changes to the generic Java Spring Boot application, perform the following from the root of the project:

1.  Pull latest
2.  Implement changes in the `skeleton/` directory
3.  Build and test skeleton (from `skeleton/` directory)
    ```bash
    gradlew spotlessApply
    gradlew devloop
    gradlew :app:gatlingRun
    ## in separate terminal
    scripts/consume-kafka.sh 
    ```
4.  Generate, build, and diff the project skeleton from the template (from project root directory)
    ```bash
    gradlew ccloop
    ```
5.  Compare the changes that have been made between skeleton and template
6.  Copy (or update) the relevant changes from the `skeleton/` to `templates/project/{{cookiecutter.PROJECT_NAME}}/` or `templates/resources/{{cookiecutter.PROJECT_NAME}}/`
7.  Repeat from step #4 until no diffs remain
8.  Validate the template by [starting the service](docs/tutorial.md#Starting-service), ensuring that the service works and the changes have been implemented as expected.


> Note: You may run into issues with the availability of dependencies provided by the `dps-starter-boot` project.
> Set up `secrethub` so that you can access the Github Packages artifact repository.
> As an alternative, you can just clone that repo and run `gradlew clean build check publishToMavenLocal`.
> Don't forget to run `gradlew cV` to determine the current version of the starter-boot and update `gradle.propertes` files in the starterkit-java appropriately.
