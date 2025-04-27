package com.example.hospitalapi.medicalstaff.application.command;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.repository.MedicalStaffRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Qualification;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import com.example.hospitalapi.shared.domain.bus.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler for CreateMedicalStaffCommand
 */
@Service
@RequiredArgsConstructor
public class CreateMedicalStaffCommandHandler implements CommandHandler<CreateMedicalStaffCommand, StaffId> {
    
    private final MedicalStaffRepository medicalStaffRepository;
    
    /**
     * Handle the CreateMedicalStaffCommand
     * @param command the command to handle
     * @return the ID of the created medical staff member
     */
    @Transactional
    public StaffId handle(CreateMedicalStaffCommand command) {
        // Create value objects
        StaffId staffId = new StaffId();
        Name name = new Name(command.getFirstName(), command.getLastName());
        Email email = new Email(command.getEmail());
        PhoneNumber phoneNumber = new PhoneNumber(command.getPhoneNumber());
        
        // Create qualification
        Qualification qualification = new Qualification(
                command.getDegree(),
                command.getInstitution(),
                command.getDateObtained(),
                command.getLicenseNumber(),
                command.getLicenseExpiryDate()
        );
        
        // Create medical staff entity
        MedicalStaff medicalStaff = new MedicalStaff(
                staffId,
                name,
                email,
                phoneNumber,
                command.getDateOfBirth(),
                command.getAddress(),
                command.getSpecialty(),
                qualification,
                command.getHireDate()
        );
        
        // Save medical staff
        MedicalStaff savedMedicalStaff = medicalStaffRepository.save(medicalStaff);
        
        return savedMedicalStaff.getId();
    }
}