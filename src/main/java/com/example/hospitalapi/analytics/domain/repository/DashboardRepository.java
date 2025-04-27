package com.example.hospitalapi.analytics.domain.repository;

import com.example.hospitalapi.analytics.domain.valueobject.DashboardId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository interface for dashboards
 */
public interface DashboardRepository {
    
    /**
     * Get a dashboard by ID
     * @param dashboardId the dashboard ID
     * @return the dashboard data if found
     */
    Optional<Map<String, Object>> findById(DashboardId dashboardId);
    
    /**
     * Get a dashboard by name
     * @param dashboardName the dashboard name
     * @return the dashboard data if found
     */
    Optional<Map<String, Object>> findByName(String dashboardName);
    
    /**
     * Get all available dashboards
     * @return list of dashboard data
     */
    List<Map<String, Object>> findAll();
    
    /**
     * Save a dashboard
     * @param dashboardName the name of the dashboard
     * @param dashboardData the dashboard data
     * @return the ID of the saved dashboard
     */
    DashboardId saveDashboard(String dashboardName, Map<String, Object> dashboardData);
    
    /**
     * Update a dashboard
     * @param dashboardId the dashboard ID
     * @param dashboardData the updated dashboard data
     */
    void updateDashboard(DashboardId dashboardId, Map<String, Object> dashboardData);
    
    /**
     * Delete a dashboard
     * @param dashboardId the dashboard ID
     */
    void deleteDashboard(DashboardId dashboardId);
}