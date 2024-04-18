package com.yanggang.autoinspection.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanggang.autoinspection.CustomObjectMapper;
import com.yanggang.autoinspection.SubscribingPostAddToInboxUsecase;
import com.yanggang.autoinspection.SubscribingPostRemoveFromInboxUsecase;
import com.yanggang.autoinspection.adapter.common.OperationType;
import com.yanggang.autoinspection.adapter.common.Topic;
import com.yanggang.autoinspection.adapter.exception.KafkaConsumerException;
import com.yanggang.autoinspection.adapter.inspectedpost.InspectedPostMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ContentSubscribingWorker {

    private CustomObjectMapper objectMapper = new CustomObjectMapper();
    private final SubscribingPostAddToInboxUsecase subscribingPostAddToInboxUsecase;
    private final SubscribingPostRemoveFromInboxUsecase subscribingPostRemoveFromInboxUsecase;

    public ContentSubscribingWorker(SubscribingPostAddToInboxUsecase subscribingPostAddToInboxUsecase,
                                    SubscribingPostRemoveFromInboxUsecase subscribingPostRemoveFromInboxUsecase) {
        this.subscribingPostAddToInboxUsecase = subscribingPostAddToInboxUsecase;
        this.subscribingPostRemoveFromInboxUsecase = subscribingPostRemoveFromInboxUsecase;
    }

    @KafkaListener(
            topics = { Topic.INSPECTED_POST_TOPIC },
            groupId = "subscribing-post-consumer",
            concurrency = "3"
    )
    public void listen(ConsumerRecord<String, String> message) {
        try {
            InspectedPostMessage inspectedPostMessage = objectMapper.readValue(message.value(), InspectedPostMessage.class);

            if (inspectedPostMessage.getOperationType().equals(OperationType.CREATE)) {
                handleCreateMessage(inspectedPostMessage);
            } else if (inspectedPostMessage.getOperationType().equals(OperationType.DELETE)) {
                handleDeleteMessage(inspectedPostMessage);
            }

        } catch (JsonProcessingException e) {
            throw new KafkaConsumerException(String.format("Kafka Consume 예외가 발생했습니다. message: %{s}", message), e);
        }
    }

    private void handleCreateMessage(InspectedPostMessage inspectedPostMessage) {
        subscribingPostAddToInboxUsecase.saveSubscribingInboxPost(inspectedPostMessage.getPayload().getPost());
    }

    private void handleDeleteMessage(InspectedPostMessage inspectedPostMessage) {
        subscribingPostRemoveFromInboxUsecase.removeSubscribingInboxPost(inspectedPostMessage.getId());
    }
}
