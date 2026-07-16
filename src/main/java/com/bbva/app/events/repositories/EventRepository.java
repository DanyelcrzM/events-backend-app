package com.bbva.app.events.repositories;

import com.bbva.app.events.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findByAccountIdIgnoreCase(String accountId);
}