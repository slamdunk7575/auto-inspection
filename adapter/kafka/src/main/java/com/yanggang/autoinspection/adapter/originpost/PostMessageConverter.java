package com.yanggang.autoinspection.adapter.originpost;

import com.yanggang.autoinspection.content.post.model.Post;

public class PostMessageConverter {

    private PostMessageConverter() {
    }

    public static Post toModel(PostMessage postMessage) {
        return Post.builder()
                .id(postMessage.getId())
                .title(postMessage.getPayload().getTitle())
                .content(postMessage.getPayload().getContent())
                .userId(postMessage.getPayload().getUserId())
                .categoryId(postMessage.getPayload().getCategoryId())
                .createdDate(postMessage.getPayload().getCreatedDate())
                .updatedDate(postMessage.getPayload().getUpdatedDate())
                .deletedDate(postMessage.getPayload().getDeletedDate())
                .build();
    }
}
