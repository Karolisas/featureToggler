package com.example.featureToogler.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.featureToogler.security.Roles.ADMIN_USER;
import static com.example.featureToogler.security.Roles.SIMPLE_USER;

//@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//                .anyRequest().authenticated();
                .antMatchers("/").permitAll()
//                .antMatchers("/feature").permitAll()
                .antMatchers(HttpMethod.GET, "/feature").hasAnyRole(SIMPLE_USER.name(), ADMIN_USER.name())
                .antMatchers(HttpMethod.POST, "/feature/**").hasRole(ADMIN_USER.name())
                .antMatchers(HttpMethod.PUT, "/feature/**").hasRole(ADMIN_USER.name())

                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("pass")
//                .roles(SIMPLE_USER.name());
//    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("pass"))
                .roles(SIMPLE_USER.name())
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(ADMIN_USER.name())
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
