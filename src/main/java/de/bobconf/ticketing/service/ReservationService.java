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
                .onItem().ifNull().failWith(
                        () -> new EventNotFoundException("Event not found: " + request.eventId()))
                // pure Funktion
                .onItem().transform(event -> validateAndReserve(event, request))
                // Seiteneffekt: persist
                .onItem().transformToUni(updatedEvent ->
                        eventRepository.persistReactive(updatedEvent)
                                .replaceWith(updatedEvent)
                )
                // Seiteneffekt: Payment
                .onItem().transformToUni(updatedEvent ->
                        paymentService.charge(updatedEvent, request)
                                .onItem().transform(payment -> new EventPaymentPair(updatedEvent, payment, request.ticketCount()))
                )
                // pure Mapping auf Ergebnis
                .onItem().transform(pair -> toReservationResult(pair))
                .onFailure(EventNotFoundException.class)
                .recoverWithItem(th -> ReservationResult.rejected(th.getMessage()))
                .onFailure(PaymentFailedException.class)
                .recoverWithItem(th -> ReservationResult.rejected(th.getMessage()));
    }

    private Event validateAndReserve(Event event, ReservationRequest request) {
        if (!event.hasEnoughTickets(request.ticketCount())) {
            throw new IllegalStateException("Not enough tickets");
        }
        return event.reserve(request.ticketCount()); // neues Event
    }

    // pure Funktion
    private ReservationResult toReservationResult(EventPaymentPair pair) {
        if (!pair.payment().success()) {
            throw new PaymentFailedException("Payment declined");
        }
        return ReservationResult.accepted(
                pair.event().id(),
                pair.requestedTickets(),
                pair.payment().transactionId()
        );
    }

    private record EventPaymentPair(Event event, PaymentResult payment, int requestedTickets) {}
}
