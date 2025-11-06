package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.entity.Product;
import com.heshima.heshima_studio.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for ProductServiceImpl.
 *
 * This test verifies that the service layer:
 *  - delegates to ProductRepository correctly
 *  - returns only active products via findByIsActiveTrue()
 *  - returns an Optional for lookups by id
 *
 * We mock the repository so the tests do not hit the database.
 */
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    @DisplayName("getAllActiveProducts returns list from repository")
    void getAllActiveProducts_returnsList() {
        // arrange
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

        when(productRepository.findByIsActiveTrue()).thenReturn(List.of(branding, web));

        // act
        List<Product> result = productService.getAllActiveProducts();

        // assert
        assertEquals(2, result.size());
        assertEquals("Branding", result.get(0).getName());
        assertEquals("Web Design", result.get(1).getName());
    }

    @Test
    @DisplayName("getProductById returns product when repository finds it")
    void getProductById_found() {
        //arrange
        Product branding = new Product(
                "Branding",
                "Brand identity package",
                new BigDecimal("750.00")
        );
        when(productRepository.findById(1L)).thenReturn(Optional.of(branding));

        // act
        Optional<Product> result = productService.getProductById(1L);

        // assert
        assertTrue(result.isPresent());
        assertEquals("Branding", result.get().getName());
    }

    @Test
    @DisplayName("getProductById returns empty when repository does not find it")
    void getProductById_notFound() {
        // arrange
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        // act
        Optional<Product> result = productService.getProductById(99L);

        // assert
        assertTrue(result.isEmpty());
    }
}
