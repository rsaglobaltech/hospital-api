package com.example.hospitalapi.medicalstaff.infrastructure.persistence.adapter;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.valueobject.HireDate;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Qualification;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.medicalstaff.infrastructure.persistence.entity.MedicalStaffJpaEntity;
import com.example.hospitalapi.medicalstaff.infrastructure.persistence.entity.QualificationJpaEntity;
import com.example.hospitalapi.medicalstaff.infrastructure.persistence.repository.MedicalStaffJpaRepository;
import com.example.hospitalapi.patient.domain.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MedicalStaffRepositoryAdapterTest {

    @Mock
    private MedicalStaffJpaRepository medicalStaffJpaRepository;

    private MedicalStaffRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new MedicalStaffRepositoryAdapter(medicalStaffJpaRepository);
    }

    @Test
    void testSave() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        StaffId staffId = new StaffId(uuid);
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

        // Create JPA entity for qualification
        QualificationJpaEntity qualificationJpaEntity = new QualificationJpaEntity();
        qualificationJpaEntity.setId(UUID.randomUUID().toString());
        qualificationJpaEntity.setDegree(degree);
        qualificationJpaEntity.setInstitution(institution);
        qualificationJpaEntity.setDateObtained(dateObtained);
        qualificationJpaEntity.setLicenseNumber(licenseNumber);
        qualificationJpaEntity.setLicenseExpiryDate(licenseExpiryDate);
        qualificationJpaEntity.setStaffId(uuid.toString());

        List<QualificationJpaEntity> qualificationJpaEntities = new ArrayList<>();
        qualificationJpaEntities.add(qualificationJpaEntity);

        // Create JPA entity for medical staff
        MedicalStaffJpaEntity medicalStaffJpaEntity = new MedicalStaffJpaEntity();
        medicalStaffJpaEntity.setId(uuid.toString());
        medicalStaffJpaEntity.setFirstName(firstName);
        medicalStaffJpaEntity.setLastName(lastName);
        medicalStaffJpaEntity.setEmail(email);
        medicalStaffJpaEntity.setPhoneNumber(phoneNumber);
        medicalStaffJpaEntity.setDateOfBirth(dateOfBirth);
        medicalStaffJpaEntity.setAddress(address);
        medicalStaffJpaEntity.setSpecialty(specialty);
        medicalStaffJpaEntity.setQualifications(qualificationJpaEntities);
        medicalStaffJpaEntity.setActive(true);
        medicalStaffJpaEntity.setHireDate(hireDate);
        medicalStaffJpaEntity.setTerminationDate(null);

        when(medicalStaffJpaRepository.save(any(MedicalStaffJpaEntity.class))).thenReturn(medicalStaffJpaEntity);

        // Act
        MedicalStaff result = adapter.save(medicalStaff);

        // Assert
        assertNotNull(result);

        // Verify that save was called
        ArgumentCaptor<MedicalStaffJpaEntity> entityCaptor = ArgumentCaptor.forClass(MedicalStaffJpaEntity.class);
        verify(medicalStaffJpaRepository).save(entityCaptor.capture());

        MedicalStaffJpaEntity capturedEntity = entityCaptor.getValue();
        assertEquals(uuid.toString(), capturedEntity.getId());
    }

    @Test
    void testFindById() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        StaffId staffId = new StaffId(uuid);

        // Create JPA entity for qualification
        QualificationJpaEntity qualificationJpaEntity = new QualificationJpaEntity();
        qualificationJpaEntity.setId(UUID.randomUUID().toString());
        qualificationJpaEntity.setDegree("MD");
        qualificationJpaEntity.setInstitution("Harvard Medical School");
        qualificationJpaEntity.setDateObtained(LocalDate.of(2005, 5, 15));
        qualificationJpaEntity.setLicenseNumber("ML123456");
        qualificationJpaEntity.setLicenseExpiryDate(LocalDate.now().plusYears(2));
        qualificationJpaEntity.setStaffId(uuid.toString());

        List<QualificationJpaEntity> qualificationJpaEntities = new ArrayList<>();
        qualificationJpaEntities.add(qualificationJpaEntity);

        // Create JPA entity for medical staff
        MedicalStaffJpaEntity medicalStaffJpaEntity = new MedicalStaffJpaEntity();
        medicalStaffJpaEntity.setId(uuid.toString());
        medicalStaffJpaEntity.setFirstName("John");
        medicalStaffJpaEntity.setLastName("Doe");
        medicalStaffJpaEntity.setEmail("john.doe@hospital.com");
        medicalStaffJpaEntity.setPhoneNumber("+1234567890");
        medicalStaffJpaEntity.setDateOfBirth(LocalDate.of(1980, 1, 1));
        medicalStaffJpaEntity.setAddress("123 Main St, City, Country");
        medicalStaffJpaEntity.setSpecialty(Specialty.CARDIOLOGY);
        medicalStaffJpaEntity.setQualifications(qualificationJpaEntities);
        medicalStaffJpaEntity.setActive(true);
        medicalStaffJpaEntity.setHireDate(LocalDate.of(2010, 1, 15));
        medicalStaffJpaEntity.setTerminationDate(null);

        when(medicalStaffJpaRepository.findById(uuid.toString())).thenReturn(Optional.of(medicalStaffJpaEntity));

        // Act
        Optional<MedicalStaff> result = adapter.findById(staffId);

        // Assert
        assertTrue(result.isPresent());
        MedicalStaff medicalStaff = result.get();
        assertEquals(staffId.toString(), medicalStaff.getId().toString());
        assertEquals("John", medicalStaff.getName().getFirstName());
        assertEquals("Doe", medicalStaff.getName().getLastName());
        assertEquals("john.doe@hospital.com", medicalStaff.getEmail().toString());
        assertEquals("+1234567890", medicalStaff.getPhoneNumber().toString());
        assertEquals(LocalDate.of(1980, 1, 1), medicalStaff.getDateOfBirth());
        assertEquals("123 Main St, City, Country", medicalStaff.getAddress());
        assertEquals(Specialty.CARDIOLOGY, medicalStaff.getSpecialty());
        assertEquals(1, medicalStaff.getQualifications().size());
        assertTrue(medicalStaff.isActive());
        assertEquals(LocalDate.of(2010, 1, 15), medicalStaff.getHireDate());
        assertNull(medicalStaff.getTerminationDate());
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        StaffId staffId = new StaffId(uuid);

        when(medicalStaffJpaRepository.findById(uuid.toString())).thenReturn(Optional.empty());

        // Act
        Optional<MedicalStaff> result = adapter.findById(staffId);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        // Arrange
        // Create first JPA entity
        UUID uuid1 = UUID.randomUUID();

        QualificationJpaEntity qualificationJpaEntity1 = new QualificationJpaEntity();
        qualificationJpaEntity1.setId(UUID.randomUUID().toString());
        qualificationJpaEntity1.setDegree("MD");
        qualificationJpaEntity1.setInstitution("Harvard Medical School");
        qualificationJpaEntity1.setDateObtained(LocalDate.of(2005, 5, 15));
        qualificationJpaEntity1.setLicenseNumber("ML123456");
        qualificationJpaEntity1.setLicenseExpiryDate(LocalDate.now().plusYears(2));
        qualificationJpaEntity1.setStaffId(uuid1.toString());

        List<QualificationJpaEntity> qualificationJpaEntities1 = new ArrayList<>();
        qualificationJpaEntities1.add(qualificationJpaEntity1);

        MedicalStaffJpaEntity medicalStaffJpaEntity1 = new MedicalStaffJpaEntity();
        medicalStaffJpaEntity1.setId(uuid1.toString());
        medicalStaffJpaEntity1.setFirstName("John");
        medicalStaffJpaEntity1.setLastName("Doe");
        medicalStaffJpaEntity1.setEmail("john.doe@hospital.com");
        medicalStaffJpaEntity1.setPhoneNumber("+1234567890");
        medicalStaffJpaEntity1.setDateOfBirth(LocalDate.of(1980, 1, 1));
        medicalStaffJpaEntity1.setAddress("123 Main St, City, Country");
        medicalStaffJpaEntity1.setSpecialty(Specialty.CARDIOLOGY);
        medicalStaffJpaEntity1.setQualifications(qualificationJpaEntities1);
        medicalStaffJpaEntity1.setActive(true);
        medicalStaffJpaEntity1.setHireDate(LocalDate.of(2010, 1, 15));
        medicalStaffJpaEntity1.setTerminationDate(null);

        // Create second JPA entity
        UUID uuid2 = UUID.randomUUID();

        QualificationJpaEntity qualificationJpaEntity2 = new QualificationJpaEntity();
        qualificationJpaEntity2.setId(UUID.randomUUID().toString());
        qualificationJpaEntity2.setDegree("PhD");
        qualificationJpaEntity2.setInstitution("Stanford University");
        qualificationJpaEntity2.setDateObtained(LocalDate.of(2010, 6, 20));
        qualificationJpaEntity2.setLicenseNumber("PL789012");
        qualificationJpaEntity2.setLicenseExpiryDate(LocalDate.now().plusYears(3));
        qualificationJpaEntity2.setStaffId(uuid2.toString());

        List<QualificationJpaEntity> qualificationJpaEntities2 = new ArrayList<>();
        qualificationJpaEntities2.add(qualificationJpaEntity2);

        MedicalStaffJpaEntity medicalStaffJpaEntity2 = new MedicalStaffJpaEntity();
        medicalStaffJpaEntity2.setId(uuid2.toString());
        medicalStaffJpaEntity2.setFirstName("Jane");
        medicalStaffJpaEntity2.setLastName("Smith");
        medicalStaffJpaEntity2.setEmail("jane.smith@hospital.com");
        medicalStaffJpaEntity2.setPhoneNumber("+9876543210");
        medicalStaffJpaEntity2.setDateOfBirth(LocalDate.of(1985, 5, 10));
        medicalStaffJpaEntity2.setAddress("456 Oak St, Town, Country");
        medicalStaffJpaEntity2.setSpecialty(Specialty.NEUROLOGY);
        medicalStaffJpaEntity2.setQualifications(qualificationJpaEntities2);
        medicalStaffJpaEntity2.setActive(true);
        medicalStaffJpaEntity2.setHireDate(LocalDate.of(2015, 3, 20));
        medicalStaffJpaEntity2.setTerminationDate(null);

        List<MedicalStaffJpaEntity> jpaEntities = Arrays.asList(medicalStaffJpaEntity1, medicalStaffJpaEntity2);
        when(medicalStaffJpaRepository.findAll()).thenReturn(jpaEntities);

        // Act
        List<MedicalStaff> result = adapter.findAll();

        // Assert
        assertEquals(2, result.size());

        // Verify first medical staff
        MedicalStaff medicalStaff1 = result.get(0);
        assertEquals(uuid1.toString(), medicalStaff1.getId().toString());
        assertEquals("John", medicalStaff1.getName().getFirstName());
        assertEquals(Specialty.CARDIOLOGY, medicalStaff1.getSpecialty());

        // Verify second medical staff
        MedicalStaff medicalStaff2 = result.get(1);
        assertEquals(uuid2.toString(), medicalStaff2.getId().toString());
        assertEquals("Jane", medicalStaff2.getName().getFirstName());
        assertEquals(Specialty.NEUROLOGY, medicalStaff2.getSpecialty());
    }

    @Test
    void testFindBySpecialty() {
        // Arrange
        Specialty specialty = Specialty.CARDIOLOGY;

        // Create JPA entity
        UUID uuid = UUID.randomUUID();

        QualificationJpaEntity qualificationJpaEntity = new QualificationJpaEntity();
        qualificationJpaEntity.setId(UUID.randomUUID().toString());
        qualificationJpaEntity.setDegree("MD");
        qualificationJpaEntity.setInstitution("Harvard Medical School");
        qualificationJpaEntity.setDateObtained(LocalDate.of(2005, 5, 15));
        qualificationJpaEntity.setLicenseNumber("ML123456");
        qualificationJpaEntity.setLicenseExpiryDate(LocalDate.now().plusYears(2));
        qualificationJpaEntity.setStaffId(uuid.toString());

        List<QualificationJpaEntity> qualificationJpaEntities = new ArrayList<>();
        qualificationJpaEntities.add(qualificationJpaEntity);

        MedicalStaffJpaEntity medicalStaffJpaEntity = new MedicalStaffJpaEntity();
        medicalStaffJpaEntity.setId(uuid.toString());
        medicalStaffJpaEntity.setFirstName("John");
        medicalStaffJpaEntity.setLastName("Doe");
        medicalStaffJpaEntity.setEmail("john.doe@hospital.com");
        medicalStaffJpaEntity.setPhoneNumber("+1234567890");
        medicalStaffJpaEntity.setDateOfBirth(LocalDate.of(1980, 1, 1));
        medicalStaffJpaEntity.setAddress("123 Main St, City, Country");
        medicalStaffJpaEntity.setSpecialty(specialty);
        medicalStaffJpaEntity.setQualifications(qualificationJpaEntities);
        medicalStaffJpaEntity.setActive(true);
        medicalStaffJpaEntity.setHireDate(LocalDate.of(2010, 1, 15));
        medicalStaffJpaEntity.setTerminationDate(null);

        List<MedicalStaffJpaEntity> jpaEntities = Arrays.asList(medicalStaffJpaEntity);
        when(medicalStaffJpaRepository.findBySpecialty(specialty)).thenReturn(jpaEntities);

        // Act
        List<MedicalStaff> result = adapter.findBySpecialty(specialty);

        // Assert
        assertEquals(1, result.size());

        MedicalStaff medicalStaff = result.get(0);
        assertEquals(uuid.toString(), medicalStaff.getId().toString());
        assertEquals("John", medicalStaff.getName().getFirstName());
        assertEquals(specialty, medicalStaff.getSpecialty());
    }

    @Test
    void testDeleteById() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        StaffId staffId = new StaffId(uuid);

        // Act
        adapter.deleteById(staffId);

        // Assert
        verify(medicalStaffJpaRepository).deleteById(uuid.toString());
    }

    @Test
    void testExistsById() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        StaffId staffId = new StaffId(uuid);

        when(medicalStaffJpaRepository.existsById(uuid.toString())).thenReturn(true);

        // Act
        boolean result = adapter.existsById(staffId);

        // Assert
        assertTrue(result);
        verify(medicalStaffJpaRepository).existsById(uuid.toString());
    }
}
