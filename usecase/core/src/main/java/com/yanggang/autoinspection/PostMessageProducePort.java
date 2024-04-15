package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.Post;

public interface PostMessageProducePort {

    void sendCreateMessage(Post post);

    void sendUpdateMessage(Post post);

    void sendDeleteMessage(Long postId);
}
