package com.learning.tinderaibackend.matches;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
public record Match(
        @Id
        String matchId,
        String profileId1,
        String profileId2,
//        String conversationId,
        LocalDateTime matchedAt
) {
}
