package com.taskmanager.taskmanager.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AuthUser extends User {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String profilePictureUrl;
    private String description;
    private String role;


    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                    Long id, String firstname, String lastname, String email,
                    String profilePictureUrl, String description) {
        super(username, password, authorities);
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
        this.description = description;
        this.role = authorities.iterator().next().getAuthority();
    }

    // Getters
    public Long getId() {
        return id;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
    public String getDescription() {
        return description;
    }
    public String getRole() {
    return role;
}
}
