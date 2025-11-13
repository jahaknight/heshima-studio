package com.heshima.heshima_studio.controller;

import com.heshima.heshima_studio.controller.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException from anywhere in the REST layer.
     *
     * Usage in this app:
     * - InquiryService throws IllegalArgumentException when an order/inquiry
     *   cannot be found or a product id is invalid.
     *
     * Response:
     *  - HTTP 404 Not Found
     *  - Body is an ApiError payload with a timestamp, status, and readable message.
     *
     * @param ex the IllegalArgumentException that was thrown
     * @param request the current HTTP request, used to capture the request path
     * @return a ResponseEntity containing the ApiError and 404 status code
     */

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        ApiError body = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

/**
 * Fallback handler for any other unhandled exceptions.
 *
 * Purpose:
 * - Prevents raw stack traces from leaking to the client.
 * - Gives the frontend a predictable JSON shape even when something
 *   unexpected happens on the server.
 *
 * Response:
 * - HTTP 500 Internal Server Error
 * - Body is an ApiError with a generic 500 status and server message.
 *
 * @param ex any unhandled exception thrown in the REST layer
 * @param request the current HTTP request, used to capture the request path
 * @return a ResponseEntity containing the ApiError and 500 status code
 */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        ApiError body = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
