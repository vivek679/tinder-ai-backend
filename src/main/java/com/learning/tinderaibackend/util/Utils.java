package com.learning.tinderaibackend.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.learning.tinderaibackend.profile.Profile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

    public static List<Profile> loadProfilesFromJson(String filePath) {
        Gson gson = new Gson();
        try (InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + filePath);
            }

            return gson.fromJson(
                    new InputStreamReader(inputStream),
                    new TypeToken<ArrayList<Profile>>() {}.getType()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error loading JSON file: " + filePath, e);
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
}
