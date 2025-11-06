package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.controller.dto.InquiryResponse;
import com.heshima.heshima_studio.entity.Order;
import com.heshima.heshima_studio.entity.OrderItem;
import com.heshima.heshima_studio.entity.OrderStatus;
import com.heshima.heshima_studio.entity.Product;
import com.heshima.heshima_studio.repository.OrderItemRepository;
import com.heshima.heshima_studio.repository.OrderRepository;
import com.heshima.heshima_studio.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for InquiryServiceImpl.
 * <p>
 * These tests verify that the service:
 * - looks up the product
 * - saves an Order and an OrderItem
 * - maps the saved Order back into an InquiryResponse DTO
 * - supports read-all, read-one, and delete operations
 * <p>
 * Repositories are mocked so no database is touched.
 */
public class InquiryServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    private InquiryServiceImpl inquiryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inquiryService = new InquiryServiceImpl(productRepository, orderRepository, orderItemRepository);
    }

    @Test
    @DisplayName("createInquiry saves order + item and returns DTO")
    void createInquiry_createsOrderAndItem() {
        // ---------- arrange ----------
        // product that the user is inquiring about
        Product product = new Product("Branding", "Brand identity package", new BigDecimal("750.00"));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // order that the repository will "save"
        // (we return the same instance; we don't assert on id because entity may not expose setId)
        Order savedOrder = new Order();
        savedOrder.setCustomerName("Jaha");
        savedOrder.setCustomerEmail("jaha@test.com");
        savedOrder.setNotes("hi!");
        savedOrder.setStatus(OrderStatus.NEW);
        savedOrder.setCreatedAt(LocalDateTime.now());
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // we don't need to stub orderItemRepository.save(...) to return anything specific
        when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ---------- act ----------
        InquiryResponse result = inquiryService.createInquiry(
                1L,
                "Jaha",
                "jaha@test.com",
                "hi!"
        );

        // ---------- assert ----------
        // verify repositories were called
        verify(productRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
        verify(orderItemRepository).save(any(OrderItem.class));

        // verify DTO fields
        assertNotNull(result);
        assertEquals("Jaha", result.getCustomerName());
        assertEquals("jaha@test.com", result.getCustomerEmail());
        assertEquals("hi!", result.getNotes());
        assertEquals(1, result.getItems().size());
        assertEquals("Branding", result.getItems().get(0).getProductName());
        assertEquals(new BigDecimal("750.00"), result.getItems().get(0).getFinalPrice());
    }

    @Test
    @DisplayName("getAllInquiries maps orders to InquiryResponse list")
    void getAllInquiries_returnsMappedDtos() {
        // ---------- arrange ----------
        Order order = new Order();
        order.setCustomerName("Client A");
        order.setCustomerEmail("client@example.com");
        order.setNotes("need a site");
        order.setCreatedAt(LocalDateTime.now());

        // order needs at least one item for the mapper
        OrderItem item = new OrderItem();
        Product product = new Product("Web Design", "Site", new BigDecimal("1200.00"));
        item.setProduct(product);
        item.setQuantity(1);
        item.setFinalPrice(new BigDecimal("1200.00"));
        item.setOrder(order);

        order.setItems(List.of(item));

        when(orderRepository.findAll(any(Sort.class))).thenReturn(List.of(order));

        // ---------- act ----------
        List<InquiryResponse> result = inquiryService.getAllInquiries();

        // ---------- assert ----------
        assertEquals(1, result.size());
        InquiryResponse dto = result.get(0);
        assertEquals("Client A", dto.getCustomerName());
        assertEquals(1, dto.getItems().size());
        assertEquals("Web Design", dto.getItems().get(0).getProductName());
    }

    @Test
    @DisplayName("getInquiryById returns mapped DTO when order exists")
    void getInquiryById_returnsDto() {
        // arrange
        Order order = new Order();
        order.setCustomerName("Kiara");
        order.setCustomerEmail("kiara@example.com");
        order.setNotes("rebrand");
        order.setCreatedAt(LocalDateTime.now());

        OrderItem item = new OrderItem();
        Product product = new Product("Branding", "Brand kit", new BigDecimal("500.00"));
        item.setProduct(product);
        item.setQuantity(1);
        item.setFinalPrice(new BigDecimal("500.00"));
        item.setOrder(order);

        order.setItems(List.of(item));

        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));

        // act
        Optional<InquiryResponse> result = inquiryService.getInquiryById(10L);

        // assert
        assertTrue(result.isPresent());
        assertEquals("Kiara", result.get().getCustomerName());
        assertEquals("Branding", result.get().getItems().get(0).getProductName());
    }

    @Test
    @DisplayName("deleteInquiry deletes when id exists")
    void deleteInquiry_deletesWhenExists() {
        // arrange
        when(orderRepository.existsById(5L)).thenReturn(true);

        // act
        inquiryService.deleteInquiry(5L);

        // assert
        verify(orderRepository).deleteById(5L);
    }

    @Test
    @DisplayName("deleteInquiry throws when id does not exist")
    void deleteInquiry_throwsWhenMissing() {
        // arrange
        when(orderRepository.existsById(999L)).thenReturn(false);

        // act + assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> inquiryService.deleteInquiry(999L)
        );
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    @DisplayName("createInquiry throws when product is not found")
    void createInquiry_throwsWhenProductMissing() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> inquiryService.createInquiry(
                        999L,
                        "Jaha",
                        "jaha@example.com",
                        "please contact me"
                )
        );
    }
}
