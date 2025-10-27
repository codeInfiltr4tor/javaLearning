package com.example.texttosqlchat.controller;

import com.example.texttosqlchat.service.LLMService;
import com.example.texttosqlchat.service.DatabaseExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

@RestController
public class TextToSQLController {

    private final LLMService llmService;
    private final DatabaseExecutionService dbExecutionService;

    @Autowired
    public TextToSQLController(LLMService llmService, DatabaseExecutionService dbExecutionService) {
        this.llmService = llmService;
        this.dbExecutionService = dbExecutionService;
    }

    /**
     * Main endpoint to convert a natural language query into executed database results.
     * Accessible via: GET /api/query?prompt=Find all books published after 1990
     */
    @GetMapping("/api/query")
    public ResponseEntity<?> executeTextToSql(@RequestParam String prompt) {

        System.out.println("User Prompt Received: " + prompt);

        // 1. LLM: Generate SQL Query
        String generatedSql = llmService.generateSqlQuery(prompt);

        if (generatedSql == null) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to generate SQL query from LLM.", "prompt", prompt));
        }

        System.out.println("Generated SQL: " + generatedSql);

        // 2. Database: Execute the Generated SQL
        try {
            List<Map<String, Object>> results = dbExecutionService.executeQuery(generatedSql);

            // 3. Return the results from the live database
            return ResponseEntity.ok(Map.of(
                    "prompt", prompt,
                    "sql_query", generatedSql,
                    "data", results
            ));

        } catch (UnsupportedOperationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage(), "sql_query", generatedSql));
        } catch (Exception e) {
            // Catching SQL syntax errors, database connection issues, etc.
            System.err.println("Database Execution Error: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Failed to execute SQL query against database.",
                    "sql_query", generatedSql,
                    "detail", e.getMessage()
            ));
        }
    }
}

