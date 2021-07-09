# Getting Started

## Dev Tools Setup

After you've generated a skeleton project, ensure that the required developer tools are installed:

- `cd scripts`
- Run `sh mac-dev-tools.sh`

## Setting up SecretHub credentials

SecretHub is used both locally, in CircleCI, and our deployments.
For local development, developers will need to install and setup their workstation with the `secrethub-cli`:

- If you ran the `mac-dev-tools.sh` script, `secrethub-cli` should be installed.
  Otherwise, run `brew install secrethub/tools/secrethub-cli`.
- Sign up for a SecretHub account through [https://signup.secrethub.io/signup](https://signup.secrethub.io/signup).
- After signing up, select `MacOS` and you should see code for installation.
  Copy the setup code and run `secrethub init --setup-code <SETUP_CODE>`.
- SecretHub recommends after setting up your account, generating a backup code in case you need to set up your credentials again (on current or other machine) [https://secrethub.io/docs/reference/cli/credential/#backup].
- To generate a backup-code, run: `secrethub credential backup [options]`.
  Keep this secret, keep it safe (don't commit nor share)!
- Add SecretHub username for which credentials are initialized to `secrethub.user` in `local.properties`.

### Pulling artifacts

There are necessary dependencies which are supplied by the `dps-starter-boot` project.
These dependencies are published to the Github packages site, and require the appropriate Github credentials.

- To pass in secrets from secrethub as environment variables to docker: `secrethub run -- ./gradlew build`.
    - secrethub will look within `secrethub.env` for defined secrets to map to environment variables.
- If run without secrethub before gradle, the environment variables defined with `build.gradle` will default to `override-me`

## Development Guide

See the [Development Guide](development-guide.md) for details on normal development workflow.
