#!/bin/bash

# Parse command-line arguments
while getopts ":u:p:" opt; do
  case ${opt} in
    u )
      USERNAME=$OPTARG
      ;;
    p )
      APP_PASSWORD=$OPTARG
      ;;
    \? )
      echo "Usage: $0 <workspace> -u <username> -p <password>"
      exit 1
      ;;
    : )
      echo "Invalid option: $OPTARG requires an argument" 1>&2
      exit 1
      ;;
  esac
done
shift $((OPTIND -1))

# Set workspace from positional argument
WORKSPACE=$1

# Check if USERNAME and APP_PASSWORD are provided via command line or environment variables
if [[ -z "$USERNAME" ]]; then
    USERNAME=$BITBUCKET_USERNAME
fi

if [[ -z "$APP_PASSWORD" ]]; then
    APP_PASSWORD=$BITBUCKET_APP_PASSWORD
fi

# Check if required variables are set
if [[ -z "$USERNAME" || -z "$APP_PASSWORD" || -z "$WORKSPACE" ]]; then
    echo "Error: Please provide username, password, and workspace." >&2
    echo "Usage: $0 <workspace> -u <username> -p <password>" >&2
    exit 1
fi

BASE_URL="https://api.bitbucket.org/2.0/repositories/$WORKSPACE"

echo "cloneUrl,branchName"

# Start fetching repositories
NEXT_URL=$BASE_URL

while [ -n "$NEXT_URL" ]; do
  RESPONSE=$(curl -s -u "$USERNAME:$APP_PASSWORD" "$NEXT_URL")

  # Extract repository data and append to CSV file
  echo $RESPONSE | jq -r '.values[] | (.links.clone[] | select(.name=="https") | .href) as $cloneUrl | .mainbranch.name as $branchName | "\($cloneUrl),\($branchName)"'

  # Get the next URL
  NEXT_URL=$(echo $RESPONSE | jq -r '.next // empty')
done
