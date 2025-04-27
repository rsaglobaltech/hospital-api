package com.example.hospitalapi.medicalstaff.domain.event;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a medical staff member is created
 */
@Getter
public class MedicalStaffCreatedEvent extends BaseDomainEvent {
    
    private final MedicalStaff medicalStaff;
    
    /**
     * Create a new MedicalStaffCreatedEvent
     * @param medicalStaff the medical staff member that was created
     */
    public MedicalStaffCreatedEvent(MedicalStaff medicalStaff) {
        super(medicalStaff.getId().toString());
        this.medicalStaff = medicalStaff;
    }
}