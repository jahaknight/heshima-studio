package com.heshima.heshima_studio.controller;

import com.heshima.heshima_studio.controller.dto.InquiryRequest;
import com.heshima.heshima_studio.entity.Order;
import com.heshima.heshima_studio.service.InquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@CrossOrigin(origins = "*")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public ResponseEntity<Order> createInquiry(@RequestBody InquiryRequest request) {
        Order saved = inquiryService.createInquiry(
                request.getProductId(),
                request.getName(),
                request.getEmail(),
                request.getMessage()
        );

        return ResponseEntity
                .created(URI.create("/api/inquiries/" + saved.getId()))
                .body(saved);
    }

    // GET all
    @GetMapping
    public ResponseEntity<List<Order>> getAllInquiries() {
        return ResponseEntity.ok(inquiryService.getAllInquiries());
    }

    // ✅ GET one by id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getInquiryById(@PathVariable Long id) {
        return inquiryService.getInquiryById(id)
                .map(ResponseEntity::ok)               // if present → 200 + body
                .orElseGet(() -> ResponseEntity.notFound().build()); // if not → 404
    }

    // DELETE /api/inquiries/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long id) {
        try {
            inquiryService.deleteInquiry(id);
            // 204 = success, no content
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            // if service says "not found" return 404
            return ResponseEntity.notFound().build();
        }
    }
}
