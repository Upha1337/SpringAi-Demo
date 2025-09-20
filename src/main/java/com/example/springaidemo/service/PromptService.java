package com.example.springaidemo.service;

import com.example.springaidemo.dtos.ChatRequest;
import com.example.springaidemo.config.MistralAiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromptService {
    private static final Logger log = LoggerFactory.getLogger(PromptService.class);
    @Autowired
    private MistralAiConfig _config;

    public String chatClient(ChatRequest req)
    {
        log.info(req.getPrompt());
        ChatClient chatClient = _config.mistralChatClient();
        return chatClient
                .prompt(req.getPrompt())
                .call()
                .content();
    }

    public String chatModel(ChatRequest req)
    {
        return _config.mistralAiChatModel().call(req.getPrompt());
    }
}
