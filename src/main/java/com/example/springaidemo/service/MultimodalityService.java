package com.example.springaidemo.service;

import com.example.springaidemo.config.MistralAiConfig;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MultimodalityService {
    @Autowired
    private MistralAiConfig _config;

    public String scanImage(String message, MultipartFile imageFile)
    {
        if (imageFile == null || imageFile.getContentType() == null) {
            throw new IllegalArgumentException("Image file and its content type must not be null");
        }

        Media media = new Media(
                MimeTypeUtils.parseMimeType(imageFile.getContentType()),
                imageFile.getResource()
        );

        return _config.mistralChatClient()
                .prompt()
                .user(u -> u
                        .text(message)
                        .media(media)
                )
                .call()
                .content();
    }
}
