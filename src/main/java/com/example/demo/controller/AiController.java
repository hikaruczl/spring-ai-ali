package com.example.demo.controller;

import com.example.demo.service.LlmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment; // For reading properties
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // Import Map

@RestController
public class AiController {

    private final Map<String, LlmService> llmServices; // Map of all LlmService beans
    private final Environment environment;
    private LlmService selectedLlmService; // To hold the chosen service

    @Autowired
    public AiController(Map<String, LlmService> llmServices, Environment environment) {
        this.llmServices = llmServices;
        this.environment = environment;
        this.selectedLlmService = getSelectedLlmService();
    }

    private LlmService getSelectedLlmService() {
        String provider = environment.getProperty("app.llm.provider", "ollamaService"); // Default to ollamaService
        // Ensure the qualifier includes "Service" if the bean names are like "ollamaService", "dashscopeService"
        if (provider != null && !provider.endsWith("Service")) { // Simple heuristic, ensure provider is not null
             provider = provider + "Service";
        }
        LlmService service = llmServices.get(provider);
        if (service == null) {
            // Fallback or error handling if the configured provider is not found
            System.err.println("Warning: LLM provider '" + provider + "' not found. Falling back to 'ollamaService'.");
            service = llmServices.get("ollamaService");
            if (service == null && !llmServices.isEmpty()) {
                 // If ollamaService is also missing, pick the first available one.
                 Map.Entry<String, LlmService> firstEntry = llmServices.entrySet().iterator().next();
                 service = firstEntry.getValue();
                 System.err.println("Warning: 'ollamaService' not found. Falling back to first available LLM provider: " + firstEntry.getKey());
            } else if (service == null) {
                 throw new IllegalStateException("No LLM service providers found. Please check your configuration.");
            }
        }
        // Small correction: provider name might already have "Service" or it might be null
        String serviceKey = "unknown";
        for(Map.Entry<String, LlmService> entry : llmServices.entrySet()){
            if(entry.getValue() == service){
                serviceKey = entry.getKey();
                break;
            }
        }
        System.out.println("Selected LLM Provider: " + serviceKey); // For debugging
        return service;
    }

    // Use this.selectedLlmService in the endpoint methods
    @PostMapping("/generate") // Changed to PostMapping,RequestBody as per snippet
    public String generate(@RequestBody String prompt) {
        return this.selectedLlmService.generateText(prompt);
    }

    @PostMapping("/summarize")
    public String summarize(@RequestBody String textToSummarize) {
        // Basic summarization prompt, can be enhanced
        String prompt = "Summarize the following text: " + textToSummarize;
        return this.selectedLlmService.generateText(prompt);
    }

    @PostMapping("/embed")
    public List<Double> embed(@RequestBody String textToEmbed) {
        return this.selectedLlmService.embedText(textToEmbed);
    }
}
