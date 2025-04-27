package com.example.hospitalapi.shared.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Value object representing a timestamp
 */
@Getter
@EqualsAndHashCode
public class Timestamp {
    private final LocalDateTime value;

    public Timestamp(LocalDateTime value) {
        if (value == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        this.value = value;
    }

    public static Timestamp now() {
        return new Timestamp(LocalDateTime.now());
    }

    public boolean isBefore(Timestamp other) {
        return this.value.isBefore(other.value);
    }

    public boolean isAfter(Timestamp other) {
        return this.value.isAfter(other.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}