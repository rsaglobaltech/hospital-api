package com.example.hospitalapi.analytics.application.query;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for KPI data
 */
@Data
@Builder
public class KpiResponse {
    
    private final Map<String, Object> kpiValues;
    private final String timePeriod;
    private final LocalDateTime calculatedAt;
}