package com.example.testdslexample2.adapter.out.memory;

import com.example.testdslexample2.domain.VisitSlot;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryVisitSlotRepository {

    private final Map<String, VisitSlot> slotsById = new LinkedHashMap<>();

    public InMemoryVisitSlotRepository() {
        replaceAll(List.of(VisitSlot.available("slot-1", "Bella")));
    }

    public synchronized List<VisitSlot> findAll() {
        return new ArrayList<>(slotsById.values());
    }

    public synchronized VisitSlot save(VisitSlot slot) {
        slotsById.put(slot.id(), slot);
        return slot;
    }

    public synchronized void replaceAll(Collection<VisitSlot> slots) {
        slotsById.clear();
        slots.forEach(slot -> slotsById.put(slot.id(), slot));
    }
}
