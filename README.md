[![Maintainability](https://api.codeclimate.com/v1/badges/FIXME_TOKEN/maintainability)](https://codeclimate.com/repos/FIXME_TOKEN/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/FIXME_TOKEN/test_coverage)](https://codeclimate.com/repos/FIXME_TOKEN/test_coverage)
[![CircleCI](https://circleci.com/gh/ThoughtWorks-DPS/dps-starterkit-java.svg?style=shield&circle-token=FIXME_TOKEN)](https://app.circleci.com/pipelines/github/ThoughtWorks-DPS/dps-starterkit-java?branch=main)

# DPS Starter Kit - Java

An opinionated starter skeleton which is capable of building a service and deploying it into production.

- [About](#about)
- [Features](#features)
- [Getting Started](#getting-started)
- [Contributing](#contributing)
    - [Dev Tools Setup](#dev-tools-setup)
    - [Development Process](#development-process)
    - [Project Structure](#project-structure)

---

## About

As a developer, I would like to be able to create a new service quickly, and have all the common bits already done (for some definition of "common bits").
The purpose of this tool is to provide lift to the development process, such that developers don't need to build their services from scratch.

Using this tool, developers can generate a generic project which can be customized based on the needs of their team.

## Features

Check the [Explore Features Guide](docs/explore-features.md) to see a comprehensive list of features.

## Getting Started

Check the [Getting Started Guide](docs/getting-started.md) to see how to generate a generic Java Spring Boot API

## Contributing

The developer workflow describes the development process, as well as how to navigate the project structure.

### Dev Tools Setup

- `cd scripts`
- Run `sh mac-dev-tools.sh`

### Development Process

To make changes to the generic Java Spring Boot application, perform the following from the root of the project:

1. Pull latest
1. Implement changes in the `skeleton/` directory
1. Build and test skeleton

    ```bash
    gradlew spotlessApply
    gradlew devloop
    gradlew :app:gatlingRun
    ## in separate terminal
    scripts/consume-kafka.sh 
    ```

1. Generate and diff the project skeleton from the template (from project root directory)

    ```bash
    gradlew ccdiff
    ```

1. Compare the changes that have been made between skeleton and template
1. Copy (or update) the relevant changes from the `skeleton/` to `templates/project/{{cookiecutter.PROJECT_NAME}}/` or `templates/resources/{{cookiecutter.PROJECT_NAME}}/`
1. Repeat from step #4 until no diffs remain
1. Generate, build, test, and diff the project skeleton from the template (from project root directory)

    ```bash
    gradlew ccloop
    ```

1. Validate the template by [starting the service](docs/tutorial.md#Starting-service), ensuring that the service works and the changes have been implemented as expected.

> Note: You may run into issues with the availability of dependencies provided by the `dps-starter-boot` project.
> Set up `secrethub` so that you can access the Github Packages artifact repository.
> As an alternative, you can just clone the `dps-starter-boot` repo and run `gradlew clean build check publishToMavenLocal`.
> Don't forget to run `gradlew cV` to determine the current version of the starter-boot and update `gradle.propertes` in the starterkit-java appropriately.

### Project Structure

To facilitate the development process, the project directory structure separates the application code from the template code.

```text
project
│ .pre-commit-config.yaml
│ README.md
│ build.gradle    
│ gradle.properties
│ settings.gradle
└─docs/
└─scripts/
└─skeleton/
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

## TODO

use Builder patterns everywhere
/ validator functions in controller and service tests
/ ActionSubActionControllerTest - response validator needs more
X Add TODO: in all the spots necessary to update for refactoring
X fix .equals -> .isEqualTo
X remove ignore for parentId mapper
/ update architecter naming conventions, add Builders
/ update coding rules, exceptions for field injection provider.subresources
Note which resource elements are expected to be unique or not, for data repository testing
X persistence tests, don't add default single in populate()  make function void
ServiceImplTest, add assertion functions
