## Repo Fetchers

This directory contains three bash scripts that can be used to fetch repositories. Below are the details of each script along with examples of how to invoke them and their required/optional arguments.

### `bitbucket-data-center.sh`

This script fetches all repositories from a Bitbucket Data Center instance and assumes the project as the organization.

#### Usage
```sh
./bitbucket-data-center.sh <bitbucket_url>
```

#### Description
This script fetches all repositories from the specified Bitbucket Data Center URL. If the `AUTH_TOKEN` environment variable is set, it will be used for authentication.

#### Example
To fetch all repositories from a Bitbucket Data Center instance:
```sh
./bitbucket-data-center.sh https://my-bitbucket.com/stash
```

### `bitbucket-cloud.sh`

This script fetches all repositories from a Bitbucket Cloud workspace and assumes the workspace as the organization.
Note: Email addresses do not work, you need your Bitbucket username which you can find (here)[https://bitbucket.org/account/settings/]

#### Using paremeters
```sh
./bitbucket-cloud.sh -u <username> -p <app_password> <workspace> > repos.csv
```

#### Using env vars
```sh
env BITBUCKET_USERNAME="<username>" BITBUCKET_APP_PASSWORD="<app_password>" ./bitbucket-cloud.sh <workspace>
```

#### Description
This script fetches all repositories from the specified Bitbucket Cloud workspace.

#### Example
To fetch all repositories from a Bitbucket Data Center instance:
```sh
./bitbucket-cloud.sh -u moderne -p secret moderneinc > repos.csv
```

### `gitlab.sh`

This script fetches all repositories from a GitLab instance or a specific group within a GitLab instance using the (sub)group path as the organization

#### Usage
```sh
./gitlab.sh [-g <group>] [-h <gitlab_domain>]
```

#### Description
This script fetches all repositories from a GitLab instance or a specific group within a GitLab instance. The `AUTH_TOKEN` environment variable must be set for authentication. The `-g` option specifies a group to fetch repositories from. The `-h` option specifies the GitLab domain (defaults to `https://gitlab.com` if not provided).

#### Example
To fetch all repositories from a specific group on a custom GitLab domain:
```sh
./gitlab.sh -g my-group -h https://my-gitlab.com
```

### `github.sh`

This script fetches all repositories from a GitHub organization.

#### Usage
```sh
./github.sh <organization_name>
```

#### Description
This script fetches all repositories from the specified GitHub organization. The `GITHUB_TOKEN` environment variable must be set for authentication.

#### Example
To fetch all repositories from a GitHub organization:
```sh
./github.sh my-organization
```

