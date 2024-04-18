package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.Post;

public interface SubscribingPostAddToInboxUsecase {
    void saveSubscribingInboxPost(Post post);
}
