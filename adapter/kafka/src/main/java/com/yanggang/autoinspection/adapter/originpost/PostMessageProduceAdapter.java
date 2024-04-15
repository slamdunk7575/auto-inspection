package com.yanggang.autoinspection.adapter.originpost;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanggang.autoinspection.CustomObjectMapper;
import com.yanggang.autoinspection.PostMessageProducePort;
import com.yanggang.autoinspection.adapter.common.OperationType;
import com.yanggang.autoinspection.adapter.exception.KafkaProduceException;
import com.yanggang.autoinspection.content.model.Post;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.yanggang.autoinspection.adapter.common.Topic.ORIGINAL_POST_TOPIC;

@Component
public class PostMessageProduceAdapter implements PostMessageProducePort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CustomObjectMapper objectMapper;

    public PostMessageProduceAdapter(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new CustomObjectMapper();
    }

    @Override
    public void sendCreateMessage(Post post) {
        PostMessage message = convertToMessage(post.getId(), post, OperationType.CREATE);
        sendMessage(message);
    }

    @Override
    public void sendUpdateMessage(Post post) {
        PostMessage message = convertToMessage(post.getId(), post, OperationType.UPDATE);
        sendMessage(message);
    }

    @Override
    public void sendDeleteMessage(Long postId) {
        PostMessage message = convertToMessage(postId, null, OperationType.DELETE);
        sendMessage(message);
    }

    private PostMessage convertToMessage(Long id, Post post, OperationType operationType) {
        return new PostMessage(
                id,
                post == null ? null : PostMessage.Payload.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .userId(post.getUserId())
                        .categoryId(post.getCategoryId())
                        .createdDate(post.getCreatedDate())
                        .updatedDate(post.getUpdatedDate())
                        .deletedDate(post.getDeletedDate())
                        .build(),
                operationType
        );
    }

    private void sendMessage(PostMessage message) {
        try {
            kafkaTemplate.send(ORIGINAL_POST_TOPIC, message.getId().toString(), objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new KafkaProduceException(String.format("Kafka Produce 예외가 발생했습니다. message: %{s}", message), e);
        }
    }
}
