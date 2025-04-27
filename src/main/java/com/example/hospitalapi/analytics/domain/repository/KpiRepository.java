package com.example.hospitalapi.analytics.domain.repository;

import com.example.hospitalapi.analytics.domain.valueobject.KpiId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository interface for KPIs
 */
public interface KpiRepository {
    
    /**
     * Get KPI values by names and time period
     * @param kpiNames list of KPI names
     * @param timePeriod the time period for which to calculate the KPIs
     * @return map of KPI names to their values
     */
    Map<String, Object> getKpiValues(List<String> kpiNames, String timePeriod);
    
    /**
     * Get a KPI by ID
     * @param kpiId the KPI ID
     * @return the KPI data if found
     */
    Optional<Map<String, Object>> findById(KpiId kpiId);
    
    /**
     * Get a KPI by name
     * @param kpiName the KPI name
     * @return the KPI data if found
     */
    Optional<Map<String, Object>> findByName(String kpiName);
    
    /**
     * Get all available KPIs
     * @return list of KPI data
     */
    List<Map<String, Object>> findAll();
    
    /**
     * Save a KPI definition
     * @param kpiName the name of the KPI
     * @param kpiDefinition the KPI definition
     * @return the ID of the saved KPI
     */
    KpiId saveKpi(String kpiName, Map<String, Object> kpiDefinition);
    
    /**
     * Update a KPI definition
     * @param kpiId the KPI ID
     * @param kpiDefinition the updated KPI definition
     */
    void updateKpi(KpiId kpiId, Map<String, Object> kpiDefinition);
    
    /**
     * Delete a KPI
     * @param kpiId the KPI ID
     */
    void deleteKpi(KpiId kpiId);
}