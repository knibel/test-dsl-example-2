package com.example.testdslexample2.application;

import com.example.testdslexample2.adapter.out.memory.InMemoryVisitSlotRepository;
import com.example.testdslexample2.domain.VisitSlot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitBookingService {

    private final InMemoryVisitSlotRepository repository;

    public VisitBookingService(InMemoryVisitSlotRepository repository) {
        this.repository = repository;
    }

    public List<VisitSlot> allSlots() {
        return repository.findAll();
    }

    public VisitSlot bookFirstAvailableSlot(String ownerName) {
        VisitSlot availableSlot = repository.findAll().stream()
                .filter(VisitSlot::available)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No available visit slot found"));

        return repository.save(availableSlot.bookBy(ownerName));
    }
}
