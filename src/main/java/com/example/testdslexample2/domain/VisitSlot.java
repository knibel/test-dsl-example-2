package com.example.testdslexample2.domain;

public record VisitSlot(String id, String petName, boolean available, String bookedByOwner) {

    public static VisitSlot available(String id, String petName) {
        return new VisitSlot(id, petName, true, null);
    }

    public VisitSlot bookBy(String ownerName) {
        return new VisitSlot(id, petName, false, ownerName);
    }
}
