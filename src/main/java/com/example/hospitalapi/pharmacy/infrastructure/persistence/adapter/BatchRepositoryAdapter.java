package com.example.hospitalapi.pharmacy.infrastructure.persistence.adapter;

import com.example.hospitalapi.pharmacy.domain.entity.Batch;
import com.example.hospitalapi.pharmacy.domain.repository.BatchRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.BatchId;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.BatchJpaEntity;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.repository.BatchJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementing BatchRepository using JPA
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BatchRepositoryAdapter implements BatchRepository {

    private final BatchJpaRepository batchJpaRepository;

    @Override
    public Batch save(Batch batch) {
        log.debug("Saving batch with ID: {}", batch.getId());
        try {
            BatchJpaEntity batchJpaEntity = mapToJpaEntity(batch);
            BatchJpaEntity savedEntity = batchJpaRepository.save(batchJpaEntity);
            log.debug("Batch saved successfully with ID: {}", batch.getId());
            return mapToDomainEntity(savedEntity);
        } catch (Exception e) {
            log.error("Error saving batch with ID {}: {}", batch.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Batch> findById(BatchId id) {
        log.debug("Finding batch by ID: {}", id);
        try {
            Optional<Batch> result = batchJpaRepository.findById(id.toString())
                    .map(this::mapToDomainEntity);

            if (result.isPresent()) {
                log.debug("Batch found with ID: {}", id);
            } else {
                log.debug("Batch not found with ID: {}", id);
            }

            return result;
        } catch (Exception e) {
            log.error("Error finding batch with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Batch> findAll() {
        log.debug("Finding all batches");
        try {
            List<Batch> batches = batchJpaRepository.findAll().stream()
                    .map(this::mapToDomainEntity)
                    .collect(Collectors.toList());

            log.debug("Found {} batches", batches.size());
            return batches;
        } catch (Exception e) {
            log.error("Error finding all batches: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteById(BatchId id) {
        log.debug("Deleting batch with ID: {}", id);
        try {
            batchJpaRepository.deleteById(id.toString());
            log.debug("Batch deleted successfully with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting batch with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(BatchId id) {
        log.debug("Checking if batch exists with ID: {}", id);
        try {
            boolean exists = batchJpaRepository.existsById(id.toString());
            log.debug("Batch exists with ID {}: {}", id, exists);
            return exists;
        } catch (Exception e) {
            log.error("Error checking if batch exists with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Batch> findByMedicationId(MedicationId medicationId) {
        log.debug("Finding batches by medication ID: {}", medicationId);
        try {
            List<Batch> batches = batchJpaRepository.findByMedicationId(medicationId.toString()).stream()
                    .map(this::mapToDomainEntity)
                    .collect(Collectors.toList());

            log.debug("Found {} batches for medication ID: {}", batches.size(), medicationId);
            return batches;
        } catch (Exception e) {
            log.error("Error finding batches by medication ID {}: {}", medicationId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Batch> findByBatchNumber(String batchNumber) {
        log.debug("Finding batches by batch number: {}", batchNumber);
        try {
            List<Batch> batches = batchJpaRepository.findByBatchNumber(batchNumber).stream()
                    .map(this::mapToDomainEntity)
                    .collect(Collectors.toList());

            log.debug("Found {} batches with batch number: {}", batches.size(), batchNumber);
            return batches;
        } catch (Exception e) {
            log.error("Error finding batches by batch number {}: {}", batchNumber, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Batch> findByExpirationDateBefore(LocalDate date) {
        log.debug("Finding batches expiring before: {}", date);
        try {
            List<Batch> batches = batchJpaRepository.findByExpirationDateBefore(date).stream()
                    .map(this::mapToDomainEntity)
                    .collect(Collectors.toList());

            log.debug("Found {} batches expiring before: {}", batches.size(), date);
            return batches;
        } catch (Exception e) {
            log.error("Error finding batches expiring before {}: {}", date, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Batch> findByQuantityGreaterThan(int quantity) {
        log.debug("Finding batches with quantity greater than: {}", quantity);
        try {
            List<Batch> batches = batchJpaRepository.findByQuantityGreaterThan(quantity).stream()
                    .map(this::mapToDomainEntity)
                    .collect(Collectors.toList());

            log.debug("Found {} batches with quantity greater than: {}", batches.size(), quantity);
            return batches;
        } catch (Exception e) {
            log.error("Error finding batches with quantity greater than {}: {}", quantity, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Batch> findByMedicationIdAndQuantityGreaterThan(MedicationId medicationId, int quantity) {
        log.debug("Finding batches by medication ID: {} with quantity greater than: {}", medicationId, quantity);
        try {
            List<Batch> batches = batchJpaRepository.findByMedicationIdAndQuantityGreaterThan(
                    medicationId.toString(), quantity).stream()
                    .map(this::mapToDomainEntity)
                    .collect(Collectors.toList());

            log.debug("Found {} batches for medication ID: {} with quantity greater than: {}", 
                    batches.size(), medicationId, quantity);
            return batches;
        } catch (Exception e) {
            log.error("Error finding batches by medication ID {} with quantity greater than {}: {}", 
                    medicationId, quantity, e.getMessage(), e);
            throw e;
        }
    }

    private BatchJpaEntity mapToJpaEntity(Batch batch) {
        log.trace("Mapping domain entity to JPA entity for batch ID: {}", batch.getId());
        return BatchJpaEntity.builder()
                .id(batch.getId().toString())
                .medicationId(batch.getMedicationId().toString())
                .batchNumber(batch.getBatchNumber().getValue())
                .manufacturingDate(batch.getManufacturingDate().getValue())
                .expirationDate(batch.getExpirationDate().getValue())
                .quantity(batch.getQuantity().getValue())
                .build();
    }

    public Batch mapToDomainEntity(BatchJpaEntity batchJpaEntity) {
        log.trace("Mapping JPA entity to domain entity for batch ID: {}", batchJpaEntity.getId());
        BatchId batchId = new BatchId(UUID.fromString(batchJpaEntity.getId()));
        MedicationId medicationId = new MedicationId(UUID.fromString(batchJpaEntity.getMedicationId()));

        return new Batch(
                batchId,
                medicationId,
                batchJpaEntity.getBatchNumber(),
                batchJpaEntity.getManufacturingDate(),
                batchJpaEntity.getExpirationDate(),
                batchJpaEntity.getQuantity()
        );
    }
}
