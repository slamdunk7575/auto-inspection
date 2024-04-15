package com.yanggang.autoinspection;

import com.yanggang.autoinspection.content.post.model.Post;
import lombok.Builder;
import lombok.Getter;

public interface PostUpdateUsecase {

    Post update(Request request);

    @Getter
    class Request {
        private final Long postId;
        private final String title;
        private final String content;
        private final Long categoryId;

        @Builder
        public Request(Long postId,
                       String title,
                       String content,
                       Long categoryId) {
            this.postId = postId;
            this.title = title;
            this.content = content;
            this.categoryId = categoryId;
        }
    }
}
