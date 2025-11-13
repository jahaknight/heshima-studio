package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for managing {@link Role} entities.
 *
 * Roles are used by Spring Security to determine what a user is allowed to do
 * (for example, ADMIN vs USER). Extending {@link JpaRepository} gives me
 * basic CRUD operations and simple query support.
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
