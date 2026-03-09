package de.bobconf.ticketing.api;

import de.bobconf.ticketing.domain.Event;
import de.bobconf.ticketing.domain.ReservationRequest;
import de.bobconf.ticketing.domain.ReservationResult;
import de.bobconf.ticketing.service.EventRepository;
import de.bobconf.ticketing.service.ReservationService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource {

    @Inject
    EventRepository eventRepository;

    @Inject
    ReservationService reservationService;

    @GET
    @Path("events")
    public Multi<Event> getEvents() {
        return eventRepository.streamAllReactive();
    }

    @POST
    @Path("reservations")
    public Uni<ReservationResult> postReservation(ReservationRequest request) {
        // Läuft auf IO-Thread und ist komplett blockierend
        return reservationService.reserveReactive(request);
    }
}
