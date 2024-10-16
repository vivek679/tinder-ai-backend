package com.learning.tinderaibackend.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.learning.tinderaibackend.profile.Profile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

    /**
     * This method is used to reade profiles file stored in `src/main/resources/`
     *
     * @param filePath file path or name
     * @return List of profiles
     */
    public static List<Profile> loadProfilesFromJson(String filePath) {
        Gson gson = new Gson();
        try (InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + filePath);
            }

            return gson.fromJson(
                    new InputStreamReader(inputStream),
                    new TypeToken<ArrayList<Profile>>() {
                    }.getType()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error loading JSON file: " + filePath, e);
        }
    }

    public static <T> List<T> loadDataFromJson(String filePath, Type type) {
        File file = new File(filePath);
        if (!file.exists()) {
            logger.info("File not found: {}", filePath);
            new ArrayList<T>();
        }
        Gson gson = new Gson();
        try (Reader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            logger.error("Error while reading | processing json file");
            throw new RuntimeException(e.getMessage());
        }
    }


    public static List<String> selfieTypes() {
        return List.of(
                "closeup with head and partial shoulders",
                "Reflection in a mirror",
                "action selfie, person in motion",
                "candid photo",
                "standing in nature",
                "sitting on a chair",
                "indoor photograph",
                "outdoor photograph"
        );
    }


    /**
     * Method to create a list of personalityType
     *
     * @return personalityType
     */
    public static List<String> generateMyersBriggsTypes() {
        List<String> myersBriggsPersonalityTypes = new ArrayList<>();

        String[] dimensions = {
                "E,I", // Extraversion or Introversion
                "S,N", // Sensing or Intuition
                "T,F", // Thinking or Feeling
                "J,P"  // Judging or Perceiving
        };

        // Generate all combinations
        for (String e : dimensions[0].split(",")) {
            for (String s : dimensions[1].split(",")) {
                for (String t : dimensions[2].split(",")) {
                    for (String j : dimensions[3].split(",")) {
                        myersBriggsPersonalityTypes.add(e + s + t + j);
                    }
                }
            }
        }

        return myersBriggsPersonalityTypes;
    }

    /**
     * Used to give random value from the supplied values.
     *
     * @param list of values
     * @param <T>  Type casting
     * @return Random value
     */
    public static <T> T getRandomElement(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

}
