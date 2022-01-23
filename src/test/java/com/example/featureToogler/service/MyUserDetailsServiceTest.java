package com.example.featureToogler.service;

import com.example.featureToogler.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MyUserDetailsServiceTest {

    @Autowired
    MyUserDetailsService service;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void userDetailServiceComponentsAreNotNull() {
        assertNotNull(service);
        assertNotNull(userRepository);
    }

    @Test
    public void loadUserByUsername (){
        assertEquals("user",service.loadUserByUsername("user").getUsername());
        assertEquals("admin",service.loadUserByUsername("admin").getUsername());
    }
}