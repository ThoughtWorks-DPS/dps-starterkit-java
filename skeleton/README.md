[![Maintainability](https://api.codeclimate.com/v1/badges/FIXME_TOKEN/maintainability)](https://codeclimate.com/repos/FIXME_TOKEN/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/FIXME_TOKEN/test_coverage)](https://codeclimate.com/repos/FIXME_TOKEN/test_coverage)
[![CircleCI](https://circleci.com/gh/ThoughtWorks-DPS/dps-starterkit-java.svg?style=shield&circle-token=FIXME_TOKEN)](https://app.circleci.com/pipelines/github/ThoughtWorks-DPS/dps-starterkit-java?branch=master)

# DPS StarterKit - Java

- [About](#about)
- [Getting Started](#getting-started)
    - [Required Dependencies](#required-dependencies)
    - [Running the Application](#running-the-application)
    - [Verifying that the Application is Running](#verifying-the-application-is-running)
- [What's Next](#whats-next)

---

## About

This is a Java Spring Boot application that has been created using the [DPS Starter Kit][1].
It is intended to be used as a starting point for building Java APIs and should be customized to deliver whatever functionality is required.
If no other changes have been made, this application will have [these features][2] included by default.

## Getting Started

### Required Dependencies

Before you run this application locally, you will need to make sure you have all the following required dependencies available in your local environment:

- [java][6]
- [docker][7]
- [haodolint][8]
- [shellcheck][9]

>This application currently supports Mac OS for local development environments.
> Use the [Mac OS Guide][4] to make sure you have all the above dependencies available in your local environment.
> Otherwise, refer to the [Other Operating Systems Guide][5].

You will also need to have a GitHub personal access token with the `read:packages` permission exported in your local shell.
This is required to in order to download application artifacts that are published to the [DPS GitHub Package Registry][10].

You can generate a new access token by following [this guide][11].
When you have your token, make sure it is available in your local shell by running:

```bash
export GITHUB_ACCESS_TOKEN=<replace-with-token-from-github>
```

### Running the Application

Once you have all the required dependencies, you can start the application in your local environment by navigating to the root of your application directory and running the following command:

```bash
./gradlew clean build check docker
```

This will build all the application artifacts and docker images

You can then start the application by running:

```bash
./gradlew :app:dockerComposeDown :app:dcPrune :app:dockerComposeUp
```

This should bring up a docker container with the app running at [http://localhost:8081](http://localhost:8081)

> Note that at this time, `./gradlew run` and `./gradlew bootRun` require additional setup with database dependencies prior to use with a local development environment.

### Verifying the Application is Running

You can verify that the application is up and running by issuing the following commands in your terminal:

```bash
curl localhost:8081/actuator/health
curl localhost:8081/actuator/info
```

You should get back responses similar to the following:

```bash
curl localhost:8081/actuator/health

{
    "components": {
        "db": {
            "details": {
                "database": "PostgreSQL",
                "validationQuery": "isValid()"
            },
            "status": "UP"
        },
        "diskSpace": {
            "details": {
                "exists": true,
                "free": 48291934208,
                "threshold": 10485760,
                "total": 62725623808
            },
            "status": "UP"
        },
        "livenessState": {
            "status": "UP"
        },
        "ping": {
            "status": "UP"
        },
        "readinessState": {
            "status": "UP"
        }
    },
    "groups": [
        "liveness",
        "readiness"
    ],
    "status": "UP"
}
```

```bash
curl localhost:8081/actuator/info

{
    "app": {
        "description": "Java API Starter from Template",
        "name": "dps-starterkit-java"
    }
}
```

## What's Next

Once you have verified that you are able to run the application successfully, you can now start customizing the application to deliver the functionality you would like.

By default, this application assumes the use of a build, test, release cycle as defined in [this development guide][12].
Take a look at that guide to see how you can make changes, test them and get them deployed to a target environment.

The application itself is organized into the following three tiers of functionality:

- API / Controller
- Service (business logic)
- Persistence

To see how each of these tiers is used by default, take a look at the [Project Structure][13] documentation.

[1]: https://github.com/thoughtworks-dps/dps-starterkit-java
[2]: https://github.com/thoughtworks-dps/dps-starterkit-java#features
[4]: https://github.com/thoughtworks-dps/dps-starterkit-java/blob/main/docs/developing-on-mac.md
[5]: https://github.com/thoughtworks-dps/dps-starterkit-java/blob/main/docs/developing-on-other-os.md
[6]: https://www.oracle.com/java/technologies/javase-jdk16-downloads.html
[7]: https://docs.docker.com/get-docker/
[8]: https://github.com/hadolint/hadolint#install
[9]: https://github.com/koalaman/shellcheck#readme
[10]: https://github.com/orgs/thoughtworks-dps/packages
[11]: https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token
[12]: https://github.com/thoughtworks-dps/dps-starterkit-java/blob/main/docs/development-guid.md
[13]: https://github.com/thoughtworks-dps/dps-starterkit-java/blob/main/docs/project-structure.md
