# Charge Ops (demo)

Demo repository for the smartlab champions training, 16 Nov 2026.
A small back-office app: operators see charge points and start a charging session remotely.

- `backend/`: Spring Boot 4, Java 25, Maven (`./mvnw`)
- `frontend/`: Vue 3, Vite, Vitest, Cypress, pnpm workspace with a catalog

## Run

```bash
./mvnw -pl backend spring-boot:run      # API on :8080
pnpm install                            # pnpm 10; Node 25+: npm i -g pnpm first
pnpm -C frontend dev                     # UI on :5173, proxies /api
```

## Test

```bash
./mvnw -q -pl backend test                                   # all backend tests
./mvnw -q -pl backend test -Dtest=ChargePointServiceTest     # one test class
pnpm -C frontend test:unit                                   # frontend unit tests
pnpm -C frontend lint                                        # ESLint and vue-tsc
pnpm -C frontend build && pnpm -C frontend test:e2e          # Cypress against the build
```

## Training steps

Each module of the training moves ticket DEMO-42 one step. Every step is a tag:

| Tag | Module | What is new |
| --- | --- | --- |
| `step-0-baseline` | 1 | The app, green tests, no agent setup |
| `step-1-context` | 2 | AGENTS.md, CLAUDE.md, `.claude/settings.json`, rules, skills |
| `step-2-spec` | 3 | `docs/specs/DEMO-42/`: intent, spec, plan |
| `step-3-tests` | 4 | AC1–AC3 as tests (red commit), then the code (green commit) |
| `step-4-hooks` | 5 | PostToolUse and Stop hooks |
| `step-5-pr` | 6 | `azure-pipelines.yml` (tier 3), PR template |
| `step-6-review` | 6 | REVIEW.md, reviewer subagent, `azure-pipelines-review.yml` |

`git switch -c try-3 step-3-tests` starts a branch at any step.
