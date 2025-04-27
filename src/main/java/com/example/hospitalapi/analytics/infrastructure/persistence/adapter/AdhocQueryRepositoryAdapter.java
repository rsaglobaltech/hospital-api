package com.example.hospitalapi.analytics.infrastructure.persistence.adapter;

import com.example.hospitalapi.analytics.domain.repository.AdhocQueryRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Adapter implementation for AdhocQueryRepository
 */
@Component
public class AdhocQueryRepositoryAdapter implements AdhocQueryRepository {

    // In a real implementation, this would use a database or data warehouse
    // This is just a mock implementation for demonstration purposes
    private final Map<String, Map<String, Object>> savedQueries = new HashMap<>();
    private final Map<String, Map<String, String>> tableSchemas = new HashMap<>();

    public AdhocQueryRepositoryAdapter() {
        // Initialize some mock table schemas
        Map<String, String> admissionsSchema = new HashMap<>();
        admissionsSchema.put("id", "UUID");
        admissionsSchema.put("patient_id", "UUID");
        admissionsSchema.put("admission_date", "TIMESTAMP");
        admissionsSchema.put("discharge_date", "TIMESTAMP");
        admissionsSchema.put("length_of_stay", "INTEGER");
        admissionsSchema.put("diagnosis_code", "STRING");
        admissionsSchema.put("department", "STRING");
        admissionsSchema.put("is_readmission", "BOOLEAN");
        admissionsSchema.put("discharge_disposition", "STRING");
        tableSchemas.put("admissions", admissionsSchema);

        Map<String, String> patientsSchema = new HashMap<>();
        patientsSchema.put("id", "UUID");
        patientsSchema.put("name", "STRING");
        patientsSchema.put("date_of_birth", "DATE");
        patientsSchema.put("gender", "STRING");
        patientsSchema.put("address", "STRING");
        patientsSchema.put("phone", "STRING");
        patientsSchema.put("email", "STRING");
        tableSchemas.put("patients", patientsSchema);

        Map<String, String> appointmentsSchema = new HashMap<>();
        appointmentsSchema.put("id", "UUID");
        appointmentsSchema.put("patient_id", "UUID");
        appointmentsSchema.put("doctor_id", "UUID");
        appointmentsSchema.put("start_time", "TIMESTAMP");
        appointmentsSchema.put("end_time", "TIMESTAMP");
        appointmentsSchema.put("reason", "STRING");
        appointmentsSchema.put("notes", "STRING");
        appointmentsSchema.put("status", "STRING");
        tableSchemas.put("appointments", appointmentsSchema);
    }

    @Override
    public List<Map<String, Object>> executeQuery(String query, Map<String, Object> parameters) {
        // In a real implementation, this would execute the query against a database
        // For demonstration, generate mock results based on the query
        
        // Simple query parsing to determine what kind of data to return
        List<Map<String, Object>> results = new ArrayList<>();
        
        if (query.toLowerCase().contains("admissions")) {
            // Generate mock admission data
            for (int i = 0; i < 10; i++) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", UUID.randomUUID().toString());
                row.put("patient_id", UUID.randomUUID().toString());
                row.put("admission_date", "2023-01-" + (i + 1) + "T10:00:00");
                row.put("discharge_date", "2023-01-" + (i + 5) + "T14:00:00");
                row.put("length_of_stay", 4);
                row.put("diagnosis_code", "ICD-" + (100 + i));
                row.put("department", List.of("Cardiology", "Neurology", "Orthopedics", "Pediatrics").get(i % 4));
                row.put("is_readmission", i % 3 == 0);
                row.put("discharge_disposition", "Home");
                results.add(row);
            }
        } else if (query.toLowerCase().contains("patients")) {
            // Generate mock patient data
            for (int i = 0; i < 10; i++) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", UUID.randomUUID().toString());
                row.put("name", "Patient " + (i + 1));
                row.put("date_of_birth", "19" + (70 + i % 30) + "-" + (i % 12 + 1) + "-" + (i % 28 + 1));
                row.put("gender", i % 2 == 0 ? "Male" : "Female");
                row.put("address", "123 Main St, City");
                row.put("phone", "555-123-" + (1000 + i));
                row.put("email", "patient" + (i + 1) + "@example.com");
                results.add(row);
            }
        } else if (query.toLowerCase().contains("appointments")) {
            // Generate mock appointment data
            for (int i = 0; i < 10; i++) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", UUID.randomUUID().toString());
                row.put("patient_id", UUID.randomUUID().toString());
                row.put("doctor_id", UUID.randomUUID().toString());
                row.put("start_time", "2023-02-" + (i + 1) + "T09:00:00");
                row.put("end_time", "2023-02-" + (i + 1) + "T09:30:00");
                row.put("reason", "Checkup");
                row.put("notes", "Regular checkup");
                row.put("status", List.of("SCHEDULED", "COMPLETED", "CANCELLED").get(i % 3));
                results.add(row);
            }
        } else {
            // Generic results
            for (int i = 0; i < 5; i++) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", i + 1);
                row.put("name", "Item " + (i + 1));
                row.put("value", Math.random() * 100);
                results.add(row);
            }
        }
        
        // Apply parameters if provided
        if (parameters != null && !parameters.isEmpty()) {
            // This is a very simplified parameter application
            // In a real implementation, this would be much more sophisticated
            for (Map.Entry<String, Object> param : parameters.entrySet()) {
                String key = param.getKey();
                Object value = param.getValue();
                
                // Filter results based on parameter
                results = results.stream()
                        .filter(row -> row.containsKey(key) && 
                                (value == null || value.toString().equals(row.get(key).toString())))
                        .toList();
            }
        }
        
        return results;
    }

    @Override
    public boolean validateQuery(String query) {
        // In a real implementation, this would validate the query syntax and security
        // For demonstration, perform some basic validation
        
        // Check for SQL injection attempts
        if (containsSqlInjection(query)) {
            return false;
        }
        
        // Check for basic SQL syntax
        if (!query.toLowerCase().contains("select")) {
            return false;
        }
        
        // Check for dangerous operations
        if (query.toLowerCase().contains("drop") || 
            query.toLowerCase().contains("delete") || 
            query.toLowerCase().contains("update") || 
            query.toLowerCase().contains("insert") || 
            query.toLowerCase().contains("alter") || 
            query.toLowerCase().contains("truncate")) {
            return false;
        }
        
        return true;
    }
    
    private boolean containsSqlInjection(String query) {
        // Very basic SQL injection detection
        // In a real implementation, this would be much more sophisticated
        Pattern pattern = Pattern.compile("'\\s*or\\s*'\\s*=\\s*'", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(query).find();
    }

    @Override
    public List<String> getAvailableTables() {
        // Return all available table names
        return new ArrayList<>(tableSchemas.keySet());
    }

    @Override
    public Map<String, String> getTableSchema(String tableName) {
        // Return the schema for the specified table
        return tableSchemas.getOrDefault(tableName, new HashMap<>());
    }

    @Override
    public void saveQuery(String queryName, String query, String description) {
        // In a real implementation, this would save to a database
        Map<String, Object> savedQuery = new HashMap<>();
        savedQuery.put("name", queryName);
        savedQuery.put("query", query);
        savedQuery.put("description", description);
        savedQuery.put("created", new Date());
        
        savedQueries.put(queryName, savedQuery);
    }

    @Override
    public Map<String, Object> getSavedQuery(String queryName) {
        // Return the saved query if found
        return savedQueries.getOrDefault(queryName, Map.of("error", "Query not found: " + queryName));
    }

    @Override
    public List<Map<String, Object>> getAllSavedQueries() {
        // Return all saved queries
        return new ArrayList<>(savedQueries.values());
    }
}