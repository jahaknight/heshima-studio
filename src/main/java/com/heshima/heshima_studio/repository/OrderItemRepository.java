package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
