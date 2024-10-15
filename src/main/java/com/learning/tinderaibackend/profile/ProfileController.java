package com.learning.tinderaibackend.profile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.tinderaibackend.enums.Gender;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles/")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping(path = "{userGender}/random")
    public Profile getRandomProfile(@PathVariable Gender userGender) {
        return profileService.getRandomProfileForUser(userGender);
    }

    @PostMapping(path = "/register-new-user")
    public Profile addNewUserFromAppProperties() {
        return profileService.registerNewUserFromAppProperties();
    }
}
