package com.example.hospitalapi.security.infrastructure.mapper;

import com.example.hospitalapi.security.domain.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper for converting User entity to Spring Security UserDetails
 */
@Component
public class UserDetailsMapper {

    /**
     * Convert User entity to Spring Security UserDetails
     * @param user the User entity
     * @return the UserDetails
     */
    public UserDetails toUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                        .collect(Collectors.toList())
        );
    }
}