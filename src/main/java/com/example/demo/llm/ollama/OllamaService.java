package com.example.demo.llm.ollama;

import com.example.demo.service.LlmService;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier; // Added import
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Qualifier("ollamaService") // Added qualifier
public class OllamaService implements LlmService {

    private final OllamaChatModel chatModel;
    private final OllamaEmbeddingModel embeddingModel;

    public OllamaService(OllamaChatModel chatModel, OllamaEmbeddingModel embeddingModel) {
        this.chatModel = chatModel;
        this.embeddingModel = embeddingModel;
    }

    @Override
    public String generateText(String prompt) {
        return chatModel.call(prompt);
    }

    // This method is specific to OllamaService, not part of LlmService interface
    public String summarizeText(String text) {
        String prompt = "Summarize the following text: \n" + text;
        return chatModel.call(prompt);
    }

    @Override
    public List<Double> embedText(String text) {
        return embeddingModel.embed(text);
    }
}
