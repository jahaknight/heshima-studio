package com.heshima.heshima_studio.security;

import com.heshima.heshima_studio.entity.Role;
import com.heshima.heshima_studio.entity.User;
import com.heshima.heshima_studio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for CustomUserDetailsService.
 *
 * Verifies:
 * - loadUserByUsername(email) returns a Spring Security UserDetails
 * when the user exists in the database
 * - it maps the role to "ROLE_<name>"
 * - it throws UsernameNotFoundException when the user is missing
 */
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customUserDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    @DisplayName("loadUserByUsername returns UserDetails with correct authority")
    void loadUserByUsername_found() {
        // --- arrange ---
        User user = new User();
        user.setEmail("admin@heshima.studio");
        user.setPasswordHash("encoded-password");

        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        user.setRole(adminRole);

        // repo should return this user for the given email
        when(userRepository.findByEmail("admin@heshima.studio")).thenReturn(Optional.of(user));

        // --- act ---
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin@heshima.studio");

        // --- assert ---
        assertNotNull(userDetails);
        assertEquals("admin@heshima.studio", userDetails.getUsername());
        assertEquals("encoded-password", userDetails.getPassword());
        // should have exactly 1 authority: ROLE_ADMIN
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    @DisplayName("loadUserByUsername throws when user not found")
    void loadUserByUsername_notFound() {
        // repo returns empty for this email
        when(userRepository.findByEmail("missing@heshima.studio")).thenReturn(Optional.empty());

        // act + assert
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("missing@heshima.studio")
        );
    }
}