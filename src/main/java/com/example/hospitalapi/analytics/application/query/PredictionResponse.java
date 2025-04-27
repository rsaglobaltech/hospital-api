package com.example.hospitalapi.analytics.application.query;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for prediction data
 */
@Data
@Builder
public class PredictionResponse {
    
    private final String predictionId;
    private final String predictionModel;
    private final Map<String, Object> result;
    private final Map<String, String> contextParams;
    private final LocalDateTime generatedAt;
    private final String confidence;
}