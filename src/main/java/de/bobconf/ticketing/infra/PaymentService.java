package de.bobconf.ticketing.infra;

import de.bobconf.ticketing.domain.Event;
import de.bobconf.ticketing.domain.ReservationRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentService {
    public Uni<PaymentResult> charge(Event event, ReservationRequest request) {
        // Fake-Implementierung für Workshop: immer erfolgreich
        return Uni.createFrom()
                .item(PaymentResult.ok("TX-" + event.getId() + "-" + System.currentTimeMillis()));
    }
}
