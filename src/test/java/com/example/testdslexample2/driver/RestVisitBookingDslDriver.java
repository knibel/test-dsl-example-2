package com.example.testdslexample2.driver;

import com.example.testdslexample2.adapter.out.memory.InMemoryVisitSlotRepository;
import com.example.testdslexample2.domain.VisitSlot;
import com.example.testdslexample2.dsl.GivenVisitBookingDsl;
import com.example.testdslexample2.dsl.ThenVisitBookingDsl;
import com.example.testdslexample2.dsl.VisitBookingTestDsl;
import com.example.testdslexample2.dsl.WhenVisitBookingDsl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Driver for <strong>REST / integration-test scope</strong>.
 * <p>
 * Uses Spring {@link MockMvc} to send HTTP requests to the REST API.
 * The <em>given</em> and <em>then</em> phases still operate directly on the
 * repository (dataset verification), but the <em>when</em> phase goes through
 * the full HTTP layer.
 */
public class RestVisitBookingDslDriver implements VisitBookingTestDsl, GivenVisitBookingDsl, WhenVisitBookingDsl, ThenVisitBookingDsl {

    private final MockMvc mockMvc;
    private final InMemoryVisitSlotRepository repository;

    public RestVisitBookingDslDriver(MockMvc mockMvc, InMemoryVisitSlotRepository repository) {
        this.mockMvc = mockMvc;
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
        try {
            mockMvc.perform(post("/api/visit-slots/bookings")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"ownerName\":\"" + ownerName + "\"}"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException("REST booking request failed", e);
        }
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
