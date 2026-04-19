package com.example.testdslexample2;

import com.example.testdslexample2.adapter.out.memory.InMemoryVisitSlotRepository;
import com.example.testdslexample2.driver.RestVisitBookingDslDriver;
import com.example.testdslexample2.dsl.AbstractVisitBookingDslTest;
import com.example.testdslexample2.dsl.VisitBookingTestDsl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * <strong>REST / integration-test scope</strong> – actions go through MockMvc.
 * <p>
 * Uses {@link RestVisitBookingDslDriver} which sends real HTTP requests via
 * {@link MockMvc} while setup and assertions operate on the dataset directly.
 */
@SpringBootTest
@AutoConfigureMockMvc
class VisitBookingRestDslTest extends AbstractVisitBookingDslTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryVisitSlotRepository repository;

    @Test
    void booksVisitSlotThroughRestDriver() {
        bookingAnAvailableSlotAssignsItToTheOwner();
    }

    @Override
    protected VisitBookingTestDsl dsl() {
        return new RestVisitBookingDslDriver(mockMvc, repository);
    }
}
