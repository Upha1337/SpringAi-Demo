package com.example.springaidemo.service;

import com.example.springaidemo.config.VectorStoreConifg;
import com.example.springaidemo.dtos.Book;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VectorSearchService {
    private static final Logger log = LoggerFactory.getLogger(VectorSearchService.class);
    @Autowired
    private VectorStoreConifg _config;
    
    public List<?> searchByTitle(String query, int topK) {
        String normalizedQuery = query.toLowerCase().trim();
        SearchRequest request = SearchRequest.builder()
                .query(normalizedQuery)
                .topK(Math.max(1, topK))
                .build();
        
        var result = _config.vectorStore().similaritySearch(request)
                .stream()
                .filter(doc -> doc.getMetadata().get("title").toString().toLowerCase().trim().startsWith(normalizedQuery))
                .limit(topK)
                .distinct()
                .map(this::documentToMap);
        
        return result.collect(Collectors.toList());
    }
    
    public <T> List<T> readCSV(String path, Class<T> clazz)
    {
        try (Reader reader = new FileReader(path)){
            return new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void addDocument() {
        try {
            String path = "src/main/java/com/example/springaidemo/data/book.csv";
            List<Book> books = this.readCSV(path, Book.class);
            log.info("Books loaded from CSV: {}", books.size());
            List<Document> docs = books.stream()
                    .map(Book::toDocument)
                    .toList();
            _config.vectorStore().add(docs);
            log.info("Added documents to vector store");
        } catch (Exception e) {
            log.error("Error reading CSV or adding documents", e);
        }
    }
    
    
    private Map<String, Object> documentToMap(Document document)
    {
        Map<String, Object> bookMap = new HashMap<>();
        String content = document.getText();

        assert content != null;
        String[] parts = content.split(", ");

        for (String part : parts) {
            String[] keyValue = part.split(": ", 2);
            if (keyValue.length == 2) {
                bookMap.put(keyValue[0].trim().toLowerCase(), keyValue[1].trim());
            }
        }
        
        return bookMap;
    }
}
