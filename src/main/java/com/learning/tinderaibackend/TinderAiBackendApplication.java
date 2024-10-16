package com.learning.tinderaibackend;

import static com.learning.tinderaibackend.enums.Gender.MALE;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.learning.tinderaibackend.conversations.ConversationRepository;
import com.learning.tinderaibackend.matches.MatchRepository;
import com.learning.tinderaibackend.profile.ProfileCreationService;
import com.learning.tinderaibackend.profile.ProfileRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class TinderAiBackendApplication implements CommandLineRunner {

    @Value("${startup-actions.initializeProfiles}")
    private Boolean initializeProfiles;

    private final ProfileRepository profileRepository;
    private final MatchRepository matchRepository;
    private final ConversationRepository conversationRepository;

    private final ProfileCreationService profileCreationService;

    public static void main(String[] args) {
        SpringApplication.run(TinderAiBackendApplication.class, args);
    }

    private void clearAllData() {
        conversationRepository.deleteAll();
        matchRepository.deleteAll();
        profileRepository.deleteAll();
    }

    @Override
    public void run(String... args) {
        System.out.println("Pre-processing-application");
        clearAllData();
        profileCreationService.saveProfilesToDB();
        if (initializeProfiles) {
            profileCreationService.createProfile(5, MALE);
        }
    }
}
