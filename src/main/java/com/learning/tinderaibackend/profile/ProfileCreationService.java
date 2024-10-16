package com.learning.tinderaibackend.profile;

import static com.learning.tinderaibackend.util.Utils.generateMyersBriggsTypes;
import static com.learning.tinderaibackend.util.Utils.getRandomElement;
import static com.learning.tinderaibackend.util.Utils.loadDataFromJson;
import static com.learning.tinderaibackend.util.Utils.loadProfilesFromJson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.learning.tinderaibackend.enums.Gender;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileCreationService {

    @Value("${profiles.file-path}")
    private String profileFilePath;

    private static final String MALE_PROFILE_PATH = "profiles_male.json";
    private static final String FEMALE_PROFILE_PATH = "profiles_female.json";

    private final ProfileRepository profileRepository;
    private final OllamaChatModel ollamaChatModel;

    private final List<Profile> generatedProfiles = new ArrayList<>();

    /**
     * Used to pre-process the profiles before the App runs.
     */
    public void saveProfilesToDB() {
        List<Profile> allProfiles = new ArrayList<>();
        allProfiles.addAll(loadProfilesFromJson(FEMALE_PROFILE_PATH));
        allProfiles.addAll(loadProfilesFromJson(MALE_PROFILE_PATH));

        profileRepository.deleteAll();
        profileRepository.saveAll(allProfiles);
    }

    // For each combination of these variables
    //      make a call to Ollama to generate a sample Tinder profile for these variables
    //      Save the values in a JSON file
    /**
     * Create Profile based on age range, gender, ethnicities and personalityType for generating profiles.
     * @param numberOfProfiles number of unique profiles.
     * @param lookingFor MALE, FEMALE, or NON_BINARY
     */
    public void createProfile(int numberOfProfiles, Gender lookingFor) {
        LocalDateTime startTime = LocalDateTime.now();
        logger.info("Profile creation:: In-progress");
        // Identify the age range, gender, ethnicities and personalityType for generating profiles.
        List<Integer> ages = IntStream.rangeClosed(22, 30).boxed().collect(Collectors.toList());
        List<Gender> genders = new ArrayList<>(List.of(lookingFor));
        List<String> ethnicities = new ArrayList<>(List.of("White", "Black", "Indian", "Asian", "Native American", "Hispanic"));
        List<String> myersBriggsPersonalityTypes = generateMyersBriggsTypes();

        while (this.generatedProfiles.size() < numberOfProfiles) {
            int age = getRandomElement(ages);
            Gender gender = getRandomElement(genders);
            String ethnicity = getRandomElement(ethnicities);
            String personalityType = getRandomElement(myersBriggsPersonalityTypes);

            String prompt = "Create a Tinder profile persona of an " + personalityType + " " + age + " year old " + ethnicity + " " + gender.toString()
                    + " including the first name, last name, Myers Briggs Personality type and a tinder bio." +
                    " Save the profile using the saveProfile function";

            logger.debug("Chat prompt:{}", prompt);
            ChatResponse response = ollamaChatModel.call(
                    new Prompt(prompt, OllamaOptions.builder()
                            .withFunction("saveProfile")
                            .build())
            );
            logger.trace("Chat response output :{}", response.getResult().getOutput());
        }

        this.generatedProfiles.forEach(profile -> logger.trace("Profile: {}", profile));

        //TODO: Save the values in a JSON file. Open the Json file and read the contents into a Set<Profile>
        saveProfilesToJson(this.generatedProfiles);
        // TODO: Save those values to MongoDB in different table.
        logger.info("Profile creation:: Complete {}", Duration.between(startTime, LocalDateTime.now()).toSeconds());
    }

    /**
     * Process the generated profile and update the json_file.
     *
     * @param generatedProfiles profiles created by model.
     */
    private void saveProfilesToJson(List<Profile> generatedProfiles) {
        try {
            // Ensure the directory exists
            Path path = Paths.get(profileFilePath).getParent();
            if (Objects.nonNull(path) && Files.notExists(path)) {
                Files.createFile(path);
            }

            Gson gson = new Gson();
            // Load existing profiles, if any
            Type profileListType = new TypeToken<List<Profile>>() {
            }.getType();
            List<Profile> existingProfiles = loadDataFromJson(profileFilePath, profileListType);
            generatedProfiles.addAll(existingProfiles);

            // TODO: Process the images based on the generatedProfiles.
            // TODO: Update the image urls and add uuid in the profile.
            List<Profile> profilesWithImages = new ArrayList<>();
            for (Profile profile : generatedProfiles) {
                if (profile.imageUrl() == null || profile.imageUrl().isEmpty()) {
                    logger.debug("Image processor call ");
                    // profile = generateProfileImage(profile);
                }
            }

            // Write JSON to file (overwrite mode)
            String jsonString = gson.toJson(this.generatedProfiles);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(profileFilePath, false))) {
                writer.write(jsonString);
            }
            logger.info("Successfully saved {} profiles to {}", generatedProfiles.size(), profileFilePath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing Profile");
        }
    }

    /**
     * Spring AI ChatModel will create an instance of a FunctionCallbackWrapper that adds the logic for it being invoked via the AI model.
     *
     * @return generated Profile.
     */
    @Bean
    @Description("Save the Tinder profile information, without any image URL")
    public Function<Profile, Boolean> saveProfile() {
        return (profile) -> {
            logger.trace("This is the function that we expect to be called by Spring AI by looking at the OllamaAI response");
            this.generatedProfiles.add(profile);
//            String profileId = UUID.randomUUID().toString();
//            this.generatedProfiles.add(new Profile(
//                    profileId,
//                    profile.firstName(),
//                    profile.lastName(),
//                    profile.age(),
//                    profile.ethnicity(),
//                    profile.gender(),
//                    profile.bio(),
//                    profileId + ".jpg",
//                    profile.myersBriggsPersonalityType()
//            ));
            return true;
        };
    }

}
