package com.learning.tinderaibackend.config;

import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

//@Configuration
public class MongoConfig {
// NOT IN USE
//    @Bean
    public MongoClient mongoClient() {
        CodecRegistry codecRegistry = fromRegistries(
                fromCodecs(new OffsetDateTimeCodec()), // Register custom codec
                MongoClientSettings.getDefaultCodecRegistry()
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .build();

        return MongoClients.create(settings);
    }
}
