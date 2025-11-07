package com.heshima.heshima_studio.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Security integration tests
 *
 * What this proves:
 * - public endpoints in SecurityConfig are actually public
 * - admin-only endpoints are blocked for anonymous users
 * - admin-only endpoints are allowed for ROLE_ADMIN
 * - non-admin authenticated users get 403
 *
 * This lines up with SecurityConfig:
 *  .requestMatchers("/api/health").permitAll()
 *  .requestMatchers("/api/products/**").permitAll()
 *  .requestMatchers("/api/inquiries/**").hasRole("ADMIN")
 */
@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("public health endpoint should be accessible without auth")
    void health_isPublic() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("public products endpoint should be accessible without auth")
    void products_isPublic() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("admin endpoint should return 401 when no user is logged in")
    void inquiries_noAuth_isUnauthorized() throws Exception {
        mockMvc.perform(get("/api/inquiries"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin@heshima.studio", roles = {"ADMIN"})
    @DisplayName("admin endpoint should return 200 for ROLE_ADMIN")
    void inquiries_admin_isOk() throws Exception {
        mockMvc.perform(get("/api/inquiries"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "client@example.com", roles = {"USER"})
    @DisplayName("admin endpoint should return 403 for non-admin user")
    void inquiries_nonAdmin_isForbidden() throws Exception {
        mockMvc.perform(get("/api/inquiries"))
                .andExpect(status().isForbidden());
    }
}
