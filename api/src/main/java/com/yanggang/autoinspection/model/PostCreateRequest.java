package com.yanggang.autoinspection.model;

import com.yanggang.autoinspection.PostCreateUsecase;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {

    private String title;
    private String content;
    private Long userId;
    private Long categoryId;

    @Builder
    public PostCreateRequest(String title,
                             String content,
                             Long userId,
                             Long categoryId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public PostCreateUsecase.Request toRequest() {
        return PostCreateUsecase.Request.builder()
                .title(this.title)
                .content(this.content)
                .userId(this.userId)
                .categoryId(this.categoryId)
                .build();
    }
}
