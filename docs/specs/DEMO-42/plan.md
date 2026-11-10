# Plan: DEMO-42

1. RED: tests for AC1, AC2 (incl. UNKNOWN as offline)
   ChargePointServiceTest, RemoteStartControllerTest
2. GREEN: availability check in ChargePointService,
   new codes in RemoteStartError, 409 in ApiExceptionHandler   (AC1, AC2)
3. RED: Vitest for AC3
   ChargePointDetail.spec.ts
4. GREEN: disabled button and readable reason in
   ChargePointDetail.vue                                       (AC3)
5. Refactor, full check, one Cypress run in CI

When the code departs from this plan, update the plan in the same commit.
