# Intent: refuse remote start on unavailable charge points

## Problem
Operators start sessions on charge points that are offline or in maintenance.
The command times out after 60 s with a generic error. Operators retry,
call support, and blame the app.

## Who
Operators in the back office (charge point detail page).

## Out of scope
- Bulk start from the list view
- Changing the 60 s timeout
