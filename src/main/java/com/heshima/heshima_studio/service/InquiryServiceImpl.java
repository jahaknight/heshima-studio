package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.controller.dto.InquiryItemResponse;
import com.heshima.heshima_studio.controller.dto.InquiryResponse;
import com.heshima.heshima_studio.entity.Order;
import com.heshima.heshima_studio.entity.OrderItem;
import com.heshima.heshima_studio.entity.OrderStatus;
import com.heshima.heshima_studio.entity.Product;
import com.heshima.heshima_studio.repository.OrderItemRepository;
import com.heshima.heshima_studio.repository.OrderRepository;
import com.heshima.heshima_studio.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InquiryServiceImpl implements InquiryService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public InquiryServiceImpl(ProductRepository productRepository,
                              OrderRepository orderRepository,
                              OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * Creates an inquiry in the system.
     * If the user selected a specific product, we attach an order item for it.
     * If they did NOT select a product (general inquiry), we still save the order
     * so the admin can see who reached out.
     */
    @Override
    public InquiryResponse createInquiry(Long productId, String name, String email, String message) {
        // 1) create the order header
        Order order = new Order();
        order.setCustomerName(name);
        order.setCustomerEmail(email);
        order.setNotes(message);
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // 2) only create an order item if a productId was actually sent
        if (productId != null) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

            OrderItem item = new OrderItem();
            item.setOrder(savedOrder);
            item.setProduct(product);
            item.setQuantity(1);
            item.setFinalPrice(product.getBasePrice() != null ? product.getBasePrice() : BigDecimal.ZERO);

            orderItemRepository.save(item);

            // attach to order so mapping works
            savedOrder.setItems(List.of(item));
        } else {
            // general inquiry – no product line
            savedOrder.setItems(Collections.emptyList());
        }

        // 3) return a clean DTO back to the controller
        return toInquiryResponse(savedOrder);
    }

    /**
     * Returns all inquiries (admin view).
     */
    @Override
    public List<InquiryResponse> getAllInquiries() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::toInquiryResponse)
                .toList();
    }

    /**
     * Returns a single inquiry by id, or empty if not found.
     */
    @Override
    public Optional<InquiryResponse> getInquiryById(Long id) {
        return orderRepository.findById(id)
                .map(this::toInquiryResponse);
    }

    /**
     * Deletes an inquiry from the system.
     */
    @Override
    public void deleteInquiry(Long id) {
        boolean exists = orderRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("Inquiry (order) not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    /**
     * Helper to map our Order + OrderItems into the DTO the controller returns.
     */
    private InquiryResponse toInquiryResponse(Order order) {
        List<InquiryItemResponse> itemDtos = order.getItems().stream()
                .map(item -> new InquiryItemResponse(
                        item.getId(),
                        // if product is null (shouldn’t be in normal flow), show a safe label
                        item.getProduct() != null ? item.getProduct().getName() : "General inquiry",
                        item.getQuantity(),
                        item.getFinalPrice()
                ))
                .collect(Collectors.toList());

        return new InquiryResponse(
                order.getId(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getNotes(),
                order.getCreatedAt(),
                itemDtos
        );
    }
}