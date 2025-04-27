package com.example.hospitalapi.scheduling.domain.repository;

import com.example.hospitalapi.scheduling.domain.entity.AvailableSlot;
import com.example.hospitalapi.scheduling.domain.valueobject.AvailableSlotId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for AvailableSlot entity
 */
public interface AvailableSlotRepository {
    
    /**
     * Save an available slot
     * @param availableSlot the available slot to save
     * @return the saved available slot
     */
    AvailableSlot save(AvailableSlot availableSlot);
    
    /**
     * Find an available slot by ID
     * @param id the available slot ID
     * @return the available slot if found
     */
    Optional<AvailableSlot> findById(AvailableSlotId id);
    
    /**
     * Find all available slots
     * @return list of all available slots
     */
    List<AvailableSlot> findAll();
    
    /**
     * Find available slots by doctor ID
     * @param doctorId the doctor ID
     * @return list of available slots for the doctor
     */
    List<AvailableSlot> findByDoctorId(String doctorId);
    
    /**
     * Find available slots by date
     * @param date the date
     * @return list of available slots for the date
     */
    List<AvailableSlot> findByDate(LocalDate date);
    
    /**
     * Find available slots by doctor ID and date
     * @param doctorId the doctor ID
     * @param date the date
     * @return list of available slots for the doctor and date
     */
    List<AvailableSlot> findByDoctorIdAndDate(String doctorId, LocalDate date);
    
    /**
     * Find available (not booked) slots by doctor ID and date
     * @param doctorId the doctor ID
     * @param date the date
     * @return list of available slots for the doctor and date
     */
    List<AvailableSlot> findAvailableByDoctorIdAndDate(String doctorId, LocalDate date);
    
    /**
     * Delete an available slot
     * @param id the available slot ID
     */
    void deleteById(AvailableSlotId id);
    
    /**
     * Check if an available slot exists
     * @param id the available slot ID
     * @return true if the available slot exists
     */
    boolean existsById(AvailableSlotId id);
}