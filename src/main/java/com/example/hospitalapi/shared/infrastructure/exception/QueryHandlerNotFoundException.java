package com.example.hospitalapi.shared.infrastructure.exception;

import com.example.hospitalapi.shared.domain.bus.Query;

/**
 * Exception thrown when a query handler is not found for a query
 */
public class QueryHandlerNotFoundException extends InfrastructureException {

    /**
     * Create a new QueryHandlerNotFoundException with a query
     * @param query the query for which no handler was found
     */
    public QueryHandlerNotFoundException(Query<?> query) {
        super("No handler registered for " + query.getClass().getName());
    }

    /**
     * Create a new QueryHandlerNotFoundException with a message
     * @param message the exception message
     */
    public QueryHandlerNotFoundException(String message) {
        super(message);
    }
}