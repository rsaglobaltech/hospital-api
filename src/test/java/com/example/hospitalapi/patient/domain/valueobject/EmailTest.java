package com.example.hospitalapi.patient.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    
    @Test
    void testCreateEmail() {
        String emailValue = "john.doe@example.com";
        
        Email email = new Email(emailValue);
        
        assertEquals(emailValue, email.getValue());
        assertEquals(emailValue, email.toString());
    }
    
    @Test
    void testCreateEmailWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email(null);
        });
    }
    
    @Test
    void testCreateEmailWithEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("");
        });
    }
    
    @Test
    void testCreateEmailWithBlankValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("   ");
        });
    }
    
    @Test
    void testCreateEmailWithInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("invalid-email");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("invalid@");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("@invalid.com");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("invalid@invalid");
        });
    }
    
    @Test
    void testEmailEquality() {
        Email email1 = new Email("john.doe@example.com");
        Email email2 = new Email("john.doe@example.com");
        Email email3 = new Email("jane.doe@example.com");
        
        assertEquals(email1, email2);
        assertNotEquals(email1, email3);
        assertNotEquals(email2, email3);
    }
    
    @Test
    void testEmailHashCode() {
        Email email1 = new Email("john.doe@example.com");
        Email email2 = new Email("john.doe@example.com");
        
        assertEquals(email1.hashCode(), email2.hashCode());
    }
    
    @Test
    void testEmailTrimming() {
        Email email = new Email("  john.doe@example.com  ");
        
        assertEquals("john.doe@example.com", email.getValue());
        assertEquals("john.doe@example.com", email.toString());
    }
    
    @Test
    void testValidEmailFormats() {
        // Test various valid email formats
        assertDoesNotThrow(() -> new Email("simple@example.com"));
        assertDoesNotThrow(() -> new Email("very.common@example.com"));
        assertDoesNotThrow(() -> new Email("disposable.style.email.with+symbol@example.com"));
        assertDoesNotThrow(() -> new Email("other.email-with-hyphen@example.com"));
        assertDoesNotThrow(() -> new Email("fully-qualified-domain@example.com"));
        assertDoesNotThrow(() -> new Email("user.name+tag+sorting@example.com"));
        assertDoesNotThrow(() -> new Email("x@example.com"));
        assertDoesNotThrow(() -> new Email("example-indeed@strange-example.com"));
        assertDoesNotThrow(() -> new Email("example@s.example"));
    }
}