package com.example.hospitalapi.shared.infrastructure.exception;

import com.example.hospitalapi.shared.domain.bus.Command;

/**
 * Exception thrown when a command handler is not found for a command
 */
public class CommandHandlerNotFoundException extends InfrastructureException {

    /**
     * Create a new CommandHandlerNotFoundException with a command
     * @param command the command for which no handler was found
     */
    public CommandHandlerNotFoundException(Command command) {
        super("No handler registered for " + command.getClass().getName());
    }

    /**
     * Create a new CommandHandlerNotFoundException with a message
     * @param message the exception message
     */
    public CommandHandlerNotFoundException(String message) {
        super(message);
    }
}