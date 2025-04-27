package com.example.hospitalapi.pharmacy.application.query;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response object for dispensation queries
 */
@Getter
@Builder
public class DispensationResponse {
    private final String id;
    private final String prescriptionId;
    private final String patientId;
    private final String pharmacistId;
    private final LocalDateTime dispensationDateTime;
    private final List<DispensationItemResponse> items;
    private final BigDecimal totalAmount;
    private final String notes;
    
    /**
     * Response object for dispensation item
     */
    @Getter
    @Builder
    public static class DispensationItemResponse {
        private final String medicationId;
        private final String medicationName;
        private final int quantity;
        private final BigDecimal unitPrice;
        private final BigDecimal subtotal;
        private final String batchNumber;
    }
}