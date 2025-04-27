package com.example.hospitalapi.analytics.domain.repository;

import com.example.hospitalapi.analytics.domain.valueobject.ReportId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository interface for reports
 */
public interface ReportRepository {
    
    /**
     * Get a report by name with filter parameters
     * @param reportName the name of the report
     * @param filterParams the filter parameters
     * @return the report data
     */
    Map<String, Object> getReportByName(String reportName, Map<String, String> filterParams);
    
    /**
     * Get a report by ID
     * @param reportId the report ID
     * @return the report if found
     */
    Optional<Map<String, Object>> findById(ReportId reportId);
    
    /**
     * Get all available report names
     * @return list of report names
     */
    List<String> getAvailableReports();
    
    /**
     * Save a report
     * @param reportName the name of the report
     * @param reportData the report data
     * @return the ID of the saved report
     */
    ReportId saveReport(String reportName, Map<String, Object> reportData);
}