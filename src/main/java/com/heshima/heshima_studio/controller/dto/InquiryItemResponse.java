package com.heshima.heshima_studio.controller.dto;

import java.math.BigDecimal;

/**
 * Read-only line-item payload for an inquiry/order response.
 *
 * Purpose:
 * - Represents a single product the customer inquired about, including
 * the resolved price at the time of creation.
 *
 * Design notes:
 * - Uses {@link BigDecimal} for currency-safe arithmetic (avoids double rounding issues).
 * - DTO stays immutable from the client's perspective: values are set in the constructor
 *   and exposed via getters (no public setters).
 * - Quantity is an Integer to align with JSON number types; server-side validation
 *   enforces allowed ranges (e.g., >= 1).
 */

public class InquiryItemResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal finalPrice;

    public InquiryItemResponse(Long productId, String productName,
                               Integer quantity, BigDecimal finalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }
}
