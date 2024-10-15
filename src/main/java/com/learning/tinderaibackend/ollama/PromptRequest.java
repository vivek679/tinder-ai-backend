package com.learning.tinderaibackend.ollama;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "prompts")
public record PromptRequest(
        @Id
        String promptId,
        String promptMessage,
        LocalDateTime timestamp) {
}
