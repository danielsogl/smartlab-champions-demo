# AGENTS.md

Back-office app for charge point operators: list charge points, start a session remotely.
`backend/` is Spring Boot (Java 25, Maven), `frontend/` is Vue 3 (Vite, TypeScript).
Run, test and lint commands: [README.md](README.md#test).

## Rules
- Specs live in `docs/specs/<ticket>/` (intent.md, spec.md, plan.md). Use the `spec` skill.
- Every acceptance criterion has a test with its id: `@DisplayName("AC1: ...")`, `it('AC1: ...')`.

If this file and the code disagree, the code wins. Point out the mismatch.
