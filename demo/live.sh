#!/usr/bin/env bash
# Prepares the start state of a live demo on its own branch (live-3 … live-6) and prints
# the prompts to type. Tags stay untouched; delete the branch afterwards.
#
#   demo/live.sh 3    spec:      /spec on intent.md
#   demo/live.sh 4    red-green: backend done, tasks 3 and 4 (frontend) open
#   demo/live.sh 5    stop hook: a broken line is committed, the task is a Javadoc
#   demo/live.sh 6    review:    the review call from CI, run locally
set -euo pipefail
cd "$(git rev-parse --show-toplevel)"

# The tags do not contain this script: bash must read all of it before the first switch.
main() {
self="$0"

if [ -n "$(git status --porcelain)" ]; then
  echo "Working tree not clean. Commit or stash first." >&2
  exit 1
fi

case "${1:-}" in
  3)
    git switch -q -C live-3 step-1-context
    git checkout step-2-spec -- docs/specs/DEMO-42/intent.md
    git commit -q -m "demo: intent for DEMO-42"
    cat <<'EOF'
Branch live-3: intent.md, no spec yet. In claude:

  /spec docs/specs/DEMO-42/intent.md

When it asks, answer (covers every question it has asked in test runs):

  UNKNOWN counts as offline. The backend checks again on every request.
  The API answers 409 with the code in the body, like other errors.
  The page disables "Remote start" and shows "In maintenance".
  Everything else: your proposals.

After spec.md:  spec.md is accepted.
EOF
    ;;
  4)
    git switch -q -C live-4 step-3-tests
    git checkout step-2-spec -- frontend/src/views/ChargePointDetail.vue \
      frontend/src/views/__tests__/ChargePointDetail.spec.ts frontend/cypress/e2e/remote-start.cy.ts
    git commit -q -m "demo: backend done, tasks 3 and 4 open"
    cat <<'EOF'
Branch live-4: backend green, frontend not started. In claude:

  use red-green for task 3 of plan.md

It stops at RED. Read the failure, then commit the test yourself:

  git commit -am "test: DEMO-42 AC3 red"

Back in claude:  committed. continue with task 4.
EOF
    ;;
  5)
    git switch -q -C live-5 step-4-hooks
    f=backend/src/main/java/com/example/chargeops/chargepoint/ChargePointService.java
    sed -i.bak 's/case INOPERATIVE -> RemoteStartError.CHARGE_POINT_INOPERATIVE;/case INOPERATIVE -> null;/' "$f"
    rm -f "$f.bak"
    git diff --quiet && { echo "Setup line not found in $f" >&2; exit 1; }
    git commit -q -am "wip"
    cat <<'EOF'
Branch live-5: AC2 is broken in the last commit. In claude:

  Add a one-line Javadoc to ChargePointService.requestRemoteStart.

Do not add "Nothing else": then the agent keeps to scope and the hook loops until the cap.
EOF
    ;;
  6)
    git switch -q -C live-6 step-6-review
    cat <<'EOF'
Branch live-6. Run the review as in CI:

  claude -p "Review the diff step-2-spec...step-3-tests. Follow REVIEW.md. Read docs/specs/DEMO-42/spec.md and plan.md first." \
    --allowedTools "Read,Grep,Glob,Bash(git diff *)" --permission-mode dontAsk --max-turns 20

Findings vary from run to run. A recorded run: demo-fallback in the training repo.
EOF
    ;;
  *)
    sed -n '2,9p' "$self" | sed 's/^# \{0,1\}//'
    exit 1
    ;;
esac
}
main "$@"
