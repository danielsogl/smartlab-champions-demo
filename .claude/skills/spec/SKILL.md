---
name: spec
description: Turns a ticket or intent.md into spec.md and plan.md under docs/specs/<ticket>/. Use when starting work on a ticket, or when the user says "spec", "write the spec" or "plan this ticket".
---
# From intent to spec to plan

Goal: nobody writes code for this ticket until a human has agreed on what "done" means.

1. **Intent.** Read `docs/specs/<ticket>/intent.md`. If it does not exist, ask the user for the problem, who is affected and what is out of scope, and write it from `templates/intent.md`. Do not invent the problem.
2. **Read the code** the ticket touches. Look for states, edge cases and existing behaviour the intent does not mention.
3. **Ask before you write.** List every open question (unclear states, conflicting behaviour, missing error cases). Ask the user. Do not guess an answer and do not write the spec yet.
4. **Spec.** Write `spec.md` from `templates/spec.md`: acceptance criteria as Gherkin scenarios `AC1`, `AC2`, … in domain language, plus the answered questions.
5. **Stop.** Ask the user to review spec.md. Continue only after they agree.
6. **Plan.** Write `plan.md` from `templates/plan.md`: small tasks in order, RED before GREEN per layer, files each task touches, the AC each task serves.

## Gotchas
- One scenario, one observable behaviour. "And no command is sent" is part of the scenario, not a detail.
- Scenarios describe behaviour, not classes or endpoints.
- A task that serves no AC is scope creep. An AC without a task gets forgotten.
