package com.example.hospitalapi.pharmacy.infrastructure.persistence.adapter;

import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.pharmacy.domain.entity.Prescription;
import com.example.hospitalapi.pharmacy.domain.repository.PrescriptionRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.domain.valueobject.PrescriptionId;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.PrescriptionItemJpaEntity;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.PrescriptionJpaEntity;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.repository.PrescriptionItemJpaRepository;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.repository.PrescriptionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementing PrescriptionRepository using JPA
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PrescriptionRepositoryAdapter implements PrescriptionRepository {

    private final PrescriptionJpaRepository prescriptionJpaRepository;
    private final PrescriptionItemJpaRepository prescriptionItemJpaRepository;

    @Override
    @Transactional
    public Prescription save(Prescription prescription) {
        log.debug("Saving prescription with ID: {}", prescription.getId());
        try {
            // Save prescription
            PrescriptionJpaEntity prescriptionJpaEntity = mapToJpaEntity(prescription);
            PrescriptionJpaEntity savedPrescriptionEntity = prescriptionJpaRepository.save(prescriptionJpaEntity);
            
            // Delete existing items (if any) to handle updates
            prescriptionItemJpaRepository.deleteByPrescriptionId(prescription.getId().toString());
            
            // Save prescription items
            List<PrescriptionItemJpaEntity> itemEntities = mapToJpaItemEntities(prescription);
            prescriptionItemJpaRepository.saveAll(itemEntities);
            
            log.debug("Prescription saved successfully with ID: {}", prescription.getId());
            
            // Retrieve the saved prescription with its items
            return findById(prescription.getId()).orElse(prescription);
        } catch (Exception e) {
            log.error("Error saving prescription with ID {}: {}", prescription.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Prescription> findById(PrescriptionId id) {
        log.debug("Finding prescription by ID: {}", id);
        try {
            Optional<PrescriptionJpaEntity> prescriptionEntityOptional = prescriptionJpaRepository.findById(id.toString());
            
            if (prescriptionEntityOptional.isPresent()) {
                PrescriptionJpaEntity prescriptionEntity = prescriptionEntityOptional.get();
                List<PrescriptionItemJpaEntity> itemEntities = prescriptionItemJpaRepository.findByPrescriptionId(id.toString());
                
                Prescription prescription = mapToDomainEntity(prescriptionEntity, itemEntities);
                log.debug("Prescription found with ID: {}", id);
                return Optional.of(prescription);
            } else {
                log.debug("Prescription not found with ID: {}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error finding prescription with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Prescription> findAll() {
        log.debug("Finding all prescriptions");
        try {
            List<PrescriptionJpaEntity> prescriptionEntities = prescriptionJpaRepository.findAll();
            List<Prescription> prescriptions = new ArrayList<>();
            
            for (PrescriptionJpaEntity prescriptionEntity : prescriptionEntities) {
                List<PrescriptionItemJpaEntity> itemEntities = 
                        prescriptionItemJpaRepository.findByPrescriptionId(prescriptionEntity.getId());
                prescriptions.add(mapToDomainEntity(prescriptionEntity, itemEntities));
            }
            
            log.debug("Found {} prescriptions", prescriptions.size());
            return prescriptions;
        } catch (Exception e) {
            log.error("Error finding all prescriptions: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteById(PrescriptionId id) {
        log.debug("Deleting prescription with ID: {}", id);
        try {
            // Delete prescription items first
            prescriptionItemJpaRepository.deleteByPrescriptionId(id.toString());
            
            // Delete prescription
            prescriptionJpaRepository.deleteById(id.toString());
            log.debug("Prescription deleted successfully with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting prescription with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(PrescriptionId id) {
        log.debug("Checking if prescription exists with ID: {}", id);
        try {
            boolean exists = prescriptionJpaRepository.existsById(id.toString());
            log.debug("Prescription exists with ID {}: {}", id, exists);
            return exists;
        } catch (Exception e) {
            log.error("Error checking if prescription exists with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Prescription> findByPatientId(String patientId) {
        log.debug("Finding prescriptions by patient ID: {}", patientId);
        try {
            List<PrescriptionJpaEntity> prescriptionEntities = prescriptionJpaRepository.findByPatientId(patientId);
            List<Prescription> prescriptions = new ArrayList<>();
            
            for (PrescriptionJpaEntity prescriptionEntity : prescriptionEntities) {
                List<PrescriptionItemJpaEntity> itemEntities = 
                        prescriptionItemJpaRepository.findByPrescriptionId(prescriptionEntity.getId());
                prescriptions.add(mapToDomainEntity(prescriptionEntity, itemEntities));
            }
            
            log.debug("Found {} prescriptions for patient ID: {}", prescriptions.size(), patientId);
            return prescriptions;
        } catch (Exception e) {
            log.error("Error finding prescriptions by patient ID {}: {}", patientId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Prescription> findByDoctorId(String doctorId) {
        log.debug("Finding prescriptions by doctor ID: {}", doctorId);
        try {
            List<PrescriptionJpaEntity> prescriptionEntities = prescriptionJpaRepository.findByDoctorId(doctorId);
            List<Prescription> prescriptions = new ArrayList<>();
            
            for (PrescriptionJpaEntity prescriptionEntity : prescriptionEntities) {
                List<PrescriptionItemJpaEntity> itemEntities = 
                        prescriptionItemJpaRepository.findByPrescriptionId(prescriptionEntity.getId());
                prescriptions.add(mapToDomainEntity(prescriptionEntity, itemEntities));
            }
            
            log.debug("Found {} prescriptions for doctor ID: {}", prescriptions.size(), doctorId);
            return prescriptions;
        } catch (Exception e) {
            log.error("Error finding prescriptions by doctor ID {}: {}", doctorId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Prescription> findValidByPatientId(String patientId) {
        log.debug("Finding valid prescriptions by patient ID: {}", patientId);
        try {
            // Valid prescriptions are those that are not dispensed
            List<PrescriptionJpaEntity> prescriptionEntities = 
                    prescriptionJpaRepository.findByPatientIdAndDispensed(patientId, false);
            List<Prescription> prescriptions = new ArrayList<>();
            
            for (PrescriptionJpaEntity prescriptionEntity : prescriptionEntities) {
                List<PrescriptionItemJpaEntity> itemEntities = 
                        prescriptionItemJpaRepository.findByPrescriptionId(prescriptionEntity.getId());
                Prescription prescription = mapToDomainEntity(prescriptionEntity, itemEntities);
                
                // Additional check for expiration in the domain entity
                if (!prescription.isExpired()) {
                    prescriptions.add(prescription);
                }
            }
            
            log.debug("Found {} valid prescriptions for patient ID: {}", prescriptions.size(), patientId);
            return prescriptions;
        } catch (Exception e) {
            log.error("Error finding valid prescriptions by patient ID {}: {}", patientId, e.getMessage(), e);
            throw e;
        }
    }

    private PrescriptionJpaEntity mapToJpaEntity(Prescription prescription) {
        log.trace("Mapping domain entity to JPA entity for prescription ID: {}", prescription.getId());
        return PrescriptionJpaEntity.builder()
                .id(prescription.getId().toString())
                .patientId(prescription.getPatientId().getValue().toString())
                .doctorId(prescription.getDoctorId().getValue().toString())
                .issueDate(prescription.getIssueDate().getValue())
                .expirationDate(prescription.getExpirationDate().getValue())
                .dispensed(prescription.isDispensed())
                .dispensedDate(prescription.getDispensedDate().getValue())
                .notes(prescription.getNotes().getValue())
                .build();
    }

    private List<PrescriptionItemJpaEntity> mapToJpaItemEntities(Prescription prescription) {
        log.trace("Mapping domain items to JPA entities for prescription ID: {}", prescription.getId());
        return prescription.getItems().stream()
                .map(item -> PrescriptionItemJpaEntity.builder()
                        .prescriptionId(prescription.getId().toString())
                        .medicationId(item.getMedicationId().toString())
                        .quantity(item.getQuantity().getValue())
                        .instructions(item.getInstructions().getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private Prescription mapToDomainEntity(PrescriptionJpaEntity prescriptionEntity, 
                                          List<PrescriptionItemJpaEntity> itemEntities) {
        log.trace("Mapping JPA entity to domain entity for prescription ID: {}", prescriptionEntity.getId());
        PrescriptionId prescriptionId = new PrescriptionId(UUID.fromString(prescriptionEntity.getId()));
        
        List<Prescription.PrescriptionItem> items = itemEntities.stream()
                .map(itemEntity -> new Prescription.PrescriptionItem(
                        new MedicationId(UUID.fromString(itemEntity.getMedicationId())),
                        itemEntity.getQuantity(),
                        itemEntity.getInstructions()))
                .collect(Collectors.toList());
        
        Prescription prescription = new Prescription(
                prescriptionId,
                new PatientId(prescriptionEntity.getPatientId()),
                new DoctorId(prescriptionEntity.getDoctorId()),
                prescriptionEntity.getIssueDate(),
                prescriptionEntity.getExpirationDate(),
                items,
                prescriptionEntity.getNotes()
        );
        
        // Set dispensed status and date if applicable
        if (prescriptionEntity.isDispensed()) {
            prescription.markAsDispensed();
            // Note: The domain entity sets the dispensed date to now when markAsDispensed is called,
            // but we want to preserve the original dispensed date from the database
            // This is a limitation of the current domain model design
        }
        
        return prescription;
    }
}