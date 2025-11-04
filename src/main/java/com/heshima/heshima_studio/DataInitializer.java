package com.heshima.heshima_studio;

import com.heshima.heshima_studio.entity.Role;
import com.heshima.heshima_studio.entity.User;
import com.heshima.heshima_studio.repository.RoleRepository;
import com.heshima.heshima_studio.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserService userService;

    public DataInitializer(RoleRepository roleRepository,
                           UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER")));

        Optional<User> existingAdmin = userService.findByEmail("admin@heshima.studio");

        if (existingAdmin.isEmpty()) {
            User admin = new User(
                    "Heshima",
                    "Admin",
                    "admin@heshima.studio",
                    "password123",      // TODO: hash this
                    adminRole
            );
            userService.createUser(admin);
            System.out.println("✅ Created default admin: admin@heshima.studio / password123");
        } else {
            System.out.println("ℹ️ Admin already exists, skipping seed.");
        }
    }
}
