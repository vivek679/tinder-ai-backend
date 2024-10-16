package com.learning.tinderaibackend.matches;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MatchRepository extends MongoRepository<Match, String> {
    @Query("{ '$or':  [{ 'profileId1': ?0}, {'profileId2':  ?0}]}")
    List<Match> findMatchesByProfileId(String profileId);
}
