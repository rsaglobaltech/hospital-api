package com.example.hospitalapi.analytics.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object for report ID
 */
public class ReportId extends Id {

    public ReportId() {
        super();
    }

    public ReportId(UUID value) {
        super(value);
    }

    public ReportId(String value) {
        super(value);
    }
}
