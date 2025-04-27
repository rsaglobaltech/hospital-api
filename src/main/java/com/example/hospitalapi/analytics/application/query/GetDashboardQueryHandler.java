package com.example.hospitalapi.analytics.application.query;

import com.example.hospitalapi.analytics.domain.repository.DashboardRepository;
import com.example.hospitalapi.analytics.domain.valueobject.DashboardId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * Handler for GetDashboardQuery
 */
@Service
@RequiredArgsConstructor
public class GetDashboardQueryHandler implements QueryHandler<GetDashboardQuery, DashboardResponse> {

    private final DashboardRepository dashboardRepository;

    /**
     * Handle the GetDashboardQuery
     * @param query the query to handle
     * @return the dashboard data
     * @throws IllegalArgumentException if the dashboard is not found
     */
    @Transactional(readOnly = true)
    public DashboardResponse handle(GetDashboardQuery query) {
        // Get dashboard data from repository
        DashboardId dashboardId = new DashboardId(query.getDashboardId());
        
        Optional<Map<String, Object>> dashboardDataOpt = dashboardRepository.findById(dashboardId);
        
        if (dashboardDataOpt.isEmpty()) {
            throw new IllegalArgumentException("Dashboard not found with ID: " + dashboardId);
        }
        
        Map<String, Object> dashboardData = dashboardDataOpt.get();
        
        // Create response
        return DashboardResponse.builder()
                .dashboardId(dashboardId.toString())
                .dashboardName((String) dashboardData.getOrDefault("name", "Unknown Dashboard"))
                .data(dashboardData)
                .lastUpdated(LocalDateTime.now())
                .description((String) dashboardData.getOrDefault("description", ""))
                .build();
    }
}