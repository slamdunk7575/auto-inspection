package com.yanggang.autoinspection.model;

import com.yanggang.autoinspection.content.model.ResolvedPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostDetailResponseDto {

    private Long id;
    private String title;
    private String content;
    private String userName;
    private String categoryName;
    private LocalDateTime createdDate;
    private boolean isUpdated;

    @Builder
    public PostDetailResponseDto(Long id, String title, String content, String userName,
                                 String categoryName, LocalDateTime createdDate, boolean isUpdated) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.categoryName = categoryName;
        this.createdDate = createdDate;
        this.isUpdated = isUpdated;
    }

    public static PostDetailResponseDto toDto(ResolvedPost resolvedPost) {
        return PostDetailResponseDto.builder()
                .id(resolvedPost.getId())
                .title(resolvedPost.getTitle())
                .content(resolvedPost.getContent())
                .userName(resolvedPost.getUserName())
                .categoryName(resolvedPost.getCategoryName())
                .createdDate(resolvedPost.getCreatedDate())
                .isUpdated(resolvedPost.isUpdated())
                .build();
    }
}
