package com.example.demo.llm.ollama;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
// Import for Spring AI Prompt, ChatResponse, Generation, EmbeddingRequest, EmbeddingResponse, Embedding are not strictly needed
// for OllamaService's current direct usage of call(String) and embed(String)
// but if the underlying model's methods were to be mocked with more complex objects, they might be.
// For now, they are unused in THIS test if OllamaService uses the simplified methods.

import java.util.List;
// import java.util.Collections; // Unused

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OllamaServiceTest {

    @Mock
    private OllamaChatModel mockChatModel;

    @Mock
    private OllamaEmbeddingModel mockEmbeddingModel;

    @InjectMocks
    private OllamaService ollamaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateText_shouldReturnModelResponse() {
        String promptText = "Test prompt";
        String expectedResponse = "Test response";

        // OllamaService.generateText directly calls chatModel.call(String)
        when(mockChatModel.call(promptText)).thenReturn(expectedResponse);

        String actualResponse = ollamaService.generateText(promptText);

        assertEquals(expectedResponse, actualResponse);
        verify(mockChatModel).call(promptText);
    }

    @Test
    void embedText_shouldReturnEmbeddings() {
        String textToEmbed = "Test text";
        List<Double> expectedEmbeddings = List.of(0.1, 0.2, 0.3);

        // OllamaService.embedText directly calls embeddingModel.embed(String)
        when(mockEmbeddingModel.embed(textToEmbed)).thenReturn(expectedEmbeddings);

        List<Double> actualEmbeddings = ollamaService.embedText(textToEmbed);

        assertEquals(expectedEmbeddings, actualEmbeddings);
        verify(mockEmbeddingModel).embed(textToEmbed);
    }
}
