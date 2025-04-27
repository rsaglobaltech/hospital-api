package com.example.hospitalapi.medicalstaff.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * JPA entity for Qualification
 */
@Entity
@Table(name = "qualifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualificationJpaEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String degree;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private LocalDate dateObtained;

    @Column(nullable = false)
    private String licenseNumber;

    @Column(nullable = false)
    private LocalDate licenseExpiryDate;

    @Column(name = "staff_id", insertable = false, updatable = false)
    private String staffId;
}
