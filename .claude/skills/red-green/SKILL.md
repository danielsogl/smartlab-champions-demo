---
name: red-green
description: Test-first implementation with a human review stop between red and green. Use when implementing a task from plan.md, fixing a bug test-first, or when the user says "red", "go green" or "TDD".
---
# Red, green, refactor

The tests are what the human reviews. That only works if they fail first
for the right reason and nobody changes them afterwards.

## Phase 1: RED
- At least one test per AC in the task, AC id in the name (`@DisplayName("AC1: ...")`, `it('AC1: ...')`).
  Decisions in the spec are cases too.
- Run only the new tests. Each must fail **for the right reason**: an assertion about the missing behaviour.
- A compile error or a Spring context that does not start is a broken test, not red.
  Add what the compiler needs (enum constant, empty method, a placeholder switch arm) but no behaviour, and say what you added.
- A test that passes before your change, new or committed, proves nothing yet: report it.
- **Stop.** Show the failure messages and ask the user to review and commit the tests.

## Phase 2: GREEN
- Run the committed tests first: they should still be red. Uncommitted tests have not been reviewed yet.
- Simplest change that makes them pass, in the style of the code around it, one plan task at a time.
- Then run the module's tests (commands in README.md or the build file) and fix every failure before you report done.

## Phase 3: REFACTOR
- Same tests, green before and after.

## When a test is in the way
- Do not edit, delete, skip or weaken a committed test (`@Disabled`, `.skip`, `assumeTrue` count):
  green would no longer mean what the reviewer agreed to.
- A test contradicts the spec, or an old test asserts behaviour the spec changes: stop, show both sides, ask.
- A task does not work as plan.md says: stop and propose the change. Edit plan.md only after the user agrees.

## Gotchas
- Surefire: "Failures" are red. "Errors" are usually broken (NPE from an unstubbed mock, context).
- Vue: forgot `await flushPromises()`, so the test sees the state before the API call returned.
