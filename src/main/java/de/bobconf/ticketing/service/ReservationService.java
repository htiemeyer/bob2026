package de.bobconf.ticketing.service;

import de.bobconf.ticketing.domain.Event;
import de.bobconf.ticketing.domain.EventNotFoundException;
import de.bobconf.ticketing.domain.ReservationRequest;
import de.bobconf.ticketing.domain.ReservationResult;
import de.bobconf.ticketing.infra.PaymentFailedException;
import de.bobconf.ticketing.infra.PaymentResult;
import de.bobconf.ticketing.infra.PaymentService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ReservationService {

    @Inject
    EventRepository eventRepository;

    @Inject
    PaymentService paymentService;

    public Uni<ReservationResult> reserveReactive(ReservationRequest request) {
        return eventRepository.findByIdReactive(request.eventId())
                .onItem().ifNull().failWith(() ->
                        new EventNotFoundException("Event not found: " + request.eventId()))
                .onItem().transformToUni(event -> validateAndReserve(event, request))
                .onItem().transformToUni(event ->
                        paymentService.charge(event, request)
                                .onItem().transform(payment -> new EventPaymentPair(event, payment)))
                .onItem().transform(pair -> {
                    if (!pair.payment().success()) {
                        throw new PaymentFailedException("Payment declined");
                    }
                    return ReservationResult.accepted(
                            pair.event().getId(),
                            pair.requestedTickets(),
                            pair.payment().transactionId()
                    );
                })
                .onFailure(EventNotFoundException.class)
                .recoverWithItem(th -> ReservationResult.rejected(th.getMessage()))
                .onFailure(PaymentFailedException.class)
                .recoverWithItem(th -> ReservationResult.rejected(th.getMessage()));
    }

    private Uni<Event> validateAndReserve(Event event, ReservationRequest request) {
        if (!event.hasEnoughTickets(request.ticketCount())) {
            return Uni.createFrom().failure(
                    new IllegalStateException("Not enough tickets"));
        }
        event.reserve(request.ticketCount());
        return eventRepository.persistReactive(event);  // Uni<Event>
    }

    private record EventPaymentPair(Event event, PaymentResult payment, int requestedTickets) {
        EventPaymentPair(Event e, PaymentResult p) {
            this(e, p, 0);
        }
    }
}
