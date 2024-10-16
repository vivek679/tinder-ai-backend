package com.learning.tinderaibackend.swipes;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SwipeRepository extends MongoRepository<Swipe, String> {

    boolean existsByFromProfileIdAndToProfileId(String fromProfileId, String toProfileId);

    @Query("{ 'fromProfileId':  ?0, 'action':  'LIKED' }")
    List<Swipe> findLikedProfilesByProfile(String profileId);
}
