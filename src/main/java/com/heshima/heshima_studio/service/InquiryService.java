package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.controller.dto.InquiryResponse;

import java.util.List;
import java.util.Optional;

public interface InquiryService {

    InquiryResponse createInquiry(Long productId, String name, String email, String message);

    List<InquiryResponse> getAllInquiries();

    Optional<InquiryResponse> getInquiryById(Long id);

    void deleteInquiry(Long id);
}