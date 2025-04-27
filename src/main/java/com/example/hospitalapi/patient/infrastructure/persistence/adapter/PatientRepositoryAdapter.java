package com.example.hospitalapi.patient.infrastructure.persistence.adapter;

import com.example.hospitalapi.patient.domain.entity.Patient;
import com.example.hospitalapi.patient.domain.repository.PatientRepository;
import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import com.example.hospitalapi.patient.infrastructure.persistence.entity.PatientJpaEntity;
import com.example.hospitalapi.patient.infrastructure.persistence.repository.PatientJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementing PatientRepository using JPA
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PatientRepositoryAdapter implements PatientRepository {

    private final PatientJpaRepository patientJpaRepository;

    @Override
    public Patient save(Patient patient) {
        log.debug("Saving patient with ID: {}", patient.getId());
        try {
            PatientJpaEntity patientJpaEntity = mapToJpaEntity(patient);
            PatientJpaEntity savedEntity = patientJpaRepository.save(patientJpaEntity);
            log.debug("Patient saved successfully with ID: {}", patient.getId());
            return mapToDomainEntity(savedEntity);
        } catch (Exception e) {
            log.error("Error saving patient with ID {}: {}", patient.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Patient> findById(PatientId id) {
        log.debug("Finding patient by ID: {}", id);
        try {
            Optional<Patient> result = patientJpaRepository.findById(id.toString())
                    .map(this::mapToDomainEntity);

            if (result.isPresent()) {
                log.debug("Patient found with ID: {}", id);
            } else {
                log.debug("Patient not found with ID: {}", id);
            }

            return result;
        } catch (Exception e) {
            log.error("Error finding patient with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Patient> findAll() {
        log.debug("Finding all patients");
        try {
            List<Patient> patients = patientJpaRepository.findAll().stream()
                    .map(this::mapToDomainEntity)
                    .collect(Collectors.toList());

            log.debug("Found {} patients", patients.size());
            return patients;
        } catch (Exception e) {
            log.error("Error finding all patients: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteById(PatientId id) {
        log.debug("Deleting patient with ID: {}", id);
        try {
            patientJpaRepository.deleteById(id.toString());
            log.debug("Patient deleted successfully with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting patient with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(PatientId id) {
        log.debug("Checking if patient exists with ID: {}", id);
        try {
            boolean exists = patientJpaRepository.existsById(id.toString());
            log.debug("Patient exists with ID {}: {}", id, exists);
            return exists;
        } catch (Exception e) {
            log.error("Error checking if patient exists with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    private PatientJpaEntity mapToJpaEntity(Patient patient) {
        log.trace("Mapping domain entity to JPA entity for patient ID: {}", patient.getId());
        return PatientJpaEntity.builder()
                .id(patient.getId().toString())
                .firstName(patient.getName().getFirstName())
                .lastName(patient.getName().getLastName())
                .email(patient.getEmail().toString())
                .phoneNumber(patient.getPhoneNumber().toString())
                .dateOfBirth(patient.getDateOfBirth().getValue())
                .address(patient.getAddress().getValue())
                .medicalHistory(patient.getMedicalHistory().getValue())
                .build();
    }

    private Patient mapToDomainEntity(PatientJpaEntity patientJpaEntity) {
        log.trace("Mapping JPA entity to domain entity for patient ID: {}", patientJpaEntity.getId());
        PatientId patientId = new PatientId(UUID.fromString(patientJpaEntity.getId()));
        Name name = new Name(patientJpaEntity.getFirstName(), patientJpaEntity.getLastName());
        Email email = new Email(patientJpaEntity.getEmail());
        PhoneNumber phoneNumber = new PhoneNumber(patientJpaEntity.getPhoneNumber());

        Patient patient = new Patient(
                patientId,
                name,
                email,
                phoneNumber,
                patientJpaEntity.getDateOfBirth(),
                patientJpaEntity.getAddress()
        );

        patient.updateMedicalHistory(patientJpaEntity.getMedicalHistory());

        return patient;
    }
}
