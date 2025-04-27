package com.example.hospitalapi.analytics.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object for KPI ID
 */
public class KpiId extends Id {
    
    public KpiId() {
        super();
    }
    
    public KpiId(UUID value) {
        super(value);
    }
    
    public KpiId(String value) {
        super(value);
    }
}