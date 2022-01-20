package com.example.featureToogler.security;

import com.example.featureToogler.controller.FeatureController;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.example.featureToogler.security.Roles.ADMIN_USER;
import static com.example.featureToogler.security.Roles.SIMPLE_USER;

//@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//                .anyRequest().authenticated();
                .antMatchers("/").permitAll()
//                .antMatchers("/feature").permitAll()
                .antMatchers(HttpMethod.GET, FeatureController.FEATURE_API_BASE_PATH).hasAnyRole(SIMPLE_USER.name(), ADMIN_USER.name())
                .antMatchers(HttpMethod.POST, FeatureController.FEATURE_API_BASE_PATH + "/**").hasRole(ADMIN_USER.name())
                .antMatchers(HttpMethod.PUT, FeatureController.FEATURE_API_BASE_PATH + "/**").hasRole(ADMIN_USER.name())
                .antMatchers(HttpMethod.DELETE, FeatureController.FEATURE_API_BASE_PATH + "/**").hasAnyRole(ADMIN_USER.name())

                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
