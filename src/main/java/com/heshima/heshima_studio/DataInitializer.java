package com.heshima.heshima_studio;

import com.heshima.heshima_studio.entity.Product;
import com.heshima.heshima_studio.entity.Role;
import com.heshima.heshima_studio.entity.User;
import com.heshima.heshima_studio.repository.ProductRepository;
import com.heshima.heshima_studio.repository.RoleRepository;
import com.heshima.heshima_studio.repository.UserRepository;
import com.heshima.heshima_studio.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;        // checks if user exists
    private final UserService userService;              // creates user officially
    private final ProductRepository productRepository;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           UserService userService,
                           ProductRepository productRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @Override
    // Makes sure the role exists
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER")));

        // checks if the admin user already exists
        Optional<User> existingAdmin = userService.findByEmail("admin@heshima.studio");

        if (existingAdmin.isEmpty()) {
            User admin = new User(
                    "Heshima",
                    "Admin",
                    "admin@heshima.studio",
                    "password123",      // TODO: hash this
                    adminRole
            );
            admin.setCreatedAt(LocalDateTime.now());

            userService.createUser(admin);

            System.out.println("✅ Created default admin: admin@heshima.studio / password123");
        } else {
            System.out.println("ℹ️ Admin already exists, skipping seed.");
        }

        // seed products
        if (productRepository.count() == 0) {
            Product branding = new Product(
                    "Branding",
                    "Visual identity, logo, and brand guideline support.",
                    new BigDecimal("750.00")
            );
            Product webDesign = new Product(
                    "Web Design",
                    "Responsive site that matches Heshima Studio aesthetic.",
                    new BigDecimal("1200.00")
            );
            Product uxUi = new Product(
                    "UX / UI",
                    "Interface design for web/app dashboards.",
                    new BigDecimal("950.00")
            );

            productRepository.save(branding);
            productRepository.save(webDesign);
            productRepository.save(uxUi);

            System.out.println("✅ Seeded default products/services.");
        } else {
            System.out.println("ℹ️ Products already present, skipping seed.");
        }
    }
}
