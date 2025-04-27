package com.example.hospitalapi.medicalstaff.infrastructure.persistence.entity;

import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity for MedicalStaff
 */
@Entity
@Table(name = "medical_staff")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalStaffJpaEntity {
    
    @Id
    private String id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(nullable = false)
    private String address;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialty specialty;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "staff_id")
    private List<QualificationJpaEntity> qualifications = new ArrayList<>();
    
    @Column(nullable = false)
    private boolean active;
    
    @Column(nullable = false)
    private LocalDate hireDate;
    
    private LocalDate terminationDate;
}