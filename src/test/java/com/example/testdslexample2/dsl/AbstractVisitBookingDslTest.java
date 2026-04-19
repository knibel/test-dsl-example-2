package com.example.testdslexample2.dsl;

import org.junit.jupiter.api.Test;

/**
 * Abstract base class for visit-booking test scenarios.
 * <p>
 * Subclasses only need to provide a {@link VisitBookingTestDsl} implementation
 * via {@link #dsl()}. The same domain-language scenarios are then executed
 * against every driver – unit, REST, UI, or any future driver.
 */
public abstract class AbstractVisitBookingDslTest {

    protected abstract VisitBookingTestDsl dsl();

    @Test
    void bookingAnAvailableSlotAssignsItToTheOwner() {
        dsl().given().oneAvailableVisitSlotForPet("slot-1", "Bella");

        dsl().when().theOwnerBooksTheFirstAvailableSlot("Sam");

        dsl().then().theSlotIsBookedByOwner("slot-1", "Sam");
        dsl().then().noSlotIsAvailableAnymore();
    }
}
