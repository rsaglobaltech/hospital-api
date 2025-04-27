package com.example.hospitalapi.pharmacy.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * JPA entity for Dispensation Item
 */
@Entity
@Table(name = "dispensation_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispensationItemJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String dispensationId;
    
    @Column(nullable = false)
    private String medicationId;
    
    @Column(nullable = false)
    private String medicationName;
    
    @Column(nullable = false)
    private int quantity;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    
    private String batchNumber;
}