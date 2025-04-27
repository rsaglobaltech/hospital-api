package com.example.hospitalapi.medicalstaff.application.command;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.repository.MedicalStaffRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateMedicalStaffCommandHandlerTest {

    @Mock
    private MedicalStaffRepository medicalStaffRepository;

    private UpdateMedicalStaffCommandHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new UpdateMedicalStaffCommandHandler(medicalStaffRepository);
    }

    @Test
    void testHandleUpdateMedicalStaffCommand() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        StaffId staffId = new StaffId(uuid);
        String staffIdStr = uuid.toString();
        
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@hospital.com";
        String phoneNumber = "+1234567890";
        String address = "123 Main St, City, Country";
        Specialty specialty = Specialty.CARDIOLOGY;

        // Updated values
        String updatedFirstName = "Jane";
        String updatedLastName = "Smith";
        String updatedEmail = "jane.smith@hospital.com";
        String updatedPhoneNumber = "+9876543210";
        String updatedAddress = "456 Oak St, Town, Country";
        Specialty updatedSpecialty = Specialty.NEUROLOGY;

        UpdateMedicalStaffCommand command = UpdateMedicalStaffCommand.builder()
                .staffId(staffIdStr)
                .firstName(updatedFirstName)
                .lastName(updatedLastName)
                .email(updatedEmail)
                .phoneNumber(updatedPhoneNumber)
                .address(updatedAddress)
                .specialty(updatedSpecialty)
                .build();

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

        // Verify that the update methods were called with the correct values
        ArgumentCaptor<Name> nameCaptor = ArgumentCaptor.forClass(Name.class);
        verify(existingMedicalStaff).updateName(nameCaptor.capture());
        assertEquals(updatedFirstName, nameCaptor.getValue().getFirstName());
        assertEquals(updatedLastName, nameCaptor.getValue().getLastName());

        ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);
        verify(existingMedicalStaff).updateEmail(emailCaptor.capture());
        assertEquals(updatedEmail, emailCaptor.getValue().toString());

        ArgumentCaptor<PhoneNumber> phoneNumberCaptor = ArgumentCaptor.forClass(PhoneNumber.class);
        verify(existingMedicalStaff).updatePhoneNumber(phoneNumberCaptor.capture());
        assertEquals(updatedPhoneNumber, phoneNumberCaptor.getValue().toString());

        ArgumentCaptor<String> addressCaptor = ArgumentCaptor.forClass(String.class);
        verify(existingMedicalStaff).updateAddress(addressCaptor.capture());
        assertEquals(updatedAddress, addressCaptor.getValue());

        ArgumentCaptor<Specialty> specialtyCaptor = ArgumentCaptor.forClass(Specialty.class);
        verify(existingMedicalStaff).updateSpecialty(specialtyCaptor.capture());
        assertEquals(updatedSpecialty, specialtyCaptor.getValue());

        // Verify that save was called
        verify(medicalStaffRepository).save(existingMedicalStaff);
    }

    @Test
    void testHandleUpdateMedicalStaffCommandWithNonExistentStaff() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String staffIdStr = uuid.toString();
        
        UpdateMedicalStaffCommand command = UpdateMedicalStaffCommand.builder()
                .staffId(staffIdStr)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@hospital.com")
                .phoneNumber("+9876543210")
                .address("456 Oak St, Town, Country")
                .specialty(Specialty.NEUROLOGY)
                .build();

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