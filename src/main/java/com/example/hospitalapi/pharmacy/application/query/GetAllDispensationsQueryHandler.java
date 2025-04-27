package com.example.hospitalapi.pharmacy.application.query;

import com.example.hospitalapi.pharmacy.domain.entity.Dispensation;
import com.example.hospitalapi.pharmacy.domain.repository.DispensationRepository;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for retrieving all dispensations
 */
@Service
@RequiredArgsConstructor
public class GetAllDispensationsQueryHandler implements QueryHandler<GetAllDispensationsQuery, List<DispensationResponse>> {

    private final DispensationRepository dispensationRepository;

    /**
     * Handles the query to retrieve all dispensations
     * @param query the query
     * @return list of dispensation responses
     */
    @Transactional(readOnly = true)
    public List<DispensationResponse> handle(GetAllDispensationsQuery query) {
        List<Dispensation> dispensations = dispensationRepository.findAll();

        return dispensations.stream()
                .map(dispensation -> {
                    List<DispensationResponse.DispensationItemResponse> itemResponses = dispensation.getItems().stream()
                            .map(item -> DispensationResponse.DispensationItemResponse.builder()
                                    .medicationId(item.getMedicationId().toString())
                                    .medicationName(item.getMedicationName().toString())
                                    .quantity(item.getQuantity().getValue())
                                    .unitPrice(item.getUnitPrice().getValue())
                                    .subtotal(item.getSubtotal().getValue())
                                    .batchNumber(item.getBatchNumber().toString())
                                    .build())
                            .collect(Collectors.toList());

                    return DispensationResponse.builder()
                            .id(dispensation.getId().toString())
                            .prescriptionId(dispensation.getPrescriptionId().toString())
                            .patientId(dispensation.getPatientId().toString())
                            .pharmacistId(dispensation.getPharmacistId().toString())
                            .dispensationDateTime(dispensation.getDispensationDateTime().getValue())
                            .items(itemResponses)
                            .totalAmount(dispensation.getTotalAmount().getValue())
                            .notes(dispensation.getNotes().toString())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
