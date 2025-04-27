package com.example.hospitalapi.medicalstaff.application.query;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.repository.MedicalStaffRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.HireDate;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Qualification;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.patient.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetMedicalStaffByIdQueryHandlerTest {

    @Mock
    private MedicalStaffRepository medicalStaffRepository;

    private GetMedicalStaffByIdQueryHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetMedicalStaffByIdQueryHandler(medicalStaffRepository);
    }

    @Test
    void testHandleGetMedicalStaffByIdQuery() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        StaffId staffId = new StaffId(uuid);
        String staffIdStr = uuid.toString();
        
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@hospital.com";
        String phoneNumber = "+1234567890";
        LocalDate dateOfBirth = LocalDate.of(1980, 1, 1);
        String address = "123 Main St, City, Country";
        Specialty specialty = Specialty.CARDIOLOGY;
        LocalDate hireDate = LocalDate.of(2010, 1, 15);
        
        // Create qualification
        String degree = "MD";
        String institution = "Harvard Medical School";
        LocalDate dateObtained = LocalDate.of(2005, 5, 15);
        String licenseNumber = "ML123456";
        LocalDate licenseExpiryDate = LocalDate.now().plusYears(2);
        
        Qualification qualification = mock(Qualification.class);
        when(qualification.getDegree()).thenReturn(degree);
        when(qualification.getInstitution()).thenReturn(institution);
        when(qualification.getDateObtained()).thenReturn(dateObtained);
        when(qualification.getLicenseNumber()).thenReturn(licenseNumber);
        when(qualification.getLicenseExpiryDate()).thenReturn(licenseExpiryDate);
        when(qualification.isExpired()).thenReturn(false);
        
        List<Qualification> qualifications = new ArrayList<>();
        qualifications.add(qualification);
        
        // Create medical staff
        MedicalStaff medicalStaff = mock(MedicalStaff.class);
        when(medicalStaff.getId()).thenReturn(staffId);
        when(medicalStaff.getName()).thenReturn(new Name(firstName, lastName));
        when(medicalStaff.getEmail()).thenReturn(new Email(email));
        when(medicalStaff.getPhoneNumber()).thenReturn(new PhoneNumber(phoneNumber));
        when(medicalStaff.getDateOfBirth()).thenReturn(new DateOfBirth(dateOfBirth));
        when(medicalStaff.getAddress()).thenReturn(new Address(address));
        when(medicalStaff.getSpecialty()).thenReturn(specialty);
        when(medicalStaff.getQualifications()).thenReturn(qualifications);
        when(medicalStaff.isActive()).thenReturn(true);
        when(medicalStaff.getHireDate()).thenReturn(new HireDate(hireDate));
        when(medicalStaff.getTerminationDate()).thenReturn(null);
        
        when(medicalStaffRepository.findById(any(StaffId.class))).thenReturn(Optional.of(medicalStaff));
        
        GetMedicalStaffByIdQuery query = new GetMedicalStaffByIdQuery(staffIdStr);
        
        // Act
        MedicalStaffResponse response = handler.handle(query);
        
        // Assert
        assertNotNull(response);
        assertEquals(staffIdStr, response.getId());
        assertEquals(firstName, response.getFirstName());
        assertEquals(lastName, response.getLastName());
        assertEquals(email, response.getEmail());
        assertEquals(phoneNumber, response.getPhoneNumber());
        assertEquals(dateOfBirth, response.getDateOfBirth());
        assertEquals(address, response.getAddress());
        assertEquals(specialty, response.getSpecialty());
        assertEquals(1, response.getQualifications().size());
        
        MedicalStaffResponse.QualificationResponse qualificationResponse = response.getQualifications().get(0);
        assertEquals(degree, qualificationResponse.getDegree());
        assertEquals(institution, qualificationResponse.getInstitution());
        assertEquals(dateObtained, qualificationResponse.getDateObtained());
        assertEquals(licenseNumber, qualificationResponse.getLicenseNumber());
        assertEquals(licenseExpiryDate, qualificationResponse.getLicenseExpiryDate());
        assertFalse(qualificationResponse.isExpired());
        
        assertTrue(response.isActive());
        assertEquals(hireDate, response.getHireDate());
        assertNull(response.getTerminationDate());
    }

    @Test
    void testHandleGetMedicalStaffByIdQueryWithNonExistentStaff() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String staffIdStr = uuid.toString();
        
        when(medicalStaffRepository.findById(any(StaffId.class))).thenReturn(Optional.empty());
        
        GetMedicalStaffByIdQuery query = new GetMedicalStaffByIdQuery(staffIdStr);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(query);
        });
        
        assertEquals("Medical staff not found with ID: " + staffIdStr, exception.getMessage());
    }
}