# test-dsl-example-2

A small runnable Spring Boot example that demonstrates a custom test DSL.

## Domain

The app models a light pet-clinic-like workflow:
- a dataset starts with one available visit slot
- an owner books the first available slot
- the dataset changes so the slot is no longer available

## Run the application

```bash
mvn spring-boot:run
```

API:
- `GET /api/visit-slots`
- `POST /api/visit-slots/bookings` with JSON body: `{ "ownerName": "Sam" }`

## Run tests

```bash
mvn test
```

## DSL structure

Tests in `src/test` use explicit Given/When/Then APIs:
- `GivenVisitBookingDsl` for setup
- `WhenVisitBookingDsl` for actions
- `ThenVisitBookingDsl` for domain assertions

`SpringVisitBookingDslDriver` is the underlying driver that wires DSL steps to Spring + JUnit/assertions.
