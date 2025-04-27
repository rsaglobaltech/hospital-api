package com.example.hospitalapi.shared.domain.bus;

/**
 * Interface for query handlers in the CQRS pattern
 * @param <Q> the type of query
 * @param <R> the type of response
 */
public interface QueryHandler<Q extends Query<R>, R> {
    
    /**
     * Handle the query
     * @param query the query to handle
     * @return the result of handling the query
     */
    R handle(Q query);
}