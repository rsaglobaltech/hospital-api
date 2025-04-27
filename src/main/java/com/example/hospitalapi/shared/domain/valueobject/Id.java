package com.example.hospitalapi.shared.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

/**
 * Base value object for all IDs in the domain
 */
@Getter
@EqualsAndHashCode
public abstract class Id {
    private final UUID value;

    protected Id() {
        this.value = UUID.randomUUID();
    }

    protected Id(UUID value) {
        this.value = value;
    }

    protected Id(String value) {
        this.value = UUID.fromString(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}