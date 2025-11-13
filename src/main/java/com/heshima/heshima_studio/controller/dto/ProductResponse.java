package com.heshima.heshima_studio.controller.dto;


import java.math.BigDecimal;

/**
 * Read-only Data Transfer Object (DTO) that represents a product
 * returned to the frontend from the ProductController.
 *
 * Purpose:
 * - Keeps the API contract clean and separated from internal Product entities.
 * - Used by the frontend's Services page to render the studio’s offerings.
 *
 * Mapping notes (domain → DTO):
 * - {@code id}          ← Product.id
 * - {@code name}        ← Product.name
 * - {@code description} ← Product.description
 * - {@code basePrice}   ← Product.basePrice
 *
 * Serialization:
 * - Jackson uses the public getters to convert this object into JSON.
 * - BigDecimal is serialized as a numeric value for accurate pricing.
 *
 * Immutability:
 * - Fields are private and only exposed via getters.
 * - Constructed by the service or controller layer when responding to a request.
 */

public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;

    // constructor
    public ProductResponse(Long id, String name, String description, BigDecimal basePrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
    }

    // getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }
}
