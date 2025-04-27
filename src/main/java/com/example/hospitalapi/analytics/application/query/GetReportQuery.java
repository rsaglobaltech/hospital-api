package com.example.hospitalapi.analytics.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Query to get a report by name with filter parameters
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetReportQuery implements Query<ReportResponse> {
    
    private String reportName;
    
    @Builder.Default
    private Map<String, String> filterParams = new HashMap<>();
}