package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.pharmacy.domain.entity.Dispensation;
import com.example.hospitalapi.pharmacy.domain.exception.DispensationNotFoundException;
import com.example.hospitalapi.pharmacy.domain.repository.DispensationRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.DispensationId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for updating an existing dispensation
 */
@Service
@RequiredArgsConstructor
public class UpdateDispensationCommandHandler {
    
    private final DispensationRepository dispensationRepository;
    
    /**
     * Handles the command to update an existing dispensation
     * @param command the command containing updated dispensation details
     */
    @Transactional
    public void handle(UpdateDispensationCommand command) {
        DispensationId dispensationId = new DispensationId(command.getDispensationId());
        
        Dispensation dispensation = dispensationRepository.findById(dispensationId)
                .orElseThrow(() -> new DispensationNotFoundException(command.getDispensationId()));
        
        // Only notes can be updated for a dispensation
        dispensation.updateNotes(command.getNotes());
        
        dispensationRepository.save(dispensation);
    }
}