package com.learning.tinderaibackend.ollama;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LlamaAiService {

    private final OllamaChatModel chatModel;
    private final PromptRepository promptRepository;

    public String generateResponse(String prompt) {
        ChatResponse response = chatModel.call(
                new Prompt(prompt, OllamaOptions.create()
                        .withModel("llama3.1")
                        .withTemperature(0.9D))
        );
        logger.info("Chat response output :{}", response.getResult().getOutput());
        return response.getResult().getOutput().getContent();
    }

    public String processThePrompt(String prompt) {
        ChatResponse response = chatModel.call(
                new Prompt(prompt, OllamaOptions.create()
                        .withModel("llama3.1")
                        .withTemperature(0.9D))
        );
        PromptRequest request  = new PromptRequest(UUID.randomUUID().toString(), prompt, LocalDateTime.now());
        promptRepository.save(request);
        System.out.println(response.getMetadata());
        logger.info(String.valueOf(response.getMetadata()));
        return response.getResult().getOutput().getContent();
    }

}
