package com.example.hospitalapi.security.infrastructure.service;

import com.example.hospitalapi.security.domain.repository.UserRepository;
import com.example.hospitalapi.security.infrastructure.mapper.UserDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of UserDetailsService that loads user details from the database
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(userDetailsMapper::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}