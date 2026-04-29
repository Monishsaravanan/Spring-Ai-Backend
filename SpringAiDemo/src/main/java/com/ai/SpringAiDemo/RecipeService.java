package com.ai.SpringAiDemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final ChatClient chatClient;

    public RecipeService(ChatClient.Builder builder) {

        this.chatClient = builder.build();
    }

    public String createRecipe(String ingredients,
                               String cuisine,
                               String dietaryRestrictions) {

        String prompt = """
                I want to create a recipe using the following ingredients: %s.

                The cuisine type I prefer is %s.

                Please consider the following dietary restrictions: %s.

                Please provide:
                1. Recipe title
                2. Ingredients list
                3. Step-by-step cooking instructions
                """
                .formatted(
                        ingredients,
                        cuisine,
                        dietaryRestrictions
                );

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}