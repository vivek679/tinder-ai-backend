package com.learning.tinderaibackend.swipes;

import com.learning.tinderaibackend.enums.Action;

public record SwipeRequest(
        String fromProfileId,
        String toProfileId,
        Action action
) {
}
