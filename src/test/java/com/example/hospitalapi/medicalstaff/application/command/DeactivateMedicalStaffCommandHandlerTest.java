package com.example.hospitalapi.medicalstaff.application.command;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.repository.MedicalStaffRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeactivateMedicalStaffCommandHandlerTest {

    @Mock
    private MedicalStaffRepository medicalStaffRepository;

    private DeactivateMedicalStaffCommandHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new DeactivateMedicalStaffCommandHandler(medicalStaffRepository);
    }

    @Test
    void testHandleDeactivateMedicalStaffCommand() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        StaffId staffId = new StaffId(uuid);
        String staffIdStr = uuid.toString();
        
        LocalDate terminationDate = LocalDate.now();

        DeactivateMedicalStaffCommand command = new DeactivateMedicalStaffCommand(staffIdStr, terminationDate);

        MedicalStaff existingMedicalStaff = mock(MedicalStaff.class);
        when(medicalStaffRepository.findById(any(StaffId.class))).thenReturn(Optional.of(existingMedicalStaff));
        when(medicalStaffRepository.save(any(MedicalStaff.class))).thenReturn(existingMedicalStaff);

        // Act
        Void result = handler.handle(command);

        // Assert
        assertNull(result);

        // Verify that findById was called with the correct staffId
        ArgumentCaptor<StaffId> staffIdCaptor = ArgumentCaptor.forClass(StaffId.class);
        verify(medicalStaffRepository).findById(staffIdCaptor.capture());
        assertEquals(staffId.toString(), staffIdCaptor.getValue().toString());

        // Verify that deactivate was called with the correct termination date
        ArgumentCaptor<LocalDate> terminationDateCaptor = ArgumentCaptor.forClass(LocalDate.class);
        verify(existingMedicalStaff).deactivate(terminationDateCaptor.capture());
        assertEquals(terminationDate, terminationDateCaptor.getValue());

        // Verify that save was called
        verify(medicalStaffRepository).save(existingMedicalStaff);
    }

    @Test
    void testHandleDeactivateMedicalStaffCommandWithNonExistentStaff() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String staffIdStr = uuid.toString();
        
        LocalDate terminationDate = LocalDate.now();

        DeactivateMedicalStaffCommand command = new DeactivateMedicalStaffCommand(staffIdStr, terminationDate);

        when(medicalStaffRepository.findById(any(StaffId.class))).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(command);
        });
        
        assertEquals("Medical staff not found with ID: " + staffIdStr, exception.getMessage());
        
        // Verify that findById was called but save was not
        verify(medicalStaffRepository).findById(any(StaffId.class));
        verify(medicalStaffRepository, never()).save(any(MedicalStaff.class));
    }
}