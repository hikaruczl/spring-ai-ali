package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaController {

    private final OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam(value = "prompt", defaultValue = "Tell me a joke") String prompt) {
        return ollamaService.generateResponse(prompt);
    }

    @PostMapping("/summarize")
    public String summarize(@RequestBody String textToSummarize) {
        return ollamaService.summarizeText(textToSummarize);
    }

    @PostMapping("/embed")
    public java.util.List<Double> embed(@RequestBody String textToEmbed) {
        return ollamaService.embedText(textToEmbed);
    }
}
