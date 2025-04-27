package com.example.hospitalapi.clinicalrecord.application.command;

import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.shared.domain.bus.Command;

/**
 * Command to update an existing clinical record
 */
public class UpdateClinicalRecordCommand implements Command {
    private final String clinicalRecordId;
    private final ClinicalRecordType type;
    private final String title;
    private final String description;
    private final String attachmentUrl;

    private UpdateClinicalRecordCommand(Builder builder) {
        this.clinicalRecordId = builder.clinicalRecordId;
        this.type = builder.type;
        this.title = builder.title;
        this.description = builder.description;
        this.attachmentUrl = builder.attachmentUrl;
    }

    public String getClinicalRecordId() {
        return clinicalRecordId;
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
     * Builder for UpdateClinicalRecordCommand
     */
    public static class Builder {
        private String clinicalRecordId;
        private ClinicalRecordType type;
        private String title;
        private String description;
        private String attachmentUrl;

        public Builder clinicalRecordId(String clinicalRecordId) {
            this.clinicalRecordId = clinicalRecordId;
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

        public UpdateClinicalRecordCommand build() {
            return new UpdateClinicalRecordCommand(this);
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