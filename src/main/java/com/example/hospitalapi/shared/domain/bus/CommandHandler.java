package com.example.hospitalapi.shared.domain.bus;

/**
 * Interface for command handlers in the CQRS pattern
 * @param <C> the type of command
 * @param <R> the type of response (can be Void for commands that don't return a value)
 */
public interface CommandHandler<C extends Command, R> {
    
    /**
     * Handle the command
     * @param command the command to handle
     * @return the result of handling the command
     */
    R handle(C command);
}