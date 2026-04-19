package com.example.testdslexample2;

import com.example.testdslexample2.driver.ServiceVisitBookingDslDriver;
import com.example.testdslexample2.dsl.AbstractVisitBookingDslTest;
import com.example.testdslexample2.dsl.VisitBookingTestDsl;

/**
 * <strong>Unit-test scope</strong> – no Spring context, fastest feedback loop.
 * <p>
 * Uses {@link ServiceVisitBookingDslDriver} which creates repository and
 * service instances directly.
 */
class VisitBookingServiceDslTest extends AbstractVisitBookingDslTest {

    private final VisitBookingTestDsl dsl = new ServiceVisitBookingDslDriver();

    @Override
    protected VisitBookingTestDsl dsl() {
        return dsl;
    }
}
