# test-dsl-example-2

A small runnable Spring Boot example that demonstrates a custom test DSL with **multiple drivers** for different test scopes.

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
- `GivenVisitBookingDsl` — setup logic (given)
- `WhenVisitBookingDsl` — action logic (when)
- `ThenVisitBookingDsl` — domain assertion logic (then)

These interfaces are technology-independent and express only domain language.

### Abstract base class

`AbstractVisitBookingDslTest` contains all test scenarios written once in domain language.
Subclasses only need to supply a `VisitBookingTestDsl` implementation (the driver):

```java
class MyTest extends AbstractVisitBookingDslTest {
    @Override
    protected VisitBookingTestDsl dsl() {
        return myDriver;
    }
}
```

### Drivers

Each driver wires the same DSL to a different testing technology:

| Driver | Test scope | Technology | Spring context? |
|---|---|---|---|
| `ServiceVisitBookingDslDriver` | Unit test | Direct service call | No |
| `RestVisitBookingDslDriver` | REST / integration test | `MockMvc` | Yes |
| `UiVisitBookingDslDriver` | UI / end-to-end test | `TestRestTemplate` (real HTTP) | Yes (random port) |

### Test classes

| Test class | Driver used | What it demonstrates |
|---|---|---|
| `VisitBookingServiceDslTest` | `ServiceVisitBookingDslDriver` | Fastest feedback – no Spring, pure logic |
| `VisitBookingRestDslTest` | `RestVisitBookingDslDriver` | Actions go through HTTP via MockMvc |
| `VisitBookingUiDslTest` | `UiVisitBookingDslDriver` | Full HTTP round-trip (like a browser) |

All three test classes extend `AbstractVisitBookingDslTest` and run the **exact same scenario** — only the driver changes.
