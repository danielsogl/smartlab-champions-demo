---
name: spec
description: Turns a ticket or intent.md into spec.md and plan.md under docs/specs/<ticket>/. Use when starting work on a ticket, when the user says "spec", "write the spec" or "plan this ticket", or asks to implement a ticket that has no spec.md yet.
---
# From intent to spec to plan

Goal: nobody writes code for this ticket until a human has agreed on what "done" means.
Start at the first step whose file is missing or not yet accepted.

1. **Intent.** Use `docs/specs/<ticket>/intent.md` if it exists. Otherwise write it from `templates/intent.md` with what the ticket or the user already said. Ask like an analyst only for what is missing: problem, outcome, who, constraints. No ticket key: ask for a short name. Do not invent the problem. The product owner accepts the intent.
2. **Read the code** the ticket touches. Look for states, edge cases and existing behaviour the intent does not mention.
3. **Ask before you write.** At most five questions, only what would change an acceptance criterion, most important first. For each, say what the code does today and what you propose, so the user can answer "defaults". Propose today's behaviour unless the intent asks for a change. Wait for the answers. If the user cannot answer now, write the spec with your proposals marked as assumptions to confirm.
4. **Spec.** Write `spec.md` from `templates/spec.md`: acceptance criteria as Gherkin scenarios `AC1`, `AC2`, … in domain language. Each Then is something a test can assert: what the user sees and where. Flag conflicts with policies or existing behaviour under Areas of concern instead of settling them yourself.
5. **Stop.** Ask the product owner to review spec.md. Continue only after they accept it.
6. **Plan.** Write `plan.md` from `templates/plan.md` directly into the ticket folder, not in plan mode: plan mode writes only to its own file outside the repo. Files that change, order of work (RED before GREEN per layer, the AC of each task), risks, proof. A RED task names what must exist for its tests to compile (new enum constant, empty method) and nothing that makes them pass. Existing tests the change would break, and anything else open, go under "Needs a decision" with your proposal: do not settle them. Good enough when an engineer who never saw this conversation could implement it.
7. **Stop.** Ask the engineer to review plan.md and answer the open points. Under each open point, note the decision in one line. Change nothing else. Do not write code: implementing starts with the `red-green` skill once plan.md is committed.

## Gotchas
- One scenario, one observable behaviour. "And no command is sent" is part of the scenario, not a detail.
- Scenarios describe behaviour, not classes, enum values, endpoints or status codes. Technical answers (409, error codes) go under Decisions.
- Same Then, different Given: one `Scenario Outline` under one AC id, not several ACs.
- A task that serves no AC is scope creep. An AC without a task gets forgotten.
