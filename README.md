# Organizations service for the Moderne platform

You should create a dedicated Organizations service if you want to:

* Limit access to the organizations you've [previously defined in the Moderne Agent](https://docs.moderne.io/administrator-documentation/moderne-platform/how-to-guides/agent-configuration/configure-organizations-hierarchy) OR
* Customize commit messages by repository (e.g., adding a JIRA ticket to your commit messages based on the repository)

This repository is a template for a service that will do the above two things.

The GraphQL schema for this service is defined
in [moderne-organizations.graphqls](./src/main/resources/schema/organizations.graphqls), and serves as the
contract for what Moderne expects a customized Organization service to provide.

## Getting started

1. Either fork this repository or create a new repository using this as a template.
2. Clone the repository locally.
3. Run `./gradlew bootRun` to start the service locally.
4. Go to http://localhost:8080/graphiql to explore the GraphQL API.

## Customizing the service

You are free to customize the implementation in any way as long as the API contract is implemented correctly.
