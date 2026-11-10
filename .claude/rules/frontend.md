---
paths:
  - "frontend/**"
---
# Frontend

- Components use `<script setup lang="ts">`. HTTP only through `src/api.ts`.
- Tests live in `src/**/__tests__/`. Stub `fetch` with `vi.stubGlobal`, mount with `@vue/test-utils`, wait with `flushPromises()`.
- Find elements by role or visible text, not by CSS class.
- UI text is English. Status values come from the API as enum names (`IN_MAINTENANCE`); show a readable label.
