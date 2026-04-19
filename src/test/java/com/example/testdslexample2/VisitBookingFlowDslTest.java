package com.example.testdslexample2;

import com.example.testdslexample2.adapter.out.memory.InMemoryVisitSlotRepository;
import com.example.testdslexample2.application.VisitBookingService;
import com.example.testdslexample2.driver.SpringVisitBookingDslDriver;
import com.example.testdslexample2.dsl.VisitBookingTestDsl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VisitBookingFlowDslTest {

    @Autowired
    private InMemoryVisitSlotRepository repository;

    @Autowired
    private VisitBookingService bookingService;

    @Test
    void booksVisitSlotUsingGivenWhenThenDsl() {
        VisitBookingTestDsl dsl = new SpringVisitBookingDslDriver(repository, bookingService);

        dsl.given().oneAvailableVisitSlotForPet("slot-1", "Bella");

        dsl.when().theOwnerBooksTheFirstAvailableSlot("Sam");

        dsl.then().theSlotIsBookedByOwner("slot-1", "Sam");
        dsl.then().noSlotIsAvailableAnymore();
    }
}
