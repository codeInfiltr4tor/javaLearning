package com.example.texttosqlchat.controller;

import com.example.texttosqlchat.dto.TextToSqlRequest;
import com.example.texttosqlchat.dto.SqlResponse;
import com.example.texttosqlchat.service.OpenAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sql")
public class TextToSqlController {

    private final OpenAIService llmService;
    // NOTE: In a real app, you would inject a JpaRepository or JdbcTemplate here
    // private final ProductRepository productRepository;

    public TextToSqlController(OpenAIService llmService /*, ProductRepository productRepository */) {
        this.llmService = llmService;
        // this.productRepository = productRepository;
    }

    /**
     * Endpoint to convert a natural language query to an executable SQL query.
     */
    @PostMapping("/generate-and-execute")
    public ResponseEntity<SqlResponse> generateAndExecuteSql(@RequestBody TextToSqlRequest request) {
        if (request.getNaturalLanguageQuery() == null || request.getNaturalLanguageQuery().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    SqlResponse.builder().status("Error: Query cannot be empty.").build()
            );
        }

        // 1. Call the service to get the generated SQL from the LLM
        SqlResponse sqlResponse = llmService.convertTextToSql(request);

        if ("Success".equals(sqlResponse.getStatus()) && sqlResponse.getGeneratedSql() != null) {
            // 2. Execute the generated SQL (Simulated)
            Object queryResult = simulateSqlQueryExecution(sqlResponse.getGeneratedSql());

            // 3. Update the response with the result
            sqlResponse.setQueryResult(queryResult);
            sqlResponse.setStatus("Successfully generated and simulated execution.");
        }

        return ResponseEntity.ok(sqlResponse);
    }

    /**
     * --- IMPORTANT: This is a SIMULATION ---
     * In a real application, you would use a Spring mechanism like JdbcTemplate,
     * a stored procedure, or a safe dynamic query executor to execute the raw SQL.
     * NOTE: Executing arbitrary, LLM-generated SQL can be a massive security risk (SQL Injection).
     * Always validate the generated SQL extensively or use a limited-permission database user.
     */
    private Object simulateSqlQueryExecution(String sql) {
        System.out.println("Executing SQL: " + sql);

        // Simple mock data based on the sample Product entity
        return List.of(
                Map.of("name", "Laptop Pro", "price", 999.99),
                Map.of("name", "Mouse Wireless", "price", 49.99)
        );
    }
}
