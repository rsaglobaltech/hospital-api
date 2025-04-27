package com.example.hospitalapi.patient.domain.entity;

import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    
    private PatientId patientId;
    private Name name;
    private Email email;
    private PhoneNumber phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    
    @BeforeEach
    void setUp() {
        patientId = new PatientId();
        name = new Name("John", "Doe");
        email = new Email("john.doe@example.com");
        phoneNumber = new PhoneNumber("+1234567890");
        dateOfBirth = LocalDate.of(1990, 1, 1);
        address = "123 Main St, City, Country";
    }
    
    @Test
    void testCreatePatient() {
        Patient patient = new Patient(
                patientId,
                name,
                email,
                phoneNumber,
                dateOfBirth,
                address
        );
        
        assertEquals(patientId, patient.getId());
        assertEquals(name, patient.getName());
        assertEquals(email, patient.getEmail());
        assertEquals(phoneNumber, patient.getPhoneNumber());
        assertEquals(dateOfBirth, patient.getDateOfBirth());
        assertEquals(address, patient.getAddress());
        assertEquals("", patient.getMedicalHistory());
    }
    
    @Test
    void testUpdatePatient() {
        Patient patient = new Patient(
                patientId,
                name,
                email,
                phoneNumber,
                dateOfBirth,
                address
        );
        
        Name newName = new Name("Jane", "Smith");
        Email newEmail = new Email("jane.smith@example.com");
        PhoneNumber newPhoneNumber = new PhoneNumber("+9876543210");
        String newAddress = "456 Oak St, Town, Country";
        String medicalHistory = "No known allergies. Had appendectomy in 2010.";
        
        patient.updateName(newName);
        patient.updateEmail(newEmail);
        patient.updatePhoneNumber(newPhoneNumber);
        patient.updateAddress(newAddress);
        patient.updateMedicalHistory(medicalHistory);
        
        assertEquals(newName, patient.getName());
        assertEquals(newEmail, patient.getEmail());
        assertEquals(newPhoneNumber, patient.getPhoneNumber());
        assertEquals(newAddress, patient.getAddress());
        assertEquals(medicalHistory, patient.getMedicalHistory());
    }
    
    @Test
    void testCreatePatientWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Patient(
                    null,
                    name,
                    email,
                    phoneNumber,
                    dateOfBirth,
                    address
            );
        });
    }
    
    @Test
    void testCreatePatientWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Patient(
                    patientId,
                    null,
                    email,
                    phoneNumber,
                    dateOfBirth,
                    address
            );
        });
    }
    
    @Test
    void testCreatePatientWithNullEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Patient(
                    patientId,
                    name,
                    null,
                    phoneNumber,
                    dateOfBirth,
                    address
            );
        });
    }
    
    @Test
    void testCreatePatientWithNullPhoneNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Patient(
                    patientId,
                    name,
                    email,
                    null,
                    dateOfBirth,
                    address
            );
        });
    }
    
    @Test
    void testCreatePatientWithNullDateOfBirth() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Patient(
                    patientId,
                    name,
                    email,
                    phoneNumber,
                    null,
                    address
            );
        });
    }
    
    @Test
    void testCreatePatientWithNullAddress() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Patient(
                    patientId,
                    name,
                    email,
                    phoneNumber,
                    dateOfBirth,
                    null
            );
        });
    }
    
    @Test
    void testCreatePatientWithEmptyAddress() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Patient(
                    patientId,
                    name,
                    email,
                    phoneNumber,
                    dateOfBirth,
                    ""
            );
        });
    }
    
    @Test
    void testUpdateNameWithNull() {
        Patient patient = new Patient(
                patientId,
                name,
                email,
                phoneNumber,
                dateOfBirth,
                address
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            patient.updateName(null);
        });
    }
    
    @Test
    void testUpdateEmailWithNull() {
        Patient patient = new Patient(
                patientId,
                name,
                email,
                phoneNumber,
                dateOfBirth,
                address
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            patient.updateEmail(null);
        });
    }
    
    @Test
    void testUpdatePhoneNumberWithNull() {
        Patient patient = new Patient(
                patientId,
                name,
                email,
                phoneNumber,
                dateOfBirth,
                address
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            patient.updatePhoneNumber(null);
        });
    }
    
    @Test
    void testUpdateAddressWithNull() {
        Patient patient = new Patient(
                patientId,
                name,
                email,
                phoneNumber,
                dateOfBirth,
                address
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            patient.updateAddress(null);
        });
    }
    
    @Test
    void testUpdateAddressWithEmpty() {
        Patient patient = new Patient(
                patientId,
                name,
                email,
                phoneNumber,
                dateOfBirth,
                address
        );
        
        assertThrows(IllegalArgumentException.class, () -> {
            patient.updateAddress("");
        });
    }
    
    @Test
    void testUpdateMedicalHistoryWithNull() {
        Patient patient = new Patient(
                patientId,
                name,
                email,
                phoneNumber,
                dateOfBirth,
                address
        );
        
        patient.updateMedicalHistory(null);
        assertEquals("", patient.getMedicalHistory());
    }
}