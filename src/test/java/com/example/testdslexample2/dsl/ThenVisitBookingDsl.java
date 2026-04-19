package com.example.testdslexample2.dsl;

public interface ThenVisitBookingDsl {
    void theSlotIsBookedByOwner(String slotId, String ownerName);

    void noSlotIsAvailableAnymore();
}
