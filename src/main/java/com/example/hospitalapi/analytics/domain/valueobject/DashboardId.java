package com.example.hospitalapi.analytics.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object for dashboard ID
 */
public class DashboardId extends Id {
    
    public DashboardId() {
        super();
    }
    
    public DashboardId(UUID value) {
        super(value);
    }
    
    public DashboardId(String value) {
        super(value);
    }
}