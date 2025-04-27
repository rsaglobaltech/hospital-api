package com.example.hospitalapi.medicalstaff.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class QualificationTest {
    
    @Test
    void testCreateQualification() {
        String degree = "MD";
        String institution = "Harvard Medical School";
        LocalDate dateObtained = LocalDate.of(2005, 5, 15);
        String licenseNumber = "ML123456";
        LocalDate licenseExpiryDate = LocalDate.now().plusYears(2);
        
        Qualification qualification = new Qualification(
                degree,
                institution,
                dateObtained,
                licenseNumber,
                licenseExpiryDate
        );
        
        assertEquals(degree, qualification.getDegree());
        assertEquals(institution, qualification.getInstitution());
        assertEquals(dateObtained, qualification.getDateObtained());
        assertEquals(licenseNumber, qualification.getLicenseNumber());
        assertEquals(licenseExpiryDate, qualification.getLicenseExpiryDate());
        assertFalse(qualification.isExpired());
    }
    
    @Test
    void testCreateQualificationWithNullDegree() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Qualification(
                    null,
                    "Harvard Medical School",
                    LocalDate.of(2005, 5, 15),
                    "ML123456",
                    LocalDate.now().plusYears(2)
            );
        });
    }
    
    @Test
    void testCreateQualificationWithEmptyDegree() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Qualification(
                    "",
                    "Harvard Medical School",
                    LocalDate.of(2005, 5, 15),
                    "ML123456",
                    LocalDate.now().plusYears(2)
            );
        });
    }
    
    @Test
    void testCreateQualificationWithNullInstitution() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Qualification(
                    "MD",
                    null,
                    LocalDate.of(2005, 5, 15),
                    "ML123456",
                    LocalDate.now().plusYears(2)
            );
        });
    }
    
    @Test
    void testCreateQualificationWithEmptyInstitution() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Qualification(
                    "MD",
                    "",
                    LocalDate.of(2005, 5, 15),
                    "ML123456",
                    LocalDate.now().plusYears(2)
            );
        });
    }
    
    @Test
    void testCreateQualificationWithNullDateObtained() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Qualification(
                    "MD",
                    "Harvard Medical School",
                    null,
                    "ML123456",
                    LocalDate.now().plusYears(2)
            );
        });
    }
    
    @Test
    void testCreateQualificationWithNullLicenseNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Qualification(
                    "MD",
                    "Harvard Medical School",
                    LocalDate.of(2005, 5, 15),
                    null,
                    LocalDate.now().plusYears(2)
            );
        });
    }
    
    @Test
    void testCreateQualificationWithEmptyLicenseNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Qualification(
                    "MD",
                    "Harvard Medical School",
                    LocalDate.of(2005, 5, 15),
                    "",
                    LocalDate.now().plusYears(2)
            );
        });
    }
    
    @Test
    void testCreateQualificationWithNullLicenseExpiryDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Qualification(
                    "MD",
                    "Harvard Medical School",
                    LocalDate.of(2005, 5, 15),
                    "ML123456",
                    null
            );
        });
    }
    
    @Test
    void testCreateQualificationWithExpiredLicense() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Qualification(
                    "MD",
                    "Harvard Medical School",
                    LocalDate.of(2005, 5, 15),
                    "ML123456",
                    LocalDate.now().minusDays(1)
            );
        });
    }
    
    @Test
    void testIsExpiringSoon() {
        Qualification qualification = new Qualification(
                "MD",
                "Harvard Medical School",
                LocalDate.of(2005, 5, 15),
                "ML123456",
                LocalDate.now().plusMonths(3)
        );
        
        assertTrue(qualification.isExpiringSoon(6));
        assertFalse(qualification.isExpiringSoon(2));
    }
}