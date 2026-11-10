---
name: red-green
description: Use when implementing a task from plan.md. Test first, with a human review stop between red and green.
---
# Red, green, refactor

## Phase 1: RED
- One test per acceptance criterion, AC id in the name (`@DisplayName("AC1: ...")`, `it('AC1: ...')`).
- Run only the new tests. Each must fail **for the right reason**: an assertion about the missing behaviour.
- Compile errors, a Spring context that does not start, or a missing import are broken tests, not red. Fix them first.
- **Stop.** Show the failure messages and ask the user to review and commit the tests.

## Phase 2: GREEN
- Smallest change that makes the committed tests pass, one plan task at a time.
- Then run the module's tests (commands in AGENTS.md) and fix every failure before you report done.

## Phase 3: REFACTOR
- Same tests, green before and after.

## Rules
- Never edit, delete, skip or weaken a committed test (`@Disabled`, `.skip`, `assumeTrue` count).
  If a test looks wrong, stop and ask.

## Gotchas
- Spring context fails to start: broken, not red.
- Vue: forgot `await flushPromises()`, so the test sees the state before the API call returned.
