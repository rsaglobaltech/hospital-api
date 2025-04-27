package com.example.hospitalapi.analytics.application.command;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * DTO for ad-hoc query results
 */
@Data
@Builder
public class AdhocQueryResponse {
    
    private final String queryId;
    private final String query;
    private final List<Map<String, Object>> results;
    private final int rowCount;
    private final LocalDateTime executedAt;
    private final long executionTimeMs;
    private final String requestedBy;
}