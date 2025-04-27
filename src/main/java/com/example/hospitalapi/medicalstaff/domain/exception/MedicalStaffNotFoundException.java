package com.example.hospitalapi.medicalstaff.domain.exception;

import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;

/**
 * Exception thrown when a medical staff member is not found
 */
public class MedicalStaffNotFoundException extends MedicalStaffException {

    /**
     * Create a new MedicalStaffNotFoundException with a staff ID
     * @param staffId the ID of the medical staff member that was not found
     */
    public MedicalStaffNotFoundException(StaffId staffId) {
        super("Medical staff not found with ID: " + staffId);
    }

    /**
     * Create a new MedicalStaffNotFoundException with a message
     * @param message the exception message
     */
    public MedicalStaffNotFoundException(String message) {
        super(message);
    }
}