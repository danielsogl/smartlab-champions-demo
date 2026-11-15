# Plan: DEMO-42 (from intent.md 2026-11-10)

## Files that change
- RemoteStartError, ApiExceptionHandler: two new codes, mapped to 409
- ChargePointService: availability check before the command is sent
- ChargePointDetail.vue: disabled button and readable reason in maintenance
- Tests: ChargePointServiceTest, RemoteStartControllerTest, ChargePointDetail.spec.ts

## Order of work
1. RED: tests for AC1, AC2 (incl. UNKNOWN as offline)         (AC1, AC2)
   compiles with: new codes in RemoteStartError,
   placeholder arm in ApiExceptionHandler
2. GREEN: availability check in ChargePointService,
   409 in ApiExceptionHandler                                  (AC1, AC2)
3. RED: Vitest for AC3                                         (AC3)
4. GREEN: disabled button and readable reason in
   ChargePointDetail.vue                                       (AC3)
5. Refactor, full check, one Cypress run in CI

## Risks
- The page shows raw status names today. Labelling every status would change an
  existing test, so this plan labels only "In maintenance".

## Proof
- AC1, AC2: ChargePointServiceTest, RemoteStartControllerTest
- AC3: ChargePointDetail.spec.ts
- Before done: backend tests, frontend tests and lint (commands in README.md)

The plan changes only after the engineer agrees: propose the change, then update plan.md in the same commit as the code.
