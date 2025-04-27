package com.example.hospitalapi.medicalstaff.domain.event;

import com.example.hospitalapi.medicalstaff.domain.entity.MedicalStaff;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a medical staff member is updated
 */
@Getter
public class MedicalStaffUpdatedEvent extends BaseDomainEvent {
    
    private final MedicalStaff medicalStaff;
    
    /**
     * Create a new MedicalStaffUpdatedEvent
     * @param medicalStaff the medical staff member that was updated
     */
    public MedicalStaffUpdatedEvent(MedicalStaff medicalStaff) {
        super(medicalStaff.getId().toString());
        this.medicalStaff = medicalStaff;
    }
}