package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository tests for Role entity:
 *
 * - The application seeds roles (ADMIN, USER) in DataInitializer
 * - These tests verify we cna also create/ find roles manually through the repositories
 * - Uses @SpringBootTest so we can get the full Spring context + JPA + H2 test DB
 */

@SpringBootTest
@Transactional
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("save() should persist a new role")
    void save_shouldPersistRole() {
        // arrange
        Role role = new Role("TEST_ROLE");

        // act
        Role saved = roleRepository.save(role);

        // assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("TEST_ROLE");
    }

    @Test
    @DisplayName("findByName() should return an existing role (including seeded ones)")
    void findByName_shouldReturnRole() {
        // act
        // DataInitializer creates ADMIN/USER
        Optional<Role> admin = roleRepository.findByName("ADMIN");
        // assert
        assertThat(admin).isPresent();
        assertThat(admin.get().getName()).isEqualTo("ADMIN");

    }
}
