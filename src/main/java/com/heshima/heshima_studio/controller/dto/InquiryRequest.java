package com.heshima.heshima_studio.controller.dto;

/**
 * Write-only request payload from the public Inquiry form.
 *
 * Purpose:
 * - Minimal fields collected from the front end to create an inquiry/order.
 * - Kept separate from entity models to avoid over-posting and to control
 * exactly what the client can send.
 *
 *  * Validation (handled by service/controller layer or a validator):
 *  - productId: must reference an existing Product.
 *  - name: required, non-blank.
 *  - email: required, valid email format.
 *  - message: optional but recommended; trimmed to store concise notes.
 *
 *  * Notes:
 *  * - Default no-args constructor is required by Jackson for JSON deserialization.
 *  * - Only exposes setters/getters for fields that are safe to accept from clients
 */

public class InquiryRequest {

    private Long productId;
    private String name;
    private String email;
    private String message;

    public InquiryRequest() {

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
