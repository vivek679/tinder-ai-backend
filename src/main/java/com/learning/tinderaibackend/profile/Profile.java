package com.learning.tinderaibackend.profile;

import org.springframework.data.annotation.Id;

public record Profile(
        @Id
        String profileId, // As i want non predictable Ids
        String firstName,
        String lastName,
        int age,
        String ethnicity,
        Gender gender,
        String bio,
        String imageUrl,
        String myersBriggsPersonalityType
) {
}
