package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.controller.dto.InquiryResponse;

import java.util.List;
import java.util.Optional;

/**
 * Service layer contract for handling client inquiries.
 *
 * This interface defines the core operations my application supports
 * around inquiries / orders:
 *  - Creating a new inquiry from the public form.
 *  - Listing all inquiries for the admin dashboard.
 *  - Looking up a single inquiry by id.
 *  - Deleting an inquiry if the admin no longer needs it.
 *
 * The implementation that wires this up to the database is
 * {@link InquiryServiceImpl}, but the controller depends on this interface
 * to keep things clean and testable.
 */

public interface InquiryService {

    InquiryResponse createInquiry(Long productId, String name, String email, String message);

    List<InquiryResponse> getAllInquiries();

    Optional<InquiryResponse> getInquiryById(Long id);

    void deleteInquiry(Long id);
}