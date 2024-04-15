package com.yanggang.autoinspection.chatgpt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanggang.autoinspection.CustomObjectMapper;
import com.yanggang.autoinspection.PostAutoInspectPort;
import com.yanggang.autoinspection.content.inspectedpost.model.AutoInspectionResult;
import com.yanggang.autoinspection.content.post.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostAutoInspectAdapter implements PostAutoInspectPort {

    private final ChatGptClient chatGptClient;
    private final CustomObjectMapper objectMapper = new CustomObjectMapper();

    public PostAutoInspectAdapter(ChatGptClient chatGptClient) {
        this.chatGptClient = chatGptClient;
    }

    @Override
    public AutoInspectionResult inspect(Post post, String categoryName) {
        String content = buildInspectContent(post, categoryName);

        ChatGptClient.InspectionPolicy inspectionPolicy = ChatGptClient.InspectionPolicy.builder()
                .description(InspectionGuidance.DESCRIPTION)
                .exampleContent(InspectionGuidance.EXAMPLE_CONTENT)
                .exampleResult(InspectionGuidance.EXAMPLE_RESULT)
                .build();

        String inspectResult = chatGptClient.inspect(content, inspectionPolicy);
        try {
            return objectMapper.readValue(inspectResult, AutoInspectionResult.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildInspectContent(Post post, String categoryName) {
        return String.format(
                "[%s] %s - %s",
                categoryName,
                post.getTitle(),
                post.getContent()
        );
    }

    public static class InspectionGuidance {
        private static final String DESCRIPTION =
                "The task you need to accomplish is to return two items ('status' and 'tags') in JSON format. " +
                        "The information I will provide will be in the format '[Post category] Post content.' " +
                        "Then, if the content of the post aligns well with the meaning or theme of the post category, " +
                        "fill the 'status' field with the string 'GOOD.' " +
                        "If the meaning or theme appears unrelated, " +
                        "fill the 'status' field with the string 'BAD.' " +
                        "Additionally, extract and compile a list of up to 5 keywords " +
                        "that seem most important in the post content and populate the 'tags' field with them.";
        private static final String EXAMPLE_CONTENT =
                "[Health] Reps and Muscle Size - To increase muscle size, " +
                        "it is considered most ideal to exercise with the maximum weight " +
                        "that allows 8 to 12 repetitions per set.";
        private static final String EXAMPLE_RESULT =
                "{\"status\":\"GOOD\",\"tags\":[\"muscle\", \"weight\", \"repetitions\"]}";


    }
}
