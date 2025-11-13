package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing {@link Product} entities.
 *
 * Each product represents a Heshima Studio service (Branding, Web Design, etc.)
 * that can be displayed on the frontend and scoped into a project plan.
 * Extending {@link JpaRepository} gives me basic CRUD operations out of the box.
 */

public interface ProductRepository extends JpaRepository<Product, Long> {

    // helper to get only active services
    List<Product> findByIsActiveTrue();
}
