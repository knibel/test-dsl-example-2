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

### Test structure

Each concrete test class contains the full Given/When/Then scenario directly at
the top layer, so opening the test file shows the assumptions, action, and
assertions immediately. Only the driver wiring changes:

```java
class MyTest {
    private final VisitBookingTestDsl dsl = myDriver;

    @Test
    void booksVisitSlot() {
        dsl.given().oneAvailableVisitSlotForPet("slot-1", "Bella");
        dsl.when().theOwnerBooksTheFirstAvailableSlot("Sam");
        dsl.then().theSlotIsBookedByOwner("slot-1", "Sam");
        dsl.then().noSlotIsAvailableAnymore();
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

All three test classes contain the **exact same scenario** in domain language —
only the driver changes.
