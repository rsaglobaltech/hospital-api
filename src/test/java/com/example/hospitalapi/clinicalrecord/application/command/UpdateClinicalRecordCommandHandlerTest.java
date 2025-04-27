package com.example.hospitalapi.clinicalrecord.application.command;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.exception.ClinicalRecordNotFoundException;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateClinicalRecordCommandHandlerTest {

    @Mock
    private ClinicalRecordRepository clinicalRecordRepository;

    @Mock
    private ClinicalRecord clinicalRecord;

    private UpdateClinicalRecordCommandHandler handler;

    private final UUID clinicalRecordUuid = UUID.randomUUID();
    private final String clinicalRecordId = clinicalRecordUuid.toString();
    private final ClinicalRecordType type = ClinicalRecordType.DIAGNOSIS;
    private final String title = "Updated Title";
    private final String description = "Updated Description";
    private final String attachmentUrl = "https://example.com/updated-attachment.pdf";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new UpdateClinicalRecordCommandHandler(clinicalRecordRepository);
    }

    @Test
    void testHandleUpdateClinicalRecordCommand() {
        // Arrange
        UpdateClinicalRecordCommand command = UpdateClinicalRecordCommand.builder()
                .clinicalRecordId(clinicalRecordId)
                .type(type)
                .title(title)
                .description(description)
                .attachmentUrl(attachmentUrl)
                .build();

        when(clinicalRecordRepository.findById(any(ClinicalRecordId.class))).thenReturn(Optional.of(clinicalRecord));

        // Act
        handler.handle(command);

        // Assert
        verify(clinicalRecordRepository).findById(any(ClinicalRecordId.class));
        verify(clinicalRecord).updateType(type);
        verify(clinicalRecord).updateTitle(title);
        verify(clinicalRecord).updateDescription(description);
        verify(clinicalRecord).updateAttachmentUrl(attachmentUrl);
        verify(clinicalRecordRepository).save(clinicalRecord);
    }

    @Test
    void testHandleUpdateClinicalRecordCommandWithNullAttachmentUrl() {
        // Arrange
        UpdateClinicalRecordCommand command = UpdateClinicalRecordCommand.builder()
                .clinicalRecordId(clinicalRecordId)
                .type(type)
                .title(title)
                .description(description)
                .attachmentUrl(null)
                .build();

        when(clinicalRecordRepository.findById(any(ClinicalRecordId.class))).thenReturn(Optional.of(clinicalRecord));

        // Act
        handler.handle(command);

        // Assert
        verify(clinicalRecordRepository).findById(any(ClinicalRecordId.class));
        verify(clinicalRecord).updateType(type);
        verify(clinicalRecord).updateTitle(title);
        verify(clinicalRecord).updateDescription(description);
        verify(clinicalRecord).updateAttachmentUrl(null);
        verify(clinicalRecordRepository).save(clinicalRecord);
    }

    @Test
    void testHandleUpdateClinicalRecordCommandWithNonExistentClinicalRecord() {
        // Arrange
        UpdateClinicalRecordCommand command = UpdateClinicalRecordCommand.builder()
                .clinicalRecordId(clinicalRecordId)
                .type(type)
                .title(title)
                .description(description)
                .build();

        when(clinicalRecordRepository.findById(any(ClinicalRecordId.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClinicalRecordNotFoundException.class, () -> handler.handle(command));
        verify(clinicalRecordRepository).findById(any(ClinicalRecordId.class));
        verify(clinicalRecordRepository, never()).save(any(ClinicalRecord.class));
    }
}