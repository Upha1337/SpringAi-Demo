package com.example.springaidemo.service;

import com.example.springaidemo.config.MistralAiConfig;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService 
{
    @Autowired
    private MistralAiConfig _config;

    public List<float[]> embed(String text)
    {
        return _config.embeddingModel().embed(List.of(text));
    }

//    public List<float[]> generateEmbeddings(List<String> texts) {
//        EmbeddingResponse response = _config.embeddingModel().embedForResponse(texts);
//        return response.getResults().stream()
//                .map(Embedding::getOutput)
//                .collect(Collectors.toList());
//    }

    public EmbeddingResponse embedForResponse(List<String> texts) {
        return _config.embeddingModel().embedForResponse(texts);
    }
}
