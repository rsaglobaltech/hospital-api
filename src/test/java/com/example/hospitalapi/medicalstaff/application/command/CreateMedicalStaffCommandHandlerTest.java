package com.example.hospitalapi.medicalstaff.application.command;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.repository.MedicalStaffRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateMedicalStaffCommandHandlerTest {

    @Mock
    private MedicalStaffRepository medicalStaffRepository;

    private CreateMedicalStaffCommandHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new CreateMedicalStaffCommandHandler(medicalStaffRepository);
    }

    @Test
    void testHandleCreateMedicalStaffCommand() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@hospital.com";
        String phoneNumber = "+1234567890";
        LocalDate dateOfBirth = LocalDate.of(1980, 1, 1);
        String address = "123 Main St, City, Country";
        Specialty specialty = Specialty.CARDIOLOGY;
        String degree = "MD";
        String institution = "Harvard Medical School";
        LocalDate dateObtained = LocalDate.of(2005, 5, 15);
        String licenseNumber = "ML123456";
        LocalDate licenseExpiryDate = LocalDate.now().plusYears(2);
        LocalDate hireDate = LocalDate.of(2010, 1, 15);

        CreateMedicalStaffCommand command = CreateMedicalStaffCommand.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .specialty(specialty)
                .degree(degree)
                .institution(institution)
                .dateObtained(dateObtained)
                .licenseNumber(licenseNumber)
                .licenseExpiryDate(licenseExpiryDate)
                .hireDate(hireDate)
                .build();

        StaffId expectedStaffId = new StaffId(UUID.randomUUID());
        MedicalStaff savedMedicalStaff = mock(MedicalStaff.class);
        when(savedMedicalStaff.getId()).thenReturn(expectedStaffId);
        when(medicalStaffRepository.save(any(MedicalStaff.class))).thenReturn(savedMedicalStaff);

        // Act
        StaffId result = handler.handle(command);

        // Assert
        assertNotNull(result);
        assertEquals(expectedStaffId, result);

        ArgumentCaptor<MedicalStaff> medicalStaffCaptor = ArgumentCaptor.forClass(MedicalStaff.class);
        verify(medicalStaffRepository).save(medicalStaffCaptor.capture());

        MedicalStaff capturedMedicalStaff = medicalStaffCaptor.getValue();
        assertNotNull(capturedMedicalStaff);
    }

    @Test
    void testHandleCreateMedicalStaffCommandWithNonExistentPatient() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@hospital.com";
        String phoneNumber = "+1234567890";
        LocalDate dateOfBirth = LocalDate.of(1980, 1, 1);
        String address = "123 Main St, City, Country";
        Specialty specialty = Specialty.CARDIOLOGY;
        String degree = "MD";
        String institution = "Harvard Medical School";
        LocalDate dateObtained = LocalDate.of(2005, 5, 15);
        String licenseNumber = "ML123456";
        LocalDate licenseExpiryDate = LocalDate.now().plusYears(2);
        LocalDate hireDate = LocalDate.of(2010, 1, 15);

        CreateMedicalStaffCommand command = CreateMedicalStaffCommand.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phoneNumber(phoneNumber)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .specialty(specialty)
                .degree(degree)
                .institution(institution)
                .dateObtained(dateObtained)
                .licenseNumber(licenseNumber)
                .licenseExpiryDate(licenseExpiryDate)
                .hireDate(hireDate)
                .build();

        // No need to mock patientRepository.existsById since we're not using it in this test

        StaffId expectedStaffId = new StaffId(UUID.randomUUID());
        MedicalStaff savedMedicalStaff = mock(MedicalStaff.class);
        when(savedMedicalStaff.getId()).thenReturn(expectedStaffId);
        when(medicalStaffRepository.save(any(MedicalStaff.class))).thenReturn(savedMedicalStaff);

        // Act
        StaffId result = handler.handle(command);

        // Assert
        assertNotNull(result);
        assertEquals(expectedStaffId, result);

        ArgumentCaptor<MedicalStaff> medicalStaffCaptor = ArgumentCaptor.forClass(MedicalStaff.class);
        verify(medicalStaffRepository).save(medicalStaffCaptor.capture());

        MedicalStaff capturedMedicalStaff = medicalStaffCaptor.getValue();
        assertNotNull(capturedMedicalStaff);
    }
}
