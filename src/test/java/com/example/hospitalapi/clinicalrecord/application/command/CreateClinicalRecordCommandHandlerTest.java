package com.example.hospitalapi.clinicalrecord.application.command;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.patient.domain.exception.PatientNotFoundException;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateClinicalRecordCommandHandlerTest {

    @Mock
    private ClinicalRecordRepository clinicalRecordRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ClinicalRecord clinicalRecord;

    private CreateClinicalRecordCommandHandler handler;

    private final UUID patientUuid = UUID.randomUUID();
    private final String patientId = patientUuid.toString();
    private final String doctorId = UUID.randomUUID().toString();
    private final ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
    private final String title = "Test Title";
    private final String description = "Test Description";
    private final String attachmentUrl = "https://example.com/attachment.pdf";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new CreateClinicalRecordCommandHandler(clinicalRecordRepository, patientRepository);
    }

    @Test
    void testHandleCreateClinicalRecordCommand() {
        // Arrange
        CreateClinicalRecordCommand command = CreateClinicalRecordCommand.builder()
                .patientId(patientId)
                .doctorId(doctorId)
                .type(type)
                .title(title)
                .description(description)
                .attachmentUrl(attachmentUrl)
                .build();

        when(patientRepository.existsById(any(PatientId.class))).thenReturn(true);
        when(clinicalRecordRepository.save(any(ClinicalRecord.class))).thenReturn(clinicalRecord);
        when(clinicalRecord.getId()).thenReturn(new ClinicalRecordId());

        // Act
        ClinicalRecordId result = handler.handle(command);

        // Assert
        assertNotNull(result);
        verify(patientRepository).existsById(any(PatientId.class));
        
        ArgumentCaptor<ClinicalRecord> clinicalRecordCaptor = ArgumentCaptor.forClass(ClinicalRecord.class);
        verify(clinicalRecordRepository).save(clinicalRecordCaptor.capture());
        
        ClinicalRecord capturedClinicalRecord = clinicalRecordCaptor.getValue();
        assertNotNull(capturedClinicalRecord);
    }

    @Test
    void testHandleCreateClinicalRecordCommandWithNonExistentPatient() {
        // Arrange
        CreateClinicalRecordCommand command = CreateClinicalRecordCommand.builder()
                .patientId(patientId)
                .doctorId(doctorId)
                .type(type)
                .title(title)
                .description(description)
                .build();

        when(patientRepository.existsById(any(PatientId.class))).thenReturn(false);

        // Act & Assert
        assertThrows(PatientNotFoundException.class, () -> handler.handle(command));
        verify(patientRepository).existsById(any(PatientId.class));
        verify(clinicalRecordRepository, never()).save(any(ClinicalRecord.class));
    }
}