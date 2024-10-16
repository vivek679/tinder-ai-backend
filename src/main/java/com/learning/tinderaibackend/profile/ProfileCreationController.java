package com.learning.tinderaibackend.profile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.tinderaibackend.enums.Gender;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles-creations")
public class ProfileCreationController {

    private final ProfileCreationService profileCreationService;

    @PostMapping()
    public ResponseEntity<?> createProfiles(
            @RequestParam(value = "numberOfProfiles", defaultValue = "0") int numberOfProfiles,
            @RequestParam(value = "lookingFor", defaultValue = "FEMALE") Gender lookingFor) {
        profileCreationService.createProfile(numberOfProfiles, lookingFor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
