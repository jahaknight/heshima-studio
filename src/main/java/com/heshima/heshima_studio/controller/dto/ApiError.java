package com.heshima.heshima_studio.controller.dto;

import java.time.LocalDateTime;

/**
 * Simple, serializable error payload returned by the API on failures.
 *
 * Purpose:
 * - Provide a predictable JSON shape for frontend consumers (React/Postman) when something goes wrong.
 * - Make debugging easier by including a timestamp and the request path that triggered the error.
 *
 * Design notes:
 * - Immutable fields: values are set once via the constructor and exposed via getters only.
 * - Time source: uses server time at construction to reflect when the error was created.
 * - Status + error: mirrors HTTP status code (e.g., 400/404/500) and a short reason phrase.
 *
 * Typical usage:
 * - In an @ControllerAdvice exception handler, construct an ApiError and return it with the matching HTTP status.
 * - Frontend can read .message for a friendly string and .path to know which endpoint failed.
 */

public class ApiError {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

/**
 * Builds a new error payload. Timestamp is captured automatically at construction time.
 *
 * @param status  HTTP status code (e.g., 400/404/500)
 * @param error   short label matching the status (e.g., "Bad Request")
 * @param message specific explanation intended for developers / client logs
 * @param path    the request URI (e.g., "/api/products/99")
 */

    public ApiError(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
