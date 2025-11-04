package com.heshima.heshima_studio.service;

import com.heshima.heshima_studio.entity.Order;

import java.util.List;
import java.util.Optional;

public interface InquiryService {

    Order createInquiry(Long productId, String name, String email, String message);

    List<Order> getAllInquiries();

    Optional<Order> getInquiryById(Long id);
}