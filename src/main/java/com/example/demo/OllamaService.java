package com.example.demo;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel; // Import for EmbeddingModel
import org.springframework.stereotype.Service;

@Service
public class OllamaService {

    private final OllamaChatModel chatModel;
    private final OllamaEmbeddingModel embeddingModel; // Field for EmbeddingModel

    public OllamaService(OllamaChatModel chatModel, OllamaEmbeddingModel embeddingModel) { // Updated constructor
        this.chatModel = chatModel;
        this.embeddingModel = embeddingModel; // Initialize EmbeddingModel
    }

    public String generateResponse(String prompt) {
        return chatModel.call(prompt);
    }

    public String summarizeText(String text) {
        String prompt = "Summarize the following text: \n" + text;
        return chatModel.call(prompt);
    }

    public java.util.List<Double> embedText(String text) {
        return embeddingModel.embed(text);
    }
}
