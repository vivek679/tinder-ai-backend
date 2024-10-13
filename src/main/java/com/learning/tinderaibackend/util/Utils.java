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

}
