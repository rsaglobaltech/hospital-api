package com.example.hospitalapi.patient.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {
    
    @Test
    void testCreateName() {
        String firstName = "John";
        String lastName = "Doe";
        
        Name name = new Name(firstName, lastName);
        
        assertEquals(firstName, name.getFirstName());
        assertEquals(lastName, name.getLastName());
        assertEquals(firstName + " " + lastName, name.toString());
    }
    
    @Test
    void testCreateNameWithNullFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Name(null, "Doe");
        });
    }
    
    @Test
    void testCreateNameWithEmptyFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Name("", "Doe");
        });
    }
    
    @Test
    void testCreateNameWithBlankFirstName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Name("   ", "Doe");
        });
    }
    
    @Test
    void testCreateNameWithNullLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Name("John", null);
        });
    }
    
    @Test
    void testCreateNameWithEmptyLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Name("John", "");
        });
    }
    
    @Test
    void testCreateNameWithBlankLastName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Name("John", "   ");
        });
    }
    
    @Test
    void testNameEquality() {
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("John", "Doe");
        Name name3 = new Name("Jane", "Doe");
        
        assertEquals(name1, name2);
        assertNotEquals(name1, name3);
        assertNotEquals(name2, name3);
    }
    
    @Test
    void testNameHashCode() {
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("John", "Doe");
        
        assertEquals(name1.hashCode(), name2.hashCode());
    }
    
    @Test
    void testNameTrimming() {
        Name name = new Name("  John  ", "  Doe  ");
        
        assertEquals("John", name.getFirstName());
        assertEquals("Doe", name.getLastName());
        assertEquals("John Doe", name.toString());
    }
}