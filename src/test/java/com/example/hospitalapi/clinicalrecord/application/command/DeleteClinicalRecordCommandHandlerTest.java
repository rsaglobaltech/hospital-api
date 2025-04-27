package com.example.hospitalapi.clinicalrecord.application.command;

import com.example.hospitalapi.clinicalrecord.domain.exception.ClinicalRecordNotFoundException;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeleteClinicalRecordCommandHandlerTest {

    @Mock
    private ClinicalRecordRepository clinicalRecordRepository;

    private DeleteClinicalRecordCommandHandler handler;

    private final UUID clinicalRecordUuid = UUID.randomUUID();
    private final String clinicalRecordId = clinicalRecordUuid.toString();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new DeleteClinicalRecordCommandHandler(clinicalRecordRepository);
    }

    @Test
    void testHandleDeleteClinicalRecordCommand() {
        // Arrange
        DeleteClinicalRecordCommand command = new DeleteClinicalRecordCommand(clinicalRecordId);

        when(clinicalRecordRepository.existsById(any(ClinicalRecordId.class))).thenReturn(true);

        // Act
        handler.handle(command);

        // Assert
        verify(clinicalRecordRepository).existsById(any(ClinicalRecordId.class));
        verify(clinicalRecordRepository).deleteById(any(ClinicalRecordId.class));
    }

    @Test
    void testHandleDeleteClinicalRecordCommandWithNonExistentClinicalRecord() {
        // Arrange
        DeleteClinicalRecordCommand command = new DeleteClinicalRecordCommand(clinicalRecordId);

        when(clinicalRecordRepository.existsById(any(ClinicalRecordId.class))).thenReturn(false);

        // Act & Assert
        assertThrows(ClinicalRecordNotFoundException.class, () -> handler.handle(command));
        verify(clinicalRecordRepository).existsById(any(ClinicalRecordId.class));
        verify(clinicalRecordRepository, never()).deleteById(any(ClinicalRecordId.class));
    }
}