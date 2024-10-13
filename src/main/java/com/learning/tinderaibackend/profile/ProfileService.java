package com.learning.tinderaibackend.profile;

import static com.learning.tinderaibackend.profile.Gender.FEMALE;
import static com.learning.tinderaibackend.profile.Gender.MALE;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Value("#{${tinderai.character.user}}")
    private Map<String, String> userProfileProperties;

    private final ProfileRepository profileRepository;


    public Profile getRandomProfileForUser(Gender userGender) {
        return switch (userGender) {
            case MALE -> profileRepository.getRandomProfileByGender(FEMALE.toString());
            case FEMALE -> profileRepository.getRandomProfileByGender(MALE.toString());
            case NON_BINARY -> profileRepository.getRandomProfileForNonBinary();
            default -> throw new IllegalArgumentException("Invalid gender: " + userGender);
        };
    }

    public Profile registerNewUserFromAppProperties() {
        return profileRepository.findById(userProfileProperties.get("profileId"))
                .orElseGet(() -> {
                    Profile profile = new Profile(
                            userProfileProperties.get("profileId"),
                            userProfileProperties.get("firstName"),
                            userProfileProperties.get("lastName"),
                            Integer.parseInt(userProfileProperties.get("age")),
                            userProfileProperties.get("ethnicity"),
                            Gender.valueOf(userProfileProperties.get("gender")),
                            userProfileProperties.get("bio"),
                            userProfileProperties.get("imageUrl"),
                            userProfileProperties.get("myersBriggsPersonalityType")
                    );
                    return profileRepository.save(profile);
                });
    }
}
