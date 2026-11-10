#!/usr/bin/env bash
# Tier 2 (Stop): no "done" while tests or lint are red.
# Exit 2 blocks the stop; stderr is the reason the agent reads.
cat > /dev/null  # hook input, not needed here
cd "$CLAUDE_PROJECT_DIR" || exit 0
# ponytail: nothing changed, nothing to check (answers, reviews, questions)
[ -z "$(git status --porcelain)" ] && exit 0
out=$( { ./mvnw -q -pl backend test &&
         npm --prefix frontend run test:unit &&
         npm --prefix frontend run lint; } 2>&1 ) \
  && exit 0
{ echo "Checks failed. Fix them before you"
  echo "finish. Do not edit tests."
  tail -n 40 <<<"$out"; } >&2
exit 2
