package com.example.hospitalapi.analytics.domain.repository;

import java.util.List;
import java.util.Map;

/**
 * Repository interface for ad-hoc queries
 */
public interface AdhocQueryRepository {
    
    /**
     * Execute an ad-hoc query
     * @param query the query to execute
     * @param parameters the query parameters
     * @return the query results
     */
    List<Map<String, Object>> executeQuery(String query, Map<String, Object> parameters);
    
    /**
     * Validate an ad-hoc query
     * @param query the query to validate
     * @return true if the query is valid
     */
    boolean validateQuery(String query);
    
    /**
     * Get the available tables for ad-hoc queries
     * @return list of table names
     */
    List<String> getAvailableTables();
    
    /**
     * Get the schema for a table
     * @param tableName the table name
     * @return map of column names to their types
     */
    Map<String, String> getTableSchema(String tableName);
    
    /**
     * Save a query for future use
     * @param queryName the name of the query
     * @param query the query to save
     * @param description the query description
     */
    void saveQuery(String queryName, String query, String description);
    
    /**
     * Get a saved query by name
     * @param queryName the name of the query
     * @return the saved query if found
     */
    Map<String, Object> getSavedQuery(String queryName);
    
    /**
     * Get all saved queries
     * @return list of saved queries
     */
    List<Map<String, Object>> getAllSavedQueries();
}