package com.example.hospitalapi.pharmacy.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA entity for Dispensation
 */
@Entity
@Table(name = "dispensations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispensationJpaEntity {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private String prescriptionId;
    
    @Column(nullable = false)
    private String patientId;
    
    @Column(nullable = false)
    private String pharmacistId;
    
    @Column(nullable = false)
    private LocalDateTime dispensationDateTime;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
}