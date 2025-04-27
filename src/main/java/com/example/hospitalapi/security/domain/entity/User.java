package com.example.hospitalapi.security.domain.entity;

import com.example.hospitalapi.security.domain.exception.UserValidationException;
import com.example.hospitalapi.security.domain.valueobject.Role;
import com.example.hospitalapi.security.domain.valueobject.UserId;
import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a user in the system
 */
@Getter
public class User {
    private final UserId id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private final Set<Role> roles;

    public User(UserId id, String username, String password, String email) {
        if (id == null) {
            throw new UserValidationException("User ID cannot be null");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new UserValidationException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new UserValidationException("Password cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new UserValidationException("Email cannot be empty");
        }

        this.id = id;
        this.username = username.trim();
        this.password = password;
        this.email = email.trim();
        this.enabled = true;
        this.roles = new HashSet<>();
    }

    public void updateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new UserValidationException("Username cannot be empty");
        }
        this.username = username.trim();
    }

    public void updatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new UserValidationException("Password cannot be empty");
        }
        this.password = password;
    }

    public void updateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new UserValidationException("Email cannot be empty");
        }
        this.email = email.trim();
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public void addRole(Role role) {
        if (role == null) {
            throw new UserValidationException("Role cannot be null");
        }
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        if (role == null) {
            throw new UserValidationException("Role cannot be null");
        }
        this.roles.remove(role);
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }
}