package de.bobconf.ticketing.domain;


public record Event(
        Long id,
        String name,
        int totalTickets,
        int remainingTickets,
        int priceInCents
) {

    public Event reserve(int requested) {
        if (requested < 0 || remainingTickets < requested) {
            throw new IllegalStateException("Not enough tickets");
        }
        return new Event(id, name, totalTickets,
                remainingTickets - requested, priceInCents);
    }

    public boolean hasEnoughTickets(int requested) {
        return remainingTickets >= requested;
    }
}
