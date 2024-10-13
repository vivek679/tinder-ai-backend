package com.learning.tinderaibackend.conversations;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "conversations")
public record Conversation(
        @Id
        String conversationId,
        String profileId,
        List<ChatMessage> messages
) {
}
