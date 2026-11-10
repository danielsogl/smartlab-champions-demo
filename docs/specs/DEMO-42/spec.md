# Spec: DEMO-42 Refuse remote start on unavailable charge points

## Acceptance criteria

```gherkin
Scenario: AC1 start is refused when offline
  Given charge point CP-7 is offline
  When an operator requests a remote start on CP-7
  Then the request is refused with CHARGE_POINT_OFFLINE
  And no start command is sent to CP-7

Scenario: AC2 start is refused in maintenance
  Given charge point CP-7 is in maintenance
  When an operator requests a remote start on CP-7
  Then the request is refused with IN_MAINTENANCE
  And no start command is sent to CP-7

Scenario: AC3 the operator sees why
  Given charge point CP-7 is in maintenance
  When the operator opens the detail page of CP-7
  Then "Remote start" is disabled
  And the page shows "In maintenance"
```

## Open questions (answered)
- Status UNKNOWN (no heartbeat yet)? → Treat as offline: refused with CHARGE_POINT_OFFLINE. (Product, 2026-11-10)
- Status changes while the page is open? → The backend checks again on every request; AC1 and AC2 hold even if the page is stale. (Product, 2026-11-10)

## Not covered
- Bulk start from the list view (out of scope in intent.md).
- HTTP status for a refusal: 409 Conflict with the code in the body, like other API errors.
