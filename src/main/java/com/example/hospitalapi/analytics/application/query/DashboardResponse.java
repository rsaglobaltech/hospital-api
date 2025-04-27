package com.example.hospitalapi.analytics.application.query;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for dashboard data
 */
@Data
@Builder
public class DashboardResponse {
    
    private final String dashboardId;
    private final String dashboardName;
    private final Map<String, Object> data;
    private final LocalDateTime lastUpdated;
    private final String description;
}