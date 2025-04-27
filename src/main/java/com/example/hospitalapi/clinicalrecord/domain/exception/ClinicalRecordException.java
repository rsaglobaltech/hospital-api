package com.example.hospitalapi.clinicalrecord.domain.exception;

import com.example.hospitalapi.shared.domain.exception.DomainException;

/**
 * Base exception for all clinical record domain exceptions
 */
public abstract class ClinicalRecordException extends DomainException {
    
    protected ClinicalRecordException(String message) {
        super(message);
    }
    
    protected ClinicalRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}