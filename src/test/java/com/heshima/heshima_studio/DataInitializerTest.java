package com.heshima.heshima_studio;

import com.heshima.heshima_studio.entity.Product;
import com.heshima.heshima_studio.entity.Role;
import com.heshima.heshima_studio.entity.User;
import com.heshima.heshima_studio.repository.ProductRepository;
import com.heshima.heshima_studio.repository.RoleRepository;
import com.heshima.heshima_studio.repository.UserRepository;
import com.heshima.heshima_studio.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for DataInitializer.
 *
 * Goal: verify that on application startup the initializer:
 *  - creates missing roles ("ADMIN", "USER")
 *  - creates the default admin user if one does not exist
 *  - seeds default products when there are none
 *
 * Repositories and services are mocked so this test does not hit the database.
 */
class DataInitializerTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository; // not used directly in initializer but injected

    @Mock
    private UserService userService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private DataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dataInitializer = new DataInitializer(
                roleRepository,
                userRepository,
                userService,
                productRepository,
                passwordEncoder
        );
    }

    @Test
    @DisplayName("run() creates roles, admin user, and products when none exist")
    void run_createsSeedDataWhenMissing() throws Exception {
        // ----- arrange -----
        // no ADMIN or USER yet
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.empty());
        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());
        // when saving roles, just return a new Role with that name
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // no admin user in the system
        when(userService.findByEmail("admin@heshima.studio"))
                .thenReturn(Optional.empty());

        // password encoder should return some encoded value
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");

        // no products yet
        when(productRepository.count()).thenReturn(0L);

        // ----- act -----
        dataInitializer.run();

        // ----- assert -----
        // roles should be created
        verify(roleRepository).save(argThat(r -> r.getName().equals("ADMIN")));
        verify(roleRepository).save(argThat(r -> r.getName().equals("USER")));

        // admin user should be created via the service layer
        verify(userService).createUser(any(User.class));

        // products should be seeded (3 saves)
        verify(productRepository, times(3)).save(any(Product.class));
    }

    @Test
    @DisplayName("run() skips creating admin and products when they already exist")
    void run_skipsWhenDataExists() throws Exception {
        // ----- arrange -----
        // roles already exist
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(new Role("ADMIN")));
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(new Role("USER")));

        // admin already exists
        when(userService.findByEmail("admin@heshima.studio"))
                .thenReturn(Optional.of(new User()));

        // products already present
        when(productRepository.count()).thenReturn(5L);

        // ----- act -----
        dataInitializer.run();

        // ----- assert -----
        // should NOT save roles a second time
        verify(roleRepository, never()).save(any(Role.class));

        // should NOT create admin again
        verify(userService, never()).createUser(any(User.class));

        // should NOT seed products if count > 0
        verify(productRepository, never()).save(any(Product.class));
    }
}
