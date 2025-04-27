package com.example.hospitalapi.shared.domain.event;

import java.time.LocalDateTime;

/**
 * Interface for domain events in the system
 */
public interface DomainEvent {
    
    /**
     * Get the unique identifier of the event
     * @return the event ID
     */
    String getEventId();
    
    /**
     * Get the timestamp when the event occurred
     * @return the event timestamp
     */
    LocalDateTime getOccurredOn();
    
    /**
     * Get the aggregate ID that the event is related to
     * @return the aggregate ID
     */
    String getAggregateId();
}