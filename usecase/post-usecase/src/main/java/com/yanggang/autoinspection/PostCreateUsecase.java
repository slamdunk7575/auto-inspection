package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;
import lombok.*;

public interface PostCreateUsecase {

    Post create(Request request);

    @Getter
    @AllArgsConstructor
    class Request {
        private final String title;
        private final String content;
        private final Long userId;
        private final Long categoryId;
    }
}
