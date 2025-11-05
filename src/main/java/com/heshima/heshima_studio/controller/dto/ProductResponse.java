package com.heshima.heshima_studio.controller.dto;


import java.math.BigDecimal;

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
