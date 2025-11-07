package com.heshima.heshima_studio.repository;

import com.heshima.heshima_studio.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository tests for the Order aggregate.
 *
 * - Our Inquiry/Order flow creates an Order and at least one OrderItem.
 * - Order has a @OneToMany to OrderItem with cascade = ALL, so we want to
 *   verify that saving the Order also persists its items.
 * - We also verify we can update the Order (e.g. change status).
 */
@SpringBootTest
@Transactional
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("save() should persist an order with one order item")
    void save_shouldPersistOrderWithItem() {
        // arrange: get a product to attach to the order item
        Product product = productRepository.findAll().stream().findFirst()
                .orElseGet(() -> productRepository.save(
                        new Product("Temp Product", "For order tests", new BigDecimal("99.00")))
                );

        // build the order
        Order order = new Order();
        order.setCustomerName("Test Customer");
        order.setCustomerEmail("customer@example.com");
        order.setNotes("Please call before starting.");
        order.setStatus(OrderStatus.NEW);

        // build the order item and attach to order
        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(1);
        item.setFinalPrice(product.getBasePrice() != null ? product.getBasePrice() : BigDecimal.ZERO);

        // helper on Order so both sides are in sync
        order.addItem(item);

        // act: save the parent order
        Order savedOrder = orderRepository.save(order);

        // assert
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getItems()).hasSize(1);
        assertThat(savedOrder.getItems().get(0).getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("findById() should return order with its items")
    void findById_shouldReturnOrderWithItems() {
        // arrange: first create and save an order with an item
        Product product = productRepository.findAll().stream().findFirst()
                .orElseGet(() -> productRepository.save(
                        new Product("Temp Product 2", "For find test", new BigDecimal("120.00")))
                );

        Order order = new Order();
        order.setCustomerName("Lookup Customer");
        order.setCustomerEmail("lookup@example.com");
        order.setStatus(OrderStatus.NEW);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2);
        item.setFinalPrice(product.getBasePrice() != null ? product.getBasePrice() : BigDecimal.ZERO);

        order.addItem(item);

        Order saved = orderRepository.save(order);

        // act: fetch from DB
        Optional<Order> foundOpt = orderRepository.findById(saved.getId());

        // assert
        assertThat(foundOpt).isPresent();
        Order found = foundOpt.get();
        assertThat(found.getCustomerEmail()).isEqualTo("lookup@example.com");
        assertThat(found.getItems()).hasSize(1);
        assertThat(found.getItems().get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("can update order status")
    void canUpdateOrderStatus() {
        // arrange
        Order order = new Order();
        order.setCustomerName("Status Customer");
        order.setCustomerEmail("status@example.com");
        order.setStatus(OrderStatus.NEW);

        Order saved = orderRepository.save(order);

        // act: change to IN_PROGRESS
        saved.setStatus(OrderStatus.IN_PROGRESS);
        Order updated = orderRepository.save(saved);

        // assert
        assertThat(updated.getStatus()).isEqualTo(OrderStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("can delete order and its items (because of orphanRemoval = true)")
    void canDeleteOrder() {
        // arrange: create order with item
        Product product = productRepository.findAll().stream().findFirst()
                .orElseGet(() -> productRepository.save(
                        new Product("Temp Product 3", "For delete test", new BigDecimal("42.00")))
                );

        Order order = new Order();
        order.setCustomerName("Delete Me");
        order.setCustomerEmail("delete@example.com");
        order.setStatus(OrderStatus.NEW);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(1);
        item.setFinalPrice(product.getBasePrice() != null ? product.getBasePrice() : BigDecimal.ZERO);
        order.addItem(item);

        Order saved = orderRepository.save(order);
        Long orderId = saved.getId();
        Long itemId = saved.getItems().get(0).getId();

        // act: delete the order
        orderRepository.deleteById(orderId);

        // assert: order should be gone
        assertThat(orderRepository.findById(orderId)).isEmpty();
        // and because of cascade/orphanRemoval, item should be gone too
        assertThat(orderItemRepository.findById(itemId)).isEmpty();
    }
}
