package com.learning.tinderaibackend.blocked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.learning.tinderaibackend.exceptions.ApiExceptions;
import com.learning.tinderaibackend.profile.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockedProfileService {

    private final BlockedProfileRepository blockedProfileRepository;
    private final ProfileRepository profileRepository;

    public void addProfileToBlockedList(String profileId, String blockProfileId) {
        BlockedProfile blockedProfile = blockedProfileRepository.findById(profileId)
                .orElseGet(() -> new BlockedProfile(profileId, new ArrayList<>()));

        List<String> blockedProfileIds = new ArrayList<>(blockedProfile.blockedProfileIds());

        // Check if the blockedProfileId is already in the list
        if (!blockedProfileIds.contains(blockProfileId)) {
            blockedProfileIds.add(blockProfileId); // Add the new blocked profile ID

            // Save the updated BlockedProfile back to the repository
            blockedProfileRepository.save(new BlockedProfile(profileId, blockedProfileIds));
            logger.info("Added profile {} to blocked list of profile {}", blockProfileId, profileId);
        } else {
            logger.info("Profile {} is already in the blocked list of profile {}", blockProfileId, profileId);
        }
    }

    public Map<String, List<String>> getBlockedList(String profileId) {
        if (!profileRepository.existsById(profileId)) {
            logger.error("Profile: {} not found", profileId);
            throw new ApiExceptions.NotFoundException("Profile not found");
        }
        // Find the BlockedProfile for the given profileId
        return blockedProfileRepository.findById(profileId)
                .map(blockedProfile -> Map.of(profileId, blockedProfile.blockedProfileIds()))
                .orElseGet(() -> {
                    logger.info("No blocked profiles found for profile {}", profileId);
                    return new HashMap<>();
                });
    }
}
