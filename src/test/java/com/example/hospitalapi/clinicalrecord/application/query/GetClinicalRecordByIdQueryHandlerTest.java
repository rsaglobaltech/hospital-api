package com.example.hospitalapi.clinicalrecord.application.query;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.exception.ClinicalRecordNotFoundException;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.*;
import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.valueobject.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetClinicalRecordByIdQueryHandlerTest {

    @Mock
    private ClinicalRecordRepository clinicalRecordRepository;

    @Mock
    private ClinicalRecord clinicalRecord;

    private GetClinicalRecordByIdQueryHandler handler;

    private final UUID clinicalRecordUuid = UUID.randomUUID();
    private final String clinicalRecordId = clinicalRecordUuid.toString();
    private final UUID patientUuid = UUID.randomUUID();
    private final String patientId = patientUuid.toString();
    private final String doctorId = UUID.randomUUID().toString();
    private final ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
    private final String title = "Test Title";
    private final String description = "Test Description";
    private final String attachmentUrl = "https://example.com/attachment.pdf";
    private final LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
    private final LocalDateTime updatedAt = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetClinicalRecordByIdQueryHandler(clinicalRecordRepository);

        // Setup mock clinical record
        when(clinicalRecord.getId()).thenReturn(new ClinicalRecordId(clinicalRecordId));
        when(clinicalRecord.getPatientId()).thenReturn(new PatientId(patientId));
        when(clinicalRecord.getDoctorId()).thenReturn(new DoctorId(doctorId));
        when(clinicalRecord.getType()).thenReturn(type);
        when(clinicalRecord.getTitle()).thenReturn(new ClinicalRecordTitle(title));
        when(clinicalRecord.getDescription()).thenReturn(new ClinicalRecordDescription(description));
        when(clinicalRecord.getAttachmentUrl()).thenReturn(new AttachmentUrl(attachmentUrl));
        when(clinicalRecord.getCreatedAt()).thenReturn(new Timestamp(createdAt));
        when(clinicalRecord.getUpdatedAt()).thenReturn(new Timestamp(updatedAt));
    }

    @Test
    void testHandleGetClinicalRecordByIdQuery() {
        // Arrange
        GetClinicalRecordByIdQuery query = new GetClinicalRecordByIdQuery(clinicalRecordId);

        when(clinicalRecordRepository.findById(any(ClinicalRecordId.class))).thenReturn(Optional.of(clinicalRecord));

        // Act
        ClinicalRecordResponse response = handler.handle(query);

        // Assert
        assertNotNull(response);
        assertEquals(clinicalRecordId, response.getId());
        assertEquals(patientId, response.getPatientId());
        assertEquals(doctorId, response.getDoctorId());
        assertEquals(type, response.getType());
        assertEquals(title, response.getTitle());
        assertEquals(description, response.getDescription());
        assertEquals(attachmentUrl, response.getAttachmentUrl());
        assertEquals(createdAt, response.getCreatedAt());
        assertEquals(updatedAt, response.getUpdatedAt());

        verify(clinicalRecordRepository).findById(any(ClinicalRecordId.class));
    }

    @Test
    void testHandleGetClinicalRecordByIdQueryWithNonExistentClinicalRecord() {
        // Arrange
        GetClinicalRecordByIdQuery query = new GetClinicalRecordByIdQuery(clinicalRecordId);

        when(clinicalRecordRepository.findById(any(ClinicalRecordId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClinicalRecordNotFoundException.class, () -> handler.handle(query));
        verify(clinicalRecordRepository).findById(any(ClinicalRecordId.class));
    }
}