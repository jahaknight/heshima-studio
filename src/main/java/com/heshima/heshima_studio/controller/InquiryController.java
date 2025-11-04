package com.heshima.heshima_studio.controller;

import com.heshima.heshima_studio.entity.Order;
import com.heshima.heshima_studio.service.InquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
@CrossOrigin(origins = "*")
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
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
}
