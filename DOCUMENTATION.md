# AI Application Development Platform Documentation

## 1. Introduction

This document provides an overview of the AI Application Development Platform. It is built using Spring AI and features a refactored layered architecture including controllers, a generic service interface (`LlmService`), and specific LLM provider implementations (Ollama, and planned DashScope).

(Note: The project initially planned for Alibaba Tongyi Qwen, then focused on Ollama due to environmental setup. DashScope integration was attempted but faced challenges with dependency management due to tool/environment issues.)

## 2. Core AI Functionalities & Service Layer

The platform exposes AI functionalities through a REST API, leveraging a central service interface for flexibility.

### 2.1. `LlmService` Interface
- **Package:** `com.example.demo.service.LlmService`
- **Purpose:** Defines a generic contract for Large Language Model operations, allowing for interchangeable backend LLM providers.
- **Methods:**
  - `String generateText(String prompt)`: Generates text based on a given prompt.
  - `List<Double> embedText(String text)`: Generates a vector embedding for a given text.

### 2.2. Ollama Implementation
- **Class:** `com.example.demo.llm.ollama.OllamaService`
- **Description:** Implements `LlmService` using an Ollama backend. This is currently the default and most tested implementation.

### 2.3. Alibaba Cloud DashScope Implementation (Conditional)
- **Class:** `com.example.demo.llm.dashscope.DashScopeService`
- **Description:** Implements `LlmService` using Alibaba Cloud's DashScope (e.g., Qwen models).
- **Important Note:** This service's functionality is contingent on the user manually ensuring that the `spring-ai-alibaba-starter-dashscope` dependency and `spring-ai-alibaba-bom` are correctly added to the `pom.xml`. Automated addition of these dependencies faced persistent environment/tooling issues during development.

## 3. API Endpoints

The REST API is served by `AiController` located in `com.example.demo.controller`. The following endpoints are available:

### 3.1. Text Generation
- **Endpoint:** `POST /generate`
- **Request:** Plain text string in the request body representing the prompt.
- **Response:** Plain text string with the generated text.
- **Description:** Generates text based on the provided prompt using the currently configured LLM provider via `LlmService`.

### 3.2. Text Summarization
- **Endpoint:** `POST /summarize`
- **Request:** Plain text in the request body.
- **Response:** Plain text containing the summary.
- **Description:** Summarizes the input text. The controller constructs a summarization prompt and uses the `LlmService.generateText()` method.

### 3.3. Text Embedding
- **Endpoint:** `POST /embed`
- **Request:** Plain text in the request body.
- **Response:** JSON array of numbers representing the text embedding.
- **Description:** Generates a vector embedding for the input text using the currently configured LLM provider via `LlmService`.

## 4. Setup and Configuration

### 4.1. Prerequisites
- Java JDK (e.g., 17 or later)
- Maven
- Access to an Ollama instance (if using Ollama)
- Valid DashScope API Key (if using DashScope)

### 4.2. Application Configuration (`application.properties`)

#### Ollama Configuration:
```properties
spring.ai.ollama.base-url=http://localhost:11434 # Or your Ollama instance URL
spring.ai.ollama.chat.options.model=llama2      # Example model
# spring.ai.ollama.embedding.options.model=llama2 # Often same model for embedding or specific one
```

#### Alibaba DashScope Configuration (Conditional - see note in section 2.3):
```properties
# Alibaba DashScope Configuration
spring.ai.alibaba.dashscope.api-key=YOUR_DASHSCOPE_API_KEY_PLEASE_REPLACE
spring.ai.alibaba.dashscope.chat.options.model=qwen-turbo
spring.ai.alibaba.dashscope.embedding.options.model=text-embedding-v1
```

#### LLM Provider Selection:
The active LLM provider is chosen using the `app.llm.provider` property. The controller logic appends "Service" to this value to find the correct Spring bean (e.g., `ollama` becomes `ollamaService`).
```properties
# LLM Provider Selection (e.g., ollama, dashscope)
app.llm.provider=ollama
```
- **Accepted values:** `ollama`, `dashscope`. The application defaults to `ollama` if the property is missing or the specified provider is not found (and `ollamaService` is available).

## 5. User Interface (Intended - Not Implemented)

A web-based user interface was planned to allow users to easily interact with the AI functionalities. This was not implemented due to file system tool errors preventing the addition of necessary dependencies (e.g., Thymeleaf).

## 6. Security (Intended - Not Implemented)

User authentication and authorization using Spring Security were planned. This was not implemented due to file system tool errors preventing the addition of necessary dependencies.
    - Intended features: Form-based login, secured API endpoints.

## 7. Deployment and Monitoring (Intended - Not Implemented)

Capabilities for easy deployment (e.g., Docker) and monitoring (e.g., Spring Boot Actuator) were planned but not implemented due to file system tool errors.

## 8. API Usage Examples (If UI is not available)

Instructions on how to use `curl` or other API tools to interact with the endpoints. Note the change to `/generate` endpoint using plain text for the prompt.

e.g.,
`curl -X POST -H "Content-Type: text/plain" -d "Tell me a joke" http://localhost:8080/generate`
`curl -X POST -H "Content-Type: text/plain" -d "Some long text to summarize..." http://localhost:8080/summarize`
`curl -X POST -H "Content-Type: text/plain" -d "Text to embed" http://localhost:8080/embed`

*(Note: The actual functionality of these endpoints, especially with different providers, could not be fully verified due to Maven timeouts and dependency management issues in the execution environment).*
