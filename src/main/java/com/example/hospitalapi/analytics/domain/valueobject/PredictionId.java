package com.example.hospitalapi.analytics.domain.valueobject;

import com.example.hospitalapi.shared.domain.valueobject.Id;

import java.util.UUID;

/**
 * Value object for prediction ID
 */
public class PredictionId extends Id {
    
    public PredictionId() {
        super();
    }
    
    public PredictionId(UUID value) {
        super(value);
    }
    
    public PredictionId(String value) {
        super(value);
    }
}