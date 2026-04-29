package com.ai.SpringAiDemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StabilityImageService {

    private final RestTemplate restTemplate =
            new RestTemplate();

    @Value("${stability.api.key}")
    private String apiKey;

    public String generateImage(String prompt) {

        try {

            HttpHeaders headers =
                    new HttpHeaders();

            headers.setBearerAuth(apiKey);

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            headers.set(
                    "Accept",
                    "application/json"
            );

            String body = """
            {
              "text_prompts":[{"text":"%s"}],
              "cfg_scale":7,
              "height":512,
              "width":512,
              "samples":1,
              "steps":20
            }
            """.formatted(prompt);

            HttpEntity<String> entity =
                    new HttpEntity<>(body, headers);

            String url =
                    "https://api.stability.ai/v1/generation/stable-diffusion-xl-1024-v1-0/text-to-image";

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            url,
                            HttpMethod.POST,
                            entity,
                            String.class
                    );

            return response.getBody();

        } catch (Exception e) {

            e.printStackTrace();

            return "Error: " + e.getMessage();
        }
    }
}