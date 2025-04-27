package com.example.hospitalapi.patient.domain.entity;

import com.example.hospitalapi.patient.domain.exception.PatientValidationException;
import com.example.hospitalapi.patient.domain.valueobject.Address;
import com.example.hospitalapi.patient.domain.valueobject.DateOfBirth;
import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.MedicalHistory;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import com.example.hospitalapi.shared.domain.entity.AggregateRoot;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Entity representing a patient in the hospital system
 */
@Getter
public class Patient extends AggregateRoot {
    private final PatientId id;
    private Name name;
    private Email email;
    private PhoneNumber phoneNumber;
    private DateOfBirth dateOfBirth;
    private Address address;
    private MedicalHistory medicalHistory;

    public Patient(PatientId id, Name name, Email email, PhoneNumber phoneNumber, 
                  LocalDate dateOfBirthValue, String addressValue) {
        if (id == null) {
            throw new PatientValidationException("Patient ID cannot be null");
        }
        if (name == null) {
            throw new PatientValidationException("Name cannot be null");
        }
        if (email == null) {
            throw new PatientValidationException("Email cannot be null");
        }
        if (phoneNumber == null) {
            throw new PatientValidationException("Phone number cannot be null");
        }
        if (dateOfBirthValue == null) {
            throw new PatientValidationException("Date of birth cannot be null");
        }
        if (addressValue == null || addressValue.trim().isEmpty()) {
            throw new PatientValidationException("Address cannot be empty");
        }

        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = new DateOfBirth(dateOfBirthValue);
        this.address = new Address(addressValue);
        this.medicalHistory = new MedicalHistory("");
    }

    public void updateName(Name name) {
        if (name == null) {
            throw new PatientValidationException("Name cannot be null");
        }
        this.name = name;
    }

    public void updateEmail(Email email) {
        if (email == null) {
            throw new PatientValidationException("Email cannot be null");
        }
        this.email = email;
    }

    public void updatePhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber == null) {
            throw new PatientValidationException("Phone number cannot be null");
        }
        this.phoneNumber = phoneNumber;
    }

    public void updateAddress(String addressValue) {
        if (addressValue == null || addressValue.trim().isEmpty()) {
            throw new PatientValidationException("Address cannot be empty");
        }
        this.address = new Address(addressValue);
    }

    public void updateMedicalHistory(String medicalHistoryValue) {
        this.medicalHistory = new MedicalHistory(medicalHistoryValue);
    }
}
