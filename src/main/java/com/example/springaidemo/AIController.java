package com.example.springaidemo;

import com.example.springaidemo.dtos.ChatRequest;
import com.example.springaidemo.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController("LLM")
@RequestMapping("/api/v1/")
@Tag(name = "LLM")
public class AIController {
    @Autowired private PromptService _promptService;
    @Autowired private StructedOutputService _structedOutputService;
    @Autowired private TemplateService _templateService;
    @Autowired private MultimodalityService _multimodalityService;
    @Autowired private EmbeddingService       _embeddingService;
    @Autowired private VectorSearchService _semanticSearchService;

    @PostMapping("prompt")
    public ResponseEntity<?> prompt(@RequestBody ChatRequest req) {
        var resultModel = _promptService.chatModel(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "prompt", req.toString(),
                "resultModel", resultModel
        ));
    }

    @PostMapping("topic/template")
    public ResponseEntity<?> promptInformation(@RequestBody ChatRequest req) {
        var resultModel = _templateService.chatTemplate(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "prompt", req.toString(),
                "resultModel", resultModel
        ));
    }

    @PostMapping("topic/structedOutput")
    public ResponseEntity<?> structedOutput(@RequestBody ChatRequest req) {
        var info = _structedOutputService.chatStructedOutput(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(info);
    }

    @PostMapping("getBookFromGenre")
    public ResponseEntity<?> structedOutput(String genre) {
        var info = _structedOutputService.getBookTitlesConverter(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(info);
    }

    @PostMapping(value = "multimodal/fluent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> multimodalChatFluent(
            @RequestParam("message") String message,
            @RequestParam("image") MultipartFile imageFile
    ){
        String response = _multimodalityService.scanImage(message,imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("embed/v1")
    public ResponseEntity<?> embedText1(String text) {
        var response = _embeddingService.embed(text);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("embed/v2")
    public ResponseEntity<?> embedText2(@RequestBody List<String> text) {
        var response = _embeddingService.embedForResponse(text);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("semantic-search/add")
    public ResponseEntity<?> addDocument()
    {
        _semanticSearchService.addDocument();
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("semantic-search/search")
    public ResponseEntity<?> search(
            String title,
            int topK
    ) {
        var result = _semanticSearchService.searchByTitle(
                title,
                topK
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
