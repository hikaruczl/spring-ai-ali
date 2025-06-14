# AI Application Development Platform Documentation

## 1. Introduction

This document provides an overview of the AI Application Development Platform, built using Spring AI with Ollama integration. (Note: Originally planned with Alibaba Tongyi Qwen, but Ollama was used due to environmental setup).

## 2. Core AI Functionalities

The platform provides the following core AI functionalities accessible via a REST API:

### 2.1. Text Generation
- **Endpoint:** `POST /generate`
- **Request:** JSON with a "prompt" field (e.g., `{"prompt": "Translate 'hello' to French"}`)
- **Response:** JSON with the generated text.
- **Description:** Generates text based on the provided prompt using the configured Ollama model.

### 2.2. Text Summarization
- **Endpoint:** `POST /summarize`
- **Request:** Plain text in the request body.
- **Response:** Plain text containing the summary.
- **Description:** Summarizes the input text.

### 2.3. Text Embedding
- **Endpoint:** `POST /embed`
- **Request:** Plain text in the request body.
- **Response:** JSON array of numbers representing the text embedding.
- **Description:** Generates a vector embedding for the input text.

## 3. Setup and Configuration (Intended)

### 3.1. Prerequisites
- Java JDK (version)
- Maven
- Access to an Ollama instance

### 3.2. Application Configuration
- `application.properties` would contain:
  - `spring.ai.ollama.base-url=http://localhost:11434` (or your Ollama instance URL)
  - `spring.ai.ollama.chat.options.model=your-ollama-model` (e.g., llama2)
  - `spring.ai.ollama.embedding.options.model=your-ollama-embedding-model`

## 4. User Interface (Intended - Not Implemented)

A web-based user interface was planned to allow users to easily interact with the AI functionalities. This was not implemented due to file system tool errors preventing the addition of necessary dependencies (Thymeleaf).

## 5. Security (Intended - Not Implemented)

User authentication and authorization using Spring Security were planned. This was not implemented due to file system tool errors preventing the addition of necessary dependencies.
    - Intended features: Form-based login, secured API endpoints.

## 6. Deployment and Monitoring (Intended - Not Implemented)

Capabilities for easy deployment (e.g., Docker) and monitoring (e.g., Spring Boot Actuator) were planned but not implemented due to file system tool errors.

## 7. API Usage Examples (If UI is not available)

Instructions on how to use `curl` or other API tools to interact with the endpoints.

e.g.,
`curl -X POST -H "Content-Type: application/json" -d '{"prompt":"Hello"}' http://localhost:8080/generate`
`curl -X POST -H "Content-Type: text/plain" -d "Some long text to summarize..." http://localhost:8080/summarize`
`curl -X POST -H "Content-Type: text/plain" -d "Text to embed" http://localhost:8080/embed`

*(Note: The actual functionality of these endpoints could not be fully verified due to Maven timeouts in the execution environment).*
