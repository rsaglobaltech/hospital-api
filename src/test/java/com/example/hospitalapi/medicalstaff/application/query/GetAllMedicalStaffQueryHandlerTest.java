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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllMedicalStaffQueryHandlerTest {

    @Mock
    private MedicalStaffRepository medicalStaffRepository;

    private GetAllMedicalStaffQueryHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GetAllMedicalStaffQueryHandler(medicalStaffRepository);
    }

    @Test
    void testHandleGetAllMedicalStaffQuery() {
        // Arrange
        // Create first medical staff
        UUID uuid1 = UUID.randomUUID();
        StaffId staffId1 = new StaffId(uuid1);
        String firstName1 = "John";
        String lastName1 = "Doe";
        String email1 = "john.doe@hospital.com";
        String phoneNumber1 = "+1234567890";
        DateOfBirth dateOfBirth1 = new DateOfBirth(LocalDate.of(1980, 1, 1));
        Address address1 = new Address("123 Main St, City, Country");
        Specialty specialty1 = Specialty.CARDIOLOGY;
        HireDate hireDate1 = new HireDate(LocalDate.of(2010, 1, 15));
        
        Qualification qualification1 = mock(Qualification.class);
        when(qualification1.getDegree()).thenReturn("MD");
        when(qualification1.getInstitution()).thenReturn("Harvard Medical School");
        when(qualification1.getDateObtained()).thenReturn(LocalDate.of(2005, 5, 15));
        when(qualification1.getLicenseNumber()).thenReturn("ML123456");
        when(qualification1.getLicenseExpiryDate()).thenReturn(LocalDate.now().plusYears(2));
        when(qualification1.isExpired()).thenReturn(false);
        
        List<Qualification> qualifications1 = new ArrayList<>();
        qualifications1.add(qualification1);
        
        MedicalStaff medicalStaff1 = mock(MedicalStaff.class);
        when(medicalStaff1.getId()).thenReturn(staffId1);
        when(medicalStaff1.getName()).thenReturn(new Name(firstName1, lastName1));
        when(medicalStaff1.getEmail()).thenReturn(new Email(email1));
        when(medicalStaff1.getPhoneNumber()).thenReturn(new PhoneNumber(phoneNumber1));
        when(medicalStaff1.getDateOfBirth()).thenReturn(dateOfBirth1);
        when(medicalStaff1.getAddress()).thenReturn(address1);
        when(medicalStaff1.getSpecialty()).thenReturn(specialty1);
        when(medicalStaff1.getQualifications()).thenReturn(qualifications1);
        when(medicalStaff1.isActive()).thenReturn(true);
        when(medicalStaff1.getHireDate()).thenReturn(hireDate1);
        when(medicalStaff1.getTerminationDate()).thenReturn(null);
        
        // Create second medical staff
        UUID uuid2 = UUID.randomUUID();
        StaffId staffId2 = new StaffId(uuid2);
        String firstName2 = "Jane";
        String lastName2 = "Smith";
        String email2 = "jane.smith@hospital.com";
        String phoneNumber2 = "+9876543210";
        DateOfBirth dateOfBirth2 = new DateOfBirth(LocalDate.of(1985, 5, 10));
        Address address2 = new Address("456 Oak St, Town, Country");
        Specialty specialty2 = Specialty.NEUROLOGY;
        HireDate hireDate2 = new HireDate(LocalDate.of(2015, 3, 20));
        
        Qualification qualification2 = mock(Qualification.class);
        when(qualification2.getDegree()).thenReturn("PhD");
        when(qualification2.getInstitution()).thenReturn("Stanford University");
        when(qualification2.getDateObtained()).thenReturn(LocalDate.of(2010, 6, 20));
        when(qualification2.getLicenseNumber()).thenReturn("PL789012");
        when(qualification2.getLicenseExpiryDate()).thenReturn(LocalDate.now().plusYears(3));
        when(qualification2.isExpired()).thenReturn(false);
        
        List<Qualification> qualifications2 = new ArrayList<>();
        qualifications2.add(qualification2);
        
        MedicalStaff medicalStaff2 = mock(MedicalStaff.class);
        when(medicalStaff2.getId()).thenReturn(staffId2);
        when(medicalStaff2.getName()).thenReturn(new Name(firstName2, lastName2));
        when(medicalStaff2.getEmail()).thenReturn(new Email(email2));
        when(medicalStaff2.getPhoneNumber()).thenReturn(new PhoneNumber(phoneNumber2));
        when(medicalStaff2.getDateOfBirth()).thenReturn(dateOfBirth2);
        when(medicalStaff2.getAddress()).thenReturn(address2);
        when(medicalStaff2.getSpecialty()).thenReturn(specialty2);
        when(medicalStaff2.getQualifications()).thenReturn(qualifications2);
        when(medicalStaff2.isActive()).thenReturn(true);
        when(medicalStaff2.getHireDate()).thenReturn(hireDate2);
        when(medicalStaff2.getTerminationDate()).thenReturn(null);
        
        List<MedicalStaff> medicalStaffList = Arrays.asList(medicalStaff1, medicalStaff2);
        when(medicalStaffRepository.findAll()).thenReturn(medicalStaffList);
        
        GetAllMedicalStaffQuery query = new GetAllMedicalStaffQuery();
        
        // Act
        List<MedicalStaffResponse> responseList = handler.handle(query);
        
        // Assert
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        
        // Verify first medical staff
        MedicalStaffResponse response1 = responseList.get(0);
        assertEquals(staffId1.toString(), response1.getId());
        assertEquals(firstName1, response1.getFirstName());
        assertEquals(lastName1, response1.getLastName());
        assertEquals(email1, response1.getEmail());
        assertEquals(phoneNumber1, response1.getPhoneNumber());
        assertEquals(dateOfBirth1, response1.getDateOfBirth());
        assertEquals(address1, response1.getAddress());
        assertEquals(specialty1, response1.getSpecialty());
        assertEquals(1, response1.getQualifications().size());
        assertTrue(response1.isActive());
        assertEquals(hireDate1, response1.getHireDate());
        assertNull(response1.getTerminationDate());
        
        // Verify second medical staff
        MedicalStaffResponse response2 = responseList.get(1);
        assertEquals(staffId2.toString(), response2.getId());
        assertEquals(firstName2, response2.getFirstName());
        assertEquals(lastName2, response2.getLastName());
        assertEquals(email2, response2.getEmail());
        assertEquals(phoneNumber2, response2.getPhoneNumber());
        assertEquals(dateOfBirth2, response2.getDateOfBirth());
        assertEquals(address2, response2.getAddress());
        assertEquals(specialty2, response2.getSpecialty());
        assertEquals(1, response2.getQualifications().size());
        assertTrue(response2.isActive());
        assertEquals(hireDate2, response2.getHireDate());
        assertNull(response2.getTerminationDate());
    }

    @Test
    void testHandleGetAllMedicalStaffQueryWithEmptyList() {
        // Arrange
        when(medicalStaffRepository.findAll()).thenReturn(new ArrayList<>());
        
        GetAllMedicalStaffQuery query = new GetAllMedicalStaffQuery();
        
        // Act
        List<MedicalStaffResponse> responseList = handler.handle(query);
        
        // Assert
        assertNotNull(responseList);
        assertTrue(responseList.isEmpty());
    }
}