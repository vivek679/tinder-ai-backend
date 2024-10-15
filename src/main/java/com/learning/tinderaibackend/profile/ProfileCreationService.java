package com.learning.tinderaibackend.profile;

import static com.learning.tinderaibackend.util.Utils.generateMyersBriggsTypes;
import static com.learning.tinderaibackend.util.Utils.loadProfilesFromJson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.learning.tinderaibackend.enums.Gender;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileCreationService {

    private static final String MALE_PROFILE_PATH = "profiles_male.json";
    private static final String FEMALE_PROFILE_PATH = "profiles_female.json";
    private static final String NEW_PROFILE_PATH = "newProfiles.json";

    private final ProfileRepository profileRepository;
    private final OllamaChatModel ollamaChatModel;

    private List<Profile> generatedProfiles = new ArrayList<>();

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

    /**
     * Used to give random value from the supplied values.
     *
     * @param list of values
     * @return Random value
     * @param <T> Type casting
     */
    private static <T> T getRandomElement(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    // For each combination of these variables
    //      make a call to Ollama to generate a sample Tinder profile for these variables
    //      Save the values in a JSON file
    /**
     * Create Profile based on age range, gender, ethnicities and personalityType for generating profiles.
     * @param numberOfProfiles number of unique profiles.
     */
    public void createProfile(int numberOfProfiles) {
        // Identify the age range, gender, ethnicities and personalityType for generating profiles.
        List<Integer> ages = new ArrayList<>();
        for (int i = 20; i <= 35; i++) {
            ages.add(i);
        }
        List<Gender> genders = new ArrayList<>(List.of(Gender.MALE, Gender.FEMALE));
        List<String> ethnicities = new ArrayList<>(List.of("White", "Black", "Asian", "Native American", "Hispanic"));
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
            logger.info("Chat response output :{}", response.getResult().getOutput());
        }
        this.generatedProfiles.forEach(profile -> logger.info("Profile: {}", profile));
        //Save the values in a JSON file
        // Open the Json file and read the contents into a Set<Profile>
        saveProfilesToJson(generatedProfiles);
        // Save those values to MongoDB in different table.

    }

    /**
     * Process the generated profile and update the json_file.
     *
     * @param generatedProfiles profiles created by model.
     */
    private void saveProfilesToJson(List<Profile> generatedProfiles) {
        try {
            Gson gson = new Gson();
            List<Profile> existingProfiles = loadProfilesFromJson(NEW_PROFILE_PATH);
            generatedProfiles.addAll(existingProfiles);
            // Process the images based on the generatedProfiles
            // Update the image urls and add uuid in the profile.

            String jsonString = gson.toJson(generatedProfiles);
            FileWriter writer = new FileWriter(NEW_PROFILE_PATH);
            writer.write(jsonString);
            writer.close();
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
    @Description("Save the Tinder profile information")
    public Function<Profile, Boolean> saveProfile() {
        return (profile) -> {
            logger.debug("This is the function that we expect to be called by Spring AI by looking at the OllamaAI response");
            this.generatedProfiles.add(profile);
            return true;
        };
    }

}
