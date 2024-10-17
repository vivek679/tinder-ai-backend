package com.learning.tinderaibackend.blocked;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedProfileRepository extends MongoRepository<BlockedProfile, String> {

}
