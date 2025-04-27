package com.example.hospitalapi.scheduling.infrastructure.persistence.adapter;

import com.example.hospitalapi.scheduling.domain.entity.AvailableSlot;
import com.example.hospitalapi.scheduling.domain.repository.AvailableSlotRepository;
import com.example.hospitalapi.scheduling.domain.valueobject.AvailableSlotId;
import com.example.hospitalapi.scheduling.infrastructure.persistence.entity.AvailableSlotJpaEntity;
import com.example.hospitalapi.scheduling.infrastructure.persistence.repository.AvailableSlotJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter for AvailableSlotRepository that uses JPA
 */
@Component
@RequiredArgsConstructor
public class AvailableSlotRepositoryAdapter implements AvailableSlotRepository {

    private final AvailableSlotJpaRepository availableSlotJpaRepository;

    @Override
    public AvailableSlot save(AvailableSlot availableSlot) {
        AvailableSlotJpaEntity availableSlotJpaEntity = mapToJpaEntity(availableSlot);
        
        // Set timestamps
        LocalDateTime now = LocalDateTime.now();
        if (availableSlotJpaEntity.getCreatedAt() == null) {
            availableSlotJpaEntity.setCreatedAt(now);
        }
        availableSlotJpaEntity.setUpdatedAt(now);
        
        AvailableSlotJpaEntity savedEntity = availableSlotJpaRepository.save(availableSlotJpaEntity);
        return mapToDomainEntity(savedEntity);
    }

    @Override
    public Optional<AvailableSlot> findById(AvailableSlotId id) {
        return availableSlotJpaRepository.findById(id.toString())
                .map(this::mapToDomainEntity);
    }

    @Override
    public List<AvailableSlot> findAll() {
        return availableSlotJpaRepository.findAll().stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailableSlot> findByDoctorId(String doctorId) {
        return availableSlotJpaRepository.findByDoctorId(doctorId).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailableSlot> findByDate(LocalDate date) {
        return availableSlotJpaRepository.findByDate(date).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailableSlot> findByDoctorIdAndDate(String doctorId, LocalDate date) {
        return availableSlotJpaRepository.findByDoctorIdAndDate(doctorId, date).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailableSlot> findAvailableByDoctorIdAndDate(String doctorId, LocalDate date) {
        return availableSlotJpaRepository.findByDoctorIdAndDateAndBooked(doctorId, date, false).stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(AvailableSlotId id) {
        availableSlotJpaRepository.deleteById(id.toString());
    }

    @Override
    public boolean existsById(AvailableSlotId id) {
        return availableSlotJpaRepository.existsById(id.toString());
    }

    /**
     * Map a domain entity to a JPA entity
     * @param availableSlot the domain entity
     * @return the JPA entity
     */
    private AvailableSlotJpaEntity mapToJpaEntity(AvailableSlot availableSlot) {
        return AvailableSlotJpaEntity.builder()
                .id(availableSlot.getId().toString())
                .doctorId(availableSlot.getDoctorId())
                .date(availableSlot.getDate())
                .startTime(availableSlot.getStartTime())
                .endTime(availableSlot.getEndTime())
                .booked(!availableSlot.isAvailable())
                .build();
    }

    /**
     * Map a JPA entity to a domain entity
     * @param jpaEntity the JPA entity
     * @return the domain entity
     */
    private AvailableSlot mapToDomainEntity(AvailableSlotJpaEntity jpaEntity) {
        AvailableSlot availableSlot = new AvailableSlot(
                new AvailableSlotId(jpaEntity.getId()),
                jpaEntity.getDoctorId(),
                jpaEntity.getDate(),
                jpaEntity.getStartTime(),
                jpaEntity.getEndTime()
        );
        
        if (jpaEntity.isBooked()) {
            availableSlot.book();
        }
        
        return availableSlot;
    }
}