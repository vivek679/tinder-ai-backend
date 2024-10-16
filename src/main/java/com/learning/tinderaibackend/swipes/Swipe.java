package com.learning.tinderaibackend.swipes;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.learning.tinderaibackend.enums.Action;

@Document(collection = "swipes")
public record Swipe(
        @Id
        String swipeId,
        String fromProfileId,
        String toProfileId,
        Action action,
        LocalDateTime timestamp
) {
}
