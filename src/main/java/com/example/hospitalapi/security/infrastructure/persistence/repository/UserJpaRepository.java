package com.example.hospitalapi.security.infrastructure.persistence.repository;

import com.example.hospitalapi.security.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository for UserJpaEntity
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String> {
    
    /**
     * Find a user by username
     * @param username the username
     * @return the user if found
     */
    Optional<UserJpaEntity> findByUsername(String username);
    
    /**
     * Find a user by email
     * @param email the email
     * @return the user if found
     */
    Optional<UserJpaEntity> findByEmail(String email);
    
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