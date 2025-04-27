package com.example.hospitalapi.clinicalrecord.infrastructure.persistence.entity;

import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * JPA entity for ClinicalRecord
 */
@Entity
@Table(name = "clinical_records")
public class ClinicalRecordJpaEntity {
    
    @Id
    private String id;
    
    @Column(name = "patient_id", nullable = false)
    private String patientId;
    
    @Column(name = "doctor_id", nullable = false)
    private String doctorId;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClinicalRecordType type;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 4000)
    private String description;
    
    @Column(name = "attachment_url")
    private String attachmentUrl;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Default constructor
    public ClinicalRecordJpaEntity() {
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public ClinicalRecordType getType() {
        return type;
    }

    public void setType(ClinicalRecordType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}