package com.heshima.heshima_studio.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * JPA entity that represents a Heshima Studio offering (service/product).
 *
 * In this portfolio project:
 * - Each Product is something a client can select on the Services page.
 * - The React frontend calls /api/products and renders these as service cards.
 * - basePrice is stored as BigDecimal for safe currency handling.
 * - isActive allows me to “soft hide” a product without deleting it.
 */

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal basePrice = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean isActive = true;

    // constructors
    public Product() {
    }

    /**
     * Convenience constructor for seeding and tests.
     *
     * @param name        display name of the service
     * @param description marketing description that appears in the UI
     * @param basePrice   starting price for this service
     */

    public Product(String name, String description, BigDecimal basePrice) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.isActive = true;
    }

    // getters & setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
