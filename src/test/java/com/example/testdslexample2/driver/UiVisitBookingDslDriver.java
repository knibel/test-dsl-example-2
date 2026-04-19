package com.example.testdslexample2.driver;

import com.example.testdslexample2.adapter.out.memory.InMemoryVisitSlotRepository;
import com.example.testdslexample2.domain.VisitSlot;
import com.example.testdslexample2.dsl.GivenVisitBookingDsl;
import com.example.testdslexample2.dsl.ThenVisitBookingDsl;
import com.example.testdslexample2.dsl.VisitBookingTestDsl;
import com.example.testdslexample2.dsl.WhenVisitBookingDsl;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Driver for <strong>UI / end-to-end test scope</strong>.
 * <p>
 * Uses {@link TestRestTemplate} against a real running server
 * (started with {@code webEnvironment = RANDOM_PORT}). This simulates
 * what a browser or UI client would do – full HTTP round-trips over the
 * network stack.
 * <p>
 * In a real project this driver would be backed by Selenium WebDriver,
 * Playwright, or a similar tool. The DSL stays the same; only the driver
 * implementation changes.
 */
public class UiVisitBookingDslDriver implements VisitBookingTestDsl, GivenVisitBookingDsl, WhenVisitBookingDsl, ThenVisitBookingDsl {

    private final TestRestTemplate restTemplate;
    private final InMemoryVisitSlotRepository repository;

    public UiVisitBookingDslDriver(TestRestTemplate restTemplate, InMemoryVisitSlotRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @Override
    public GivenVisitBookingDsl given() {
        return this;
    }

    @Override
    public WhenVisitBookingDsl when() {
        return this;
    }

    @Override
    public ThenVisitBookingDsl then() {
        return this;
    }

    @Override
    public void oneAvailableVisitSlotForPet(String slotId, String petName) {
        repository.replaceAll(List.of(VisitSlot.available(slotId, petName)));
    }

    @Override
    public void theOwnerBooksTheFirstAvailableSlot(String ownerName) {
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/visit-slots/bookings",
                Map.of("ownerName", ownerName),
                String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Override
    public void theSlotIsBookedByOwner(String slotId, String ownerName) {
        VisitSlot slot = repository.findAll().stream()
                .filter(existing -> existing.id().equals(slotId))
                .findFirst()
                .orElseThrow();

        assertThat(slot.bookedByOwner()).isEqualTo(ownerName);
        assertThat(slot.available()).isFalse();
    }

    @Override
    public void noSlotIsAvailableAnymore() {
        long availableCount = repository.findAll().stream().filter(VisitSlot::available).count();
        assertThat(availableCount).isZero();
    }
}
