package com.example.hospitalapi.medicalstaff.application.command;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.repository.MedicalStaffRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for UpdateMedicalStaffCommand
 */
@Service
@RequiredArgsConstructor
public class UpdateMedicalStaffCommandHandler implements CommandHandler<UpdateMedicalStaffCommand, Void> {
    
    private final MedicalStaffRepository medicalStaffRepository;
    
    /**
     * Handle the UpdateMedicalStaffCommand
     * @param command the command to handle
     * @return null (void)
     * @throws IllegalArgumentException if the medical staff member is not found
     */
    @Transactional
    public Void handle(UpdateMedicalStaffCommand command) {
        // Create staff ID
        StaffId staffId = new StaffId(command.getStaffId());
        
        // Find medical staff
        MedicalStaff medicalStaff = medicalStaffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Medical staff not found with ID: " + staffId));
        
        // Create value objects
        Name name = new Name(command.getFirstName(), command.getLastName());
        Email email = new Email(command.getEmail());
        PhoneNumber phoneNumber = new PhoneNumber(command.getPhoneNumber());
        
        // Update medical staff
        medicalStaff.updateName(name);
        medicalStaff.updateEmail(email);
        medicalStaff.updatePhoneNumber(phoneNumber);
        medicalStaff.updateAddress(command.getAddress());
        medicalStaff.updateSpecialty(command.getSpecialty());
        
        // Save medical staff
        medicalStaffRepository.save(medicalStaff);
        
        return null;
    }
}