package com.heshima.heshima_studio.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Read-only response payload returned to clients after an inquiry is created
 * or fetched by the admin dashboard.
 *
 * Purpose:
 * - Decouples external API shape from internal JPA entities.
 * - Prevents over-exposing internal fields and keeps the contract stable.
 *
 * Mapping notes (from domain to DTO):
 * - {@code id}           ← Order.id
 * - {@code customerName} ← Order.customerName
 * - {@code customerEmail}← Order.customerEmail
 * - {@code notes}        ← Order.notes
 * - {@code createdAt}    ← Order.createdAt
 * - {@code items}        ← Order.items mapped to {@link InquiryItemResponse}
 *
 * Serialization:
 * - Jackson uses getters to serialize this DTO to JSON.
 * - Types are JSON-friendly (primitives/Strings/ISO-8601 timestamp + nested DTO list).
 *
 * Validation:
 * - This class is intentionally immutable from the client’s perspective (no setters).
 * - Service layer ensures values are present/correct before constructing this DTO.
 */

public class InquiryResponse {
    private Long id;
    private String customerName;
    private String customerEmail;
    private String notes;
    private LocalDateTime createdAt;
    private List<InquiryItemResponse> items;

    public InquiryResponse(Long id,
                           String customerName,
                           String customerEmail,
                           String notes,
                           LocalDateTime createdAt,
                           List<InquiryItemResponse> items) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.notes = notes;
        this.createdAt = createdAt;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<InquiryItemResponse> getItems() {
        return items;
    }
}
