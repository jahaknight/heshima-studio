package com.heshima.heshima_studio.controller;

import com.heshima.heshima_studio.entity.Product;
import com.heshima.heshima_studio.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller-level tests for ProductController.
 *
 * This test uses standalone MockMvc plus a mocked ProductService
 * so it stays compatible with newer JDKs (no @WebMvcTest / @MockBean).
 * It verifies that:
 *  - GET /api/products returns the active products from the service
 *  - GET /api/products/{id} returns 200 when the product exists
 *  - GET /api/products/{id} returns 404 when the product is missing
 */
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // create the real controller and inject the mocked service
        ProductController controller = new ProductController(productService);

        // build MockMvc around JUST this controller
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("GET /api/products returns active products")
    void getAllProducts_returnsList() throws Exception {
        // arrange: build two Product entities using your actual constructor
        Product branding = new Product(
                "Branding",
                "Brand identity package",
                new BigDecimal("750.00")
        );
        Product web = new Product(
                "Web Design",
                "Responsive marketing site",
                new BigDecimal("1200.00")
        );

        // controller calls productService.getAllActiveProducts()
        when(productService.getAllActiveProducts()).thenReturn(List.of(branding, web));

        // act + assert
        mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // assert on JSON using field names (name, description, basePrice)
                .andExpect(jsonPath("$[0].name").value("Branding"))
                .andExpect(jsonPath("$[0].basePrice").value(750.00))
                .andExpect(jsonPath("$[1].name").value("Web Design"))
                .andExpect(jsonPath("$[1].basePrice").value(1200.00));
    }

    @Test
    @DisplayName("GET /api/products/{id} returns product when found")
    void getProductById_returnsOne() throws Exception {
        // arrange
        Product branding = new Product(
                "Branding",
                "Brand identity package",
                new BigDecimal("750.00")
        );

        when(productService.getProductById(1L)).thenReturn(Optional.of(branding));

        // act + assert
        mockMvc.perform(get("/api/products/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Branding"))
                .andExpect(jsonPath("$.basePrice").value(750.00));
    }

    @Test
    @DisplayName("GET /api/products/{id} returns 404 when missing")
    void getProductById_returns404() throws Exception {
        // arrange
        when(productService.getProductById(anyLong())).thenReturn(Optional.empty());

        // act + assert
        mockMvc.perform(get("/api/products/999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

