# dps-multi-module-starterkit-java

Java API Starter from Template

## Getting started

Suppose we need a new service to support a new business line.
As a developer, I would like to be able to create a new service quickly, and have all of the common bits already done (for some definition of "common bits").

This is essentially what the starter kit provides: an opinionated starter skeleton which is capable of building a service and deploying it into production.

### Common Bits

At the current stage of development, the "common bits" are:

* Spring-Boot application structure
* Three-tier service architecture
  * API interface with SpringMVC and OpenAPI annotations
  * A Controller class implementing the API interface
  * A Service layer, consisting of:
    * Service Provider Interface (SPI)
    * Provider implementation
  * A Persistence layer
* Unit / Integration testing
* Architectural fitness functions (to enforce 3-tier arch)
* Flyway database migration support
* Code coverage metrics and minimum coverage thresholds
* Build execution time tracking
* Endpoint security via Open Policy Agent
* Kafka support
* Open Telemetry integration
* Gatling load / performance testing
* Model data lifecycle notifications (i.e. data mutation events)
* Documentation support, using MkDocs (deploy to Github Pages)
* OpenAPI v3.0 support for generating docs from code
* Versioning support for dependency updates
* Versioning support for building application
* Helm charts for deployment
* Container support
  * Docker container creation (app, db-init, opa-init)
* Local execution support
  * Docker-Compose definition for running service and dependencies
  * Supporting Postgres, Kafka, Jaeger, OPA, Spring-Boot app

## Dependencies

1. Refer to build requirements in the README.md of the root of this repo
2. Install CookieCutter as per: https://cookiecutter.readthedocs.io/en/1.7.3/installation.html
3. Clone the following repo: https://github.com/thoughtworks-dps/dps-multi-module-starter-boot

## Generating a new API service

Here is a walk-through of the process for creating a new service skeleton including a sub-resource structure, using a project called bookbinder as an example.

```bash
% export CC_PROJECT=https://github.com/thoughtworks-dps/dps-multi-module-starterkit-java # (1)
% export PROJECT_NAME=bookbinder
% cookiecutter "${CC_PROJECT}" \
--directory templates/project \
PROJECT_NAME="${PROJECT_NAME}" \
PACKAGE_NAME="${PROJECT_NAME}" \
RESOURCE_VAR_NAME=bindingContract \ # this is the name of your database entity
projectDir="${CC_PROJECT}" \
--no-input

% cd "${PROJECT_NAME}"

% gradlew clean build check docker
% gradlew :app:dockerComposeDown :app:dcPrune :app:dockerComposeUp # (2)
```

> Note:
> 1. If testing locally, set this to the path to your local repo
> 2. If there are issues with flyway migrations, pruning the docker volumes may resolve them: `./gradlew :app:dcPruneVolume`

> Note: Any time you are pulling artifacts from the Github packages repository, it is likely that you will need to specify your authorizations.
> Typically, we run the build behind `secrethub` to obtain the necessary credentials for Github Packages. 
> ```bash
> % secrethub run -- gradlew clean build check docker
> ```
> You may also be required to use a Personal Access Token in lieu of your password.

## Load/Performance Tests

In order to see the system working, attach a consumer to the Kafka queue.
Then run the performance test to generate traffic.

```bash
% scripts/consume-kafka.sh # <--- run this in a separate window
% gradlew :app:gatlingRun  
```

The console output of the Gatling tests will provide a link to a browser view with details on the tests, e.g.

```
Reports generated in 0s.
Please open the following file: /<path>/<to>/<project>/app/build/reports/gatling/<api-name>simulation-<timestamp>/index.html
== CSV Build Time Summary ==
Build time today: 6:50.347
Total build time: 6:50.347
(measured since 8 minutes ago)
```

## OpenAPI Spec

View the OpenAPI documentation for your service

```bash
% chrome http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
 ```

## Tracing

View the Jaeger monitoring system

```bash
% chrome http://localhost:16686/
 ```

