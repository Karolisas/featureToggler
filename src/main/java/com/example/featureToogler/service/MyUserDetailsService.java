package com.example.featureToogler.service;

import com.example.featureToogler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.featureToogler.security.Roles.ADMIN_USER;
import static com.example.featureToogler.security.Roles.SIMPLE_USER;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public MyUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //todo proper DB connection || using hardcoded users
//        com.example.featureToogler.dto.User user1 = userRepository.findByUserName(username)
//                .orElseThrow(EntityNotFoundException::new);
//        return User.builder()
//                .username(user1.getUserName())
//                .roles(user1.getRole())
//                .build();
//        }

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

        return "user".equals(username) ? user :
                "admin".equals(username) ? admin : null;
    }


}
