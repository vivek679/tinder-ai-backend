package com.learning.tinderaibackend.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        String reason,
        String path,
        LocalDateTime timestamp) {

    public ErrorResponse(String message, int status, String reason, String path) {
        this(message, status, reason, path, LocalDateTime.now());
    }
}
