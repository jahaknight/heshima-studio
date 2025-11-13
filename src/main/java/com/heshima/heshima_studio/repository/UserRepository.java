package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for managing {@link User} entities.
 *
 * Extending {@link JpaRepository} gives me out-of-the-box CRUD functionality
 * (create, read, update, delete). This allows the service layer to interact
 * with the underlying database cleanly, without writing SQL directly.
 *
 * This repository will be especially important for future enhancements,
 * such as adding authentication or admin login features.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

