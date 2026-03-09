package de.bobconf.ticketing.domain;

public record ReservationResult(
        boolean accepted,
        String message,
        Long eventId,
        int ticketCount,
        String paymentTransactionId)
{

    public static ReservationResult accepted(Long eventId, int ticketCount, String txId) {
        return new ReservationResult(true, "Reservation accepted", eventId, ticketCount, txId);
    }

    public static ReservationResult rejected(String message, Long eventId, int ticketCount, String txId) {
        return new ReservationResult(false, message, eventId, ticketCount, txId);
    }

    public static ReservationResult rejected(String message) {
        return new ReservationResult(
                false,      // accepted = false
                message,    // Fehlermeldung (z.B. "Event not found")
                null,       // kein Event-Name
                0,          // keine Tickets
                null        // keine Transaction-ID
        );
    }
}
