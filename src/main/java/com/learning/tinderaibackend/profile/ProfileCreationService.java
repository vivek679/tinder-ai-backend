package com.learning.tinderaibackend.profile;

import static com.learning.tinderaibackend.util.Utils.loadProfilesFromJson;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProfileCreationService {

    private static final String MALE_PROFILE_PATH = "profiles_male.json";
    private static final String FEMALE_PROFILE_PATH = "profiles_female.json";

    private final ProfileRepository profileRepository;

    public ProfileCreationService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void saveProfilesToDB() {
        List<Profile> allProfiles = new ArrayList<>();
        allProfiles.addAll(loadProfilesFromJson(FEMALE_PROFILE_PATH));
        allProfiles.addAll(loadProfilesFromJson(MALE_PROFILE_PATH));

        profileRepository.deleteAll();
        profileRepository.saveAll(allProfiles);
    }

}
