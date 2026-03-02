package de.bobconf.ticketing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int totalTickets;
    private int remainingTickets;
    private int priceInCents;

    protected Event() {
        // for JPA
    }

    public Event(String name, int totalTickets, int priceInCents) {
        if (totalTickets < 0) {
            throw new IllegalArgumentException("totalTickets must be >= 0");
        }
        this.name = name;
        this.totalTickets = totalTickets;
        this.remainingTickets = totalTickets;
        this.priceInCents = priceInCents;
    }

    public boolean hasEnoughTickets(int requested) {
        return remainingTickets >= requested;
    }

    public void reserve(int requested) {
        if (!hasEnoughTickets(requested)) {
            throw new IllegalStateException("Not enough tickets");
        }
        remainingTickets -= requested;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
    }

}
