package com.example.texttosqlchat.service;

import com.example.texttosqlchat.dto.TextToSqlRequest;
import com.example.texttosqlchat.dto.SqlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    private final WebClient webClient;
    private final String llmModel;

    // Define the specific API path for chat completions
    private static final String CHAT_COMPLETION_PATH = "/chat/completions";

    // Use constructor injection for dependencies and configuration properties
    public OpenAIService(
            WebClient.Builder webClientBuilder,
            @Value("${llm.api.base-url}") String baseUrl,
            @Value("${llm.api.model}") String model,
            @Value("${llm.api.key}") String apiKey) {

        // Configure WebClient for calling the LLM API
        // NOTE: We rely on the base URL being structured correctly (e.g., https://router.huggingface.co/v1)
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                // Use Bearer token authorization, standard for many APIs
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();
        this.llmModel = model;
    }

    /**
     * The main method to convert a natural language query into a valid SQL statement.
     * @param request The user's natural language request.
     * @return The response DTO containing the generated SQL.
     */
    public SqlResponse convertTextToSql(TextToSqlRequest request) {
        try {
            // 1. Generate the prompt with the schema and the user query
            String prompt = createLlmPrompt(request.getNaturalLanguageQuery());

            // 2. Build the LLM API request payload using the standard 'messages' structure
            Map<String, Object> payload = Map.of(
                    "model", llmModel,
                    // The 'messages' structure for system instruction and user query
                    "messages", createMessages(prompt),
                    "max_tokens", 200,
                    "temperature", 0.0 // Ensure deterministic, precise SQL generation
            );

            // 3. Call the LLM API using WebClient with the corrected path
            Map responseMap = webClient.post()
                    .uri(CHAT_COMPLETION_PATH) // Use the standard, fixed path
                    .body(BodyInserters.fromValue(payload))
                    .retrieve()
                    .bodyToMono(Map.class) // Retrieve as a generic map
                    .block();

            String generatedSql = extractSqlFromLlmResponse(responseMap);

            if (generatedSql == null || generatedSql.trim().isEmpty()) {
                return SqlResponse.builder()
                        .status("Error: LLM returned an empty or invalid SQL statement.")
                        .build();
            }

            // 4. Return the successful response
            return SqlResponse.builder()
                    .generatedSql(generatedSql.trim())
                    .status("Success")
                    .build();

        } catch (Exception e) {
            System.err.println("LLM API Call Error: " + e.getMessage());
            return SqlResponse.builder()
                    .status("LLM API Error: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Helper to structure the prompt with the database schema context.
     * This remains largely the same, focusing on clear instructions.
     */
    private String createLlmPrompt(String userQuery) {
        String databaseSchema = deriveSchemaFromProductEntity();

        // System Instruction: Tell the LLM its role.
        return String.format("""
            You are an expert SQL translator. 
            You must ONLY return a single, valid SQL query based on the user's request and the provided schema. 
            DO NOT include any explanations, formatting, markdown tags (like ```sql), or extra text.

            DATABASE SCHEMA:
            %s
            
            USER REQUEST:
            %s
            
            SQL QUERY:
            """, databaseSchema, userQuery);
    }

    /**
     * A simple representation of the Product entity's SQL schema.
     */
    private String deriveSchemaFromProductEntity() {
        // Use standard SQL data types for clarity
        return """
            TABLE product (
              id BIGINT PRIMARY KEY, 
              name VARCHAR, 
              category VARCHAR, 
              price DOUBLE, 
              stock_quantity INTEGER
            );
            """;
    }

    /**
     * Helper to create the 'messages' structure required by the OpenAI-compatible API.
     */
    private List<Map<String, String>> createMessages(String systemPrompt) {
        // We put all instructions into a single user message for simplicity,
        // as the final output is just the SQL.
        return List.of(
                Map.of("role", "user", "content", systemPrompt)
        );
    }

    /**
     * Extracts the generated text content from the standard API response structure.
     */
    private String extractSqlFromLlmResponse(Map response) {
        System.out.println("LLM API Response Received: " + response);

        try {
            // Traverse the standard path: choices[0].message.content
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, String> message = (Map<String, String>) choices.get(0).get("message");
                if (message != null) {
                    return message.get("content");
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to parse LLM response: " + e.getMessage());
        }
        return null;
    }
}
