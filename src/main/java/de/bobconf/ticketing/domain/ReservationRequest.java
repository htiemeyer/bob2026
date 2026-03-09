package de.bobconf.ticketing.domain;

public record ReservationRequest(
        Long eventId,
        int ticketCount,
        String customerEmail
) {}
