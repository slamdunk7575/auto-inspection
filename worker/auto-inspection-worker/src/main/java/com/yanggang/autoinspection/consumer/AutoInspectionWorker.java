package com.yanggang.autoinspection.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanggang.autoinspection.CustomObjectMapper;
import com.yanggang.autoinspection.InspectedPostMessageProducePort;
import com.yanggang.autoinspection.PostInspectUsecase;
import com.yanggang.autoinspection.adapter.common.OperationType;
import com.yanggang.autoinspection.adapter.common.Topic;
import com.yanggang.autoinspection.adapter.originpost.PostMessage;
import com.yanggang.autoinspection.adapter.originpost.PostMessageConverter;
import com.yanggang.autoinspection.content.inspectedpost.model.InspectedPost;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AutoInspectionWorker {

    private final CustomObjectMapper customObjectMapper = new CustomObjectMapper();
    private final PostInspectUsecase postInspectUsecase;
    private final InspectedPostMessageProducePort inspectedPostMessageProducePort;

    public AutoInspectionWorker(PostInspectUsecase postInspectUsecase,
                                InspectedPostMessageProducePort inspectedPostMessageProducePort) {
        this.postInspectUsecase = postInspectUsecase;
        this.inspectedPostMessageProducePort = inspectedPostMessageProducePort;
    }

    @KafkaListener(
            topics = { Topic.ORIGINAL_POST_TOPIC },
            groupId = "auto-inspection-consumer",
            concurrency = "3"
    )
    public void listen(ConsumerRecord<String, String> message) throws JsonProcessingException {
        PostMessage postMessage = customObjectMapper.readValue(message.value(), PostMessage.class);

        if (postMessage.getOperationType().equals(OperationType.CREATE)) {
            handleCreateMessage(postMessage);
        } else if (postMessage.getOperationType().equals(OperationType.UPDATE)) {
            handleUpdateMessage(postMessage);
        } else if (postMessage.getOperationType().equals(OperationType.DELETE)) {
            handleDeleteMessage(postMessage);
        }
    }

    public void handleCreateMessage(PostMessage postMessage) {
        InspectedPost inspectedPost = postInspectUsecase.inspectAndGetIfValid(PostMessageConverter.toModel(postMessage));

        if (inspectedPost == null) {
            return;
        }

        inspectedPostMessageProducePort.sendCreateMessage(inspectedPost);
    }

    public void handleUpdateMessage(PostMessage postMessage) {
        InspectedPost inspectedPost = postInspectUsecase.inspectAndGetIfValid(PostMessageConverter.toModel(postMessage));

        if (inspectedPost == null) {
          handleDeleteMessage(postMessage);
          return;
        }

        inspectedPostMessageProducePort.sendUpdateMessage(inspectedPost);
    }

    public void handleDeleteMessage(PostMessage postMessage) {
        inspectedPostMessageProducePort.sendDeleteMessage(postMessage.getId());
    }
}
