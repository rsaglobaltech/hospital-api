package com.example.hospitalapi.security.domain.repository;

import com.example.hospitalapi.security.domain.entity.User;
import com.example.hospitalapi.security.domain.valueobject.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity
 */
public interface UserRepository {
    
    /**
     * Save a user
     * @param user the user to save
     * @return the saved user
     */
    User save(User user);
    
    /**
     * Find a user by ID
     * @param id the user ID
     * @return the user if found
     */
    Optional<User> findById(UserId id);
    
    /**
     * Find a user by username
     * @param username the username
     * @return the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find a user by email
     * @param email the email
     * @return the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find all users
     * @return list of all users
     */
    List<User> findAll();
    
    /**
     * Delete a user
     * @param id the user ID
     */
    void deleteById(UserId id);
    
    /**
     * Check if a user exists
     * @param id the user ID
     * @return true if the user exists
     */
    boolean existsById(UserId id);
    
    /**
     * Check if a username exists
     * @param username the username
     * @return true if the username exists
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if an email exists
     * @param email the email
     * @return true if the email exists
     */
    boolean existsByEmail(String email);
}