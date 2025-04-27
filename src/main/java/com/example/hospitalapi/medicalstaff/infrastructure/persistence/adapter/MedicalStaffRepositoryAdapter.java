package com.example.hospitalapi.medicalstaff.infrastructure.persistence.adapter;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.repository.MedicalStaffRepository;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Qualification;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.medicalstaff.infrastructure.persistence.entity.MedicalStaffJpaEntity;
import com.example.hospitalapi.medicalstaff.infrastructure.persistence.entity.QualificationJpaEntity;
import com.example.hospitalapi.medicalstaff.infrastructure.persistence.repository.MedicalStaffJpaRepository;
import com.example.hospitalapi.patient.domain.valueobject.Email;
import com.example.hospitalapi.patient.domain.valueobject.Name;
import com.example.hospitalapi.patient.domain.valueobject.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementing MedicalStaffRepository using JPA
 */
@Component
@RequiredArgsConstructor
public class MedicalStaffRepositoryAdapter implements MedicalStaffRepository {

    private final MedicalStaffJpaRepository medicalStaffJpaRepository;

    @Override
    public MedicalStaff save(MedicalStaff medicalStaff) {
        MedicalStaffJpaEntity medicalStaffJpaEntity = mapToJpaEntity(medicalStaff);
        MedicalStaffJpaEntity savedEntity = medicalStaffJpaRepository.save(medicalStaffJpaEntity);
        return mapToDomainEntity(savedEntity);
    }

    @Override
    public Optional<MedicalStaff> findById(StaffId id) {
        return medicalStaffJpaRepository.findById(id.toString())
                .map(this::mapToDomainEntity);
    }

    @Override
    public List<MedicalStaff> findAll() {
        return medicalStaffJpaRepository.findAll().stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalStaff> findBySpecialty(Specialty specialty) {
        return medicalStaffJpaRepository.findBySpecialty(specialty).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalStaff> findByActiveTrue() {
        return medicalStaffJpaRepository.findByActiveTrue().stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalStaff> findByActiveFalse() {
        return medicalStaffJpaRepository.findByActiveFalse().stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(StaffId id) {
        medicalStaffJpaRepository.deleteById(id.toString());
    }

    @Override
    public boolean existsById(StaffId id) {
        return medicalStaffJpaRepository.existsById(id.toString());
    }

    private MedicalStaffJpaEntity mapToJpaEntity(MedicalStaff medicalStaff) {
        List<QualificationJpaEntity> qualificationEntities = medicalStaff.getQualifications().stream()
                .map(this::mapToQualificationJpaEntity)
                .collect(Collectors.toList());

        return MedicalStaffJpaEntity.builder()
                .id(medicalStaff.getId().toString())
                .firstName(medicalStaff.getName().getFirstName())
                .lastName(medicalStaff.getName().getLastName())
                .email(medicalStaff.getEmail().toString())
                .phoneNumber(medicalStaff.getPhoneNumber().toString())
                .dateOfBirth(medicalStaff.getDateOfBirth().getValue())
                .address(medicalStaff.getAddress().getValue())
                .specialty(medicalStaff.getSpecialty())
                .qualifications(qualificationEntities)
                .active(medicalStaff.isActive())
                .hireDate(medicalStaff.getHireDate().getValue())
                .terminationDate(medicalStaff.getTerminationDate().isPresent() ? medicalStaff.getTerminationDate().get().getValue() : null)
                .build();
    }

    private QualificationJpaEntity mapToQualificationJpaEntity(Qualification qualification) {
        return QualificationJpaEntity.builder()
                .id(UUID.randomUUID().toString())
                .degree(qualification.getDegree())
                .institution(qualification.getInstitution())
                .dateObtained(qualification.getDateObtained())
                .licenseNumber(qualification.getLicenseNumber())
                .licenseExpiryDate(qualification.getLicenseExpiryDate())
                .build();
    }

    private MedicalStaff mapToDomainEntity(MedicalStaffJpaEntity entity) {
        StaffId staffId = new StaffId(UUID.fromString(entity.getId()));
        Name name = new Name(entity.getFirstName(), entity.getLastName());
        Email email = new Email(entity.getEmail());
        PhoneNumber phoneNumber = new PhoneNumber(entity.getPhoneNumber());

        // Get the first qualification to create the MedicalStaff entity
        Qualification firstQualification = mapToQualification(entity.getQualifications().get(0));

        MedicalStaff medicalStaff = new MedicalStaff(
                staffId,
                name,
                email,
                phoneNumber,
                entity.getDateOfBirth(),
                entity.getAddress(),
                entity.getSpecialty(),
                firstQualification,
                entity.getHireDate()
        );

        // Add the rest of the qualifications
        for (int i = 1; i < entity.getQualifications().size(); i++) {
            medicalStaff.addQualification(mapToQualification(entity.getQualifications().get(i)));
        }

        // Set active status
        if (!entity.isActive() && entity.getTerminationDate() != null) {
            medicalStaff.deactivate(entity.getTerminationDate());
        }

        return medicalStaff;
    }

    private Qualification mapToQualification(QualificationJpaEntity entity) {
        return new Qualification(
                entity.getDegree(),
                entity.getInstitution(),
                entity.getDateObtained(),
                entity.getLicenseNumber(),
                entity.getLicenseExpiryDate()
        );
    }
}
