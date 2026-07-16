package com.bbva.app.events.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbva.app.events.models.AccountSummary;
import com.bbva.app.events.models.Event;
import com.bbva.app.events.repositories.EventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public Event saveEvent(Event event) {

        if (event.getEventId() == null || event.getEventId().isEmpty()) {
            throw new IllegalArgumentException("eventId obligatorio");
        }
        if (event.getAccountId() == null || event.getAccountId().isEmpty()) {
            throw new IllegalArgumentException("accountId obligatorio");
        }
        if (event.getAmount() <= 0) {
            throw new IllegalArgumentException("amount debe ser mayor a 0");
        }
        if (eventRepository.existsById(event.getEventId())) {
            throw new IllegalStateException("Evento duplicado");
        }

        if (event.getEventTime() == null) {
            event.setEventTime(LocalDateTime.now());
        }

        return eventRepository.save(event);
    }

    @Transactional(readOnly = true)
    public List<Event> getEvents(String accountId) {
        if (accountId != null && !accountId.trim().isEmpty()) {
            return eventRepository.findByAccountIdIgnoreCase(accountId);
        }
        return eventRepository.findAll();
    }


    @Transactional(readOnly = true)
    public AccountSummary getAccountSummary(String accountId) {
        List<Event> events = eventRepository.findByAccountIdIgnoreCase(accountId);

        double totalCredits = 0;
        double totalDebits = 0;

        for (Event e : events) {
            if ("CREDIT".equals(e.getType())) {
                totalCredits += e.getAmount();
            } else if ("DEBIT".equals(e.getType())) {
                totalDebits += e.getAmount();
            }
        }

        return new AccountSummary(accountId, totalCredits, totalDebits, events.size());
    }
}