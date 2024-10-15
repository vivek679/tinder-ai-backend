package com.learning.tinderaibackend;

import static com.learning.tinderaibackend.enums.Gender.FEMALE;
import static com.learning.tinderaibackend.enums.Gender.MALE;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.learning.tinderaibackend.conversations.ConversationRepository;
import com.learning.tinderaibackend.matches.MatchRepository;
import com.learning.tinderaibackend.profile.Profile;
import com.learning.tinderaibackend.profile.ProfileCreationService;
import com.learning.tinderaibackend.profile.ProfileRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class TinderAiBackendApplication implements CommandLineRunner {

    private final ProfileRepository profileRepository;
    private final MatchRepository matchRepository;
    private final ConversationRepository conversationRepository;

    private final ProfileCreationService profileCreationService;


    private final OpenAiChatModel chatModel;

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
        profileCreationService.createProfile(8);
    }

    public void run2(String... args) {
        System.out.println("My app is running");

        Prompt prompt = new Prompt("Hi how can i connect springboot app with ollama running in docker");
        ChatResponse response = chatModel.call(prompt);
        System.out.println(response.getResult().getOutput());

        Profile profile1 = new Profile(
                "1",
                "vivek",
                "kumar",
                28,
                "Indian",
                MALE,
                "Software programmer",
                "foo.jpg",
                "LET'S DO IT"
        );

        Profile profile2 = new Profile(
                "2",
                "foo",
                "bar",
                25,
                "Indian",
                FEMALE,
                "Software programmer",
                "fo01.jpg",
                "STRONGER"
        );

        if (!profileRepository.existsById(profile1.profileId())) {
            profileRepository.save(profile1);
        }
        if (!profileRepository.existsById(profile2.profileId())) {
            profileRepository.save(profile2);
        }
        profileRepository.findAll().forEach(System.out::println);

    }

}
