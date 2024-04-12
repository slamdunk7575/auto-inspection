package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.model.Post;
import lombok.*;

public interface PostCreateUsecase {

    Post create(Request request);

    @Getter

    class Request {
        private final String title;
        private final String content;
        private final Long userId;
        private final Long categoryId;

        @Builder
        public Request(String title, String content, Long userId, Long categoryId) {
            this.title = title;
            this.content = content;
            this.userId = userId;
            this.categoryId = categoryId;
        }
    }
}
