package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing {@link Order} entities.
 *
 * Each order represents an inquiry submitted through the frontend,
 * including customer details, created timestamp, and the list of
 * selected services. Extending {@link JpaRepository} gives me
 * built-in CRUD operations (save, findById, findAll, delete, etc.)
 * without writing any SQL manually.
 */

public interface OrderRepository extends JpaRepository<Order, Long> {
}
