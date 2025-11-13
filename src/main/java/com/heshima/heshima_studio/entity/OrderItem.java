package com.heshima.heshima_studio.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * JPA entity that represents a single line item on an order.
 *
 * In this project:
 * - Each OrderItem links an Order to a specific Product.
 * - It stores the final price and quantity at the time of the inquiry.
 * - This allows the admin view (and InquiryResponse DTO) to show
 *   exactly which services the client selected.
 */

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    @JsonIgnore // stops looping in postman
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity = 1;

    @Column(nullable = false)
    private BigDecimal finalPrice = BigDecimal.ZERO;

    public OrderItem() {

    }
    /**
     * Convenience constructor for building an OrderItem with all key fields.
     *
     * @param product    the product (service) being requested
     * @param quantity   how many units of this product
     * @param finalPrice the final price that should be stored for this line
     */
    public OrderItem(Product product, Integer quantity, BigDecimal finalPrice) {
        this.product = product;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
    }

    // getters & setters
    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
