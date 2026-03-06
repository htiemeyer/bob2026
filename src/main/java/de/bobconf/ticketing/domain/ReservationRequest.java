package de.bobconf.ticketing.domain;

public record ReservationRequest(
        Long eventId,
        int tickets,
        String customerEmail
) {}
