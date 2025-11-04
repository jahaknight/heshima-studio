package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllActiveProducts();

    Optional<Product> getProductById(Long id);
}
