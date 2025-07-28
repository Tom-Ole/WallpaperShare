package com.taskmanager.taskmanager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskmanager.taskmanager.entity.AuthUser;
import com.taskmanager.taskmanager.entity.UserEntity;
import com.taskmanager.taskmanager.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;

    CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity admin = userRepository.findByUsername(username).orElseThrow(() ->
            new UsernameNotFoundException("User not found with username: " + username)
        );

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + admin.getRole()));
        AuthUser authUser = new AuthUser(username, admin.getPassword(), admin.getRole(), admin.getFirstname(), admin.getLastname(), admin.getEmail());
        authUser.setId(admin.getId());

        return authUser;
    }


}
