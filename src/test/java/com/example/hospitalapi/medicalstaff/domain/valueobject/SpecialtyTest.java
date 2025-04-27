package com.example.hospitalapi.medicalstaff.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyTest {

    @Test
    void testSpecialtyEnumValues() {
        // Assert that all expected specialties are defined
        assertEquals(18, Specialty.values().length);
        
        // Test specific values
        assertEquals(Specialty.CARDIOLOGY, Specialty.valueOf("CARDIOLOGY"));
        assertEquals(Specialty.DERMATOLOGY, Specialty.valueOf("DERMATOLOGY"));
        assertEquals(Specialty.ENDOCRINOLOGY, Specialty.valueOf("ENDOCRINOLOGY"));
        assertEquals(Specialty.GASTROENTEROLOGY, Specialty.valueOf("GASTROENTEROLOGY"));
        assertEquals(Specialty.GENERAL_PRACTICE, Specialty.valueOf("GENERAL_PRACTICE"));
        assertEquals(Specialty.HEMATOLOGY, Specialty.valueOf("HEMATOLOGY"));
        assertEquals(Specialty.NEUROLOGY, Specialty.valueOf("NEUROLOGY"));
        assertEquals(Specialty.OBSTETRICS_GYNECOLOGY, Specialty.valueOf("OBSTETRICS_GYNECOLOGY"));
        assertEquals(Specialty.ONCOLOGY, Specialty.valueOf("ONCOLOGY"));
        assertEquals(Specialty.OPHTHALMOLOGY, Specialty.valueOf("OPHTHALMOLOGY"));
        assertEquals(Specialty.ORTHOPEDICS, Specialty.valueOf("ORTHOPEDICS"));
        assertEquals(Specialty.OTOLARYNGOLOGY, Specialty.valueOf("OTOLARYNGOLOGY"));
        assertEquals(Specialty.PEDIATRICS, Specialty.valueOf("PEDIATRICS"));
        assertEquals(Specialty.PSYCHIATRY, Specialty.valueOf("PSYCHIATRY"));
        assertEquals(Specialty.PULMONOLOGY, Specialty.valueOf("PULMONOLOGY"));
        assertEquals(Specialty.RADIOLOGY, Specialty.valueOf("RADIOLOGY"));
        assertEquals(Specialty.SURGERY, Specialty.valueOf("SURGERY"));
        assertEquals(Specialty.UROLOGY, Specialty.valueOf("UROLOGY"));
    }
    
    @Test
    void testSpecialtyOrdinals() {
        // Test ordinal values for a few specialties
        assertEquals(0, Specialty.CARDIOLOGY.ordinal());
        assertEquals(1, Specialty.DERMATOLOGY.ordinal());
        assertEquals(2, Specialty.ENDOCRINOLOGY.ordinal());
    }
    
    @Test
    void testSpecialtyValueOf() {
        // Test valueOf with valid specialty name
        assertEquals(Specialty.CARDIOLOGY, Specialty.valueOf("CARDIOLOGY"));
        
        // Test valueOf with invalid specialty name
        assertThrows(IllegalArgumentException.class, () -> {
            Specialty.valueOf("INVALID_SPECIALTY");
        });
    }
    
    @Test
    void testSpecialtyToString() {
        // Test toString method
        assertEquals("CARDIOLOGY", Specialty.CARDIOLOGY.toString());
        assertEquals("NEUROLOGY", Specialty.NEUROLOGY.toString());
        assertEquals("SURGERY", Specialty.SURGERY.toString());
    }
}