package com.example.hospitalapi.shared.infrastructure.exception;

/**
 * Exception thrown when the command or query class cannot be determined from a handler
 */
public class HandlerClassResolutionException extends InfrastructureException {

    /**
     * Create a new HandlerClassResolutionException with a handler class
     * @param handlerClass the handler class for which the command or query class could not be determined
     */
    public HandlerClassResolutionException(Class<?> handlerClass) {
        super("Could not determine command/query class for handler " + handlerClass.getName());
    }

    /**
     * Create a new HandlerClassResolutionException with a message
     * @param message the exception message
     */
    public HandlerClassResolutionException(String message) {
        super(message);
    }

    /**
     * Create a new HandlerClassResolutionException with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public HandlerClassResolutionException(String message, Throwable cause) {
        super(message, cause);
    }
}