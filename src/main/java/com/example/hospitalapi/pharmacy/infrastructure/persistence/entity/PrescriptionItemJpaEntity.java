package com.example.hospitalapi.pharmacy.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity for Prescription Item
 */
@Entity
@Table(name = "prescription_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String prescriptionId;
    
    @Column(nullable = false)
    private String medicationId;
    
    @Column(nullable = false)
    private int quantity;
    
    @Column(columnDefinition = "TEXT")
    private String instructions;
}