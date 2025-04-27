package com.example.hospitalapi.pharmacy.infrastructure.persistence.repository;

import com.example.hospitalapi.pharmacy.infrastructure.persistence.entity.InventoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for InventoryJpaEntity
 */
@Repository
public interface InventoryJpaRepository extends JpaRepository<InventoryJpaEntity, String> {
    
    /**
     * Find inventories with available stock (quantity > 0)
     * @return list of inventories with available stock
     */
    List<InventoryJpaEntity> findByAvailableQuantityGreaterThan(int quantity);
    
    /**
     * Find inventories with low stock (below threshold)
     * @return list of inventories with low stock
     */
    @Query("SELECT i FROM InventoryJpaEntity i WHERE i.availableQuantity <= i.lowStockThreshold")
    List<InventoryJpaEntity> findWithLowStock();
    
    /**
     * Find inventories with low stock (below custom threshold)
     * @param threshold the threshold
     * @return list of inventories with low stock
     */
    @Query("SELECT i FROM InventoryJpaEntity i WHERE i.availableQuantity <= :threshold")
    List<InventoryJpaEntity> findWithLowStockCustomThreshold(@Param("threshold") int threshold);
}