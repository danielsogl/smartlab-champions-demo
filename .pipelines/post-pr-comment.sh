#!/usr/bin/env bash
# Posts a file as a new comment thread on the current Azure DevOps PR.
# Usage: post-pr-comment.sh review.md   (needs SYSTEM_ACCESSTOKEN and jq)
set -euo pipefail
url="${SYSTEM_COLLECTIONURI}${SYSTEM_TEAMPROJECT}/_apis/git/repositories/${BUILD_REPOSITORY_ID}/pullRequests/${SYSTEM_PULLREQUEST_PULLREQUESTID}/threads?api-version=7.1"
jq -n --rawfile body "$1" '{comments: [{parentCommentId: 0, content: $body, commentType: 1}], status: 1}' |
  curl -sSf -X POST "$url" \
    -H "Authorization: Bearer $SYSTEM_ACCESSTOKEN" \
    -H "Content-Type: application/json" \
    --data-binary @- > /dev/null
