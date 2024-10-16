package com.learning.tinderaibackend.matches;

import java.time.LocalDateTime;

public record MatchResponse(
        String matchId,
        String profileId1,
        String profileId2,
        LocalDateTime matchedAt
) {
}
