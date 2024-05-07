# Organizations service for the Moderne platform

This repository template is a service that can be run by [Moderne](https://www.moderne.io/) customers to inform the platform about the organizational structure of their
repositories. In the reference implementation provided here, the [repositories and their organization hierarchy are determined by a hardcoded CSV file](/src/main/resources/repos.csv)
plus an "ALL" organization that contains all repositories. This is meant to be customized per organization.

The GraphQL schema for this service is defined
in [moderne-organizations.graphqls](src/main/resources/schema/moderne-organizations.graphqls), and serves as the
contract for what Moderne expects a customized organization service to provide.

If there is no organization service [configured for your agent](https://docs.moderne.io/administrator-documentation/on-premise-agent/configure-organizations-service), Moderne will fall back on a single organization named "ALL". 
This is convenient for onboarding initially, but as a customer grows and brings more teams onto the
platform, they should supply organizational hierarchy to help developers action only their assets.

## Getting started

1. Either fork this repository or create a new repository using this as a template.
2. Clone the repository locally.
3. Run `./gradlew bootRun` to start the service locally.
4. Go to http://localhost:8080/graphiql to explore the GraphQL API.
5. Run the following GraphQL command to test the service:

```graphql
query testOrgService {
  allOrganizations {
    ...Org
  }
  userOrganizations(user: {email: "example@foo.com"}) {
    ...Org
    parent {
      ...Org
    }
  }
}

fragment Org on Organization {
  id
  name
  commitOptions
}
```

## Customizing the service

You are free to customize the implementation in any way as long as the API contract is implemented correctly.

### Creating repos.csv

Use [repo-fetchers](repo-fetchers/README.md) to generate a (partial) repos.csv and add your organization structure.

### Commit options
The `commitOptions` field on the `Organization` type is a list of strings that represent the commit options that are
available to developers in that organization. These are the options that are presented to developers when they create a
commit through the Moderne platform. The options are defined in the `CommitOption` enum in
[moderne-organizations.graphqls](src/main/resources/schema/moderne-organizations.graphqls#L38-L44).

#### CommitOption explained
| Name          | Description |
|---------------| ----------- |
| `Direct`      | The commit will be pushed directly to the repository. |
| `Branch`      | The commit will be pushed to a branch. |
| `Fork`        | The commit will be pushed to a fork of the repository. |
| `PullRequest` | The commit will be pushed to a pull request. |
| `ForkAndPullRequest` | The commit will be pushed to a fork of the repository and a pull request will be opened. |

### Organization hierarchy
The `parent` field on the [Organization type](/src/main/java/io/moderne/organizations/types/Organization.java) is a reference to the parent organization. This is how you define the
hierarchy of organizations in your company. The root organization should have a `null` parent.
