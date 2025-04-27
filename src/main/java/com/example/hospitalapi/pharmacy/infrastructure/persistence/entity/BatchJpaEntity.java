package com.example.hospitalapi.pharmacy.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * JPA entity for Batch
 */
@Entity
@Table(name = "batches")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchJpaEntity {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private String medicationId;
    
    @Column(nullable = false)
    private String batchNumber;
    
    @Column(nullable = false)
    private LocalDate manufacturingDate;
    
    @Column(nullable = false)
    private LocalDate expirationDate;
    
    @Column(nullable = false)
    private int quantity;
}