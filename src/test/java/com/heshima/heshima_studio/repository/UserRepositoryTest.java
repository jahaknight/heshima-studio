package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.Role;
import com.heshima.heshima_studio.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserRepository tests
 * - hits the security/user part of the app
 * - uses the same Spring Boot test setup + H2 test DB
 */
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("save() should persist a new user with a role")
    void save_shouldPersistUser() {
        // arrange: grab an existing role that DataInitializer seeded
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new IllegalStateException("ADMIN role should exist in test DB"));

        User user = new User(
                "Test",
                "User",
                "testuser@heshima.studio",
                "{noop}password123",
                adminRole
        );

        // act
        User saved = userRepository.save(user);

        // assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("testuser@heshima.studio");
        assertThat(saved.getRole()).isNotNull();
        assertThat(saved.getRole().getName()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("findByEmail() should return an existing user")
    void findByEmail_shouldReturnUser() {
        // arrange: make sure a user is in the DB
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("USER role should exist in test DB"));

        User user = new User(
                "Client",
                "Account",
                "client@heshima.studio",
                "{noop}clientpass",
                userRole
        );
        userRepository.save(user);

        // act
        Optional<User> found = userRepository.findByEmail("client@heshima.studio");

        // assert
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Client");
        assertThat(found.get().getRole().getName()).isEqualTo("USER");
    }

    @Test
    @DisplayName("can update user profile fields")
    void canUpdateUser() {
        // arrange
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow();
        User user = new User(
                "Old",
                "Name",
                "update-me@heshima.studio",
                "{noop}pass",
                adminRole
        );
        User saved = userRepository.save(user);

        // act: change first name
        saved.setFirstName("New");
        User updated = userRepository.save(saved);

        // assert
        assertThat(updated.getFirstName()).isEqualTo("New");
    }
}
