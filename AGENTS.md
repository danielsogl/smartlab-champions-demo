# AGENTS.md

Back-office app for charge point operators: list charge points, start a session remotely.
`backend/` is Spring Boot (Java 25, Maven), `frontend/` is Vue 3 (Vite, TypeScript).

## Commands
- Backend tests: `./mvnw -q -pl backend test`
- One backend test: `./mvnw -q -pl backend test -Dtest=ChargePointServiceTest`
- Frontend tests: `npm --prefix frontend run test:unit`
- Frontend lint and types: `npm --prefix frontend run lint`
- Run: `./mvnw -pl backend spring-boot:run` and `npm --prefix frontend run dev`

## Rules
- REST errors: throw `ApiException`; `ApiExceptionHandler` maps codes to HTTP.
  Never build an error `ResponseEntity` in a controller.
- Business refusals are a `RemoteStartResult` with an error, not an exception.
- Every acceptance criterion has a test with its id: `@DisplayName("AC1: ...")`, `it('AC1: ...')`.
- Specs live in `docs/specs/<ticket>/` (intent.md, spec.md, plan.md). Use the `spec` skill.

## Gotchas
- `npx vitest` without `run` starts watch mode and never returns. Use the npm script.
- `./mvnw` reads `.mvn/settings.xml` (Maven Central) via `.mvn/maven.config`. Do not add mirrors.
- `ChargePointStatus.UNKNOWN` means no heartbeat yet. It is not the same as AVAILABLE.
- `CommandGateway.sendStart` to a disconnected charge point times out after 60 s in production.
