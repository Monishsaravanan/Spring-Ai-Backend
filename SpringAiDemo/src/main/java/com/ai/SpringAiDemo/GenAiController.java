package com.ai.SpringAiDemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*")
@RestController

public class GenAiController {

    private final ChatService chatService;
    private final RecipeService recipeService;

    @Value("${stability.api.key}")
    private String apiKey;

    public GenAiController(ChatService chatService,
                           RecipeService recipeService) {

        this.chatService = chatService;
        this.recipeService = recipeService;
    }

    // CHAT API

    @GetMapping("/ask-ai")
    public String getResponse(@RequestParam String prompt) {

        return chatService.getResponse(prompt);
    }

    @GetMapping("/ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt) {

        return chatService.getResponseOptions(prompt);
    }


    //IMAGE API
    @GetMapping(value = "/image", produces = "text/html")
    public String generateImages(@RequestParam String prompt) {

        try {

            String html = """
                
                <html>

                <head>

                    <title>
                        AI Image Generator
                    </title>

                    <style>

                        body {

                            background-color: black;
                            color: white;
                            text-align: center;
                            font-family: Arial;
                            padding: 30px;
                        }

                        img {

                            border-radius: 20px;
                            box-shadow: 0px 0px 20px white;
                            margin-top: 20px;
                        }

                    </style>

                </head>

                <body>

                    <h1>
                        🎨 AI Image Generator
                    </h1>

                    <h2>
                        Prompt:
                    </h2>

                    <p style="
                        font-size:20px;
                    ">
                        %s
                    </p>

                    <img
                        src="https://loremflickr.com/600/600/%s"
                        width="600"
                        height="600"
                    />

                </body>

                </html>

                """.formatted(prompt, prompt);

            return html;

        } catch (Exception e) {

            e.printStackTrace();

            return "<h1>Error : " + e.getMessage() + "</h1>";
        }
    }
    // RECIPE API

    @GetMapping("/recipe-creator")
    public String recipeCreator(
            @RequestParam String ingredients,

            @RequestParam(defaultValue = "any")
            String cuisine,

            @RequestParam(defaultValue = "")
            String dietaryRestrictions
    ) {

        return recipeService.createRecipe(
                ingredients,
                cuisine,
                dietaryRestrictions
        );
    }
}