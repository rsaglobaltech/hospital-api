package com.example.hospitalapi.analytics.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Query to get KPI values
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetKpisQuery implements Query<KpiResponse> {
    
    @Builder.Default
    private List<String> kpiNames = new ArrayList<>();
    
    private String timePeriod;
}