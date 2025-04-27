package com.example.hospitalapi.shared.domain.event;

/**
 * Interface for publishing domain events
 */
public interface EventPublisher {
    
    /**
     * Publish a domain event
     * @param event the event to publish
     */
    void publish(DomainEvent event);
}