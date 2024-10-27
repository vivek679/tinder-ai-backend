package com.learning.tinderaibackend.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.learning.tinderaibackend.exceptions.BadRequestException;
import com.learning.tinderaibackend.profile.ProfileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SwipeValidationService {
    private final ProfileRepository profileRepository;

    public void validateProfileExists(String profileId1, String profileId2) {

        if (StringUtils.isEmpty(profileId1) || StringUtils.isEmpty(profileId2)) {
            throw new BadRequestException("Invalid profileIds");
        }
    }
}
