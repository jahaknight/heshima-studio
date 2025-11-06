package com.heshima.heshima_studio.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for GlobalExceptionHandler.
 *
 * We register a dummy controller that throws an exception
 * and verify that the @RestControllerAdvice turns it into
 * the correct ApiError response.
 */
class GlobalExceptionHandlerTest {

    // simple controller used only for this test
    @RestController
    static class ThrowingController {
        @GetMapping("/throw-illegal")
        public String throwIllegal() {
            throw new IllegalArgumentException("Inquiry (order) not found with id: 999");
        }

        @GetMapping("/throw-generic")
        public String throwGeneric() {
            throw new RuntimeException("Something bad happened");
        }
    }

    @Test
    @DisplayName("IllegalArgumentException is mapped to 404 ApiError")
    void illegalArgument_isHandledAsNotFound() throws Exception {
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new ThrowingController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mockMvc.perform(get("/throw-illegal").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Inquiry (order) not found with id: 999"))
                .andExpect(jsonPath("$.path").value("/throw-illegal"));
    }

    @Test
    @DisplayName("Generic Exception is mapped to 500 ApiError")
    void generic_isHandledAsInternalServerError() throws Exception {
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new ThrowingController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mockMvc.perform(get("/throw-generic").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Something bad happened"))
                .andExpect(jsonPath("$.path").value("/throw-generic"));
    }
}
