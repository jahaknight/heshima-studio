package com.heshima.heshima_studio.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Simple smoke test for the HealthController.
 *
 * Verifies:
 *  - the /api/health endpoint is mapped
 *  - it returns 200 OK
 *  - it returns {"status":"ok"}
 */
class HealthControllerTest {

    @Test
    @DisplayName("GET /api/health returns {\"status\":\"ok\"}")
    void health_returnsOkJson() throws Exception {
        // 1) create the controller we want to test
        HealthController controller = new HealthController();

        // 2) build MockMvc around JUST this controller (no full Spring context)
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // 3) perform the request and assert
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }
}