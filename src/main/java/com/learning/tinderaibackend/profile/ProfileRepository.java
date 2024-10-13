package com.learning.tinderaibackend.profile;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

    @Aggregation(pipeline = {
            "{ $match: { 'gender': ?0 } }",  // Match based on gender
            "{ $sample: { size: 1 } }"       // Get a random document
    })
    Profile getRandomProfileByGender(String gender);

    @Aggregation(pipeline = {
            "{ $match: { 'gender': { $in: [ 'MALE', 'FEMALE' ] } } }",
            "{ $sample: { size: 1 } }"                                  // Get a random document
    })
    Profile getRandomProfileForNonBinary();


}
