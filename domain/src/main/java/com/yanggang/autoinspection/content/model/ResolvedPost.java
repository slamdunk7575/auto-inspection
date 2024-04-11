package com.yanggang.autoinspection.content.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 서비스에서 사용 (Post + 메타정보)
@NoArgsConstructor
@Getter
public class ResolvedPost {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String userName;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private boolean isUpdated;

    @Builder
    public ResolvedPost(Long id, String title, String content, Long userId, String userName, Long categoryId,
                        String categoryName, LocalDateTime createdDate, LocalDateTime updatedDate, boolean isUpdated) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isUpdated = isUpdated;
    }

    public static ResolvedPost of(
            Post post,
            String userName,
            String categoryName
    ) {
        return ResolvedPost.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUserId())
                .userName(userName)
                .categoryId(post.getCategoryId())
                .categoryName(categoryName)
                .createdDate(post.getCreatedDate())
                .updatedDate(post.getUpdatedDate())
                .isUpdated(!post.getCreatedDate().equals(post.getUpdatedDate()))
                .build();
    }
}
