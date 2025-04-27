package com.example.hospitalapi.security.infrastructure.persistence.adapter;

import com.example.hospitalapi.security.domain.entity.User;
import com.example.hospitalapi.security.domain.repository.UserRepository;
import com.example.hospitalapi.security.domain.valueobject.UserId;
import com.example.hospitalapi.security.infrastructure.persistence.entity.UserJpaEntity;
import com.example.hospitalapi.security.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adapter implementing UserRepository using JPA
 */
@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        UserJpaEntity userJpaEntity = mapToJpaEntity(user);
        UserJpaEntity savedEntity = userJpaRepository.save(userJpaEntity);
        return mapToDomainEntity(savedEntity);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return userJpaRepository.findById(id.toString())
                .map(this::mapToDomainEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(this::mapToDomainEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(this::mapToDomainEntity);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll().stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UserId id) {
        userJpaRepository.deleteById(id.toString());
    }

    @Override
    public boolean existsById(UserId id) {
        return userJpaRepository.existsById(id.toString());
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    private UserJpaEntity mapToJpaEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .roles(user.getRoles())
                .build();
    }

    private User mapToDomainEntity(UserJpaEntity entity) {
        UserId userId = new UserId(entity.getId());
        User user = new User(
                userId,
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail()
        );
        
        // Set enabled status
        if (!entity.isEnabled()) {
            user.disable();
        }
        
        // Add roles
        entity.getRoles().forEach(user::addRole);
        
        return user;
    }
}