package com.example.springaidemo.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.springframework.ai.document.Document;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @CsvBindByName(column = "title", required = true)
    private String title;

    @CsvBindByName(column = "author", required = true)
    private String author;

    @CsvBindByName(column = "description", required = true)
    private String description;

    public Document toDocument() {
        Map<String, Object> metadata = Map.of(
                "title", this.title,
                "author", this.author
        );
        return new Document(
                String.format("Title: %s, Author: %s, Description: %s", title, author, description),
                metadata
        );
    }
}
