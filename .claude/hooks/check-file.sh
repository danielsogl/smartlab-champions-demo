#!/usr/bin/env bash
# Tier 1 (PostToolUse on Edit|Write): compile or lint what just changed.
# Exit 2 sends stderr back to the agent. Keep this under ~10 s.
cd "$CLAUDE_PROJECT_DIR" || exit 0
f=$(jq -r '.tool_input.file_path // empty')
case "$f" in
  *.java) out=$(./mvnw -q -o -pl backend compile 2>&1) ;;
  *.vue|*.ts) out=$(cd frontend && npx eslint "$f" 2>&1 && npx vue-tsc --build 2>&1) ;;
  *) exit 0 ;;
esac || { echo "$out" >&2; exit 2; }
