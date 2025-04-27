package com.example.hospitalapi.analytics.application.query;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for report data
 */
@Data
@Builder
public class ReportResponse {
    
    private final String reportId;
    private final String reportName;
    private final Map<String, Object> data;
    private final Map<String, String> filterParams;
    private final LocalDateTime generatedAt;
    private final String format;
}