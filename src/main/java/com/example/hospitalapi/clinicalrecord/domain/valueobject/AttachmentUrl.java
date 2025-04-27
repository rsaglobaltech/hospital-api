package com.example.hospitalapi.clinicalrecord.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value object representing an attachment URL for a clinical record
 */
@Getter
@EqualsAndHashCode
public class AttachmentUrl {
    private final String value;

    public AttachmentUrl(String value) {
        this.value = value != null ? value.trim() : "";
    }

    public boolean isEmpty() {
        return value.trim().isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }
}