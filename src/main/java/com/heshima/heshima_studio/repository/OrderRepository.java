package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<Order, Long> {
}
