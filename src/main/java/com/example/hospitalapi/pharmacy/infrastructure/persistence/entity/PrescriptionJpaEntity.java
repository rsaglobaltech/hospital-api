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
 * JPA entity for Prescription
 */
@Entity
@Table(name = "prescriptions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionJpaEntity {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private String patientId;
    
    @Column(nullable = false)
    private String doctorId;
    
    @Column(nullable = false)
    private LocalDate issueDate;
    
    @Column(nullable = false)
    private LocalDate expirationDate;
    
    @Column(nullable = false)
    private boolean dispensed;
    
    private LocalDate dispensedDate;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
}