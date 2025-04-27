package com.example.hospitalapi.pharmacy.application.command;

import com.example.hospitalapi.pharmacy.domain.exception.DispensationNotFoundException;
import com.example.hospitalapi.pharmacy.domain.exception.PharmacyValidationException;
import com.example.hospitalapi.pharmacy.domain.repository.DispensationRepository;
import com.example.hospitalapi.pharmacy.domain.valueobject.DispensationId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Handler for deleting a dispensation
 */
@Service
@RequiredArgsConstructor
public class DeleteDispensationCommandHandler implements CommandHandler<DeleteDispensationCommand, Void> {

    private final DispensationRepository dispensationRepository;

    /**
     * Handles the command to delete a dispensation
     * @param command the command containing the dispensation ID to delete
     * @return null
     */
    @Transactional
    public Void handle(DeleteDispensationCommand command) {
        DispensationId dispensationId = new DispensationId(command.getDispensationId());

        // Check if dispensation exists
        var dispensation = dispensationRepository.findById(dispensationId)
                .orElseThrow(() -> new DispensationNotFoundException(command.getDispensationId()));

        // Only allow deletion within 24 hours of creation
        LocalDateTime now = LocalDateTime.now();
        long hoursSinceCreation = ChronoUnit.HOURS.between(dispensation.getDispensationDateTime().getValue(), now);

        if (hoursSinceCreation > 24) {
            throw new PharmacyValidationException(
                    "Cannot delete dispensation after 24 hours. Dispensation ID: " + command.getDispensationId());
        }

        dispensationRepository.deleteById(dispensationId);
        return null;
    }
}
