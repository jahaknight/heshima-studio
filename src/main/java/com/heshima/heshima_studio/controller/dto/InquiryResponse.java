package com.heshima.heshima_studio.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

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
