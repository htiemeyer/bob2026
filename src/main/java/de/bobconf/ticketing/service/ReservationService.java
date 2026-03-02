package de.bobconf.ticketing.service;

import de.bobconf.ticketing.domain.Event;
import de.bobconf.ticketing.domain.ReservationRequest;
import de.bobconf.ticketing.domain.ReservationResult;
import de.bobconf.ticketing.infra.PaymentClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ReservationService {

    @Inject
    EventRepository eventRepository;

    @Inject
    PaymentClient paymentClient;

    @Transactional
    public ReservationResult reserve(ReservationRequest request) {
        // BLOCKING DB
        Event event = eventRepository.findById(request.eventId());
        if (event == null) {
            return ReservationResult.rejected("Event not found", request.eventId(), request.tickets());
        }

        if (!event.hasEnoughTickets(request.tickets())) {
            return ReservationResult.rejected("Not enough tickets", request.eventId(), request.tickets());
        }

        int totalPrice = request.tickets() * event.getPriceInCents();

        // BLOCKING externer Call
        boolean paymentOk = paymentClient.charge(request.customerEmail(), totalPrice);
        if (!paymentOk) {
            return ReservationResult.rejected("Payment failed", request.eventId(), request.tickets());
        }

        // BLOCKING DB
        event.reserve(request.tickets());
        eventRepository.persist(event);

        return ReservationResult.success(event.getId(), request.tickets());
    }
}
