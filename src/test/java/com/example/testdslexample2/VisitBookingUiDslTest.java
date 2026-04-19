package com.example.testdslexample2;

import com.example.testdslexample2.adapter.out.memory.InMemoryVisitSlotRepository;
import com.example.testdslexample2.driver.UiVisitBookingDslDriver;
import com.example.testdslexample2.dsl.VisitBookingTestDsl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

/**
 * <strong>UI / end-to-end test scope</strong> – actions go through a real HTTP
 * server.
 * <p>
 * Uses {@link UiVisitBookingDslDriver} backed by {@link TestRestTemplate},
 * which makes full HTTP round-trips against an embedded Tomcat started on a
 * random port. In a real project this driver would be backed by Selenium
 * WebDriver or Playwright; the DSL stays exactly the same.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VisitBookingUiDslTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InMemoryVisitSlotRepository repository;

    @Test
    void booksVisitSlotThroughUiDriver() {
        VisitBookingTestDsl dsl = new UiVisitBookingDslDriver(restTemplate, repository);

        dsl.given().oneAvailableVisitSlotForPet("slot-1", "Bella");

        dsl.when().theOwnerBooksTheFirstAvailableSlot("Sam");

        dsl.then().theSlotIsBookedByOwner("slot-1", "Sam");
        dsl.then().noSlotIsAvailableAnymore();
    }
}
