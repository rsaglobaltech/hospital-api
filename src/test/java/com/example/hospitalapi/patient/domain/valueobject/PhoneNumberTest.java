package com.example.hospitalapi.patient.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {
    
    @Test
    void testCreatePhoneNumber() {
        String phoneNumberValue = "+1234567890";
        
        PhoneNumber phoneNumber = new PhoneNumber(phoneNumberValue);
        
        assertEquals(phoneNumberValue, phoneNumber.getValue());
        assertEquals(phoneNumberValue, phoneNumber.toString());
    }
    
    @Test
    void testCreatePhoneNumberWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PhoneNumber(null);
        });
    }
    
    @Test
    void testCreatePhoneNumberWithEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PhoneNumber("");
        });
    }
    
    @Test
    void testCreatePhoneNumberWithBlankValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PhoneNumber("   ");
        });
    }
    
    @Test
    void testCreatePhoneNumberWithInvalidFormat() {
        // Too short
        assertThrows(IllegalArgumentException.class, () -> {
            new PhoneNumber("123456789");
        });
        
        // Too long
        assertThrows(IllegalArgumentException.class, () -> {
            new PhoneNumber("12345678901234567890");
        });
        
        // Contains invalid characters
        assertThrows(IllegalArgumentException.class, () -> {
            new PhoneNumber("123-456-7890");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new PhoneNumber("123.456.7890");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new PhoneNumber("123 456 7890");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new PhoneNumber("phone: 1234567890");
        });
    }
    
    @Test
    void testPhoneNumberEquality() {
        PhoneNumber phoneNumber1 = new PhoneNumber("+1234567890");
        PhoneNumber phoneNumber2 = new PhoneNumber("+1234567890");
        PhoneNumber phoneNumber3 = new PhoneNumber("+9876543210");
        
        assertEquals(phoneNumber1, phoneNumber2);
        assertNotEquals(phoneNumber1, phoneNumber3);
        assertNotEquals(phoneNumber2, phoneNumber3);
    }
    
    @Test
    void testPhoneNumberHashCode() {
        PhoneNumber phoneNumber1 = new PhoneNumber("+1234567890");
        PhoneNumber phoneNumber2 = new PhoneNumber("+1234567890");
        
        assertEquals(phoneNumber1.hashCode(), phoneNumber2.hashCode());
    }
    
    @Test
    void testPhoneNumberTrimming() {
        PhoneNumber phoneNumber = new PhoneNumber("  +1234567890  ");
        
        assertEquals("+1234567890", phoneNumber.getValue());
        assertEquals("+1234567890", phoneNumber.toString());
    }
    
    @Test
    void testValidPhoneNumberFormats() {
        // Test various valid phone number formats
        assertDoesNotThrow(() -> new PhoneNumber("+1234567890"));
        assertDoesNotThrow(() -> new PhoneNumber("1234567890"));
        assertDoesNotThrow(() -> new PhoneNumber("+12345678901"));
        assertDoesNotThrow(() -> new PhoneNumber("+123456789012"));
        assertDoesNotThrow(() -> new PhoneNumber("+1234567890123"));
        assertDoesNotThrow(() -> new PhoneNumber("+12345678901234"));
        assertDoesNotThrow(() -> new PhoneNumber("+123456789012345"));
    }
    
    @Test
    void testPhoneNumberSpaceRemoval() {
        PhoneNumber phoneNumber = new PhoneNumber("+123 456 7890");
        
        assertEquals("+1234567890", phoneNumber.getValue());
        assertEquals("+1234567890", phoneNumber.toString());
    }
}