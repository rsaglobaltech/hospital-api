package com.example.hospitalapi.patient.domain.exception;

import com.example.hospitalapi.patient.domain.valueobject.PatientId;

/**
 * Exception thrown when a patient is not found
 */
public class PatientNotFoundException extends PatientException {

    /**
     * Create a new PatientNotFoundException with a patient ID
     * @param patientId the ID of the patient that was not found
     */
    public PatientNotFoundException(PatientId patientId) {
        super("Patient not found with ID: " + patientId);
    }

    /**
     * Create a new PatientNotFoundException with a message
     * @param message the exception message
     */
    public PatientNotFoundException(String message) {
        super(message);
    }
}