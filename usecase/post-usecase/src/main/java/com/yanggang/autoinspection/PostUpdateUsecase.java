package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface PostUpdateUsecase {

    Post update(Request request);

    @Getter
    @AllArgsConstructor
    class Request {
        private final Long postId;
        private final String title;
        private final String content;
        private final Long categoryId;
    }
}
