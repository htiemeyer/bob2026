package de.bobconf.ticketing.service;

import de.bobconf.ticketing.domain.Event;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class EventRepository {

    @PersistenceContext
    EntityManager em;

    public Event findById(Long id) {
        // BLOCKING: klassischer JPA-Call
        return em.find(Event.class, id);
    }

    public List<Event> findAll() {
        return em.createQuery("from Event", Event.class).getResultList();
    }

    public void persist(Event event) {
        em.persist(event);
    }
}
