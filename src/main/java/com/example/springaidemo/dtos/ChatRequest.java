package com.example.springaidemo.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRequest
{
    private String prompt;
}