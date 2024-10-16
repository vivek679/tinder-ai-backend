package com.learning.tinderaibackend.conversations;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "v1/conversations")
public class ConversationController {

    private final ConversationServiceImpl conversationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Conversation createNewConversation(@RequestBody CreateConversationRequest request) {
        return conversationService.createNewConversation(request);
    }

    @GetMapping(path = "/{conversationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Conversation getAllConversation(@PathVariable String conversationId) {
        return conversationService.getAllConversation(conversationId);
    }

    @PostMapping(path = "/{conversationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Conversation addMessageToConversation(@PathVariable String conversationId, @RequestBody ChatMessage chatMessage) {
        return conversationService.addMessageToConversation(conversationId, chatMessage);
    }

}
