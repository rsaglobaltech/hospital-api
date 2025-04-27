package com.example.hospitalapi.patient.application.exception;

import com.example.hospitalapi.patient.domain.valueobject.PatientId;

/**
 * Exception thrown when there is an error deleting a patient
 */
public class PatientDeletionException extends PatientApplicationException {

    /**
     * Create a new PatientDeletionException with a patient ID
     * @param patientId the ID of the patient that could not be deleted
     */
    public PatientDeletionException(PatientId patientId) {
        super("Error deleting patient with ID: " + patientId);
    }

    /**
     * Create a new PatientDeletionException with a message
     * @param message the exception message
     */
    public PatientDeletionException(String message) {
        super(message);
    }

    /**
     * Create a new PatientDeletionException with a message and cause
     * @param message the exception message
     * @param cause the cause of the exception
     */
    public PatientDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}