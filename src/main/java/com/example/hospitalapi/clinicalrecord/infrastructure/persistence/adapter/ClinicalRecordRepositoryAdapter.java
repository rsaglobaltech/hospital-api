package com.example.hospitalapi.clinicalrecord.infrastructure.persistence.adapter;

import com.example.hospitalapi.clinicalrecord.domain.entity.ClinicalRecord;
import com.example.hospitalapi.clinicalrecord.domain.repository.ClinicalRecordRepository;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordId;
import com.example.hospitalapi.clinicalrecord.domain.valueobject.ClinicalRecordType;
import com.example.hospitalapi.clinicalrecord.infrastructure.persistence.entity.ClinicalRecordJpaEntity;
import com.example.hospitalapi.clinicalrecord.infrastructure.persistence.repository.ClinicalRecordJpaRepository;
import com.example.hospitalapi.patient.domain.valueobject.PatientId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter for ClinicalRecordRepository that uses JPA
 */
@Component
public class ClinicalRecordRepositoryAdapter implements ClinicalRecordRepository {

    private final ClinicalRecordJpaRepository clinicalRecordJpaRepository;

    public ClinicalRecordRepositoryAdapter(ClinicalRecordJpaRepository clinicalRecordJpaRepository) {
        this.clinicalRecordJpaRepository = clinicalRecordJpaRepository;
    }

    @Override
    public ClinicalRecord save(ClinicalRecord clinicalRecord) {
        ClinicalRecordJpaEntity clinicalRecordJpaEntity = mapToJpaEntity(clinicalRecord);
        ClinicalRecordJpaEntity savedEntity = clinicalRecordJpaRepository.save(clinicalRecordJpaEntity);
        return mapToDomainEntity(savedEntity);
    }

    @Override
    public Optional<ClinicalRecord> findById(ClinicalRecordId id) {
        return clinicalRecordJpaRepository.findById(id.toString())
                .map(this::mapToDomainEntity);
    }

    @Override
    public List<ClinicalRecord> findAll() {
        return clinicalRecordJpaRepository.findAll().stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClinicalRecord> findByPatientId(PatientId patientId) {
        return clinicalRecordJpaRepository.findByPatientId(patientId.toString()).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClinicalRecord> findByDoctorId(String doctorId) {
        return clinicalRecordJpaRepository.findByDoctorId(doctorId).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClinicalRecord> findByType(ClinicalRecordType type) {
        return clinicalRecordJpaRepository.findByType(type).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClinicalRecord> findByPatientIdAndType(PatientId patientId, ClinicalRecordType type) {
        return clinicalRecordJpaRepository.findByPatientIdAndType(patientId.toString(), type).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClinicalRecord> findByPatientIdAndCreatedAtBetween(PatientId patientId, LocalDateTime startDate, LocalDateTime endDate) {
        return clinicalRecordJpaRepository.findByPatientIdAndCreatedAtBetween(patientId.toString(), startDate, endDate).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(ClinicalRecordId id) {
        clinicalRecordJpaRepository.deleteById(id.toString());
    }

    @Override
    public boolean existsById(ClinicalRecordId id) {
        return clinicalRecordJpaRepository.existsById(id.toString());
    }

    /**
     * Map a domain entity to a JPA entity
     * @param clinicalRecord the domain entity
     * @return the JPA entity
     */
    private ClinicalRecordJpaEntity mapToJpaEntity(ClinicalRecord clinicalRecord) {
        ClinicalRecordJpaEntity jpaEntity = new ClinicalRecordJpaEntity();
        jpaEntity.setId(clinicalRecord.getId().toString());
        jpaEntity.setPatientId(clinicalRecord.getPatientId().toString());
        jpaEntity.setDoctorId(clinicalRecord.getDoctorId().getValue().toString());
        jpaEntity.setType(clinicalRecord.getType());
        jpaEntity.setTitle(clinicalRecord.getTitle().getValue());
        jpaEntity.setDescription(clinicalRecord.getDescription().getValue());
        jpaEntity.setAttachmentUrl(clinicalRecord.getAttachmentUrl().getValue());
        jpaEntity.setCreatedAt(clinicalRecord.getCreatedAt().getValue());
        jpaEntity.setUpdatedAt(clinicalRecord.getUpdatedAt().getValue());
        return jpaEntity;
    }

    /**
     * Map a JPA entity to a domain entity
     * @param jpaEntity the JPA entity
     * @return the domain entity
     */
    private ClinicalRecord mapToDomainEntity(ClinicalRecordJpaEntity jpaEntity) {
        ClinicalRecord clinicalRecord = new ClinicalRecord(
                new ClinicalRecordId(jpaEntity.getId()),
                new PatientId(jpaEntity.getPatientId()),
                jpaEntity.getDoctorId(),
                jpaEntity.getType(),
                jpaEntity.getTitle(),
                jpaEntity.getDescription()
        );
        
        if (jpaEntity.getAttachmentUrl() != null && !jpaEntity.getAttachmentUrl().isEmpty()) {
            clinicalRecord.updateAttachmentUrl(jpaEntity.getAttachmentUrl());
        }
        
        return clinicalRecord;
    }
}