package com.learning.tinderaibackend.conversations;

import java.time.LocalDateTime;
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
                    logger.error("Profile not found with ID: {}", request.profileId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found for id: " + request.profileId());
                });

        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                profile.profileId(), // Profile with which I'm having conversation.
                new ArrayList<>()
        );

        return conversationRepository.save(conversation);
    }

    public Conversation getAllConversation(String conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find conversation with ID : " + conversationId));
    }


    public Conversation addMessageToConversation(String conversationId, ChatMessage chatMessage) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find conversation with ID : " + conversationId));

        profileRepository.findById(chatMessage.authorId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find profile with ID " + chatMessage.authorId())
                );

        // TODO: Need to validate that the author of a message happens to be only the profile associated with user
        ChatMessage messageWithTime = new ChatMessage(
                chatMessage.messageTest(),
                chatMessage.authorId(),
                LocalDateTime.now()
        );
        conversation.messages().add(messageWithTime);
        conversationRepository.save(conversation);
        return conversation;
    }

}
