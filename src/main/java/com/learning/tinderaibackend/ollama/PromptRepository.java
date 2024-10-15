package com.learning.tinderaibackend.ollama;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptRepository extends MongoRepository<PromptRequest, String> {
}
