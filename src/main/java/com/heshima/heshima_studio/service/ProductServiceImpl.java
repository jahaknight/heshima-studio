package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.entity.Product;
import com.heshima.heshima_studio.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link ProductService}.
 *
 * This class sits between the controller and the repository so that
 * any product-related rules (like only showing active services) live
 * in one place instead of being duplicated across controllers.
 */

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllActiveProducts() {
        return productRepository.findByIsActiveTrue();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}
