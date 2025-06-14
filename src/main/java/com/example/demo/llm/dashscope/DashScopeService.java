package com.example.demo.llm.dashscope;

import com.example.demo.service.LlmService;
// Attempting to use specific DashScope class names if they exist,
// otherwise, general Alibaba Tongyi might be used by the starter.
// The Spring AI auto-configuration should provide these beans.
import org.springframework.ai.alibaba.dashscope.AlibabaDashScopeChatModel;
import org.springframework.ai.alibaba.dashscope.AlibabaDashScopeEmbeddingModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.embedding.EmbeddingRequest; // Standard Spring AI
import org.springframework.ai.embedding.EmbeddingResponse; // Standard Spring AI
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Service
@Qualifier("dashscopeService") // To distinguish from other LlmService implementations
public class DashScopeService implements LlmService {

    private final AlibabaDashScopeChatModel chatModel;
    private final AlibabaDashScopeEmbeddingModel embeddingModel;

    @Autowired
    public DashScopeService(AlibabaDashScopeChatModel chatModel, AlibabaDashScopeEmbeddingModel embeddingModel) {
        this.chatModel = chatModel;
        this.embeddingModel = embeddingModel;
    }

    @Override
    public String generateText(String promptContent) {
        Prompt prompt = new Prompt(promptContent);
        ChatResponse chatResponse = chatModel.call(prompt);
        if (chatResponse != null && chatResponse.getResult() != null && chatResponse.getResult().getOutput() != null) {
            return chatResponse.getResult().getOutput().getContent();
        }
        return "Error: No response from DashScope model"; // Or throw exception
    }

    @Override
    public List<Double> embedText(String text) {
        // Using EmbeddingRequest as per standard Spring AI practice for more control
        EmbeddingRequest embeddingRequest = new EmbeddingRequest(List.of(text), null); // Options can be null
        EmbeddingResponse embeddingResponse = embeddingModel.call(embeddingRequest);
        if (embeddingResponse != null && embeddingResponse.getResults() != null && !embeddingResponse.getResults().isEmpty()) {
            return embeddingResponse.getResults().get(0).getOutput();
        }
        return List.of(); // Or throw exception
    }
}
