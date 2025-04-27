package com.example.hospitalapi.analytics.application.query;

import com.example.hospitalapi.shared.domain.bus.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Query to get a dashboard by ID
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDashboardQuery implements Query<DashboardResponse> {
    
    private String dashboardId;
}