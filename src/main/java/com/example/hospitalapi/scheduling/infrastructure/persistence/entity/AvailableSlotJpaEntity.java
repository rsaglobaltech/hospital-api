package com.example.hospitalapi.scheduling.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

/**
 * JPA entity for AvailableSlot
 */
@Entity
@Table(name = "available_slots")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableSlotJpaEntity {
    
    @Id
    private String id;
    
    @Column(name = "doctor_id", nullable = false)
    private String doctorId;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;
    
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
    
    @Column(nullable = false)
    private boolean booked;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}