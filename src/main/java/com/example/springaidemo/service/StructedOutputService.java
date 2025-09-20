package com.example.springaidemo.service;

import com.example.springaidemo.config.MistralAiConfig;
import com.example.springaidemo.dtos.ChatRequest;
import com.example.springaidemo.dtos.TopicInfo;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StructedOutputService 
{
    @Autowired
    private MistralAiConfig _config;

    public TopicInfo chatStructedOutput(ChatRequest req)
    {
        BeanOutputConverter<TopicInfo> outputConverter = new BeanOutputConverter<>(TopicInfo.class);

        PromptTemplate promptTemplate = new PromptTemplate("""
            Hãy cung cấp thông tin chi tiết về: {topic}
            
            Yêu cầu:
            1. Định nghĩa rõ ràng
            2. Các đặc điểm chính
            3. Ví dụ minh họa
            4. Ứng dụng thực tế
            
            {format}
        """);

        promptTemplate.add("topic", req.toString());
        promptTemplate.add("format", outputConverter.getFormat());

        var result = _config.mistralChatClient().prompt(promptTemplate.render()).call().content();
        return outputConverter.convert(result);
    }

    public Map<String, Object> getBookTitlesConverter(String genere) {
        MapOutputConverter converter = new MapOutputConverter();
        String format = converter.getFormat();

        String bookTemplate = """
            Liệt kê 5 bộ manga hay nhất {genre}
            , trả về output là map, key là tiêu đề (title) và giá trị là tác giả (author) 
            
            {format}
        """;

        return _config.mistralChatClient().prompt()
                .user(u -> u.text(bookTemplate)
                        .param("genre", genere)
                        .param("format", format)
                )
                .call()
                .entity(converter);
    }
}
