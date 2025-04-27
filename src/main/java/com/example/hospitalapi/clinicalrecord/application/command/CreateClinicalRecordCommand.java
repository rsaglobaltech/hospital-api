package com.example.hospitalapi.clinicalrecord.application.command;

import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.shared.domain.bus.Command;

/**
 * Command to create a new clinical record
 */
public class CreateClinicalRecordCommand implements Command {
    private final String patientId;
    private final String doctorId;
    private final ClinicalRecordType type;
    private final String title;
    private final String description;
    private final String attachmentUrl;

    private CreateClinicalRecordCommand(Builder builder) {
        this.patientId = builder.patientId;
        this.doctorId = builder.doctorId;
        this.type = builder.type;
        this.title = builder.title;
        this.description = builder.description;
        this.attachmentUrl = builder.attachmentUrl;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public ClinicalRecordType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    /**
     * Builder for CreateClinicalRecordCommand
     */
    public static class Builder {
        private String patientId;
        private String doctorId;
        private ClinicalRecordType type;
        private String title;
        private String description;
        private String attachmentUrl;

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

        public CreateClinicalRecordCommand build() {
            return new CreateClinicalRecordCommand(this);
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