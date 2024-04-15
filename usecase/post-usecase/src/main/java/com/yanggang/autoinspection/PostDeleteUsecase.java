package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.Post;
import lombok.Data;

public interface PostDeleteUsecase {

    Post delete(Request request);

    @Data
    class Request {
        private final Long postId;
    }
}
