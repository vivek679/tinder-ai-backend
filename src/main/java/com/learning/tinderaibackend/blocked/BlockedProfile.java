package com.learning.tinderaibackend.blocked;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blocked_profiles")
public record BlockedProfile (
        @Id
        String profileId,
        List<String> blockedProfileIds
) {
}
