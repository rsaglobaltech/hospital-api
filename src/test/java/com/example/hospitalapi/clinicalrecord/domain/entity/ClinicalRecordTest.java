package com.example.hospitalapi.clinicalrecord.domain.entity;

import com.example.hospitalapi.clinicalrecord.domain.exception.ClinicalRecordValidationException;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClinicalRecordTest {

    @Test
    void testCreateClinicalRecord() {
        // Arrange
        ClinicalRecordId id = new ClinicalRecordId();
        PatientId patientId = new PatientId(UUID.randomUUID());
        String doctorId = UUID.randomUUID().toString();
        ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
        String title = "Test Title";
        String description = "Test Description";

        // Act
        ClinicalRecord clinicalRecord = new ClinicalRecord(id, patientId, doctorId, type, title, description);

        // Assert
        assertEquals(id, clinicalRecord.getId());
        assertEquals(patientId, clinicalRecord.getPatientId());
        assertEquals(doctorId, clinicalRecord.getDoctorId());
        assertEquals(type, clinicalRecord.getType());
        assertEquals(title, clinicalRecord.getTitle());
        assertEquals(description, clinicalRecord.getDescription());
        assertNull(clinicalRecord.getAttachmentUrl());
        assertNotNull(clinicalRecord.getCreatedAt());
        assertNotNull(clinicalRecord.getUpdatedAt());
    }

    @Test
    void testCreateClinicalRecordWithNullId() {
        // Arrange
        PatientId patientId = new PatientId(UUID.randomUUID());
        String doctorId = UUID.randomUUID().toString();
        ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
        String title = "Test Title";
        String description = "Test Description";

        // Act & Assert
        assertThrows(ClinicalRecordValidationException.class, () -> {
            new ClinicalRecord(null, patientId, doctorId, type, title, description);
        });
    }

    @Test
    void testCreateClinicalRecordWithNullPatientId() {
        // Arrange
        ClinicalRecordId id = new ClinicalRecordId();
        String doctorId = UUID.randomUUID().toString();
        ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
        String title = "Test Title";
        String description = "Test Description";

        // Act & Assert
        assertThrows(ClinicalRecordValidationException.class, () -> {
            new ClinicalRecord(id, null, doctorId, type, title, description);
        });
    }

    @Test
    void testCreateClinicalRecordWithEmptyDoctorId() {
        // Arrange
        ClinicalRecordId id = new ClinicalRecordId();
        PatientId patientId = new PatientId(UUID.randomUUID());
        ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
        String title = "Test Title";
        String description = "Test Description";

        // Act & Assert
        assertThrows(ClinicalRecordValidationException.class, () -> {
            new ClinicalRecord(id, patientId, "", type, title, description);
        });
    }

    @Test
    void testCreateClinicalRecordWithNullType() {
        // Arrange
        ClinicalRecordId id = new ClinicalRecordId();
        PatientId patientId = new PatientId(UUID.randomUUID());
        String doctorId = UUID.randomUUID().toString();
        String title = "Test Title";
        String description = "Test Description";

        // Act & Assert
        assertThrows(ClinicalRecordValidationException.class, () -> {
            new ClinicalRecord(id, patientId, doctorId, null, title, description);
        });
    }

    @Test
    void testCreateClinicalRecordWithEmptyTitle() {
        // Arrange
        ClinicalRecordId id = new ClinicalRecordId();
        PatientId patientId = new PatientId(UUID.randomUUID());
        String doctorId = UUID.randomUUID().toString();
        ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
        String description = "Test Description";

        // Act & Assert
        assertThrows(ClinicalRecordValidationException.class, () -> {
            new ClinicalRecord(id, patientId, doctorId, type, "", description);
        });
    }

    @Test
    void testCreateClinicalRecordWithEmptyDescription() {
        // Arrange
        ClinicalRecordId id = new ClinicalRecordId();
        PatientId patientId = new PatientId(UUID.randomUUID());
        String doctorId = UUID.randomUUID().toString();
        ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
        String title = "Test Title";

        // Act & Assert
        assertThrows(ClinicalRecordValidationException.class, () -> {
            new ClinicalRecord(id, patientId, doctorId, type, title, "");
        });
    }

    @Test
    void testUpdateAttachmentUrl() {
        // Arrange
        ClinicalRecordId id = new ClinicalRecordId();
        PatientId patientId = new PatientId(UUID.randomUUID());
        String doctorId = UUID.randomUUID().toString();
        ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
        String title = "Test Title";
        String description = "Test Description";
        ClinicalRecord clinicalRecord = new ClinicalRecord(id, patientId, doctorId, type, title, description);
        String attachmentUrl = "https://example.com/attachment.pdf";

        // Act
        clinicalRecord.updateAttachmentUrl(attachmentUrl);

        // Assert
        assertEquals(attachmentUrl, clinicalRecord.getAttachmentUrl());
    }

    @Test
    void testUpdateType() {
        // Arrange
        ClinicalRecordId id = new ClinicalRecordId();
        PatientId patientId = new PatientId(UUID.randomUUID());
        String doctorId = UUID.randomUUID().toString();
        ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
        String title = "Test Title";
        String description = "Test Description";
        ClinicalRecord clinicalRecord = new ClinicalRecord(id, patientId, doctorId, type, title, description);
        ClinicalRecordType newType = ClinicalRecordType.LAB_RESULT;

        // Act
        clinicalRecord.updateType(newType);

        // Assert
        assertEquals(newType, clinicalRecord.getType());
    }

    @Test
    void testUpdateTitle() {
        // Arrange
        ClinicalRecordId id = new ClinicalRecordId();
        PatientId patientId = new PatientId(UUID.randomUUID());
        String doctorId = UUID.randomUUID().toString();
        ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
        String title = "Test Title";
        String description = "Test Description";
        ClinicalRecord clinicalRecord = new ClinicalRecord(id, patientId, doctorId, type, title, description);
        String newTitle = "New Test Title";

        // Act
        clinicalRecord.updateTitle(newTitle);

        // Assert
        assertEquals(newTitle, clinicalRecord.getTitle());
    }

    @Test
    void testUpdateDescription() {
        // Arrange
        ClinicalRecordId id = new ClinicalRecordId();
        PatientId patientId = new PatientId(UUID.randomUUID());
        String doctorId = UUID.randomUUID().toString();
        ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
        String title = "Test Title";
        String description = "Test Description";
        ClinicalRecord clinicalRecord = new ClinicalRecord(id, patientId, doctorId, type, title, description);
        String newDescription = "New Test Description";

        // Act
        clinicalRecord.updateDescription(newDescription);

        // Assert
        assertEquals(newDescription, clinicalRecord.getDescription());
    }
}