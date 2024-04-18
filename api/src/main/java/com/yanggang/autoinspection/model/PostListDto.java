package com.yanggang.autoinspection.model;

import com.yanggang.autoinspection.content.post.model.ResolvedPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostListDto {
    private Long id;
    private String title;
    private String userName;
    private LocalDateTime createdDate;

    @Builder
    private PostListDto(Long id,
                       String title,
                       String userName,
                       LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.createdDate = createdDate;
    }

    public static PostListDto toDto(ResolvedPost resolvedPost) {
        return PostListDto.builder()
                .id(resolvedPost.getId())
                .title(resolvedPost.getTitle())
                .userName(resolvedPost.getUserName())
                .createdDate(resolvedPost.getCreatedDate())
                .build();
    }
}
