package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // helper to get only active services
    List<Product> findByIsActiveTrue();
}
