package com.example.hospitalapi.clinicalrecord.domain.entity;

import com.example.hospitalapi.clinicalrecord.domain.exception.ClinicalRecordValidationException;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.AttachmentUrl;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordDescription;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordTitle;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.medicalstaff.domain.valueobject.DoctorId;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import com.example.hospitalapi.shared.domain.entity.AggregateRoot;
import com.example.hospitalapi.shared.domain.valueobject.Timestamp;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Entity representing a clinical record in the hospital system
 */
@Getter
public class ClinicalRecord extends AggregateRoot {
    private final ClinicalRecordId id;
    private final PatientId patientId;
    private final DoctorId doctorId;
    private ClinicalRecordType type;
    private ClinicalRecordTitle title;
    private ClinicalRecordDescription description;
    private AttachmentUrl attachmentUrl;
    private final Timestamp createdAt;
    private Timestamp updatedAt;

    /**
     * Create a new clinical record
     * @param id the clinical record ID
     * @param patientId the patient ID
     * @param doctorIdValue the doctor ID value
     * @param type the clinical record type
     * @param titleValue the title value
     * @param descriptionValue the description value
     */
    public ClinicalRecord(ClinicalRecordId id, PatientId patientId, String doctorIdValue, 
                         ClinicalRecordType type, String titleValue, String descriptionValue) {
        if (id == null) {
            throw new ClinicalRecordValidationException("Clinical record ID cannot be null");
        }
        if (patientId == null) {
            throw new ClinicalRecordValidationException("Patient ID cannot be null");
        }
        if (doctorIdValue == null || doctorIdValue.trim().isEmpty()) {
            throw new ClinicalRecordValidationException("Doctor ID cannot be empty");
        }
        if (type == null) {
            throw new ClinicalRecordValidationException("Clinical record type cannot be null");
        }
        if (titleValue == null || titleValue.trim().isEmpty()) {
            throw new ClinicalRecordValidationException("Title cannot be empty");
        }
        if (descriptionValue == null || descriptionValue.trim().isEmpty()) {
            throw new ClinicalRecordValidationException("Description cannot be empty");
        }

        this.id = id;
        this.patientId = patientId;
        this.doctorId = new DoctorId(doctorIdValue);
        this.type = type;
        this.title = new ClinicalRecordTitle(titleValue);
        this.description = new ClinicalRecordDescription(descriptionValue);
        this.attachmentUrl = new AttachmentUrl("");
        this.createdAt = Timestamp.now();
        this.updatedAt = this.createdAt;
    }

    /**
     * Update the clinical record type
     * @param type the new clinical record type
     */
    public void updateType(ClinicalRecordType type) {
        if (type == null) {
            throw new ClinicalRecordValidationException("Clinical record type cannot be null");
        }
        this.type = type;
        this.updatedAt = Timestamp.now();
    }

    /**
     * Update the clinical record title
     * @param titleValue the new title
     */
    public void updateTitle(String titleValue) {
        if (titleValue == null || titleValue.trim().isEmpty()) {
            throw new ClinicalRecordValidationException("Title cannot be empty");
        }
        this.title = new ClinicalRecordTitle(titleValue);
        this.updatedAt = Timestamp.now();
    }

    /**
     * Update the clinical record description
     * @param descriptionValue the new description
     */
    public void updateDescription(String descriptionValue) {
        if (descriptionValue == null || descriptionValue.trim().isEmpty()) {
            throw new ClinicalRecordValidationException("Description cannot be empty");
        }
        this.description = new ClinicalRecordDescription(descriptionValue);
        this.updatedAt = Timestamp.now();
    }

    /**
     * Update the clinical record attachment URL
     * @param attachmentUrlValue the new attachment URL
     */
    public void updateAttachmentUrl(String attachmentUrlValue) {
        this.attachmentUrl = new AttachmentUrl(attachmentUrlValue);
        this.updatedAt = Timestamp.now();
    }

    /**
     * Get the clinical record ID
     * @return the clinical record ID
     */
    public ClinicalRecordId getId() {
        return id;
    }

    /**
     * Get the patient ID
     * @return the patient ID
     */
    public PatientId getPatientId() {
        return patientId;
    }

    /**
     * Get the doctor ID
     * @return the doctor ID
     */
    public DoctorId getDoctorId() {
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
    public ClinicalRecordTitle getTitle() {
        return title;
    }

    /**
     * Get the description
     * @return the description
     */
    public ClinicalRecordDescription getDescription() {
        return description;
    }

    /**
     * Get the attachment URL
     * @return the attachment URL
     */
    public AttachmentUrl getAttachmentUrl() {
        return attachmentUrl;
    }

    /**
     * Get the creation date and time
     * @return the creation date and time
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Get the last update date and time
     * @return the last update date and time
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
