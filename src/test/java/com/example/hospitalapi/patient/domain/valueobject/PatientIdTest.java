package com.example.hospitalapi.patient.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PatientIdTest {
    
    @Test
    void testCreatePatientIdWithNoArguments() {
        PatientId patientId = new PatientId();
        
        assertNotNull(patientId.getValue());
        assertTrue(patientId.getValue() instanceof UUID);
    }
    
    @Test
    void testCreatePatientIdWithUUID() {
        UUID uuid = UUID.randomUUID();
        PatientId patientId = new PatientId(uuid);
        
        assertEquals(uuid, patientId.getValue());
        assertEquals(uuid.toString(), patientId.toString());
    }
    
    @Test
    void testCreatePatientIdWithString() {
        UUID uuid = UUID.randomUUID();
        PatientId patientId = new PatientId(uuid.toString());
        
        assertEquals(uuid, patientId.getValue());
        assertEquals(uuid.toString(), patientId.toString());
    }
    
    @Test
    void testCreatePatientIdWithInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PatientId("invalid-uuid");
        });
    }
    
    @Test
    void testPatientIdEquality() {
        UUID uuid = UUID.randomUUID();
        PatientId patientId1 = new PatientId(uuid);
        PatientId patientId2 = new PatientId(uuid);
        PatientId patientId3 = new PatientId(UUID.randomUUID());
        
        assertEquals(patientId1, patientId2);
        assertNotEquals(patientId1, patientId3);
        assertNotEquals(patientId2, patientId3);
    }
    
    @Test
    void testPatientIdHashCode() {
        UUID uuid = UUID.randomUUID();
        PatientId patientId1 = new PatientId(uuid);
        PatientId patientId2 = new PatientId(uuid);
        
        assertEquals(patientId1.hashCode(), patientId2.hashCode());
    }
}