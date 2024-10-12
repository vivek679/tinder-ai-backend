package com.learning.tinderaibackend.conversations;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationServiceImpl conversationService;

    @PostMapping(path = "/conversations")
    public Conversation createNewConversation(@RequestBody CreateConversationRequest request) {
        return conversationService.createNewConversation(request);
    }


}
