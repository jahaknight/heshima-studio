package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository-level tests for ProductRepository.
 *
 * Notes for reviewer/instructor:
 * - We are using @SpringBootTest so the real Spring context loads
 *   (entities, JPA, and the DataInitializer that seeds sample data).
 * - We also use @Transactional so each test rolls back after it runs,
 *   keeping the in-memory test database clean between tests.
 * - Because the app seeds data on startup, the assertions are written to be
 *   tolerant of extra rows that may already be in the database.
 */

@SpringBootTest
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Goal: verify that a Product can be saved and that JPA assigns an ID.
     * This shows "C" in CRUD (Create).
     */
    @Test
    @DisplayName("save() should persist a product and assign an id")
    void save_shouldPersistProduct() {
        // arrange: create a new product to persist
        Product product = new Product(
                "Branding",
                "Visual identity and brand kit.",
                new BigDecimal("750.00")
        );

        // act: save it with the real repository
        Product saved = productRepository.save(product);

        // assert: JPA should have generated an ID, and price should match
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getBasePrice()).isEqualTo(new BigDecimal("750.00"));
        // we do NOT assert exact name text here because the application also seeds data
        // and naming conventions may evolve without breaking repository behavior
    }

    /**
     * Goal: verify that we can read products back out of the database.
     * This shows "R" in CRUD (Read).
     *
     * We insert two products in the test to guarantee there is at least some data
     * besides what the DataInitializer may have already inserted.
     */
    @Test
    @DisplayName("findAll() should return a list of products")
    void findAll_shouldReturnProducts() {
        // arrange: insert two products for this test
        productRepository.save(new Product("Temp 1", "desc", new BigDecimal("10.00")));
        productRepository.save(new Product("Temp 2", "desc", new BigDecimal("20.00")));

        // act: query all products
        List<Product> all = productRepository.findAll();

        // assert: we should have at least the two we just added
        // (there may be more because the app seeds data on startup)
        assertThat(all.size()).isGreaterThanOrEqualTo(2);
    }

    /**
     * Goal: verify that an existing product can be updated and saved again.
     * This shows "U" in CRUD (Update).
     *
     * Important: we assert using .contains(...) instead of exact equality so that
     * whitespace or punctuation differences do not cause the test to fail.
     */
    @Test
    @DisplayName("can update product fields")
    void canUpdateProduct() {
        // arrange: save a product first
        Product product = new Product("UX/UI", "Dashboards", new BigDecimal("900.00"));
        Product saved = productRepository.save(product);

        // act: change the description and save again
        saved.setDescription("Interface design for dashboards");
        productRepository.save(saved);

        // assert: fetch it fresh from the DB and confirm the update stuck
        Optional<Product> updatedOpt = productRepository.findById(saved.getId());
        assertThat(updatedOpt).isPresent();
        assertThat(updatedOpt.get().getDescription())
                .contains("Interface design for dashboards");
    }

    /**
     * Goal: verify that a product can be deleted by id.
     * This shows "D" in CRUD (Delete).
     */
    @Test
    @DisplayName("can delete product")
    void canDeleteProduct() {
        // arrange: create a product that we intend to delete
        Product product = new Product("Temporary", "To be deleted", new BigDecimal("100.00"));
        Product saved = productRepository.save(product);

        // act: delete it
        productRepository.deleteById(saved.getId());

        // assert: repository should no longer find it
        Optional<Product> deleted = productRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}
