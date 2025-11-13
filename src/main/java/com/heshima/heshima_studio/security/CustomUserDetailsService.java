package com.heshima.heshima_studio.security;

import com.heshima.heshima_studio.entity.User;
import com.heshima.heshima_studio.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Custom {@link UserDetailsService} implementation that tells Spring Security
 * how to look up users from my database.
 *
 * Instead of using an in-memory user, this class plugs into {@link UserRepository}
 * and turns my {@link User} entity into a Spring Security {@link UserDetails} object.
 *
 * This is what makes the Basic Auth credentials work for protected endpoints
 * (for example, viewing inquiries on the admin dashboard).
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        GrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + user.getRole().getName());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                List.of(authority)
        );
    }
}
