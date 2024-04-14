package com.yanggang.autoinspection.content.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 원본 데이터 관리용
@NoArgsConstructor
@Getter
public class Post {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long categoryId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deletedDate;

    @Builder
    public Post(Long id, String title, String content, Long userId, Long categoryId,
                LocalDateTime createdDate, LocalDateTime updatedDate, LocalDateTime deletedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.categoryId = categoryId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.deletedDate = deletedDate;
    }

    public Post update(String title, String content, Long categoryId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.updatedDate = LocalDateTime.now();
        return this;
    }

    public Post delete() {
        LocalDateTime now = LocalDateTime.now();
        this.deletedDate = now;
        this.updatedDate = now;
        return this;
    }

    public Post undelete() {
        this.deletedDate = null;
        return this;
    }

    public static Post of(
            String title,
            String content,
            Long userId,
            Long categoryId) {
        LocalDateTime now = LocalDateTime.now();
        return Post.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .categoryId(categoryId)
                .createdDate(now)
                .updatedDate(now)
                .build();
    }
}
