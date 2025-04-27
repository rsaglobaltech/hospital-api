package com.example.hospitalapi.shared.domain.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for all aggregate roots in the domain model.
 * An aggregate root is the main entity in an aggregate and is responsible for
 * maintaining the consistency of the aggregate.
 */
public abstract class AggregateRoot {
    private final List<Object> domainEvents = new ArrayList<>();

    /**
     * Registers a domain event to be dispatched when the aggregate is persisted.
     *
     * @param event The domain event to register
     */
    protected void registerDomainEvent(Object event) {
        if (event != null) {
            this.domainEvents.add(event);
        }
    }

    /**
     * Gets all domain events registered by this aggregate.
     *
     * @return An unmodifiable list of domain events
     */
    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    /**
     * Clears all domain events from this aggregate.
     * This should be called after the events have been dispatched.
     */
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }
}