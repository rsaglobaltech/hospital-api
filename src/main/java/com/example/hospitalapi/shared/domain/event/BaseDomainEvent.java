package com.example.hospitalapi.shared.domain.event;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base class for domain events in the system
 */
@Getter
public abstract class BaseDomainEvent implements DomainEvent {
    
    private final String eventId;
    private final LocalDateTime occurredOn;
    private final String aggregateId;
    
    /**
     * Create a new domain event
     * @param aggregateId the ID of the aggregate that the event is related to
     */
    protected BaseDomainEvent(String aggregateId) {
        this.eventId = UUID.randomUUID().toString();
        this.occurredOn = LocalDateTime.now();
        this.aggregateId = aggregateId;
    }
}