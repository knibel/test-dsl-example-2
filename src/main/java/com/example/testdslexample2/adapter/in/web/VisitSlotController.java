package com.example.testdslexample2.adapter.in.web;

import com.example.testdslexample2.application.VisitBookingService;
import com.example.testdslexample2.domain.VisitSlot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/visit-slots")
public class VisitSlotController {

    private final VisitBookingService bookingService;

    public VisitSlotController(VisitBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<VisitSlot> allSlots() {
        return bookingService.allSlots();
    }

    @PostMapping("/bookings")
    public VisitSlot bookFirstAvailableSlot(@RequestBody BookVisitRequest request) {
        return bookingService.bookFirstAvailableSlot(request.ownerName());
    }

    public record BookVisitRequest(String ownerName) {
    }
}
