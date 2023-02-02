# Moderne Organizations Starter

This service is run by Moderne customers to inform the Moderne platform about the organizational structure of their
repositories. In the reference implementation provided here, the organizations are determined by a hardcoded JSON file
plus an optional **ALL** group that contains all repositories. 

This is meant to be customized per organization.

The GraphQL schema for this service is defined
in [`moderne-organizations.graphqls`](src/main/resources/schema/moderne-organizations.graphqls), and serves as the
contract for what Moderne expects a customized Organization service to provide.

If there is no organization service configured to your agent, Moderne will fall back on a single shared **ALL** named
repository group. This is convenient for onboarding initially, but as a customer grows and brings more teams onto the
platform, they should supply organizational hierarchy to help developers action only their assets.
