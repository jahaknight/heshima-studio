package com.heshima.heshima_studio.service;

import java.util.stream.Collectors;
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
import java.util.List;
import java.util.Optional;

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

    // CREATE
    @Override
    public InquiryResponse createInquiry(Long productId, String name, String email, String message) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        // make order
        Order order = new Order();
        order.setCustomerName(name);
        order.setCustomerEmail(email);
        order.setNotes(message);
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        // make order item
        OrderItem item = new OrderItem();
        item.setOrder(savedOrder);
        item.setProduct(product);
        item.setQuantity(1);
        item.setFinalPrice(product.getBasePrice() != null ? product.getBasePrice() : BigDecimal.ZERO);
        orderItemRepository.save(item);

        savedOrder.setItems(List.of(item));

        // return DTO
        return toInquiryResponse(savedOrder);
    }

    // READ ALL
    @Override
    public List<InquiryResponse> getAllInquiries() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::toInquiryResponse)
                .toList();
    }

    // READ ONE
    @Override
    public Optional<InquiryResponse> getInquiryById(Long id) {
        return orderRepository.findById(id)
                .map(this::toInquiryResponse);
    }

    // DELETE
    @Override
    public void deleteInquiry(Long id) {
        boolean exists = orderRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("Inquiry (order) not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    // HELPER MAPPER
    private InquiryResponse toInquiryResponse(Order order) {
        List<InquiryItemResponse> itemDtos = order.getItems().stream()
                .map(item -> new InquiryItemResponse(
                        item.getId(),
                        item.getProduct().getName(),
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