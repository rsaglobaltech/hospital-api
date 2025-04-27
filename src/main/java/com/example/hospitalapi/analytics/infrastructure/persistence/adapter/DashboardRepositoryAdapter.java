package com.example.hospitalapi.analytics.infrastructure.persistence.adapter;

import com.example.hospitalapi.analytics.domain.repository.DashboardRepository;
import com.example.hospitalapi.analytics.domain.valueobject.DashboardId;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Adapter implementation for DashboardRepository
 */
@Component
public class DashboardRepositoryAdapter implements DashboardRepository {

    // In a real implementation, this would use a database or data warehouse
    // This is just a mock implementation for demonstration purposes
    private final Map<String, Map<String, Object>> dashboards = new HashMap<>();

    public DashboardRepositoryAdapter() {
        // Initialize some mock dashboards
        Map<String, Object> erDashboard = new HashMap<>();
        erDashboard.put("id", UUID.randomUUID().toString());
        erDashboard.put("name", "ER_WaitTimes");
        erDashboard.put("description", "Emergency Room Wait Times Dashboard");
        erDashboard.put("currentWaitTime", 35);
        erDashboard.put("averageWaitTime", 42);
        erDashboard.put("waitTimeByPriority", Map.of(
                "Critical", 5,
                "High", 15,
                "Medium", 45,
                "Low", 90
        ));
        erDashboard.put("waitTimeByHour", Map.of(
                "00:00", 25,
                "06:00", 20,
                "12:00", 55,
                "18:00", 65
        ));
        dashboards.put(erDashboard.get("id").toString(), erDashboard);

        Map<String, Object> bedOccupancyDashboard = new HashMap<>();
        bedOccupancyDashboard.put("id", UUID.randomUUID().toString());
        bedOccupancyDashboard.put("name", "BedOccupancy");
        bedOccupancyDashboard.put("description", "Hospital Bed Occupancy Dashboard");
        bedOccupancyDashboard.put("totalBeds", 500);
        bedOccupancyDashboard.put("occupiedBeds", 425);
        bedOccupancyDashboard.put("occupancyRate", 85.0);
        bedOccupancyDashboard.put("occupancyByDepartment", Map.of(
                "ICU", 95.0,
                "Cardiology", 88.0,
                "Neurology", 82.0,
                "Orthopedics", 75.0,
                "Pediatrics", 70.0
        ));
        dashboards.put(bedOccupancyDashboard.get("id").toString(), bedOccupancyDashboard);
    }

    @Override
    public Optional<Map<String, Object>> findById(DashboardId dashboardId) {
        // In a real implementation, this would query a database by ID
        return Optional.ofNullable(dashboards.get(dashboardId.toString()))
                .map(HashMap::new);
    }

    @Override
    public Optional<Map<String, Object>> findByName(String dashboardName) {
        // In a real implementation, this would query a database by name
        return dashboards.values().stream()
                .filter(dashboard -> dashboardName.equals(dashboard.get("name")))
                .findFirst()
                .map(HashMap::new);
    }

    @Override
    public List<Map<String, Object>> findAll() {
        // Return all dashboards
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> dashboard : dashboards.values()) {
            result.add(new HashMap<>(dashboard));
        }
        return result;
    }

    @Override
    public DashboardId saveDashboard(String dashboardName, Map<String, Object> dashboardData) {
        // In a real implementation, this would save to a database
        DashboardId dashboardId = new DashboardId();

        // Create a new dashboard with the provided data
        Map<String, Object> dashboard = new HashMap<>(dashboardData);
        dashboard.put("id", dashboardId.toString());
        dashboard.put("name", dashboardName);

        // Save to the in-memory map
        dashboards.put(dashboardId.toString(), dashboard);

        return dashboardId;
    }

    @Override
    public void updateDashboard(DashboardId dashboardId, Map<String, Object> dashboardData) {
        // In a real implementation, this would update a database record
        if (dashboards.containsKey(dashboardId.toString())) {
            Map<String, Object> existingDashboard = dashboards.get(dashboardId.toString());

            // Update the dashboard with the provided data
            // but preserve the id and name
            String id = (String) existingDashboard.get("id");
            String name = (String) existingDashboard.get("name");

            existingDashboard.clear();
            existingDashboard.putAll(dashboardData);
            existingDashboard.put("id", id);
            existingDashboard.put("name", name);
        }
    }

    @Override
    public void deleteDashboard(DashboardId dashboardId) {
        // In a real implementation, this would delete from a database
        dashboards.remove(dashboardId.toString());
    }
}
