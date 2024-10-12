package com.learning.tinderaibackend.conversations;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.learning.tinderaibackend.profile.Profile;
import com.learning.tinderaibackend.profile.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationServiceImpl {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;

    public Conversation createNewConversation(CreateConversationRequest request) {

        Profile profile = profileRepository.findById(request.profileId())
                .orElseThrow(() -> {
                    log.error("Profile not found with ID: {}", request.profileId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND);
//                    return new ApiExceptions.NotFoundException("Profile not found for id: " + request.profileId());
                });

        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                profile.profileId(), // Profile with which I'm having conversation.
                new ArrayList<>()
        );

        return conversationRepository.save(conversation);
    }


}
