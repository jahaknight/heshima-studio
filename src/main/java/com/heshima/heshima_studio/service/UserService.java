package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.entity.User;

import java.util.Optional;

/**
 * Service layer for working with {@link User} accounts.
 *
 * This sits on top of the repository so that any user-related rules
 * (like hashing passwords, assigning roles, or onboarding logic)
 * can live in one place instead of being scattered through controllers.
 */

public interface UserService {
    /**
     * Creates a new user in the system.
     *
     * In this project it is mainly used by the DataInitializer to seed
     * the default admin account, but the method can also support future
     * registration logic.
     *
     * @param user fully-populated {@link User} entity to save
     * @return the persisted {@link User} including the generated id
     */

    User createUser(User user);

    /**
     * Finds a user by email address.
     *
     * This is the main lookup used by Spring Security during login,
     * and it also gives the application a simple way to check whether
     * a given email is already registered.
     *
     * @param email unique email for the user
     * @return {@link Optional} containing the user if found
     */

    Optional<User> findByEmail(String email);
}
