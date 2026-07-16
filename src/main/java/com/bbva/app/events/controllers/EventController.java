package com.bbva.app.events.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bbva.app.events.models.AccountSummary;
import com.bbva.app.events.models.Event;
import com.bbva.app.events.services.EventService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/events")
    public ResponseEntity<Object> createEvent(@RequestBody Event event) {
        try {
            Event saved = eventService.saveEvent(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/events")
    public List<Event> getEvents(@RequestParam(required = false) String accountId) {
        return eventService.getEvents(accountId);
    }

    @GetMapping("/accounts/{accountId}/summary")
    public ResponseEntity<AccountSummary> getAccountSummary(@PathVariable String accountId) {
        return ResponseEntity.ok(eventService.getAccountSummary(accountId));
    }

    @GetMapping("/health")
    public Map<String, String> getHealth() {
        return Map.of("status", "UP");
    }
}