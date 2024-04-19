package com.yanggang.autoinspection.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanggang.autoinspection.CustomObjectMapper;
import com.yanggang.autoinspection.PostIndexingUsecase;
import com.yanggang.autoinspection.adapter.common.OperationType;
import com.yanggang.autoinspection.adapter.common.Topic;
import com.yanggang.autoinspection.adapter.inspectedpost.InspectedPostMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PostIndexingWorker {

    private final CustomObjectMapper customObjectMapper = new CustomObjectMapper();

    private final PostIndexingUsecase postIndexingUsecase;

    public PostIndexingWorker(PostIndexingUsecase postIndexingUsecase) {
        this.postIndexingUsecase = postIndexingUsecase;
    }

    @KafkaListener(
            topics = { Topic.INSPECTED_POST_TOPIC },
            groupId = "post-indexing-consumer",
            concurrency = "3"
    )
    public void listen(ConsumerRecord<String, String> message) {
        try {
            InspectedPostMessage inspectedPostMessage = customObjectMapper.readValue(message.value(), InspectedPostMessage.class);

            if (inspectedPostMessage.getOperationType().equals(OperationType.CREATE)) {
                handleCreateMessage(inspectedPostMessage);
            } else if (inspectedPostMessage.getOperationType().equals(OperationType.UPDATE)) {
                handleUpdateMessage(inspectedPostMessage);
            } else if (inspectedPostMessage.getOperationType().equals(OperationType.DELETE)) {
                handleDeleteMessage(inspectedPostMessage);
            }


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCreateMessage(InspectedPostMessage inspectedPostMessage) {
        postIndexingUsecase.save(inspectedPostMessage.toModel());
    }

    private void handleUpdateMessage(InspectedPostMessage inspectedPostMessage) {
        postIndexingUsecase.save(inspectedPostMessage.toModel());
    }

    private void handleDeleteMessage(InspectedPostMessage inspectedPostMessage) {
        postIndexingUsecase.delete(inspectedPostMessage.getId());
    }
}
