package com.heshima.heshima_studio.controller.dto;

import java.math.BigDecimal;

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
