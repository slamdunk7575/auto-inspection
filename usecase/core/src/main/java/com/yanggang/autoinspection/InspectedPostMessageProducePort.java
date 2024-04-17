package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.inspectedpost.model.InspectedPost;

public interface InspectedPostMessageProducePort {

    void sendCreateMessage(InspectedPost post);

    void sendUpdateMessage(InspectedPost post);

    void sendDeleteMessage(Long postId);
}
