package com.learning.tinderaibackend.conversations;

import java.time.LocalDateTime;

public record ChatMessage(
        String messageTest,
        String authorId,
        LocalDateTime messageTime
) {
}
