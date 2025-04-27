package com.example.hospitalapi.medicalstaff.domain.repository;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.medicalstaff.domain.valueobject.Specialty;
import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for MedicalStaff entity
 */
public interface MedicalStaffRepository {
    
    /**
     * Save a medical staff member
     * @param medicalStaff the medical staff member to save
     * @return the saved medical staff member
     */
    MedicalStaff save(MedicalStaff medicalStaff);
    
    /**
     * Find a medical staff member by ID
     * @param id the staff ID
     * @return the medical staff member if found
     */
    Optional<MedicalStaff> findById(StaffId id);
    
    /**
     * Find all medical staff members
     * @return list of all medical staff members
     */
    List<MedicalStaff> findAll();
    
    /**
     * Find medical staff members by specialty
     * @param specialty the specialty
     * @return list of medical staff members with the given specialty
     */
    List<MedicalStaff> findBySpecialty(Specialty specialty);
    
    /**
     * Find active medical staff members
     * @return list of active medical staff members
     */
    List<MedicalStaff> findByActiveTrue();
    
    /**
     * Find inactive medical staff members
     * @return list of inactive medical staff members
     */
    List<MedicalStaff> findByActiveFalse();
    
    /**
     * Delete a medical staff member
     * @param id the staff ID
     */
    void deleteById(StaffId id);
    
    /**
     * Check if a medical staff member exists
     * @param id the staff ID
     * @return true if the medical staff member exists
     */
    boolean existsById(StaffId id);
}