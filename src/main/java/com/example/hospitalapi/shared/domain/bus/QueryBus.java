package com.example.hospitalapi.shared.domain.bus;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for the query bus in the CQRS pattern
 */
public interface QueryBus {
    
    /**
     * Dispatch a query asynchronously
     * @param query the query to dispatch
     * @param <R> the type of response
     * @return a CompletableFuture that will be completed with the result of handling the query
     */
    <R, Q extends Query<R>> CompletableFuture<R> dispatch(Q query);
}