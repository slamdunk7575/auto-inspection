package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;

public interface PostMessageProducePort {

    void sendCreateMessage(Post post);

    void sendUpdateMessage(Post post);

    void sendDeleteMessage(Long postId);
}
