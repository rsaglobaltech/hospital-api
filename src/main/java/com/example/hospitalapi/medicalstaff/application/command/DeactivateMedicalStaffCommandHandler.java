package com.example.hospitalapi.medicalstaff.application.command;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.repository.MedicalStaffRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for DeactivateMedicalStaffCommand
 */
@Service
@RequiredArgsConstructor
public class DeactivateMedicalStaffCommandHandler implements CommandHandler<DeactivateMedicalStaffCommand, Void> {
    
    private final MedicalStaffRepository medicalStaffRepository;
    
    /**
     * Handle the DeactivateMedicalStaffCommand
     * @param command the command to handle
     * @return null (void)
     * @throws IllegalArgumentException if the medical staff member is not found
     */
    @Transactional
    public Void handle(DeactivateMedicalStaffCommand command) {
        // Create staff ID
        StaffId staffId = new StaffId(command.getStaffId());
        
        // Find medical staff
        MedicalStaff medicalStaff = medicalStaffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Medical staff not found with ID: " + staffId));
        
        // Deactivate medical staff
        medicalStaff.deactivate(command.getTerminationDate());
        
        // Save medical staff
        medicalStaffRepository.save(medicalStaff);
        
        return null;
    }
}