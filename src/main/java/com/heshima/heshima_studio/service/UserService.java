package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.entity.User;

import java.util.Optional;

public interface UserService {

    User createUser(User user);
    Optional<User> findByEmail(String email);
}
