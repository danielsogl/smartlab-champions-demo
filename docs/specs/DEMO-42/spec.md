# Spec: DEMO-42 Refuse remote start on unavailable charge points

## Acceptance criteria

```gherkin
Scenario: AC1 start is refused when offline
  Given charge point CP-7 is offline
  When an operator requests a remote start on CP-7
  Then the start is refused because CP-7 is offline
  And no start command is sent to CP-7

Scenario: AC2 start is refused in maintenance
  Given charge point CP-7 is in maintenance
  When an operator requests a remote start on CP-7
  Then the start is refused because CP-7 is in maintenance
  And no start command is sent to CP-7

Scenario: AC3 the operator sees why
  Given charge point CP-7 is in maintenance
  When the operator opens the detail page of CP-7
  Then "Remote start" is disabled
  And the page shows "In maintenance"
```

## Decisions
- Which status means maintenance? → INOPERATIVE, as in OCPI. (Product, 2026-11-10)
- Status UNKNOWN (no heartbeat yet)? → Treat as offline (AC1). (Product, 2026-11-10)
- Status changes while the page is open? → The backend checks again on every request; AC1 and AC2 hold even if the page is stale. (Product, 2026-11-10)
- How does a refusal look in the API? → 409 Conflict with the code in the body, `CHARGE_POINT_OFFLINE` or `CHARGE_POINT_INOPERATIVE`, like other API errors. (Team, 2026-11-10)

## Areas of concern
- A charge point can drop off between the check and the command. That start still runs into the 60 s timeout, which intent.md keeps out of scope.

## Not covered
- Bulk start from the list view (out of scope in intent.md).
