package com.example.featureToogler.mockUsers;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithMockUser (roles = "SIMPLE_USER")
public @interface WithMockSimpleUser {
}
