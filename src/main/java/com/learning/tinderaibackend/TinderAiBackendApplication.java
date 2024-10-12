package com.learning.tinderaibackend;

import static com.learning.tinderaibackend.profile.Gender.MALE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.learning.tinderaibackend.profile.Profile;
import com.learning.tinderaibackend.profile.ProfileRepository;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

    @Autowired
    private ProfileRepository profileRepository;

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
    }

}
