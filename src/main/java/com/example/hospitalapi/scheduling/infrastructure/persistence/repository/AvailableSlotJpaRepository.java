package com.example.hospitalapi.scheduling.infrastructure.persistence.repository;

import com.example.hospitalapi.scheduling.infrastructure.persistence.entity.AvailableSlotJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA repository for AvailableSlot entity
 */
@Repository
public interface AvailableSlotJpaRepository extends JpaRepository<AvailableSlotJpaEntity, String> {
    
    /**
     * Find available slots by doctor ID
     * @param doctorId the doctor ID
     * @return list of available slots for the doctor
     */
    List<AvailableSlotJpaEntity> findByDoctorId(String doctorId);
    
    /**
     * Find available slots by date
     * @param date the date
     * @return list of available slots for the date
     */
    List<AvailableSlotJpaEntity> findByDate(LocalDate date);
    
    /**
     * Find available slots by doctor ID and date
     * @param doctorId the doctor ID
     * @param date the date
     * @return list of available slots for the doctor and date
     */
    List<AvailableSlotJpaEntity> findByDoctorIdAndDate(String doctorId, LocalDate date);
    
    /**
     * Find available (not booked) slots by doctor ID and date
     * @param doctorId the doctor ID
     * @param date the date
     * @param booked the booked status
     * @return list of available slots for the doctor and date
     */
    List<AvailableSlotJpaEntity> findByDoctorIdAndDateAndBooked(String doctorId, LocalDate date, boolean booked);
}