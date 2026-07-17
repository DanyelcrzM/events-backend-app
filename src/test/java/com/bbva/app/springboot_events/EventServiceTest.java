package com.bbva.app.springboot_events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bbva.app.events.models.AccountSummary;
import com.bbva.app.events.models.Event;
import com.bbva.app.events.repositories.EventRepository;
import com.bbva.app.events.services.EventService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    public void testGetAccountSummary_CalculatesCorrectly() {
        String accountId = "ACC-123";

        Event event1 = new Event();
        event1.setType("CREDIT");
        event1.setAmount(1500.0);

        Event event2 = new Event();
        event2.setType("DEBIT");
        event2.setAmount(500.0);

        List<Event> mockEvents = Arrays.asList(event1, event2);

        when(eventRepository.findByAccountIdIgnoreCase(accountId)).thenReturn(mockEvents);

        AccountSummary summary = eventService.getAccountSummary(accountId);

        assertEquals(1500.0, summary.getTotalCredits(), "El total de créditos no coincide");
        assertEquals(500.0, summary.getTotalDebits(), "El total de débitos no coincide");
        assertEquals(1000.0, summary.getBalance(), "El balance final calculado es erróneo (CREDIT - DEBIT)");
        assertEquals(2, summary.getEventsCount(), "La cantidad total de eventos es incorrecta");
    }
}
