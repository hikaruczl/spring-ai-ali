package com.example.demo.service;

import java.util.List;
// import reactor.core.publisher.Flux; // Commenting out for now as it's a consideration

public interface LlmService {
    String generateText(String prompt);
    List<Double> embedText(String text);
    // Consider adding a streaming variant if that's a future goal
    // Flux<String> generateTextStream(String prompt);
}
