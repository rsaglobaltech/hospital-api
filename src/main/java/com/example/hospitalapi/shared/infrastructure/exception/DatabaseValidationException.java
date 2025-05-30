package com.example.hospitalapi.shared.infrastructure.exception;

public class DatabaseValidationException extends RuntimeException{

    public DatabaseValidationException(String message) {
        super(message);
    }

    public DatabaseValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}


