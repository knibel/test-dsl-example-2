package com.example.testdslexample2;

import com.example.testdslexample2.driver.ServiceVisitBookingDslDriver;
import com.example.testdslexample2.dsl.VisitBookingTestDsl;
import org.junit.jupiter.api.Test;

/**
 * <strong>Unit-test scope</strong> – no Spring context, fastest feedback loop.
 * <p>
 * Uses {@link ServiceVisitBookingDslDriver} which creates repository and
 * service instances directly.
 */
class VisitBookingServiceDslTest {

    private final VisitBookingTestDsl dsl = new ServiceVisitBookingDslDriver();

    @Test
    void booksVisitSlotWithServiceDriver() {
        dsl.given().oneAvailableVisitSlotForPet("slot-1", "Bella");

        dsl.when().theOwnerBooksTheFirstAvailableSlot("Sam");

        dsl.then().theSlotIsBookedByOwner("slot-1", "Sam");
        dsl.then().noSlotIsAvailableAnymore();
    }
}
