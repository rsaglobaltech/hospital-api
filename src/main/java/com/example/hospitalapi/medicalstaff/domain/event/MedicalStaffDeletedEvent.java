package com.example.hospitalapi.medicalstaff.domain.event;

import com.example.hospitalapi.medicalstaff.domain.valueobject.StaffId;
import com.example.hospitalapi.shared.domain.event.BaseDomainEvent;
import lombok.Getter;

/**
 * Event that is published when a medical staff member is deleted
 */
@Getter
public class MedicalStaffDeletedEvent extends BaseDomainEvent {
    
    private final StaffId staffId;
    
    /**
     * Create a new MedicalStaffDeletedEvent
     * @param staffId the ID of the medical staff member that was deleted
     */
    public MedicalStaffDeletedEvent(StaffId staffId) {
        super(staffId.toString());
        this.staffId = staffId;
    }
}