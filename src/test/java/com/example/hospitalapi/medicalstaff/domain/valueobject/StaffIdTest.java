package com.example.hospitalapi.medicalstaff.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StaffIdTest {

    @Test
    void testCreateStaffIdWithNoArguments() {
        // Act
        StaffId staffId = new StaffId();
        
        // Assert
        assertNotNull(staffId);
        assertNotNull(staffId.getValue());
    }
    
    @Test
    void testCreateStaffIdWithUUID() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        
        // Act
        StaffId staffId = new StaffId(uuid);
        
        // Assert
        assertNotNull(staffId);
        assertEquals(uuid, staffId.getValue());
    }
    
    @Test
    void testCreateStaffIdWithString() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        
        // Act
        StaffId staffId = new StaffId(uuidString);
        
        // Assert
        assertNotNull(staffId);
        assertEquals(uuid, staffId.getValue());
    }
    
    @Test
    void testCreateStaffIdWithInvalidString() {
        // Arrange
        String invalidUuid = "not-a-uuid";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new StaffId(invalidUuid);
        });
    }
    
    @Test
    void testStaffIdEquality() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        StaffId staffId1 = new StaffId(uuid);
        StaffId staffId2 = new StaffId(uuid);
        StaffId staffId3 = new StaffId();
        
        // Assert
        assertEquals(staffId1, staffId2);
        assertNotEquals(staffId1, staffId3);
        assertNotEquals(staffId2, staffId3);
    }
    
    @Test
    void testStaffIdToString() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        StaffId staffId = new StaffId(uuid);
        
        // Act
        String result = staffId.toString();
        
        // Assert
        assertEquals(uuid.toString(), result);
    }
}