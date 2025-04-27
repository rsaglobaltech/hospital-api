package com.example.hospitalapi.pharmacy.infrastructure.persistence.adapter;

import com.example.hospitalapi.pharmacy.domain.entity.Batch;
import com.example.hospitalapi.pharmacy.domain.entity.Inventory;
import com.example.hospitalapi.pharmacy.domain.repository.InventoryRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.BatchJpaEntity;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.InventoryJpaEntity;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.repository.BatchJpaRepository;
import com.example.hospitalapi.pharmacy.infrastructure.persistence.repository.InventoryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementing InventoryRepository using JPA
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryRepositoryAdapter implements InventoryRepository {

    private final InventoryJpaRepository inventoryJpaRepository;
    private final BatchJpaRepository batchJpaRepository;
    private final BatchRepositoryAdapter batchRepositoryAdapter;

    @Override
    @Transactional
    public Inventory save(Inventory inventory) {
        log.debug("Saving inventory for medication ID: {}", inventory.getMedicationId());
        try {
            // Save inventory summary
            InventoryJpaEntity inventoryJpaEntity = mapToJpaEntity(inventory);
            inventoryJpaRepository.save(inventoryJpaEntity);
            
            // Save batches
            for (Batch batch : inventory.getBatches()) {
                batchRepositoryAdapter.save(batch);
            }
            
            log.debug("Inventory saved successfully for medication ID: {}", inventory.getMedicationId());
            return inventory;
        } catch (Exception e) {
            log.error("Error saving inventory for medication ID {}: {}", inventory.getMedicationId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Inventory> findByMedicationId(MedicationId medicationId) {
        log.debug("Finding inventory by medication ID: {}", medicationId);
        try {
            // Get batches for this medication
            List<BatchJpaEntity> batchEntities = batchJpaRepository.findByMedicationId(medicationId.toString());
            
            if (!batchEntities.isEmpty()) {
                List<Batch> batches = batchEntities.stream()
                        .map(batchRepositoryAdapter::mapToDomainEntity)
                        .collect(Collectors.toList());
                
                // Create inventory domain entity
                Inventory inventory = new Inventory(medicationId, batches);
                
                log.debug("Inventory found for medication ID: {}", medicationId);
                return Optional.of(inventory);
            } else {
                log.debug("Inventory not found for medication ID: {}", medicationId);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error finding inventory by medication ID {}: {}", medicationId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Inventory> findAll() {
        log.debug("Finding all inventories");
        try {
            // Get all inventory summaries to get all medication IDs
            List<InventoryJpaEntity> inventoryEntities = inventoryJpaRepository.findAll();
            List<String> medicationIds = inventoryEntities.stream()
                    .map(InventoryJpaEntity::getMedicationId)
                    .collect(Collectors.toList());
            
            // Also get all medication IDs from batches
            List<String> batchMedicationIds = batchJpaRepository.findAll().stream()
                    .map(BatchJpaEntity::getMedicationId)
                    .distinct()
                    .collect(Collectors.toList());
            
            // Combine both lists and remove duplicates
            medicationIds.addAll(batchMedicationIds);
            List<String> uniqueMedicationIds = medicationIds.stream()
                    .distinct()
                    .collect(Collectors.toList());
            
            // Create inventory domain entities for each medication ID
            List<Inventory> inventories = new ArrayList<>();
            for (String medicationIdStr : uniqueMedicationIds) {
                MedicationId medicationId = new MedicationId(UUID.fromString(medicationIdStr));
                List<BatchJpaEntity> batchEntities = batchJpaRepository.findByMedicationId(medicationIdStr);
                
                if (!batchEntities.isEmpty()) {
                    List<Batch> batches = batchEntities.stream()
                            .map(batchRepositoryAdapter::mapToDomainEntity)
                            .collect(Collectors.toList());
                    
                    Inventory inventory = new Inventory(medicationId, batches);
                    inventories.add(inventory);
                }
            }
            
            log.debug("Found {} inventories", inventories.size());
            return inventories;
        } catch (Exception e) {
            log.error("Error finding all inventories: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteByMedicationId(MedicationId medicationId) {
        log.debug("Deleting inventory for medication ID: {}", medicationId);
        try {
            // Delete inventory summary
            inventoryJpaRepository.deleteById(medicationId.toString());
            
            // Delete all batches for this medication
            List<BatchJpaEntity> batches = batchJpaRepository.findByMedicationId(medicationId.toString());
            for (BatchJpaEntity batch : batches) {
                batchJpaRepository.deleteById(batch.getId());
            }
            
            log.debug("Inventory deleted successfully for medication ID: {}", medicationId);
        } catch (Exception e) {
            log.error("Error deleting inventory for medication ID {}: {}", medicationId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsByMedicationId(MedicationId medicationId) {
        log.debug("Checking if inventory exists for medication ID: {}", medicationId);
        try {
            boolean existsInventory = inventoryJpaRepository.existsById(medicationId.toString());
            boolean existsBatches = !batchJpaRepository.findByMedicationId(medicationId.toString()).isEmpty();
            
            boolean exists = existsInventory || existsBatches;
            log.debug("Inventory exists for medication ID {}: {}", medicationId, exists);
            return exists;
        } catch (Exception e) {
            log.error("Error checking if inventory exists for medication ID {}: {}", medicationId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Inventory> findWithAvailableStock() {
        log.debug("Finding inventories with available stock");
        try {
            // Get all batches with stock
            List<BatchJpaEntity> batchesWithStock = batchJpaRepository.findByQuantityGreaterThan(0).stream()
                    .filter(batch -> !batch.getExpirationDate().isBefore(LocalDate.now()))
                    .collect(Collectors.toList());
            
            // Group batches by medication ID
            return batchesWithStock.stream()
                    .collect(Collectors.groupingBy(BatchJpaEntity::getMedicationId))
                    .entrySet().stream()
                    .map(entry -> {
                        MedicationId medicationId = new MedicationId(UUID.fromString(entry.getKey()));
                        List<Batch> batches = entry.getValue().stream()
                                .map(batchRepositoryAdapter::mapToDomainEntity)
                                .collect(Collectors.toList());
                        
                        return new Inventory(medicationId, batches);
                    })
                    .filter(Inventory::hasStock)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding inventories with available stock: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Inventory> findWithLowStock(int threshold) {
        log.debug("Finding inventories with low stock (threshold: {})", threshold);
        try {
            // Get all inventories
            List<Inventory> allInventories = findAll();
            
            // Filter inventories with low stock
            return allInventories.stream()
                    .filter(inventory -> inventory.getAvailableQuantity() > 0 && inventory.getAvailableQuantity() <= threshold)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error finding inventories with low stock: {}", e.getMessage(), e);
            throw e;
        }
    }

    private InventoryJpaEntity mapToJpaEntity(Inventory inventory) {
        log.trace("Mapping domain entity to JPA entity for inventory with medication ID: {}", inventory.getMedicationId());
        return InventoryJpaEntity.builder()
                .medicationId(inventory.getMedicationId().toString())
                .totalQuantity(inventory.getTotalQuantity())
                .availableQuantity(inventory.getAvailableQuantity())
                .lowStockThreshold(5) // Default threshold
                .build();
    }
}