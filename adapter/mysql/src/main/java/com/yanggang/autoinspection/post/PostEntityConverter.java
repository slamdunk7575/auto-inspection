package com.yanggang.autoinspection.post;

import com.yanggang.autoinspection.content.model.Post;

public class PostEntityConverter {

    public static PostEntity toEntity(Post post) {
        return PostEntity.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUserId())
                .categoryId(post.getCategoryId())
                .createdDate(post.getCreatedDate())
                .updatedDate(post.getUpdatedDate())
                .deletedDate(post.getDeletedDate())
                .build();
    }

    public static Post toPost(PostEntity postEntity) {
        return Post.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .userId(postEntity.getUserId())
                .categoryId(postEntity.getCategoryId())
                .createdDate(postEntity.getCreatedDate())
                .updatedDate(postEntity.getUpdatedDate())
                .deletedDate(postEntity.getDeletedDate())
                .build();
    }
}
