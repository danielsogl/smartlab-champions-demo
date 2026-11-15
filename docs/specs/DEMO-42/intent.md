# Intent: refuse remote start on unavailable charge points
Author: Operations team · Status: accepted

## Problem
Operators start sessions on charge points that are offline or in maintenance.
An offline charge point never answers: the command times out after 60 s.
One in maintenance answers Rejected without a reason. Either way operators
see a generic error, retry, call support, and blame the app.

## Proposed outcome
The app refuses such a start right away and tells the operator why.

## Affected users and systems
Operators in the back office: charge point detail page and the remote start API.

## Constraints
- Out of scope: bulk start from the list view
- Out of scope: changing the 60 s timeout

## Open questions
- None at intake. The spec pass raises them.
