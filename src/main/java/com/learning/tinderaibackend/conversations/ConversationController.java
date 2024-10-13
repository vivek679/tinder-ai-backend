package com.learning.tinderaibackend.conversations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(path = "/conversations/{conversationId}")
    public Conversation getAllConversation(@PathVariable String conversationId) {
        return conversationService.getAllConversation(conversationId);
    }

    @PostMapping(path = "/conversations/{conversationId}")
    public Conversation addMessageToConversation(@PathVariable String conversationId, @RequestBody ChatMessage chatMessage) {
        return conversationService.addMessageToConversation(conversationId, chatMessage);
    }

}
