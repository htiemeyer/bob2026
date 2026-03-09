package de.bobconf.ticketing.service;

import de.bobconf.ticketing.domain.Event;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EventRepository implements PanacheRepository<Event> {

    @WithSession
    public Uni<Event> findByIdReactive(Long id) {
        return findById(id);
    }

    public Multi<Event> streamAllReactive() {
        return listAllReactive()
                .onItem().transformToMulti(list -> Multi.createFrom().iterable(list));
    }

    @WithSession
    public Uni<List<Event>> listAllReactive() {
        // Direkt aus PanacheReactive: Uni<List<Event>>
        return listAll();
    }

    @WithTransaction
    public Uni<Event> persistReactive(Event event) {
        return persist(event).replaceWith(event);
    }
}
