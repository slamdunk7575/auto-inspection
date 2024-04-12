package com.yanggang.autoinspection.model;

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
    public PostListDto(Long id,
                       String title,
                       String userName,
                       LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.createdDate = createdDate;
    }
}
