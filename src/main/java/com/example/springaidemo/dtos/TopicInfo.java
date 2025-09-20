package com.example.springaidemo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicInfo 
{
    @JsonProperty("dinh nghia")
    @JsonPropertyDescription("Định nghĩa của chủ đề")
    private String definition;

    @JsonProperty("chuc nang")
    @JsonPropertyDescription("Các đặc điểm chính của chủ đề")
    private List<String> mainFeatures;

    @JsonProperty("vi du")
    @JsonPropertyDescription("Ví dụ minh họa")
    private String example;

    @JsonProperty("ung dung thuc te")
    @JsonPropertyDescription("Ứng dụng thực tế chủ đề")
    private List<String> realWorldApplications;
}
