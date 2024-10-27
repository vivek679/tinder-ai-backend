package com.learning.tinderaibackend.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        String reason,
        LocalDateTime timestamp) {

    public ErrorResponse(String message, int status, String reason) {
        this(message, status, reason, LocalDateTime.now());
    }
}
