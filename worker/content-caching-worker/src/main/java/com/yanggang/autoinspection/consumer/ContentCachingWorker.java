package com.yanggang.autoinspection.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanggang.autoinspection.CustomObjectMapper;
import com.yanggang.autoinspection.PostResolvingHelpUsecase;
import com.yanggang.autoinspection.adapter.common.OperationType;
import com.yanggang.autoinspection.adapter.common.Topic;
import com.yanggang.autoinspection.adapter.exception.KafkaConsumerException;
import com.yanggang.autoinspection.adapter.originpost.PostMessage;
import com.yanggang.autoinspection.adapter.originpost.PostMessageConverter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ContentCachingWorker {

    private final CustomObjectMapper customObjectMapper = new CustomObjectMapper();
    private final PostResolvingHelpUsecase postResolvingHelpUsecase;

    public ContentCachingWorker(PostResolvingHelpUsecase postResolvingHelpUsecase) {
        this.postResolvingHelpUsecase = postResolvingHelpUsecase;
    }

    @KafkaListener(
            topics = { Topic.ORIGINAL_POST_TOPIC },
            groupId = "cache-post-consumer",
            concurrency = "3"
    )
    public void listen(ConsumerRecord<String, String> message) {
        try {
            PostMessage postMessage = customObjectMapper.readValue(message.value(), PostMessage.class);

            if (postMessage.getOperationType().equals(OperationType.CREATE)) {
                handleCreateMessage(postMessage);
            } else if (postMessage.getOperationType().equals(OperationType.UPDATE)) {
                handleUpdateMessage(postMessage);
            } else if (postMessage.getOperationType().equals(OperationType.DELETE)) {
                handleDeleteMessage(postMessage);
            }

        } catch (JsonProcessingException e) {
            throw new KafkaConsumerException(String.format("Kafka Consume 예외가 발생했습니다. message: %{s}", message), e);
        }
    }

    private void handleCreateMessage(PostMessage postMessage) {
        postResolvingHelpUsecase.resolvePostAndSave(PostMessageConverter.toModel(postMessage));
    }

    private void handleUpdateMessage(PostMessage postMessage) {
        postResolvingHelpUsecase.resolvePostAndSave(PostMessageConverter.toModel(postMessage));
    }

    private void handleDeleteMessage(PostMessage postMessage) {
        postResolvingHelpUsecase.deleteResolvedPost(postMessage.getId());
    }
}
