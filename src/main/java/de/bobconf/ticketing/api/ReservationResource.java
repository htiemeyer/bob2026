package de.bobconf.ticketing.api;

import de.bobconf.ticketing.domain.Event;
import de.bobconf.ticketing.domain.ReservationRequest;
import de.bobconf.ticketing.domain.ReservationResult;
import de.bobconf.ticketing.service.EventRepository;
import de.bobconf.ticketing.service.ReservationService;
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
    //TODO: Multi
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    @POST
    @Path("reservations")
    //TODO: Uni
    public ReservationResult postReservation(ReservationRequest request) {
        // Läuft auf IO-Thread und ist komplett blockierend
        return reservationService.reserve(request);
    }
}
