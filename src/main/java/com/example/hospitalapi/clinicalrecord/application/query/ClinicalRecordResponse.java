package com.example.hospitalapi.clinicalrecord.application.query;

import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;

import java.time.LocalDateTime;

/**
 * Response DTO for clinical record data
 */
public class ClinicalRecordResponse {
    private final String id;
    private final String patientId;
    private final String doctorId;
    private final ClinicalRecordType type;
    private final String title;
    private final String description;
    private final String attachmentUrl;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Create a new ClinicalRecordResponse
     * @param builder the builder
     */
    private ClinicalRecordResponse(Builder builder) {
        this.id = builder.id;
        this.patientId = builder.patientId;
        this.doctorId = builder.doctorId;
        this.type = builder.type;
        this.title = builder.title;
        this.description = builder.description;
        this.attachmentUrl = builder.attachmentUrl;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    /**
     * Get the clinical record ID
     * @return the clinical record ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get the patient ID
     * @return the patient ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Get the doctor ID
     * @return the doctor ID
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Get the clinical record type
     * @return the clinical record type
     */
    public ClinicalRecordType getType() {
        return type;
    }

    /**
     * Get the title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the attachment URL
     * @return the attachment URL
     */
    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    /**
     * Get the creation date and time
     * @return the creation date and time
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Get the last update date and time
     * @return the last update date and time
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Builder for ClinicalRecordResponse
     */
    public static class Builder {
        private String id;
        private String patientId;
        private String doctorId;
        private ClinicalRecordType type;
        private String title;
        private String description;
        private String attachmentUrl;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder patientId(String patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder doctorId(String doctorId) {
            this.doctorId = doctorId;
            return this;
        }

        public Builder type(ClinicalRecordType type) {
            this.type = type;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder attachmentUrl(String attachmentUrl) {
            this.attachmentUrl = attachmentUrl;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ClinicalRecordResponse build() {
            return new ClinicalRecordResponse(this);
        }
    }

    /**
     * Create a new builder
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }
}