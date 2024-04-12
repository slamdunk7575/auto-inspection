package com.yanggang.autoinspection.model;

import com.yanggang.autoinspection.PostUpdateUsecase;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {

    private String title;
    private String content;
    private Long categoryId;

    public PostUpdateUsecase.Request toRequest(Long postId) {
        return PostUpdateUsecase.Request.builder()
                .postId(postId)
                .title(this.title)
                .content(this.content)
                .categoryId(this.categoryId)
                .build();
    }
}
