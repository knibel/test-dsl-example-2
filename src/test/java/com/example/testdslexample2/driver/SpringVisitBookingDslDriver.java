package com.example.testdslexample2.driver;

import com.example.testdslexample2.adapter.out.memory.InMemoryVisitSlotRepository;
import com.example.testdslexample2.application.VisitBookingService;
import com.example.testdslexample2.domain.VisitSlot;
import com.example.testdslexample2.dsl.GivenVisitBookingDsl;
import com.example.testdslexample2.dsl.ThenVisitBookingDsl;
import com.example.testdslexample2.dsl.VisitBookingTestDsl;
import com.example.testdslexample2.dsl.WhenVisitBookingDsl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringVisitBookingDslDriver implements VisitBookingTestDsl, GivenVisitBookingDsl, WhenVisitBookingDsl, ThenVisitBookingDsl {

    private final InMemoryVisitSlotRepository repository;
    private final VisitBookingService bookingService;

    public SpringVisitBookingDslDriver(InMemoryVisitSlotRepository repository, VisitBookingService bookingService) {
        this.repository = repository;
        this.bookingService = bookingService;
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
        bookingService.bookFirstAvailableSlot(ownerName);
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
