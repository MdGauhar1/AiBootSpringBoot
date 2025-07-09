package com.example.redAi;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://mdgauhar1.github.io")
public class AIController {

    private final String OPENROUTER_API_KEY = System.getenv("OPENROUTER_API_KEY");
    private final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";

    // 👉 Add this method to handle preflight (CORS check)
    @RequestMapping(value = "/ask", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/ask")
    public String getAIResponse(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + OPENROUTER_API_KEY);
        headers.set("HTTP-Referer", "https://mdgauhar1.github.io"); // or any placeholder
        headers.set("X-Title", "AI Voice App");

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "mistralai/mistral-7b-instruct"); // 💡 free & smart
        payload.put("messages", List.of(
                Map.of("role", "system", "content", "You are a helpful AI voice assistant."),
                Map.of("role", "user", "content", userMessage)
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(OPENROUTER_URL, entity, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return message.get("content").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, I couldn't reach the AI.";
        }
    }
}
