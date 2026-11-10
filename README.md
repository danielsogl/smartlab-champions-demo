# Charge Ops (demo)

Demo repository for the smartlab champions training, 16 Nov 2026.
A small back-office app: operators see charge points and start a charging session remotely.

- `backend/`: Spring Boot 4, Java 25, Maven (`./mvnw`)
- `frontend/`: Vue 3, Vite, Vitest, Cypress

## Run

```bash
./mvnw -pl backend spring-boot:run      # API on :8080
npm --prefix frontend install
npm --prefix frontend run dev            # UI on :5173, proxies /api
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

`git checkout step-3-tests` jumps to any step.

## Before the demo

1. Open the repo once with `claude` and accept the trust dialog. Until then Claude Code ignores
   the allow rules in `.claude/settings.json` (and headless runs print a warning).
2. `./mvnw -q -pl backend test` and `npm --prefix frontend ci` once, so the offline compile in
   the PostToolUse hook finds every dependency.
3. Review demo without a PR (step 6), same call as in CI:

```bash
claude -p "Review the diff step-2-spec...step-3-tests. Follow REVIEW.md. \
Read the linked spec.md and plan.md first." \
  --allowedTools "Read,Grep,Glob,Bash(git diff *),Bash(git log *)" \
  --permission-mode dontAsk --max-turns 20
```

## Azure DevOps setup

In the training both pipelines are templates; the review runs locally (step 3 above).
To use them in your own project:

1. Main branch policy: `azure-pipelines.yml` and `azure-pipelines-review.yml` as build validations,
   at least one required reviewer, "Allow requestors to approve their own changes" off.
2. Review pipeline: secret variable `ANTHROPIC_API_KEY`.
3. Project settings, Repositories, Security: "<project> Build Service" gets
   "Contribute to pull requests", so the review can post its comment.
