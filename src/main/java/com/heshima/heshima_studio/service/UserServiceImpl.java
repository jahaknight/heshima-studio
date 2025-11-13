package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.entity.User;
import com.heshima.heshima_studio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Concrete implementation of {@link UserService}.
 *
 * Right now this stays intentionally thin and delegates to {@link UserRepository},
 * but having a service layer in place gives me a clean spot to add extra logic
 * later (for example: password hashing rules, auditing, or profile updates)
 * without touching controller code.
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
