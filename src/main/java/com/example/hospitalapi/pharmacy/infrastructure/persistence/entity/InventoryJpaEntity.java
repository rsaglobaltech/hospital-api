package com.example.hospitalapi.pharmacy.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity for Inventory
 * Note: This is a simplified representation of the Inventory domain entity.
 * The actual batches are stored in the batches table and retrieved separately.
 */
@Entity
@Table(name = "inventory")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryJpaEntity {
    
    @Id
    private String medicationId;
    
    @Column(nullable = false)
    private int totalQuantity;
    
    @Column(nullable = false)
    private int availableQuantity;
    
    @Column(nullable = false)
    private int lowStockThreshold;
}