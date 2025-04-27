package com.example.hospitalapi.analytics.domain.repository;

import com.example.hospitalapi.analytics.domain.valueobject.PredictionId;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository interface for predictions
 */
public interface PredictionRepository {
    
    /**
     * Get prediction results for a specific model and context parameters
     * @param predictionModel the name of the prediction model
     * @param contextParams the context parameters for the prediction
     * @return the prediction results
     */
    Map<String, Object> getPrediction(String predictionModel, Map<String, String> contextParams);
    
    /**
     * Get a prediction by ID
     * @param predictionId the prediction ID
     * @return the prediction data if found
     */
    Optional<Map<String, Object>> findById(PredictionId predictionId);
    
    /**
     * Get all available prediction models
     * @return list of prediction model names
     */
    List<String> getAvailablePredictionModels();
    
    /**
     * Save a prediction result
     * @param predictionModel the name of the prediction model
     * @param contextParams the context parameters for the prediction
     * @param predictionResult the prediction result
     * @return the ID of the saved prediction
     */
    PredictionId savePrediction(String predictionModel, Map<String, String> contextParams, Map<String, Object> predictionResult);
    
    /**
     * Get prediction history for a patient
     * @param patientId the patient ID
     * @param predictionModel optional prediction model name to filter by
     * @return list of prediction data for the patient
     */
    List<Map<String, Object>> getPredictionHistoryForPatient(String patientId, Optional<String> predictionModel);
}