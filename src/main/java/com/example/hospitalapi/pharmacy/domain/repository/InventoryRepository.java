package com.example.hospitalapi.pharmacy.domain.repository;

import com.example.hospitalapi.pharmacy.domain.entity.Inventory;
import com.example.hospitalapi.pharmacy.domain.valueobject.MedicationId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Inventory entity
 */
public interface InventoryRepository {
    
    /**
     * Save an inventory
     * @param inventory the inventory to save
     * @return the saved inventory
     */
    Inventory save(Inventory inventory);
    
    /**
     * Find an inventory by medication ID
     * @param medicationId the medication ID
     * @return the inventory if found
     */
    Optional<Inventory> findByMedicationId(MedicationId medicationId);
    
    /**
     * Find all inventories
     * @return list of all inventories
     */
    List<Inventory> findAll();
    
    /**
     * Delete an inventory
     * @param medicationId the medication ID
     */
    void deleteByMedicationId(MedicationId medicationId);
    
    /**
     * Check if an inventory exists
     * @param medicationId the medication ID
     * @return true if the inventory exists
     */
    boolean existsByMedicationId(MedicationId medicationId);
    
    /**
     * Find inventories with available stock (quantity > 0)
     * @return list of inventories with available stock
     */
    List<Inventory> findWithAvailableStock();
    
    /**
     * Find inventories with low stock (below threshold)
     * @param threshold the threshold
     * @return list of inventories with low stock
     */
    List<Inventory> findWithLowStock(int threshold);
}