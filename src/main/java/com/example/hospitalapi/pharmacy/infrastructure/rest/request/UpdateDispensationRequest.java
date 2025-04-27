package com.example.hospitalapi.pharmacy.infrastructure.rest.request;

import lombok.Data;

/**
 * Request object for updating an existing dispensation
 */
@Data
public class UpdateDispensationRequest {
    
    private String notes;
}