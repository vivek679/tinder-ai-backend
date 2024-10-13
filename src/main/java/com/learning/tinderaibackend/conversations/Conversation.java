package com.learning.tinderaibackend.conversations;

import java.util.List;

import org.springframework.data.annotation.Id;

public record Conversation(
        @Id
        String conversationId,
        String profileId,
        List<ChatMessage> messages
) {
}
