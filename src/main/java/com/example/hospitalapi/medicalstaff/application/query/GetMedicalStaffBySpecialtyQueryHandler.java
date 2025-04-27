package com.example.hospitalapi.medicalstaff.application.query;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.repository.MedicalStaffRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Qualification;
import com.example.hospitalapi.shared.domain.bus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler for GetMedicalStaffBySpecialtyQuery
 */
@Service
@RequiredArgsConstructor
public class GetMedicalStaffBySpecialtyQueryHandler implements QueryHandler<GetMedicalStaffBySpecialtyQuery, List<MedicalStaffResponse>> {

    private final MedicalStaffRepository medicalStaffRepository;

    /**
     * Handle the GetMedicalStaffBySpecialtyQuery
     * @param query the query to handle
     * @return list of medical staff members with the given specialty
     */
    @Transactional(readOnly = true)
    public List<MedicalStaffResponse> handle(GetMedicalStaffBySpecialtyQuery query) {
        // Find medical staff by specialty
        List<MedicalStaff> medicalStaffList = medicalStaffRepository.findBySpecialty(query.getSpecialty());

        // Map to response
        return medicalStaffList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
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
                .active(medicalStaff.getStatus().isActive())
                .hireDate(medicalStaff.getHireDate().getValue())
                .terminationDate(medicalStaff.getTerminationDate().isPresent() ? medicalStaff.getTerminationDate().get().getValue() : null)
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
