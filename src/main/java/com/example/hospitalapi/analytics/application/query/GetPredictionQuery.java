package com.example.hospitalapi.analytics.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Query to get prediction results
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPredictionQuery implements Query<PredictionResponse> {
    
    private String predictionModel;
    
    @Builder.Default
    private Map<String, String> contextParams = new HashMap<>();
}