---
paths:
  - "backend/**"
---
# Backend

- Controllers stay thin: call the service, throw `ApiException` on error. HTTP status codes live only in `ApiExceptionHandler`.
- New error code: add it to `RemoteStartError` and to the `switch` in `ApiExceptionHandler` (the compiler enforces the second).
- Service tests: plain JUnit 5 + Mockito, no Spring context. Use the real `ChargePointRepository` and `save` your own data.
- Controller tests: `@WebMvcTest` with `MockMvcTester` and `@MockitoBean` for the service.
- Assertions with AssertJ. For "no command is sent": `verify(commandGateway, never()).sendStart(any())`.
