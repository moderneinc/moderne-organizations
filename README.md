# Organizations service for the Moderne platform

You should create a dedicated Organizations service if you want to:

* Limit which organizations each user can access, OR
* Customize commit messages by repository (e.g., adding a JIRA ticket to your commit messages based on the repository)

This repository is a template for a service that will do the above two things.

The GraphQL schema for this service is defined
in [organizations.graphqls](./src/main/resources/schema/organizations.graphqls), and serves as the
contract for what Moderne expects a customized Organization service to provide.
[ModerneContractTest](./src/test/java/io/moderne/organizations/ModerneContractTest.java) contains the exact queries
Moderne sends.

## How Moderne uses this service

Point the Moderne Connector at the service's GraphQL endpoint:

```yaml
moderne:
  custom-integrations:
    organization-service:
      uri: https://organizations.example.com/graphql
      # Optional: username/password for basic auth, or bearerToken
```

* `userOrganizationsPages` scopes which organizations a user sees. Access to an organization includes all of its
  descendants. If the call fails, the user gets access to none of these organizations.
* `commitMessage` rewrites the commit message for a repository. If the call fails, Moderne keeps the original message.

The service also serves its `repos.csv` at `GET /organizations`, which can be used as an HTTP `repos.csv` source for
the Connector. See [repository-fetchers](https://github.com/moderneinc/repository-fetchers) for scripts that help
create a `repos.csv`.

## Getting started

1. Either fork this repository or create a new repository using this as a template.
2. Clone the repository locally.
3. Run `./gradlew bootRun` to start the service locally.
4. Go to http://localhost:8080/graphiql to explore the GraphQL API.

## Customizing the service

You are free to customize the implementation in any way as long as the API contract is implemented correctly.
