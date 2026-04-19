package com.example.testdslexample2.dsl;

public interface VisitBookingTestDsl {
    GivenVisitBookingDsl given();

    WhenVisitBookingDsl when();

    ThenVisitBookingDsl then();
}
