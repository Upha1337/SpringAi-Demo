package com.example.springaidemo.service;

import com.example.springaidemo.config.MistralAiConfig;
import com.example.springaidemo.dtos.ChatRequest;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService 
{
    @Autowired
    private MistralAiConfig _config;
    
    public String chatTemplate(ChatRequest req)
    {
        PromptTemplate promptTemplate = new PromptTemplate("""
            Hãy cung cấp thông tin chi tiết về: {topic}
            
            Yêu cầu:
            1. Định nghĩa rõ ràng
            2. Các đặc điểm chính
            3. Ví dụ minh họa
            4. Ứng dụng thực tế
        """);

        promptTemplate.add("topic", req.toString());

        Prompt prompt = new Prompt(
                PromptTemplate.builder()
                        .template(promptTemplate.render())
                        .build().createMessage()
        );

        return _config.mistralChatClient().prompt(prompt).call().content();
    }
}
