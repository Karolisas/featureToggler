package com.example.featureToogler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.example.featureToogler.security.Roles.ADMIN_USER;
import static com.example.featureToogler.security.Roles.SIMPLE_USER;

@Component
public class MockUser {

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetails determineUser(String userName) {
        UserDetails userDetails = null;

        switch (userName) {
            case "user":
                userDetails = User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("pass"))
                        .roles(SIMPLE_USER.name())
                        .build();
                break;
            case "admin":
                userDetails = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(ADMIN_USER.name())
                        .build();
                break;

        }
        return userDetails;
    }
}
