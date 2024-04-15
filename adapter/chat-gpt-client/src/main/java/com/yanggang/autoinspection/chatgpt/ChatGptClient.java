package com.yanggang.autoinspection.chatgpt;

import com.yanggang.autoinspection.CustomObjectMapper;
import com.yanggang.autoinspection.chatgpt.model.ChatCompletionResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class ChatGptClient {

    private static final String TARGET_GPT_MODEL = "gpt-3.5-turbo";

    private final WebClient chatGptWebClient;

    @Value("${OPENAI_API_KEY}")
    private String openaiApiKey;

    private final CustomObjectMapper objectMapper = new CustomObjectMapper();

    public ChatGptClient(
            @Qualifier("chatGptWebClient") WebClient chatGptWebClient) {
        this.chatGptWebClient = chatGptWebClient;
    }

    public String inspect(String content, InspectionPolicy inspectionPolicy) {
        String jsonString = chatGptWebClient
                .post()
                .uri("/v1/chat/completions")
                .header("Authorization", "Bearer " + openaiApiKey) // OpenAI API 키 추가
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                        "model", TARGET_GPT_MODEL,
                        "messages", List.of(
                                Map.of("role", "system", "content", inspectionPolicy.description),
                                Map.of("role", "user", "content", inspectionPolicy.exampleContent),
                                Map.of("role", "assistant", "content", inspectionPolicy.exampleResult),
                                Map.of("role", "user", "content", content)
                        ),
                        "stream", false
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            ChatCompletionResponse response = objectMapper.readValue(jsonString, ChatCompletionResponse.class);
            return response.getChoices()[0].getMessage().getContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    public static class InspectionPolicy {
        private final String description;
        private final String exampleContent;
        private final String exampleResult;

        @Builder
        public InspectionPolicy(String description,
                                String exampleContent,
                                String exampleResult) {
            this.description = description;
            this.exampleContent = exampleContent;
            this.exampleResult = exampleResult;
        }
    }
}
