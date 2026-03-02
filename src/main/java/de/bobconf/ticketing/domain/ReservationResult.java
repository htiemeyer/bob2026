package de.bobconf.ticketing.domain;

public record ReservationResult(
        boolean success,
        String message,
        Long eventId,
        int tickets
) {
    public static ReservationResult success(Long eventId, int tickets) {
        return new ReservationResult(true, "Reservation confirmed", eventId, tickets);
    }

    public static ReservationResult rejected(String message, Long eventId, int tickets) {
        return new ReservationResult(false, message, eventId, tickets);
    }
}
