package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for working with {@link OrderItem} entities.
 *
 * In this project, order items represent the specific services
 * and pricing attached to an inquiry/order. This interface
 * extends {@link JpaRepository}, so Spring Data generates the
 * standard CRUD operations automatically.
 */

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
