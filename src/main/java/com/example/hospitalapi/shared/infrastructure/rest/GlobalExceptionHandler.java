package com.example.hospitalapi.shared.infrastructure.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST controllers
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle validation exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", "La validación de datos falló.");

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorResponse.getError().addDetail(fieldName, errorMessage);
        });

        String traceId = errorResponse.getError().getTraceId();
        logger.error("Validation error [traceId={}]: {}", traceId, ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse("INVALID_ARGUMENT", ex.getMessage());

        String traceId = errorResponse.getError().getTraceId();
        logger.error("Invalid argument [traceId={}]: {}", traceId, ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle resource not found exceptions
     */
    @ExceptionHandler({
        org.springframework.dao.EmptyResultDataAccessException.class,
        UsernameNotFoundException.class,
        RuntimeException.class
    })
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception ex) {
        // Check if the exception is a "NotFoundException" by name
        boolean isNotFoundException = ex.getClass().getSimpleName().contains("NotFoundException");

        if (!isNotFoundException && !(ex instanceof EmptyResultDataAccessException) && 
            !(ex instanceof UsernameNotFoundException)) {
            // If it's not a "NotFoundException", rethrow it to be handled by the general exception handler
            throw (RuntimeException) ex;
        }

        ErrorResponse errorResponse = new ErrorResponse("RESOURCE_NOT_FOUND", "El recurso solicitado no fue encontrado.");
        errorResponse.getError().addDetail("resource", ex.getMessage());

        String traceId = errorResponse.getError().getTraceId();
        logger.error("Resource not found [traceId={}]: {}", traceId, ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle data integrity violations
     */
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(org.springframework.dao.DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse("DATA_INTEGRITY_VIOLATION", "La operación viola restricciones de integridad de datos.");

        String traceId = errorResponse.getError().getTraceId();
        logger.error("Data integrity violation [traceId={}]: {}", traceId, ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", "Se produjo un error interno en el servidor.");

        String traceId = errorResponse.getError().getTraceId();
        logger.error("Unexpected error [traceId={}]: {}", traceId, ex.getMessage(), ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
