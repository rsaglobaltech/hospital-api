package com.example.hospitalapi.pharmacy.infrastructure.persistence.adapter;

import com.example.hospitalapi.pharmacy.domain.entity.Medication;
import com.example.hospitalapi.pharmacy.domain.repository.MedicationRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.MedicationJpaEntity;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.repository.MedicationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementing MedicationRepository using JPA
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MedicationRepositoryAdapter implements MedicationRepository {

    private final MedicationJpaRepository medicationJpaRepository;

    @Override
    public Medication save(Medication medication) {
        log.debug("Saving medication with ID: {}", medication.getId());
        try {
            MedicationJpaEntity medicationJpaEntity = mapToJpaEntity(medication);
            MedicationJpaEntity savedEntity = medicationJpaRepository.save(medicationJpaEntity);
            log.debug("Medication saved successfully with ID: {}", medication.getId());
            return mapToDomainEntity(savedEntity);
        } catch (Exception e) {
            log.error("Error saving medication with ID {}: {}", medication.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Medication> findById(MedicationId id) {
        log.debug("Finding medication by ID: {}", id);
        try {
            Optional<Medication> result = medicationJpaRepository.findById(id.toString())
                    .map(this::mapToDomainEntity);

            if (result.isPresent()) {
                log.debug("Medication found with ID: {}", id);
            } else {
                log.debug("Medication not found with ID: {}", id);
            }

            return result;
        } catch (Exception e) {
            log.error("Error finding medication with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Medication> findAll() {
        log.debug("Finding all medications");
        try {
            List<Medication> medications = medicationJpaRepository.findAll().stream()
                    .map(this::mapToDomainEntity)
                    .collect(Collectors.toList());

            log.debug("Found {} medications", medications.size());
            return medications;
        } catch (Exception e) {
            log.error("Error finding all medications: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteById(MedicationId id) {
        log.debug("Deleting medication with ID: {}", id);
        try {
            medicationJpaRepository.deleteById(id.toString());
            log.debug("Medication deleted successfully with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting medication with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(MedicationId id) {
        log.debug("Checking if medication exists with ID: {}", id);
        try {
            boolean exists = medicationJpaRepository.existsById(id.toString());
            log.debug("Medication exists with ID {}: {}", id, exists);
            return exists;
        } catch (Exception e) {
            log.error("Error checking if medication exists with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Medication> findByNameContaining(String name) {
        log.debug("Finding medications by name containing: {}", name);
        try {
            List<Medication> medications = medicationJpaRepository.findByNameContainingIgnoreCase(name).stream()
                    .map(this::mapToDomainEntity)
                    .collect(Collectors.toList());

            log.debug("Found {} medications with name containing: {}", medications.size(), name);
            return medications;
        } catch (Exception e) {
            log.error("Error finding medications by name containing {}: {}", name, e.getMessage(), e);
            throw e;
        }
    }

    private MedicationJpaEntity mapToJpaEntity(Medication medication) {
        log.trace("Mapping domain entity to JPA entity for medication ID: {}", medication.getId());
        return MedicationJpaEntity.builder()
                .id(medication.getId().toString())
                .name(medication.getName())
                .description(medication.getDescription())
                .dosageForm(medication.getDosageForm())
                .strength(medication.getStrength())
                .manufacturer(medication.getManufacturer())
                .price(medication.getPrice())
                .requiresPrescription(medication.isRequiresPrescription())
                .build();
    }

    private Medication mapToDomainEntity(MedicationJpaEntity medicationJpaEntity) {
        log.trace("Mapping JPA entity to domain entity for medication ID: {}", medicationJpaEntity.getId());
        MedicationId medicationId = new MedicationId(UUID.fromString(medicationJpaEntity.getId()));
        
        return new Medication(
                medicationId,
                medicationJpaEntity.getName(),
                medicationJpaEntity.getDescription(),
                medicationJpaEntity.getDosageForm(),
                medicationJpaEntity.getStrength(),
                medicationJpaEntity.getManufacturer(),
                medicationJpaEntity.getPrice(),
                medicationJpaEntity.isRequiresPrescription()
        );
    }
}