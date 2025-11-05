package com.heshima.heshima_studio.controller;

import com.heshima.heshima_studio.controller.dto.InquiryRequest;
import com.heshima.heshima_studio.controller.dto.InquiryResponse;
import com.heshima.heshima_studio.service.InquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing client inquiries.
 * This is what the React/front-end “Contact / Service Inquiry” form will talk to.
 * Public users can POST an inquiry, but viewing/deleting is protected in SecurityConfig.
 */
@RestController
@RequestMapping("/api/inquiries")
@CrossOrigin(origins = "*") // allow frontend (different port) to call this API
public class InquiryController {

    private final InquiryService inquiryService;

    // inject the service that contains the business logic
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    /**
     * POST /api/inquiries
     * Creates a new inquiry from the public form.
     * We accept a simple DTO (InquiryRequest) and return a DTO (InquiryResponse)
     * so the client gets a clean object back.
     */
    @PostMapping
    public ResponseEntity<InquiryResponse> createInquiry(@RequestBody InquiryRequest request) {
        InquiryResponse saved = inquiryService.createInquiry(
                request.getProductId(),
                request.getName(),
                request.getEmail(),
                request.getMessage()
        );

        // return 201 Created with a Location header pointing to the new resource
        return ResponseEntity
                .created(URI.create("/api/inquiries/" + saved.getId()))
                .body(saved);
    }

    /**
     * GET /api/inquiries
     * Returns all inquiries, usually for the admin dashboard.
     * SecurityConfig requires ADMIN role to hit this.
     */
    @GetMapping
    public ResponseEntity<List<InquiryResponse>> getAllInquiries() {
        return ResponseEntity.ok(inquiryService.getAllInquiries());
    }

    /**
     * GET /api/inquiries/{id}
     * Returns a single inquiry by id (also for admin).
     * 404 if it doesn’t exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InquiryResponse> getInquiryById(@PathVariable Long id) {
        return inquiryService.getInquiryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/inquiries/{id}
     * Admin-only: allows removing an inquiry from the system.
     * Returns 204 on success, 404 if the id is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long id) {
        try {
            inquiryService.deleteInquiry(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();  // 404
        }
    }
}
