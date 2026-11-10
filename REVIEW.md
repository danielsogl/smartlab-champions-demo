# REVIEW.md

Rules for reviewing a change in this repo. Approvers: required reviewers in the main branch policy.
Read the linked `docs/specs/<ticket>/spec.md` and `plan.md` before the diff.

## What failure looks like here
- A start command reaches a charge point that is offline, UNKNOWN or in maintenance.
- An acceptance criterion from spec.md has no test with its id, or a test checks less than the scenario says
  (the "And no command is sent" lines count).
- Test, hook, settings or CI files changed. Say which, and why it matters; a human reviews these first.
- The diff does something plan.md does not mention, and plan.md was not updated.
- A REST error that bypasses `ApiExceptionHandler`.

## Findings
- Label each one **Important**, **Nit** or **Pre-existing** (problem was there before this PR).
- Every finding names `file:line` and says what goes wrong, for whom.
- At most 3 nits. Flag a class of problem once, not every line.
- Start with the count, e.g. "2 findings, 1 Important". Nothing to fix? Say so in one line.
- Write the review in English.

## Skip
- Formatting, import order, naming taste (ESLint and the compiler do that).
- `frontend/package-lock.json`, `mvnw`, `mvnw.cmd`, `.mvn/wrapper/**`.
