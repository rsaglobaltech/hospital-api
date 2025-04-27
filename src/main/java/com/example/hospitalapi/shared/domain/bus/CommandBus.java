package com.example.hospitalapi.shared.domain.bus;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for the command bus in the CQRS pattern
 */
public interface CommandBus {
    
    /**
     * Dispatch a command asynchronously
     * @param command the command to dispatch
     * @param <R> the type of response
     * @return a CompletableFuture that will be completed with the result of handling the command
     */
    <R, C extends Command> CompletableFuture<R> dispatch(C command);
}