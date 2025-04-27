package com.example.hospitalapi.pharmacy.infrastructure.persistence.adapter;

import com.example.hospitalapi.pharmacy.domain.entity.Dispensation;
import com.example.hospitalapi.pharmacy.domain.repository.DispensationRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.DispensationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.DispensationItemJpaEntity;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.DispensationJpaEntity;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.repository.DispensationItemJpaRepository;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.repository.DispensationJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementing DispensationRepository using JPA
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DispensationRepositoryAdapter implements DispensationRepository {

    private final DispensationJpaRepository dispensationJpaRepository;
    private final DispensationItemJpaRepository dispensationItemJpaRepository;

    @Override
    @Transactional
    public Dispensation save(Dispensation dispensation) {
        log.debug("Saving dispensation with ID: {}", dispensation.getId());
        try {
            // Save dispensation
            DispensationJpaEntity dispensationJpaEntity = mapToJpaEntity(dispensation);
            DispensationJpaEntity savedDispensationEntity = dispensationJpaRepository.save(dispensationJpaEntity);
            
            // Delete existing items (if any) to handle updates
            dispensationItemJpaRepository.deleteByDispensationId(dispensation.getId().toString());
            
            // Save dispensation items
            List<DispensationItemJpaEntity> itemEntities = mapToJpaItemEntities(dispensation);
            dispensationItemJpaRepository.saveAll(itemEntities);
            
            log.debug("Dispensation saved successfully with ID: {}", dispensation.getId());
            
            // Retrieve the saved dispensation with its items
            return findById(dispensation.getId()).orElse(dispensation);
        } catch (Exception e) {
            log.error("Error saving dispensation with ID {}: {}", dispensation.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Dispensation> findById(DispensationId id) {
        log.debug("Finding dispensation by ID: {}", id);
        try {
            Optional<DispensationJpaEntity> dispensationEntityOptional = dispensationJpaRepository.findById(id.toString());
            
            if (dispensationEntityOptional.isPresent()) {
                DispensationJpaEntity dispensationEntity = dispensationEntityOptional.get();
                List<DispensationItemJpaEntity> itemEntities = dispensationItemJpaRepository.findByDispensationId(id.toString());
                
                Dispensation dispensation = mapToDomainEntity(dispensationEntity, itemEntities);
                log.debug("Dispensation found with ID: {}", id);
                return Optional.of(dispensation);
            } else {
                log.debug("Dispensation not found with ID: {}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error finding dispensation with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Dispensation> findAll() {
        log.debug("Finding all dispensations");
        try {
            List<DispensationJpaEntity> dispensationEntities = dispensationJpaRepository.findAll();
            List<Dispensation> dispensations = new ArrayList<>();
            
            for (DispensationJpaEntity dispensationEntity : dispensationEntities) {
                List<DispensationItemJpaEntity> itemEntities = 
                        dispensationItemJpaRepository.findByDispensationId(dispensationEntity.getId());
                dispensations.add(mapToDomainEntity(dispensationEntity, itemEntities));
            }
            
            log.debug("Found {} dispensations", dispensations.size());
            return dispensations;
        } catch (Exception e) {
            log.error("Error finding all dispensations: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteById(DispensationId id) {
        log.debug("Deleting dispensation with ID: {}", id);
        try {
            // Delete dispensation items first
            dispensationItemJpaRepository.deleteByDispensationId(id.toString());
            
            // Delete dispensation
            dispensationJpaRepository.deleteById(id.toString());
            log.debug("Dispensation deleted successfully with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting dispensation with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(DispensationId id) {
        log.debug("Checking if dispensation exists with ID: {}", id);
        try {
            boolean exists = dispensationJpaRepository.existsById(id.toString());
            log.debug("Dispensation exists with ID {}: {}", id, exists);
            return exists;
        } catch (Exception e) {
            log.error("Error checking if dispensation exists with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Dispensation> findByPatientId(String patientId) {
        log.debug("Finding dispensations by patient ID: {}", patientId);
        try {
            List<DispensationJpaEntity> dispensationEntities = dispensationJpaRepository.findByPatientId(patientId);
            List<Dispensation> dispensations = new ArrayList<>();
            
            for (DispensationJpaEntity dispensationEntity : dispensationEntities) {
                List<DispensationItemJpaEntity> itemEntities = 
                        dispensationItemJpaRepository.findByDispensationId(dispensationEntity.getId());
                dispensations.add(mapToDomainEntity(dispensationEntity, itemEntities));
            }
            
            log.debug("Found {} dispensations for patient ID: {}", dispensations.size(), patientId);
            return dispensations;
        } catch (Exception e) {
            log.error("Error finding dispensations by patient ID {}: {}", patientId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Dispensation> findByPrescriptionId(PrescriptionId prescriptionId) {
        log.debug("Finding dispensations by prescription ID: {}", prescriptionId);
        try {
            List<DispensationJpaEntity> dispensationEntities = 
                    dispensationJpaRepository.findByPrescriptionId(prescriptionId.toString());
            List<Dispensation> dispensations = new ArrayList<>();
            
            for (DispensationJpaEntity dispensationEntity : dispensationEntities) {
                List<DispensationItemJpaEntity> itemEntities = 
                        dispensationItemJpaRepository.findByDispensationId(dispensationEntity.getId());
                dispensations.add(mapToDomainEntity(dispensationEntity, itemEntities));
            }
            
            log.debug("Found {} dispensations for prescription ID: {}", dispensations.size(), prescriptionId);
            return dispensations;
        } catch (Exception e) {
            log.error("Error finding dispensations by prescription ID {}: {}", prescriptionId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Dispensation> findByPharmacistId(String pharmacistId) {
        log.debug("Finding dispensations by pharmacist ID: {}", pharmacistId);
        try {
            List<DispensationJpaEntity> dispensationEntities = 
                    dispensationJpaRepository.findByPharmacistId(pharmacistId);
            List<Dispensation> dispensations = new ArrayList<>();
            
            for (DispensationJpaEntity dispensationEntity : dispensationEntities) {
                List<DispensationItemJpaEntity> itemEntities = 
                        dispensationItemJpaRepository.findByDispensationId(dispensationEntity.getId());
                dispensations.add(mapToDomainEntity(dispensationEntity, itemEntities));
            }
            
            log.debug("Found {} dispensations for pharmacist ID: {}", dispensations.size(), pharmacistId);
            return dispensations;
        } catch (Exception e) {
            log.error("Error finding dispensations by pharmacist ID {}: {}", pharmacistId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Dispensation> findByDispensationDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Finding dispensations between {} and {}", startDate, endDate);
        try {
            List<DispensationJpaEntity> dispensationEntities = 
                    dispensationJpaRepository.findByDispensationDateTimeBetween(startDate, endDate);
            List<Dispensation> dispensations = new ArrayList<>();
            
            for (DispensationJpaEntity dispensationEntity : dispensationEntities) {
                List<DispensationItemJpaEntity> itemEntities = 
                        dispensationItemJpaRepository.findByDispensationId(dispensationEntity.getId());
                dispensations.add(mapToDomainEntity(dispensationEntity, itemEntities));
            }
            
            log.debug("Found {} dispensations between {} and {}", dispensations.size(), startDate, endDate);
            return dispensations;
        } catch (Exception e) {
            log.error("Error finding dispensations between {} and {}: {}", startDate, endDate, e.getMessage(), e);
            throw e;
        }
    }

    private DispensationJpaEntity mapToJpaEntity(Dispensation dispensation) {
        log.trace("Mapping domain entity to JPA entity for dispensation ID: {}", dispensation.getId());
        return DispensationJpaEntity.builder()
                .id(dispensation.getId().toString())
                .prescriptionId(dispensation.getPrescriptionId().toString())
                .patientId(dispensation.getPatientId().getValue().toString())
                .pharmacistId(dispensation.getPharmacistId().getValue().toString())
                .dispensationDateTime(dispensation.getDispensationDateTime().getValue())
                .totalAmount(dispensation.getTotalAmount().getValue())
                .notes(dispensation.getNotes().getValue())
                .build();
    }

    private List<DispensationItemJpaEntity> mapToJpaItemEntities(Dispensation dispensation) {
        log.trace("Mapping domain items to JPA entities for dispensation ID: {}", dispensation.getId());
        return dispensation.getItems().stream()
                .map(item -> DispensationItemJpaEntity.builder()
                        .dispensationId(dispensation.getId().toString())
                        .medicationId(item.getMedicationId().toString())
                        .medicationName(item.getMedicationName().getValue())
                        .quantity(item.getQuantity().getValue())
                        .unitPrice(item.getUnitPrice().getValue())
                        .batchNumber(item.getBatchNumber().getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private Dispensation mapToDomainEntity(DispensationJpaEntity dispensationEntity, 
                                          List<DispensationItemJpaEntity> itemEntities) {
        log.trace("Mapping JPA entity to domain entity for dispensation ID: {}", dispensationEntity.getId());
        DispensationId dispensationId = new DispensationId(UUID.fromString(dispensationEntity.getId()));
        PrescriptionId prescriptionId = new PrescriptionId(UUID.fromString(dispensationEntity.getPrescriptionId()));
        
        List<Dispensation.DispensationItem> items = itemEntities.stream()
                .map(itemEntity -> new Dispensation.DispensationItem(
                        new MedicationId(UUID.fromString(itemEntity.getMedicationId())),
                        itemEntity.getMedicationName(),
                        itemEntity.getQuantity(),
                        itemEntity.getUnitPrice(),
                        itemEntity.getBatchNumber()))
                .collect(Collectors.toList());
        
        return new Dispensation(
                dispensationId,
                prescriptionId,
                dispensationEntity.getPatientId(),
                dispensationEntity.getPharmacistId(),
                items,
                dispensationEntity.getNotes()
        );
    }
}