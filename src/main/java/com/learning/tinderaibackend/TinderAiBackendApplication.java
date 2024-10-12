package com.learning.tinderaibackend;

import static com.learning.tinderaibackend.profile.Gender.MALE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.learning.tinderaibackend.conversations.ChatMessage;
import com.learning.tinderaibackend.conversations.Conversation;
import com.learning.tinderaibackend.conversations.ConversationRepository;
import com.learning.tinderaibackend.profile.Profile;
import com.learning.tinderaibackend.profile.ProfileRepository;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    public static void main(String[] args) {
        SpringApplication.run(TinderAiBackendApplication.class, args);
    }

    public void run(String... args) {
        System.out.println("My app is running");
        Profile profile = new Profile(
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

        profileRepository.save(profile);
        profileRepository.findAll().forEach(System.out::println);

        Conversation conversation1_1 = new Conversation(
                "1",
                profile.profileId(),
                new ArrayList<>(
                        List.of(
                                new ChatMessage("Hi' there!!!", "1", LocalDateTime.now()),
                                new ChatMessage("Hello' How you doing?", "2", LocalDateTime.now())
                        ))
        );

        conversationRepository.save(conversation1_1);
        conversationRepository.findAll().forEach(System.out::println);

    }

}
