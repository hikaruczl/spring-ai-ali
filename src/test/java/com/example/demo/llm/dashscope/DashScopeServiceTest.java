package com.example.demo.llm.dashscope;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.alibaba.dashscope.AlibabaDashScopeChatModel;
import org.springframework.ai.alibaba.dashscope.AlibabaDashScopeEmbeddingModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.Embedding;


import java.util.List;
// import java.util.Collections; // Unused based on provided snippet

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DashScopeServiceTest {

    @Mock
    private AlibabaDashScopeChatModel mockChatModel;

    @Mock
    private AlibabaDashScopeEmbeddingModel mockEmbeddingModel;

    @InjectMocks
    private DashScopeService dashScopeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateText_shouldReturnModelResponse() {
        String promptText = "Test prompt";
        String expectedResponseText = "Test response";

        ChatResponse mockChatResponse = new ChatResponse(List.of(new Generation(expectedResponseText)));
        when(mockChatModel.call(any(Prompt.class))).thenReturn(mockChatResponse);

        String actualResponse = dashScopeService.generateText(promptText);

        assertEquals(expectedResponseText, actualResponse);
        verify(mockChatModel).call(any(Prompt.class));
    }

    @Test
    void embedText_shouldReturnEmbeddings() {
        String textToEmbed = "Test text";
        List<Double> expectedEmbeddings = List.of(0.1, 0.2, 0.3);

        EmbeddingResponse mockEmbeddingResponse = new EmbeddingResponse(List.of(new Embedding(expectedEmbeddings,0)));
        when(mockEmbeddingModel.call(any(EmbeddingRequest.class))).thenReturn(mockEmbeddingResponse);

        List<Double> actualEmbeddings = dashScopeService.embedText(textToEmbed);

        assertEquals(expectedEmbeddings, actualEmbeddings);
        verify(mockEmbeddingModel).call(any(EmbeddingRequest.class));
    }

    @Test
    void generateText_whenChatResponseIsNull_shouldReturnErrorMessage() {
        when(mockChatModel.call(any(Prompt.class))).thenReturn(null);
        String response = dashScopeService.generateText("prompt");
        assertTrue(response.contains("Error: No response"));
    }

    @Test
    void generateText_whenChatResultIsNull_shouldReturnErrorMessage() {
        ChatResponse mockChatResponse = mock(ChatResponse.class); // Mock to control getResult()
        when(mockChatResponse.getResult()).thenReturn(null);
        when(mockChatModel.call(any(Prompt.class))).thenReturn(mockChatResponse);
        String response = dashScopeService.generateText("prompt");
        assertTrue(response.contains("Error: No response"));
    }

    @Test
    void generateText_whenChatOutputIsNull_shouldReturnErrorMessage() {
        Generation mockGeneration = mock(Generation.class);
        when(mockGeneration.getOutput()).thenReturn(null);
        ChatResponse mockChatResponse = new ChatResponse(List.of(mockGeneration));
        when(mockChatModel.call(any(Prompt.class))).thenReturn(mockChatResponse);
        String response = dashScopeService.generateText("prompt");
        assertTrue(response.contains("Error: No response"));
    }

    @Test
    void embedText_whenEmbeddingResponseIsNull_shouldReturnEmptyList() {
         when(mockEmbeddingModel.call(any(EmbeddingRequest.class))).thenReturn(null);
         List<Double> embeddings = dashScopeService.embedText("text");
         assertTrue(embeddings.isEmpty());
    }

    @Test
    void embedText_whenEmbeddingResultsIsNull_shouldReturnEmptyList() {
        EmbeddingResponse mockEmbeddingResponse = mock(EmbeddingResponse.class);
        when(mockEmbeddingResponse.getResults()).thenReturn(null);
        when(mockEmbeddingModel.call(any(EmbeddingRequest.class))).thenReturn(mockEmbeddingResponse);
        List<Double> embeddings = dashScopeService.embedText("text");
        assertTrue(embeddings.isEmpty());
    }

    @Test
    void embedText_whenEmbeddingResultsIsEmpty_shouldReturnEmptyList() {
        EmbeddingResponse mockEmbeddingResponse = new EmbeddingResponse(List.of()); // Empty list of results
        when(mockEmbeddingModel.call(any(EmbeddingRequest.class))).thenReturn(mockEmbeddingResponse);
        List<Double> embeddings = dashScopeService.embedText("text");
        assertTrue(embeddings.isEmpty());
    }
}
