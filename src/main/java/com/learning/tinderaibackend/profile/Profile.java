package com.learning.tinderaibackend.profile;

public record Profile(
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
