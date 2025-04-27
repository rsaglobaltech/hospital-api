package com.example.hospitalapi.medicalstaff.application.query;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.repository.MedicalStaffRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Qualification;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for GetMedicalStaffByIdQuery
 */
@Service
@RequiredArgsConstructor
public class GetMedicalStaffByIdQueryHandler implements QueryHandler<GetMedicalStaffByIdQuery, MedicalStaffResponse> {
    
    private final MedicalStaffRepository medicalStaffRepository;
    
    /**
     * Handle the GetMedicalStaffByIdQuery
     * @param query the query to handle
     * @return the medical staff data
     * @throws IllegalArgumentException if the medical staff member is not found
     */
    @Transactional(readOnly = true)
    public MedicalStaffResponse handle(GetMedicalStaffByIdQuery query) {
        // Create staff ID
        StaffId staffId = new StaffId(query.getStaffId());
        
        // Find medical staff
        MedicalStaff medicalStaff = medicalStaffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Medical staff not found with ID: " + staffId));
        
        // Map to response
        return mapToResponse(medicalStaff);
    }
    
    private MedicalStaffResponse mapToResponse(MedicalStaff medicalStaff) {
        List<MedicalStaffResponse.QualificationResponse> qualificationResponses = 
                mapQualificationsToResponses(medicalStaff.getQualifications());
        
        return MedicalStaffResponse.builder()
                .id(medicalStaff.getId().toString())
                .firstName(medicalStaff.getName().getFirstName())
                .lastName(medicalStaff.getName().getLastName())
                .email(medicalStaff.getEmail().toString())
                .phoneNumber(medicalStaff.getPhoneNumber().toString())
                .dateOfBirth(medicalStaff.getDateOfBirth().getValue())
                .address(medicalStaff.getAddress().getValue())
                .specialty(medicalStaff.getSpecialty())
                .qualifications(qualificationResponses)
                .active(medicalStaff.isActive())
                .hireDate(medicalStaff.getHireDate().getValue())
                .terminationDate(medicalStaff.getTerminationDate().orElseThrow().getValue())
                .build();
    }
    
    private List<MedicalStaffResponse.QualificationResponse> mapQualificationsToResponses(
            List<Qualification> qualifications) {
        return qualifications.stream()
                .map(this::mapQualificationToResponse)
                .collect(Collectors.toList());
    }
    
    private MedicalStaffResponse.QualificationResponse mapQualificationToResponse(Qualification qualification) {
        return MedicalStaffResponse.QualificationResponse.builder()
                .degree(qualification.getDegree())
                .institution(qualification.getInstitution())
                .dateObtained(qualification.getDateObtained())
                .licenseNumber(qualification.getLicenseNumber())
                .licenseExpiryDate(qualification.getLicenseExpiryDate())
                .expired(qualification.isExpired())
                .build();
    }
}