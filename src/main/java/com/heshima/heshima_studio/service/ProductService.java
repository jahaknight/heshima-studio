package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for working with {@link Product} entities.
 *
 * I use this interface to keep the controller decoupled from the
 * data access details and make the product logic easier to test or
 * swap out later if the storage approach changes.
 */

public interface ProductService {
    List<Product> getAllActiveProducts();

    Optional<Product> getProductById(Long id);
}
