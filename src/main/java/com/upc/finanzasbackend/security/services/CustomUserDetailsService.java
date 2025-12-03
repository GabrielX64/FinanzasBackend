package com.upc.finanzasbackend.security.services;

import com.upc.finanzasbackend.exceptions.RequestException;
import com.upc.finanzasbackend.security.entities.User;
import com.upc.finanzasbackend.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserRepository userAppRepository;

    public CustomUserDetailsService(UserRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userApp = userAppRepository.findByUsername(username)
                .orElseThrow(() -> new RequestException("A001", HttpStatus.NOT_FOUND, "No se encontró el usuario"));

        GrantedAuthority authority = new SimpleGrantedAuthority(userApp.getRole().getName());

        return org.springframework.security.core.userdetails.User
                .withUsername(userApp.getUsername())
                .password(userApp.getPassword())
                .authorities(Collections.singleton(authority))
                .build();
    }
}